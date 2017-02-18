package com.crayons_2_0.controller;

import java.util.ArrayList; 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.crayons_2_0.model.CrayonsUser;
import com.crayons_2_0.service.database.UserService;
import com.crayons_2_0.view.login.RegisterView;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;

@SuppressWarnings("serial")
@SpringComponent
@ViewScope
public class RegisterFormListener implements Button.ClickListener {

	/**
	 * 
	 */
    @Autowired
    UserService userService;
    @Autowired
    RegisterView parent;

	@Override
	public void buttonClick(Button.ClickEvent event) {
		try {
			Button source = event.getButton();
			parent =  (RegisterView) source.getParent();
			String mail = parent.getEmail().getValue();
			String password = parent.getPassword().getValue();
			String firstname = parent.getFirstname().getValue();
			String lastname = parent.getLastname().getValue();
			String language = (String) parent.getSelectLanguage().getValue();
			int permission = 0;

			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			authorities.add(new SimpleGrantedAuthority("CLIENT"));
			CrayonsUser user = new CrayonsUser(firstname, lastname, mail,
					password, language, permission, true, true, false, false,
					authorities);
			userService.insertUser(user);

		} catch (Exception e) {
			Notification.show("Registration failed: " + e.getMessage());
		}

	}

}
