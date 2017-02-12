package com.example.view;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component; 

import com.example.MyUI;
import com.example.controller.RegisterFormListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@SpringView(name = RegisterView.VIEW_NAME)
@UIScope
public class RegisterView extends VerticalLayout implements View {
    /**
     * 
     */
    public static final String VIEW_NAME = "registerView";
    private static final long serialVersionUID = 1L;
    private TextField email = new TextField();
    private PasswordField password = new PasswordField();
    private TextField firstname = new TextField();
    private TextField lastname = new TextField();

    @PostConstruct
    void init() {
        registerViewBuilder();
        
    }
    private void registerViewBuilder() {
        getEmail().setRequired(true);
        getPassword().setRequired(true);
        getFirstname().setRequired(true);
        getLastname().setRequired(true);
        addComponent(new Label("firstname"));

        addComponent(getFirstname());

        addComponent(new Label("lastname"));

        addComponent(getLastname());

        addComponent(new Label("eMail:"));

        addComponent(getEmail());

        addComponent(new Label("password"));

        addComponent(getPassword());

        Button btnInsertUser = new Button("create a user");
        btnInsertUser.addClickListener(new RegisterFormListener());
        //
        // Trivial logic for closing the sub-window
        setMargin(true);
        setSpacing(true);
        addComponent(btnInsertUser);
    }

    public TextField getEmail() {
        return email;
    }

    public void setEmail(TextField email) {
        this.email = email;
    }

    public PasswordField getPassword() {
        return password;
    }

    public void setPassword(PasswordField password) {
        this.password = password;
    }

    public TextField getFirstname() {
        return firstname;
    }

    public void setFirstname(TextField firstname) {
        this.firstname = firstname;
    }

    public TextField getLastname() {
        return lastname;
    }

    public void setLastname(TextField lastname) {
        this.lastname = lastname;
    }

    @Override
    public void enter(ViewChangeEvent event) {
        // TODO Auto-generated method stub
        
    }
}
