package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.example.MyUI;
import com.example.Auth.AuthManager;
import com.example.view.Login;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.communication.PushMode;
import com.vaadin.shared.ui.ui.Transport;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;


@SpringComponent
@UIScope
public class LoginFormListener implements Button.ClickListener {
   
    @Autowired
    private AuthManager authManager;
    @Autowired
    private MyUI myUI;

    @Override
    public void buttonClick(Button.ClickEvent event) {
        try {
            Button source = event.getButton();
            Login parent =  (Login) source.getParent();
            String user = parent.getTxtLogin().getValue();
            String password = parent.getTxtPassword().getValue();

            UsernamePasswordAuthenticationToken request = new UsernamePasswordAuthenticationToken(user, password);
            // Reinitialize the session to protect against session fixation attacks. This does not work
            // with websocket communication.
            VaadinService.reinitializeSession(VaadinService.getCurrentRequest());
            Authentication result = authManager.authenticate(request);

            SecurityContextHolder.getContext().setAuthentication(result);
            
            // Now when the session is reinitialized, we can enable websocket communication. Or we could have just
            // used WEBSOCKET_XHR and skipped this step completely.
            myUI.getPushConfiguration().setTransport(Transport.WEBSOCKET);
            myUI.getPushConfiguration().setPushMode(PushMode.AUTOMATIC);
           

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