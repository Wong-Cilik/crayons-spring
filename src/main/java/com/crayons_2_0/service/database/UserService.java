package com.crayons_2_0.service.database;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;

public class UserService {
	
	@Autowired
	UserDAO userDAO;
	
	 public List<User> findAll() {
	        List<User> res = userDAO.findAll();
	        return res;
	    }

}
