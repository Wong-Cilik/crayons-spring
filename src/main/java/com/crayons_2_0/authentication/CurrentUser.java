package com.crayons_2_0.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import com.crayons_2_0.model.CrayonsUser;
import com.crayons_2_0.service.database.UserService;
import com.vaadin.spring.annotation.SpringComponent;

@SpringComponent
public class CurrentUser {

	private @Autowired UserService userService;

	private static CurrentUser instance = null;
	private String eMail;
	private CrayonsUser user;

	private CurrentUser() {
	}

	public static CurrentUser getInstance() {
		if (instance == null) {
			instance = new CurrentUser();
		}
		return instance;
	}

	public CrayonsUser get() {

		if (getInstance().getUser() == null) {
			getInstance().setUser(
					userService.findByEMail(getInstance().geteMail()));
		}
		return getInstance().getUser();
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public CrayonsUser getUser() {
		return user;
	}

	public void setUser(CrayonsUser user) {
		this.user = user;
	}
}
