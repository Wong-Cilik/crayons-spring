package com.crayons_2_0.service.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;

import com.crayons_2_0.model.CrayonsUser;
import com.crayons_2_0.service.database.UserDAO;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Ondrej Kvasnovsky
 */
public class UserService implements UserDetailsService {


    private UserDAO userDAO = new UserDAO();
	
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        // fetch user from e.g. DB
        
        /*
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
        */
        
        
        // Part 2 mit DATENBANK
        CrayonsUser user = findByEMail(username);
        return user;
        
        
    }
    
    public List<CrayonsUser> findAll() {
	    List<CrayonsUser> res = userDAO.findAll();
	    return res;
	}
    
    public boolean insertUser(CrayonsUser user) {
    	
    	/* 
    	// Check if Exists, return false if exists
    	List<CrayonsUser> users = findAll();
    	for (CrayonsUser tmpUser : users) {
        	if (tmpUser.geteMail().equals(user.geteMail())) {
        		return false;
        	}
        }
        */
    	
    	// User exists not -> Save
    	userDAO.updateUser(user);
    	return true;
    }
    
    public boolean removeUser(CrayonsUser user) {
        userDAO.deleteUser(user);
    	return true;
    }
    
    
    /*
    public CrayonsUser findByUserId(long userId) {
        return null;
    }
    */
    
    
    /*
    public CrayonsUser findByEMail(String eMail) {
        return null;
    }
    */
    
    public List<CrayonsUser> findByName(String firstName, String lastName) {
    	List<CrayonsUser> users = findAll();
        List<CrayonsUser> userWithName = new LinkedList<CrayonsUser>();
        
    	for (CrayonsUser tmpUser : users) {
        	if (tmpUser.getFirstName().equals(firstName) && tmpUser.getLastName().equals(lastName)) {
        		userWithName.add(tmpUser);
        	}
        }
    	
    	return userWithName;
    }
    
    public CrayonsUser findByEMail(String eMail) {
    	List<CrayonsUser> users = findAll();
        
    	for (CrayonsUser tmpUser : users) {
        	if (tmpUser.geteMail().equals(eMail)) {
        		return tmpUser;
        	}
        }
        throw new UsernameNotFoundException("User with mail: " + eMail + " doesnt exists!");
    }
}
