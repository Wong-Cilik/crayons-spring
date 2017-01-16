package com.crayons_2_0.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.crayons_2_0.service.Language;

public class CrayonsUser extends User {

	private String firstName;
	
	private String lastName;
	
	private Language language;
	
	private String eMail;
	
	public CrayonsUser(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		
	}

	/**
	 * Username of super is Email
	 * 
	 * @return the eMail
	 */
	public String geteMail() {
		return super.getUsername();
	}

	/**
	 * @param eMail the eMail to set
	 */
	public void seteMail(String eMail) {
		//TODO
		//super.setUsername(eMail);
	}

}
