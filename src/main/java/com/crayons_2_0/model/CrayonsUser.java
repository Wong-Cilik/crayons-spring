package com.crayons_2_0.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.crayons_2_0.service.Language;

public class CrayonsUser extends User {

	private String firstName;
	
	private String lastName;
	
	private String password;
	
	

	private Language language;
	
	private String eMail;
	
	public CrayonsUser(String firstName, String lastName, String eMail, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(firstName + " " + lastName, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.seteMail(eMail);
		this.setPassword(password);
	}

	/**
	 * Username of super is Email
	 * 
	 * @return the eMail
	 */
	public String geteMail() {
		//return super.getUsername();
		return this.eMail;
	}

	/**
	 * @param eMail the eMail to set
	 */
	public void seteMail(String eMail) {
		//TODO
		//super.setUsername(eMail);
		this.eMail = eMail;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
