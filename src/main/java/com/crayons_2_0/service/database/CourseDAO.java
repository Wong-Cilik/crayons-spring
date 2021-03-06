package com.crayons_2_0.service.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.crayons_2_0.model.Course;
import com.crayons_2_0.model.CrayonsUser;
import com.vaadin.spring.annotation.SpringComponent;

/**
 * Class for Course Data Access Object
 *
 */
@SpringComponent
public class CourseDAO implements CommandLineRunner {

	private @Autowired JdbcTemplate jdbcTemplate;

	private @Autowired UserService userService;

	// public void createDbTable() {
	// log.info("@@ Creating Course table");
	// jdbcTemplate.execute("create table if not exists courses (title varchar(100), description varchar(100), author varchar(100), students varchar(1000))");
	// log.info("@@ Done");
	// }

	/**
	 * Returns all Courses if DB
	 * 
	 * @return all courses of DB
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Course> findAll() {
		String query = "select * from courses";
		RowMapper mapper = new RowMapper<Object>() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

				String title = rs.getString("title");
				String description = rs.getString("description");
				String students = rs.getString("students");
				// String authorMail = rs.getString("author");
				CrayonsUser author = userService.findByEMail(rs
						.getString("author"));
				String[] studentsArray = students.split("/");
				Course course = new Course(title, description, author, students);
				List<CrayonsUser> users = new ArrayList<>();
				for (int i = 1; i < studentsArray.length; i++) {
					users.add(userService.findByEMail(studentsArray[i]));
				}
				course.setUsers(users);
				return course;
			}
		};
		return jdbcTemplate.query(query, mapper);

	}

	@SuppressWarnings("unchecked")
	public List<Course> searchAll(String input, String coll) {
		String query = "SELECT * FROM courses WHERE " + coll + " LIKE '%"
				+ input + "%'";
		@SuppressWarnings("rawtypes")
		RowMapper mapper = new RowMapper<Object>() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				String title = rs.getString("title");
				String description = rs.getString("description");
				String students = rs.getString("students");
				CrayonsUser author = userService.findByEMail(rs
						.getString("author"));
				String[] studentsArray = students.split("/");
				Course course = new Course(title, description, author, students);
				List<CrayonsUser> users = new ArrayList<>();
				for (int i = 1; i < studentsArray.length; i++) {
					users.add(userService.findByEMail(studentsArray[i]));
				}
				course.setUsers(users);
				return course;
			}
		};
		return jdbcTemplate.query(query, mapper);
	}

	/**
	 * insert a Course in DB
	 * 
	 * @param course
	 *            to insert in DB
	 */
	public void insert(Course course) {

		String title = course.getTitle();
		String description = course.getDescription();
		String author = course.getAuthor().getEmail();
		String students = "";
		jdbcTemplate
				.update("insert into courses (title, description, author, students, datetime) VALUES (?, ?, ?, ?,CURRENT_TIMESTAMP)",
						title, description, author, students);
	}

	/**
	 * Update a course in DB
	 * 
	 * @param course
	 *            to update in DB
	 */
	public void update(Course course, String oldTitle) {

		String title = course.getTitle();
		String description = course.getDescription();
		String author = course.getAuthor().getEmail();

		jdbcTemplate
				.update("UPDATE courses SET title=?, description=? WHERE title=? AND author=?",
						title, description, oldTitle, author);
	}

	/**
	 * Removes a Course from DB
	 * 
	 * @param course
	 *            to remove from DB
	 */
	public boolean remove(Course course) {
		String deleteStatementCourse = "DELETE FROM courses WHERE title=? AND author=?";
		String deleteStatementUnits = "DELETE FROM units WHERE coursetitle=?";
		int courseRemoved = jdbcTemplate.update(deleteStatementCourse, course.getTitle(),
				course.getAuthor().getEmail());
		jdbcTemplate.update(deleteStatementUnits, course.getTitle());
		return (courseRemoved != 0);
	}

	/**
	 * Updates students of a course
	 * 
	 * @param tmp
	 *            String of students
	 * @param course
	 *            to be updated
	 * @param authorEmail      
	 * @return true if the students were updated, otherwise false 
	 */
	public boolean updateStudentsWithAuthor(String tmp, String course,
			String authorEmail) {
		int rowsAffected = jdbcTemplate.update(
				"UPDATE courses SET students=? WHERE title=? AND author=?",
				tmp, course, authorEmail);
		return (rowsAffected == 1);
	}

	public void saveData(File file, String title) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		jdbcTemplate.update("UPDATE courses SET data=? WHERE title=?",
				new PreparedStatementSetter() {
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setBinaryStream(1, fis, (int) file.length());
						ps.setString(2, title);
					}
				});
		fis.close();
	}

	public void getData(String title) throws IOException {
		File file = new File(title + ".bin");
		FileOutputStream fos = new FileOutputStream(file);
		byte[] data = jdbcTemplate
				.queryForObject("SELECT data FROM courses WHERE title = ?",
						byte[].class, title);
		fos.write(data, 0, data.length);
		fos.flush();
		fos.close();
	}

	@Override
	public void run(String... arg0) throws Exception {
		// this.createDbTable();

	}
}
