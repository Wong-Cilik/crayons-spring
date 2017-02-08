package com.crayons_2_0.view.login;

import com.crayons_2_0.controller.RegisterFormListener2;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

class RegisterWindow extends Window {

    public RegisterWindow() {

        super("Create a new user"); // Set window caption
        setWidth(300.0f, Unit.PIXELS);
        setPositionX(800);
        // Some basic content for the window
        VerticalLayout content = new VerticalLayout();

        TextField email = new TextField();
        email.setRequired(true);
        TextField password = new TextField();
        password.setRequired(true);
        TextField firstname = new TextField();
        firstname.setRequired(true);
        TextField lastname = new TextField();
        lastname.setRequired(true);
        content.addComponent(new Label("first name:"));

        content.addComponent(firstname);

        content.addComponent(new Label("last name:"));

        content.addComponent(lastname);

        content.addComponent(new Label("eMail:"));

        content.addComponent(email);

        content.addComponent(new Label("password:"));

        content.addComponent(password);

        Button btnInsertUser = new Button("create a user");
        btnInsertUser.addClickListener(new RegisterFormListener2());
        //

        // Disable the close button
        setClosable(true);

        // Trivial logic for closing the sub-window
        content.setMargin(true);
        content.setSpacing(true);
        content.addComponent(btnInsertUser);
        setContent(content);

    }
}