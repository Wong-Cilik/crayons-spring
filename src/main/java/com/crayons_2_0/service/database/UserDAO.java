package com.crayons_2_0.service.database;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.crayons_2_0.model.CrayonsUser;

import java.lang.Object;
//import org.springframework.security.core.userdetails.User;


// LINKS:
// http://docs.spring.io/spring/docs/2.0.x/reference/jdbc.html


public class UserDAO {
	
    /**
     *  for Console logging
     */
    private static final Logger log = LoggerFactory.getLogger(UserDAO.class);
	@Autowired
    JdbcTemplate jdbcTemplate;

    public void createDbTable() {
    	// Nicht Vollständig!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        jdbcTemplate.execute("create table if not exists users (eMail varchar(100), password varchar(100), firstName varchar(100), lastName varchar(100)");
    }

//    public List<CrayonsUser> findAll() {
//        String query = "select * from realm.users";
//        RowMapper mapper = new RowMapper() {
//
//            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
//                
//            	List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
//                String mail = rs.getString("email");
//                String password = rs.getString("password");
//                String firstName = rs.getString("firstname");
//                String lastName = rs.getString("lastname");
////                String language = rs.getString("language");
//                authorities.add(new SimpleGrantedAuthority("CLIENT"));
//                CrayonsUser user = new CrayonsUser(firstName, lastName, mail, password, true, true, false, false, authorities);;
//                return user;
//            }
//        };
//        return jdbcTemplate.query(query, mapper);
//    }
    
    public List<CrayonsUser> findAll() {
        log.info("@@ Querying");
        List<CrayonsUser> entries = new ArrayList<CrayonsUser>();
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        jdbcTemplate
                .query("SELECT * FROM realm.users",
                        new Object[] {}, (rs, row) -> new CrayonsUser(rs.getString("email"),
                                rs.getString("password"), rs.getString("firstname"), rs.getString("lastname"),true,true,false,false, authorities))
                .forEach(entry -> entries.add(entry));
        log.info("> Done.");
        return entries;

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
        
        //jdbcTemplate.update("update realm.users set password = " + password + " where email = " + mail);
        // Returns numer of changed rows
        jdbcTemplate.update("UPDATE realm.users SET password=?, firstname=?, lastname=? WHERE email=? ", password, firstName, lastName, mail);

        
		
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
