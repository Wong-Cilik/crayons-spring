package com.crayons_2_0.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import com.crayons_2_0.model.CrayonsUser;
import com.crayons_2_0.service.Language;

public class CurrentUserDummy {
	
	static public CrayonsUser get() {
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		CrayonsUser user = new CrayonsUser("Levin", "Schickle", "levin@web.de", "Schwan", Language.German.toString(),true, true, false, false, authorities);;
		
		return user;
	}

}
