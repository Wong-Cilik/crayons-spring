package com.crayons_2_0.service.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.crayons_2_0.model.Course;
@Component
public class CourseDAO {


	@Autowired
    JdbcTemplate jdbcTemplate;

    public void createDbTable() {
        jdbcTemplate.execute("create table if not exists courses (title varchar(100), description varchar(100), author varchar(100))");
    }

    public List<Course> findAll() {
        String query = "select * from realm.courses";
        RowMapper mapper = new RowMapper() {

            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                
            	String title = rs.getString("title");
                String description = rs.getString("description");
                String author = rs.getString("author");

                Course course = new Course(title, description, author);
                return course;
            }
        };
        return jdbcTemplate.query(query, mapper);
        
    }

    public void save(Course course) {
        String query = "insert into courses (name) values (?)";
        jdbcTemplate.update(query, new Object[]{course.getTitle()});
    }

	
}
