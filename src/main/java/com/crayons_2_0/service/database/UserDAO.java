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

import java.lang.Object;
//import org.springframework.security.core.userdetails.User;

public class UserDAO {
	
	@Autowired
    JdbcTemplate jdbcTemplate;

    public void createDbTable() {
    	// FALSCH!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        jdbcTemplate.execute("create table if not exists users (eMail varchar(100), password varchar(100))");
    }

    public List<User> findAll() {
        String query = "select * from users";
        RowMapper mapper = new RowMapper() {

            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                
            	List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
                String mail = rs.getString("eMail");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                authorities.add(new SimpleGrantedAuthority("CLIENT"));
                User user = new User(firstName + " " + lastName, "pass", true, true, false, false, authorities);;
                return user;
            }
        };
        return jdbcTemplate.query(query, mapper);
    }

    public boolean insertUser(User user) {
        String query = "insert into users (label) values (?)";
        jdbcTemplate.update(query, new Object[]{user.getUsername()});
        return true;
    }
    
    public boolean removeUser(User user) {
        return true;
    }
    
    public User findUserById(long userId) {
        return null;
    }
    
    public User findByEMail(String eMail) {
        return null;
    }
    
    public List<User> findByName(String firstName, String lastName) {
        return null;
    }
}
