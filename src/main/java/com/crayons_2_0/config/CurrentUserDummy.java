package com.crayons_2_0.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import com.crayons_2_0.model.CrayonsUser;

public class CurrentUserDummy {
	
	static public CrayonsUser get() {
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		CrayonsUser user = new CrayonsUser("Levin", "Schickle", "levin@web.de", "Schwan", true, true, false, false, authorities);;
		
		return user;
	}

}
