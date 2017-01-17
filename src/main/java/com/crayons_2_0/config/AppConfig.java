package com.crayons_2_0.config;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.crayons_2_0.authentication.AuthManager;
import com.crayons_2_0.controller.LoginFormListener;
import com.crayons_2_0.service.database.UserService;
import com.crayons_2_0.service.database.JDBCConnection;
import com.crayons_2_0.service.database.UserDAO;

/**
 * @author Ondrej Kvasnovsky
 */
@Configuration
//@ComponentScan(basePackages = {"com.app.ui" , "com.app.auth", "com.app.service"})
public class AppConfig {

    @Bean
    public AuthManager authManager() {
        AuthManager res = new AuthManager();
        return res;
    }

    @Bean
    public UserService userService() {
        UserService res = new UserService();
        return res;
    }

    @Bean
    public LoginFormListener loginFormListener() {
        return new LoginFormListener();
    }
    
    @Autowired
    private DriverManagerDataSource dataSource;

    @Bean
    public DriverManagerDataSource driverManagerDataSource() {
        /*
    	String driverClassName = "org.h2.Driver";						//  ANPASSEN !!!!!!!!!!!!!!!!!!!!!!!!!!
        String url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
        String username = "sa";
        String password = "";
        */
    	String driverClassName = "org.postgresql.Driver";				// Siehe:  https://jdbc.postgresql.org/documentation/84/load.html
        String url = "jdbc:postgresql://localhost:2323/dbCrayons";
        String username = "postgres";
        String password = "Schwan";
    	
        DriverManagerDataSource res = new DriverManagerDataSource();
        res.setDriverClassName(driverClassName);
        res.setUrl(url);
        res.setUsername(username);
        res.setPassword(password);
        
        return res;
        
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public UserDAO userDAO() {
        return new UserDAO ();
    }
    
    
}