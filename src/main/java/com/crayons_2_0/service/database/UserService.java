package com.crayons_2_0.service.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;

import com.crayons_2_0.service.database.UserDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ondrej Kvasnovsky
 */
public class UserService implements UserDetailsService {

	@Autowired
    private UserDAO userDAO;
	
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        // fetch user from e.g. DB
        
        if ("client".equals(username)) {
            authorities.add(new SimpleGrantedAuthority("CLIENT"));
            User user = new User(username, "pass", true, true, false, false, authorities);
            return user;
        }
        if ("admin".equals(username)) {
            authorities.add(new SimpleGrantedAuthority("ADMIN"));
            User user = new User(username, "pass", true, true, false, false, authorities);
            return user;
        } else {
            return null;
        }
        
        
        // Part 2 mit DATENBANK
        			/*
        List<User> users = findAll();
        for (User tmpUser : users) {
        	if (tmpUser.equals(username)) {
        		return tmpUser;
        	}
        }
        throw new UsernameNotFoundException(User " + username + "doesn't exists!");
        */
    }
    
    public List<User> findAll() {
	    List<User> res = userDAO.findAll();
	    return res;
	}
    
    public boolean insertUser(User user) {
        return true;
    }
    
    public boolean removeUser(User user) {
        return true;
    }
    
    public User findByUserId(long userId) {
        return null;
    }
    
    public User findByEMail(String eMail) {
        return null;
    }
    
    public List<User> findByName(String firstName, String lastName) {
        return null;
    }
}
