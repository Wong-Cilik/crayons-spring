package com.crayons_2_0.service.database;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.crayons_2_0.model.CrayonsUser;
import com.crayons_2_0.service.Language;
import com.crayons_2_0.service.LanguageService;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Notification;
//import org.springframework.security.core.authority.GrantedAuthorityImpl;
//import org.springframework.security.core.authority.GrantedAuthorityImpl;

/**
 * Class for Service on users of DB
 */
@SpringComponent
public class UserService implements UserDetailsService {

	@Autowired
	private UserDAO userDAO;

	private ResourceBundle lang = LanguageService.getInstance().getRes();
	
	/**
	 * Returns an User by his Username (=eMail)
	 */
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		new ArrayList<GrantedAuthority>();

		/*
		 * if ("client".equals(username)) { authorities.add(new
		 * SimpleGrantedAuthority("CLIENT")); User user = new User(username,
		 * "pass", true, true, false, false, authorities); return user; } if
		 * ("admin".equals(username)) { authorities.add(new
		 * SimpleGrantedAuthority("ADMIN")); User user = new User(username,
		 * "pass", true, true, false, false, authorities); return user; } else {
		 * return null; }
		 */

		// Part 2 mit DATENBANK
		CrayonsUser user = findByEMail(username);
		return user;

	}

	/**
	 * Returns a List of all CrayonsUser of DB
	 * 
	 * @return List of all CrayonsUser of DB
	 */
	public List<CrayonsUser> findAll() {
		return userDAO.findAll();
	}

	/**
	 * Insert / update an User Insert if not exists, update if exists
	 * 
	 * @param user
	 *            to insert/update
	 * @return true if successfull, false otherwise
	 */
	public boolean insertUser(CrayonsUser user) {
		boolean userExists = false;
		// Check if Exists, return false if exists
		List<CrayonsUser> users = findAll();
		for (CrayonsUser tmpUser : users) {
			if (tmpUser.getEmail().equals(user.getEmail())) {
				Notification.show(lang.getString("EmailAlreadyExists"),
						Notification.Type.WARNING_MESSAGE);
				return userExists = true;
			}

		}

		if (userExists == false) {
			userDAO.insertUser(user);
		}
		// User doesn't exist -> insert

		return userExists;
	}

	/**
	 * updates an User
	 * 
	 * @param user
	 *            to update
	 * @param eMail
	 *            new eMail
	 * @param firstName
	 *            newFirst Name
	 * @param lastName
	 *            new LastName
	 * @param password
	 * 				new password
	 * @return true if successfull
	 */
	public boolean updateUser(CrayonsUser user, String eMail, String firstName,
			String lastName, String password) {
		// Check if eMail is valid
		String regex = "[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@([_A-Za-z0-9-]+\\.)+[A-Za-z]{2,6}";
		Pattern pattern = Pattern.compile(regex);
		if (!(pattern.matcher(eMail).matches())) {
			throw new IllegalArgumentException("Email is not valid");
		}
		if (eMail.length() > 30) {
			throw new IllegalArgumentException(
					"Email cannot be longer than 30 characters.");
		}

		// Check if firstName is valid
		if (firstName.isEmpty()) {
			throw new IllegalArgumentException(
					"Requireder field First Name cannot be empty or space filled.");
		}

		// Check if lastName is valid
		if (lastName.isEmpty()) {
			throw new IllegalArgumentException(
					"Requireder field Last Name cannot be empty or space filled.");
		}

		// Go on
		return userDAO.updateUser(user, eMail, firstName, lastName, password);
	}

	public boolean updateUserLanguage(CrayonsUser user, Language newLanguage) {
		if ((user == null) || (newLanguage == null)) {
			throw new IllegalArgumentException(
					"User and new language can't be null");
		}
		return userDAO.updateLanguage(user, newLanguage);
	}

	
	public boolean updateRights(String eMail, String role) {
		int r = 2;
		if (role.equals("Autor")) {
			r = 1;
		}
		if (role.equals("Admin")) {
			r = 0;
		}
		return userDAO.updateRights(eMail, r);
	}

	/**
	 * removes an User
	 * 
	 * @param user
	 *            to remove
	 * @return true if successfull
	 */
	public boolean removeUser(String user) {
		userDAO.deleteUser(user);
		return true;
	}

	/**
	 * Returns an User by his email
	 * 
	 * @param eMail
	 *            of User to find
	 * @return User of DB by Email
	 */
	public CrayonsUser findByEMail(String eMail) {
		List<CrayonsUser> users = findAll();

		for (CrayonsUser tmpUser : users) {
			if (tmpUser.getEmail().equals(eMail)) {
				return tmpUser;
			}
		}
		throw new UsernameNotFoundException("User with mail: " + eMail
				+ " doesnt exists!");
	}

}
