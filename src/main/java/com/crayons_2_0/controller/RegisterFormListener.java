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

import com.crayons_2_0.authentication.UserManager;
import com.crayons_2_0.model.CrayonsUser;
import com.crayons_2_0.service.database.UserService;
import com.crayons_2_0.view.login.LoginForm;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

@Component
public class RegisterFormListener implements Button.ClickListener {

    @Autowired
	private UserService userService;
	
	@Override
	public void buttonClick(Button.ClickEvent event) {
		try {
            Button source = event.getButton();
            VerticalLayout parent = (VerticalLayout) source.getParent();
            
            TextField txtMail = (TextField) parent.getComponent(1);
            String mail = txtMail.getValue();
            
            TextField txtPassword = (TextField) parent.getComponent(4);
            String password = txtPassword.getValue();

            String firstName = "firstName";
	        String lastName = "lastName";
	        
	        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
	        authorities.add(new SimpleGrantedAuthority("CLIENT"));
	        CrayonsUser user = new CrayonsUser(firstName, lastName, mail, password, true, true, false, false, authorities);
	        
	        
	        //userService.insertUser(user);
	        UserManager userManager = new UserManager();
	        userManager.foo(user);
	        
	        UI.getCurrent().close();

            

           

        } catch (Exception e) {
            Notification.show(e.getMessage());
        } 
		 
		 
		
		
	}

}
