package com.crayons_2_0.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.crayons_2_0.authentication.UserManager;
import com.crayons_2_0.model.CrayonsUser;
import com.crayons_2_0.view.login.LoginScreen;
import com.crayons_2_0.view.login.RegisterView;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
@SpringComponent
public class RegisterFormListener implements Button.ClickListener {

	/**
	 * 
	 */
	@Autowired
	private UserManager userManager;

	@Override
	public void buttonClick(Button.ClickEvent event) {
		try {
			Button source = event.getButton();
			RegisterView parent = (RegisterView) source.getParent();
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
			userManager.foo(user);
			UI.getCurrent().getNavigator().navigateTo(LoginScreen.VIEW_NAME);
		} catch (Exception e) {
			Notification.show("Registration failed: " + e.getMessage());
		}

	}

}
