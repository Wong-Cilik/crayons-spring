package com.crayons_2_0.service.database;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
public class CourseDAO {


	@Autowired
    JdbcTemplate jdbcTemplate;
	
	@Autowired
	UserService userService;

    public void createDbTable() {
        jdbcTemplate.execute("create table if not exists courses (title varchar(100), description varchar(100), author varchar(100), students varchar(1000))");
    }

    
    /**
     * Returns all Courses if DB
     * @return all courses of DB
     */
    public List<Course> findAll() {
        String query = "select * from courses";
        RowMapper mapper = new RowMapper() {

            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                
            	String title = rs.getString("title");
                String description = rs.getString("description");
                String students = rs.getString("students");
                //String authorMail = rs.getString("author");
                CrayonsUser author = userService.findByEMail(rs.getString("author"));
        		String[] studentsArray = students.split("/");
                Course course = new Course(title, description, author, students);
                List<CrayonsUser> users = new ArrayList<>();
                for (int i = 1; i < studentsArray.length; i++){
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
     * @param course to insert in DB
     */
    public void insert(Course course) {
        
    	String title = course.getTitle();
    	String description = course.getDescription();
    	String author = course.getAuthor().getEmail();
    	String students = "";
        jdbcTemplate.update("insert into courses (title, description, author, students) VALUES (?, ?, ?, ?)", title, description, author, students);
    }
    
    /**
     * Update a course in DB
     * @param course to update in DB
     */
    public void update(Course course) {
    	
    	String title = course.getTitle();
    	String description = course.getDescription();
    	String author = course.getAuthor().getEmail();
    	
        jdbcTemplate.update("UPDATE courses SET description=?, author=? WHERE title=? ", description, author, title);
    }

    
    /**
     * Removes a Course from DB
     * @param course to remove from DB
     */
    public void remove(Course course) {
    	String deleteStatement = "DELETE FROM courses WHERE title=?";
    	try {
			jdbcTemplate.update(deleteStatement, course.getTitle());
		} catch (RuntimeException e) {
			//throw new CourseTitleNotFoundException("Course with Title:" + course.getTitle() + "doesnt exists!");
			throw new UsernameNotFoundException("Course with Title:" + course.getTitle() + "doesnt exists!");
		}
	}

    /**
     * Updates an String of students of an Course
     * @param tmp String of Students of Course
     * @param course to Update 
     */
	public void updateStudents(String tmp, String course) {
		jdbcTemplate.update("UPDATE courses SET students=? WHERE title=? ",
				tmp, course);
	}


	public void saveData(File file, String title) throws IOException {
		jdbcTemplate.update("UPDATE courses SET data=? WHERE title=?",
				new PreparedStatementSetter() {
					public void setValues(PreparedStatement ps)
							throws SQLException {
						try {
							FileInputStream fis = new FileInputStream(file);
							ps.setBinaryStream(1, fis, (int) file.length());
							ps.setString(2, title);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				});
	}

	public void getData(String title) throws IOException {
		File file = new File(title + ".bin");
		FileOutputStream fos = new FileOutputStream (file);
		byte[] data  = jdbcTemplate.queryForObject("SELECT data FROM courses WHERE title = ?", byte[].class, title);
		fos.write(data, 0, data.length);
		fos.flush();
		fos.close();
		System.out.println(data);
	}
}
