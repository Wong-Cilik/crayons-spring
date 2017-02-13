package com.example.db;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.vaadin.spring.annotation.SpringComponent;


@SpringComponent
public class UserService implements UserDetailsService {

    @Autowired
    UserDAO userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
   
        CrayonsUser user = findUserByMail(username);
        return user;
    }

    public CrayonsUser findUserByMail(String username) {

        List<CrayonsUser> users = findAllUsers();

        for (CrayonsUser tmpUser : users) {
            if (tmpUser.getUsername().equals(username)) {
                return tmpUser;
            }
        }
        throw new UsernameNotFoundException("User with mail:" + username + "doesnt exists!");
    }
    
    
    
    public boolean insertUser(CrayonsUser user) {
        
        
        // Check if Exists, return false if exists
        List<CrayonsUser> users = findAllUsers();
        for (CrayonsUser tmpUser : users) {
            if (tmpUser.getUsername().equals(user.getUsername())) {
                //User exists, so -> update
                userDao.updateUser(user);
            }
        }
        
        
        // User doesn't exist -> insert
        userDao.insertUser(user);
        return true;
    }
    

    public List<CrayonsUser> findAllUsers() {
        List<CrayonsUser> res = userDao.findAll();
        return res;
    }
    
    public String testService (){
        return "fooooo";
    }
}