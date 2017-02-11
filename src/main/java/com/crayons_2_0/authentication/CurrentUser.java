package com.crayons_2_0.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import com.crayons_2_0.model.CrayonsUser;
import com.crayons_2_0.service.database.UserService;
import com.vaadin.spring.annotation.SpringComponent;

@SpringComponent
public class CurrentUser {
	
	@Autowired
	UserService userService;
	
	public CrayonsUser get() {
		SecurityContextHolder.setContext(AuthManager.getContext());
		String mail = SecurityContextHolder.getContext().getAuthentication().getName();
		CrayonsUser user = userService.findByEMail(mail);
		return user;
	}
}
