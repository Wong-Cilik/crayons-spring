package com.crayons_2_0.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.crayons_2_0.service.database.UserService;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;

@Component
public class RegisterFormListener implements Button.ClickListener {

	@Autowired 
	UserService userService;
	
	@Override
	public void buttonClick(Button.ClickEvent event) {
		 Button source = event.getButton();
		 
		 
		
		
	}

}
