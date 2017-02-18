package com.crayons_2_0.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.crayons_2_0.authentication.AuthManager;
import com.crayons_2_0.authentication.CurrentUser;
import com.crayons_2_0.view.login.LoginForm;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;

@SuppressWarnings("serial")
@SpringComponent
@Component
public class LoginFormListener implements Button.ClickListener {

	/**
	 * 
	 */

	@Autowired
	private AuthManager authManager;

	@Override
	public void buttonClick(Button.ClickEvent event) {
		try {
			Button source = event.getButton();
			LoginForm parent = (LoginForm) source.getParent();
			String username = parent.getTxtLogin().getValue();
			String password = parent.getTxtPassword().getValue();

			CurrentUser.getInstance().seteMail(username);
			UsernamePasswordAuthenticationToken request = new UsernamePasswordAuthenticationToken(
					username, password);

			Authentication result = authManager.authenticate(request);
			SecurityContextHolder.getContext().setAuthentication(result);

		} catch (AuthenticationException e) {
			Notification.show("Authentication failed: " + e.getMessage());
		}
	}
}
