package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.example.Auth.AuthManager;
import com.example.view.Login;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;


@Component

public class LoginFormListener implements Button.ClickListener {
    
    @Autowired
    private AuthManager authManager;

    @Override
    public void buttonClick(Button.ClickEvent event) {
        try {
            Button source = event.getButton();
            Login parent =  (Login) source.getParent();
            String user = parent.getTxtLogin().getValue();
            String password = parent.getTxtPassword().getValue();

            UsernamePasswordAuthenticationToken request = new UsernamePasswordAuthenticationToken(user, password);
            Authentication result = authManager.authenticate(request);

            SecurityContextHolder.getContext().setAuthentication(result);
            

           

        } catch (AuthenticationException e) {
            Notification.show("Authentication failed: " + e.getMessage());
        } 

    }

    
    
    /*
    protected void showMainView() {
        setContent(new MainScreen(MyUI.get());
        getNavigator().navigateTo(getNavigator().getState());
    }
    */
}