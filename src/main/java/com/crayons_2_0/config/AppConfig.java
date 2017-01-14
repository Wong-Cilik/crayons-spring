package com.crayons_2_0.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.crayons_2_0.authentication.AuthManager;
import com.crayons_2_0.controller.LoginFormListener;
import com.crayons_2_0.service.UserService;

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
}