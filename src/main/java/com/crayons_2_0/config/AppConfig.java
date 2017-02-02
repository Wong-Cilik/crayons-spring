package com.crayons_2_0.config;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.crayons_2_0.authentication.AuthManager;
import com.crayons_2_0.authentication.CurrentUser;
import com.crayons_2_0.authentication.UserManager;
import com.crayons_2_0.controller.LoginFormListener;
import com.crayons_2_0.controller.RegisterFormListener;
import com.crayons_2_0.controller.RegisterFormListener2;
import com.crayons_2_0.controller.UnitEditor2;
import com.crayons_2_0.service.database.UserService;
import com.crayons_2_0.view.authorlib.AuthorlibraryForm;
import com.crayons_2_0.service.database.CourseDAO;
import com.crayons_2_0.service.database.CourseService;
import com.crayons_2_0.service.database.JDBCConnection;
import com.crayons_2_0.service.database.UnitDAO2;
import com.crayons_2_0.service.database.UnitService2;
import com.crayons_2_0.service.database.UserDAO;

/**
 * @author Ondrej Kvasnovsky
 */
@Configuration
//@Component
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
    
    // Damit Autowired funktioniert
    @Bean
    public UserManager userManager() {
        UserManager res = new UserManager();
        return res;
    }
    
    // Damit Autowired funktioniert
    @Bean
    public RegisterFormListener registerFormListener() {
        RegisterFormListener res = new RegisterFormListener();
        return res;
    }
    
    // Damit Autowired funktioniert
    @Bean
    public RegisterFormListener2 registerFormListener2() {
        RegisterFormListener2 res = new RegisterFormListener2();
        return res;
    }
    
    
    // Damit Autowired funktioniert
    @Bean
    public CurrentUser currentUser() {
        CurrentUser res = new CurrentUser();
        return res;
    }
    
    // Damit Autowired funktioniert
    /*
    @Bean
    public UnitEditor2 unitEditor() {
    	UnitEditor2 res = new UnitEditor2();
        return res;
    }
    */
    
    // Damit Autowired funktioniert
    
    /*
    @Bean
    public AuthorlibraryForm authorlibraryForm() {
    	AuthorlibraryForm res = new AuthorlibraryForm();
        return res;
    }
    */
    
    
    
    
    @Bean
    public UnitService2 unitService() {
        return new UnitService2 ();
    }
    
    @Bean
    public UnitDAO2 unitDAO2() {
        return new UnitDAO2 ();
    }
    
    @Bean
    public CourseService courseService() {
        return new CourseService ();
    }
    
    @Bean
    public CourseDAO courseDAO() {
        return new CourseDAO ();
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
    	
        ///*
        String driverClassName = "org.postgresql.Driver";				// Siehe:  https://jdbc.postgresql.org/documentation/84/load.html
        String url = "jdbc:postgresql://localhost:5432/dbcrayons";	// @Julius
        //String url = "jdbc:postgresql://localhost:2323/dbCrayons";		// @Levin
        String username = "postgres";
        String password = "Schwan";
        //*/

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