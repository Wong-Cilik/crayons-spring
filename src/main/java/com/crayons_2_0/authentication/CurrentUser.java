package com.crayons_2_0.authentication;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.crayons_2_0.model.CrayonsUser;
import com.crayons_2_0.service.Language;
import com.crayons_2_0.service.database.UserService;

@Component
public class CurrentUser {
	// LINKS:
	// http://www.baeldung.com/get-user-in-spring-security
	
	@Autowired
	UserService userService;
	
	
	public CrayonsUser get2() {
		
		@SuppressWarnings("unused")
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String mail = user.getUsername();
		return userService.findByEMail(mail);
		
	}
	
	public CrayonsUser get() {
		createDummy();
		String mail = "dani@web.de";
		return userService.findByEMail(mail);
	}

	private void createDummy() {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		CrayonsUser dummy = new CrayonsUser("Daniela", "Katzenberger", "dani@web.de", "blond", Language.German.toString(), true, true, false, false, authorities);
		
		if (userService.findByEMail("dani@web.de") == null) {
			userService.insertUser(dummy);
		}
		
	}

}
