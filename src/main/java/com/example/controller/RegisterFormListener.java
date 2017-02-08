package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.example.db.CrayonsUser;
import com.example.db.UserService;
import com.example.view.RegisterView;
import com.example.view.RegisterWindow;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;

@Component
public class RegisterFormListener implements Button.ClickListener {
       
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    @Resource
	private UserService userService;
	    @Override
	    public void buttonClick(Button.ClickEvent event) {
	        try {
	            Button source = event.getButton();
	            RegisterView parent =  (RegisterView) source.getParent();
	            String mail = parent.getEmail().getValue();
	            String password = parent.getPassword().getValue();
	            String firstname = parent.getFirstname().getValue();
	            String lastname = parent.getLastname().getValue();
	            
	            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		        authorities.add(new SimpleGrantedAuthority("CLIENT"));
		        CrayonsUser user = new CrayonsUser(firstname ,lastname, mail, password, authorities);
	            userService.insertUser(user);
	            

	           

	        } catch (Exception e) {
	            Notification.show("Registration failed: " + e.getMessage());
	        } 

	    }



}
