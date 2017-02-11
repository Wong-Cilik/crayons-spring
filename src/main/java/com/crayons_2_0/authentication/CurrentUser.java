package com.crayons_2_0.authentication;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import com.crayons_2_0.model.CrayonsUser;
import com.crayons_2_0.service.database.UserService;
import com.vaadin.spring.annotation.SpringComponent;

@SpringComponent
public class CurrentUser {
	// LINKS:
	// http://www.baeldung.com/get-user-in-spring-security
	
	
	@Autowired
	UserService userService;
	
	public CrayonsUser get() {
		SecurityContextHolder.setContext(AuthManager.getContext());
		String mail = SecurityContextHolder.getContext().getAuthentication().getName();
		CrayonsUser user = userService.findByEMail(mail);
		return user;
	}
}
