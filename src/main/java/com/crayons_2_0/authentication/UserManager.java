package com.crayons_2_0.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.crayons_2_0.model.CrayonsUser;
import com.crayons_2_0.service.database.UserService;


public class UserManager {
	
	@Autowired
    private UserService userService = new UserService();
	
	
	public void foo(CrayonsUser user) {
		userService.insertUser(user);
	}

}
