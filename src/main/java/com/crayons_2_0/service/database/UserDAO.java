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
                String mail = rs.getString("email");
                String password = rs.getString("password");
                String firstName = rs.getString("firstname");
                String lastName = rs.getString("lastname");
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
    
    // WICHTIG: realm nicht vergessen, attribute in der sql werden kleingemacht!!
    
    
    public void insertUser(CrayonsUser user) {
    	
    	String mail = user.geteMail();
        String password = user.getPassword();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        
        jdbcTemplate.update("insert into realm.users (email, password, firstname, lastname) VALUES (?, ?, ?, ?)", mail, password, firstName, lastName);
		
    }
    
    public void updateUser(CrayonsUser user) {
    	
    	String mail = user.geteMail();
        String password = user.getPassword();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        
        try {
        	jdbcTemplate.update("update realm.users set password = " + password + " where email = " + mail);
		} catch (Exception e) {
			String ex = e.getMessage();
			System.out.println(ex);
		}
        
		
    }
    
    
    // Example: http://alvinalexander.com/blog/post/jdbc/java-spring-jdbc-dao-delete-examples-recipes
    public void deleteUser(CrayonsUser user) {
    	String deleteStatement = "DELETE FROM realm.users WHERE email=?";
    	try {
			jdbcTemplate.update(deleteStatement, user.geteMail());
		} catch (RuntimeException e) {
			throw new UsernameNotFoundException("User with mail:" + user.geteMail() + "doesnt exists!");
		}
    	
    }
    
    
}
