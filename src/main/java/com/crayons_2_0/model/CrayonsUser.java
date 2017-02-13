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
	
	public CrayonsUser(String firstName, String lastName, String eMail, String password, String language, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(eMail, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.seteMail(eMail);
		this.setPassword(password);   
		this.setLanguage(language);
	}

	public String geteMail() {
		return this.eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}
	
	public void setLanguage(String language) {
		if(language != null){
		if (language.equals(Language.German.toString())) {
			this.setLanguage(Language.German);
		} else if (language.equals(Language.English.toString())) {
			this.setLanguage(Language.English);
		} else {
			throw new IllegalArgumentException("Language not known");
		}
		}
	}

	@Override
    public String toString() {
        return String.format(
                "User[username= '%s', password= '%s', firstname = '%s', 'lastname = '%s', 'language = '%s']",
                 getUsername(), getPassword(), getFirstName(), getLastName(), getLanguage().toString());
    }
}
