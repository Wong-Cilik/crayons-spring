package com.example.view;

import org.springframework.stereotype.Component;

import com.example.MyUI;
import com.example.controller.RegisterFormListener;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@SpringUI
@Component
public class RegisterView extends VerticalLayout {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private TextField email = new TextField();
    private PasswordField password = new PasswordField();
    private TextField firstname = new TextField();
    private TextField lastname = new TextField();

    public RegisterView(MyUI ui) {

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
}
