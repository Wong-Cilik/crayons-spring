package com.example.view;

import org.springframework.context.ApplicationContext;  

import com.vaadin.ui.Button;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.example.*;
import com.example.controller.LoginFormListener;
import com.example.controller.RegisterFormListener;


public class Login extends VerticalLayout {

   
    private static final long serialVersionUID = 1L;
    private TextField username = new TextField("User:");
    private PasswordField password = new PasswordField("Password:");
    private Button loginButton = new Button("Login");
    private Button registerButton = new Button("Registrieren!!");

    public Login() {
        this.loginViewBuilder();
        this.loginViewBuilder();
        LoginFormListener loginFormListener = getLoginFormListener();
        loginButton.addClickListener(loginFormListener);
        
        

    }

    public void loginViewBuilder() {
       
        username.setWidth("300px");
        username.setRequired(true);
        username.setInputPrompt("Your username");

        password.setWidth("300px");
        password.setRequired(true);
        password.setValue("");
        password.setNullRepresentation("");

        

        
        addComponents(username,password,loginButton,registerButton);
     
        setMargin(true);
        setSpacing(true);
        
        RegisterFormListener registerFormListener = getRegisterFormListener();
        createRegisterButton();
        registerButton.addClickListener(registerFormListener);
    }

    private void createRegisterButton() {
        registerButton.addClickListener(new ClickListener() {
            
            @Override
            public void buttonClick(ClickEvent event) {
                
                MyUI.get().showRegisterView();
                
                
            }
            
            
        });
        
    }

    public LoginFormListener getLoginFormListener() {
        MyUI ui = (MyUI) UI.getCurrent();
        ApplicationContext context = ui.getApplicationContext();
        return context.getBean(LoginFormListener.class);
    }

    public TextField getTxtLogin() {
        return username;
    }

    public PasswordField getTxtPassword() {
        return password;
    }
    
    private RegisterFormListener getRegisterFormListener() {
        MyUI ui = (MyUI) UI.getCurrent();
        ApplicationContext context = ui.getApplicationContext();
        return context.getBean(RegisterFormListener.class);
    }

}
