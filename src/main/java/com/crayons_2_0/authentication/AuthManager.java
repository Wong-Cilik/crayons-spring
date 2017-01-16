package com.crayons_2_0.authentication;

import com.crayons_2_0.MyUI;
import com.crayons_2_0.service.database.UserService;
import com.vaadin.ui.UI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
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
    private static boolean hasAuthority = false;

    public Authentication authenticate(Authentication auth) throws AuthenticationException, UsernameNotFoundException {
        String username = (String) auth.getPrincipal();
        String password = (String) auth.getCredentials();
        
        UserDetails user = userService.loadUserByUsername(username);

        if (user != null && user.getPassword().equals(password)) {
            Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
            hasAuthority = true;
            MyUI.get().showMainView();
            return new UsernamePasswordAuthenticationToken(username, password, authorities);
        }
        throw new BadCredentialsException("Bad Credentials");
    }

    public static void setHasAuthority(boolean hasAuthority) {
        AuthManager.hasAuthority = hasAuthority;
    }

    public static boolean isHasAuthority() {
        return hasAuthority;
    }

    
    
}