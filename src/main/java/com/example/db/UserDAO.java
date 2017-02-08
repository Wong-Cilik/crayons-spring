package com.example.db;


import java.util.ArrayList; 
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;




/**
 * @author Windows VM
 *
 */
@Component
public class UserDAO implements CommandLineRunner {

    /**
     * Logger . A log instance that prints out what is going on in the method calls.
     */
    private static final Logger log = LoggerFactory.getLogger(UserDAO.class);
    @Autowired
    JdbcTemplate jdbcTemplate;

    private void insertUser() {
        log.info("@@ Creating tables");
        jdbcTemplate.execute("DROP TABLE IF EXISTS realm.users");
        jdbcTemplate.execute("CREATE TABLE realm.users(email VARCHAR(100) NOT NULL, password VARCHAR(100) NOT NULL, firstname VARCHAR(100)  NOT NULL,lastname VARCHAR(100)  NOT NULL)");
        jdbcTemplate.execute("insert into realm.users(email, password, firstname, lastname) values('userali','pass','ali', 'akil')");
//        jdbcTemplate.execute("insert into realm.users(email, password, firstname, lastname) values('user','pass','lala','kram')");
        log.info("@@ > Done.");
    }
    
  public void createTable() {
    jdbcTemplate.execute("DROP TABLE IF EXISTS realm.users");
    jdbcTemplate.execute("CREATE TABLE realm.users(email VARCHAR(100) NOT NULL, password VARCHAR(100) NOT NULL, firstname VARCHAR(100)  NOT NULL,lastname VARCHAR(100)  NOT NULL)");
      
  }
  public void insertUser(CrayonsUser user) {
        
        String mail = user.getUsername();
        String password = user.getPassword();
        String firstName = user.getFirstname();
        String lastName = user.getLastname();
        
        jdbcTemplate.update("insert into realm.users (email, password, firstname, lastname) VALUES (?, ?, ?, ?)", mail, password, firstName, lastName);
        
    }

  public void updateUser(CrayonsUser user) {
        
        String mail = user.getUsername();
        String password = user.getPassword();
        String firstName = user.getFirstname();
        String lastName = user.getLastname();
        
        //jdbcTemplate.update("update realm.users set password = " + password + " where email = " + mail);
        // Returns numer of changed rows
        jdbcTemplate.update("UPDATE realm.users SET password=?, firstname=?, lastname=? WHERE email=? ", password, firstName, lastName, mail);

        
        
    }
  
    /**
     * @return returns of the table <users> in the DB This method will use the
     *         jdbcTemplate instance and the query method (that accepts a SQL
     *         syntax) to get all the data; it will return a collection of
     *         Journal instances.
     * 
     */
    public List<CrayonsUser> findAll() {
        log.info("@@ Querying");
        List<CrayonsUser> entries = new ArrayList<>();
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        jdbcTemplate
                .query("SELECT * FROM realm.users",
                        new Object[] {}, (rs, row) -> new CrayonsUser(rs.getString("email"),
                                rs.getString("password"), rs.getString("firstname"), rs.getString("lastname"), authorities))
                .forEach(entry -> entries.add(entry));
        log.info("> Done.");
        return entries;

    }

    @Override
    public void run(String... arg0) throws Exception {

        log.info("@@ Inserting Data....");
        createTable();
        insertUser();
        log.info("@@ findAll() call...");
        findAll().forEach(entry -> log.info(entry.toString()));

    }

}