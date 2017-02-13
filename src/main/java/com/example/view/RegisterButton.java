package com.example.view;

import org.springframework.context.ApplicationContext;

import com.example.MyUI;
import com.example.controller.RegisterFormListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class RegisterButton extends VerticalLayout{

    private Button registerButton = new Button("Registrieren!!");
    
    public RegisterButton() {
        addComponent(registerButton);
        RegisterFormListener registerFormListener = getRegisterFormListener();
        createRegisterButton();
        registerButton.addClickListener(registerFormListener);
    }
    
    
    private void createRegisterButton() {
        registerButton.addClickListener(new ClickListener() {

            /**
             * 
             */
            private static final long serialVersionUID = 1L;

            @Override
            public void buttonClick(ClickEvent event) {

                MyUI.get().showRegisterView();

            }

        });

    }
    

    private RegisterFormListener getRegisterFormListener() {
        MyUI ui = (MyUI) UI.getCurrent();
        ApplicationContext context = ui.getApplicationContext();
        return context.getBean(RegisterFormListener.class);
    }
}
