package com.crayons_2_0.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.crayons_2_0.MyUI;
import com.crayons_2_0.authentification.AuthManager;
import com.crayons_2_0.view.MainScreen;
import com.crayons_2_0.view.login.LoginForm;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

/**
 * @author Ondrej Kvasnovsky
 */
@Component
public class LoginFormListener implements Button.ClickListener {

    @Autowired
    private AuthManager authManager;

    @Override
    public void buttonClick(Button.ClickEvent event) {
        try {
            Button source = event.getButton();
            LoginForm parent = (LoginForm) source.getParent();
            String username = parent.getTxtLogin().getValue();
            String password = parent.getTxtPassword().getValue();

            UsernamePasswordAuthenticationToken request = new UsernamePasswordAuthenticationToken(username, password);

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