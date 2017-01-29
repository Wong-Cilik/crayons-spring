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
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.crayons_2_0.model.CrayonsUser;

import java.lang.Object;
//import org.springframework.security.core.userdetails.User;

public class UserDAO {
	
	@Autowired
    JdbcTemplate jdbcTemplate;

    public void createDbTable() {
    	// FALSCH!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        jdbcTemplate.execute("create table if not exists users (eMail varchar(100), password varchar(100), firstName varchar(100), lastName varchar(100)");
    }

    public List<CrayonsUser> findAll() {
        String query = "select * from realm.users";
        RowMapper mapper = new RowMapper() {

            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                
            	List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
                String mail = rs.getString("eMail");
                String password = rs.getString("password");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                authorities.add(new SimpleGrantedAuthority("CLIENT"));
                CrayonsUser user = new CrayonsUser(firstName, lastName, mail, password, true, true, false, false, authorities);;
                return user;
            }
        };
        return jdbcTemplate.query(query, mapper);
    }
    
    /*
    // FALSCH
    public void save(CrayonsUser user) {
        String query = "insert into users (label) values (?)";
        jdbcTemplate.update(query, new Object[]{user.getUsername()});
    }
    */
    
    public void save2(CrayonsUser user2) {
    	
    	String mail = user2.geteMail();
        String password = user2.getPassword();
        String firstName = user2.getFirstName();
        String lastName = user2.getLastName();
        
        jdbcTemplate.update("Insert into users (eMail, password, firstName, lastName) VALUES(?, ?, ?, ?)", mail, password, firstName, lastName);
    }
    
    
    // Example: http://alvinalexander.com/blog/post/jdbc/java-spring-jdbc-dao-delete-examples-recipes
    public void delete(CrayonsUser user) {
    	String deleteStatement = "DELETE FROM users WHERE eMAIL=?";
    	try {
			jdbcTemplate.update(deleteStatement, user.geteMail());
		} catch (RuntimeException e) {
			throw new UsernameNotFoundException("User with mail:" + user.geteMail() + "doesnt exists!");
		}
    	
    }
    
    
}
