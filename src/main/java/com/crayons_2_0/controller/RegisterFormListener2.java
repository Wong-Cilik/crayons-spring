package com.crayons_2_0.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.crayons_2_0.authentication.AuthManager;
import com.crayons_2_0.authentication.UserManager;
import com.crayons_2_0.model.CrayonsUser;
import com.crayons_2_0.view.login.LoginForm;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;

@Component
public class RegisterFormListener2 implements Button.ClickListener {
	
	    private UserManager userManager = new UserManager();

	    @Override
	    public void buttonClick(Button.ClickEvent event) {
	        try {
	            Button source = event.getButton();
	            LoginForm parent = (LoginForm) source.getParent();
	            String mail = parent.getTxtLogin().getValue();
	            String password = parent.getTxtPassword().getValue();
	            
	            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		        authorities.add(new SimpleGrantedAuthority("CLIENT"));
		        CrayonsUser user = new CrayonsUser("first", "last", mail, password, true, true, false, false, authorities);

	            userManager.foo(user);
	            

	           

	        } catch (Exception e) {
	            Notification.show("Registration failed: " + e.getMessage());
	        } 

	    }


}
