package com.example.Auth;

import com.example.MyUI; 
import com.example.db.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author Ondrej Kvasnovsky
 */
@Component
public class AuthManager implements AuthenticationManager {

    @Autowired
    private UserService userService;

    public Authentication authenticate(Authentication auth) throws AuthenticationException, UsernameNotFoundException {
        String username = (String) auth.getPrincipal();
        String password = (String) auth.getCredentials();
        
        UserDetails user = userService.loadUserByUsername(username);
        

        if (user != null && user.getPassword().equals(password)) {
            Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
          
            MyUI.get().showMainView();
            return new UsernamePasswordAuthenticationToken(username, password, authorities);
        }
        throw new BadCredentialsException("Bad Credentials");
    }

  

    
    
}