package com.crayons_2_0.service.database;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.crayons_2_0.model.CrayonsUser;
import com.vaadin.spring.annotation.SpringComponent;
//import org.springframework.security.core.authority.GrantedAuthorityImpl;
//import org.springframework.security.core.authority.GrantedAuthorityImpl;

/**
 * Class for Service on users of DB
 */
@SpringComponent
public class UserService implements UserDetailsService {

	@Autowired
	private UserDAO userDAO;

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
		// Check if Exists, return false if exists
		List<CrayonsUser> users = findAll();
		for (CrayonsUser tmpUser : users) {
			if (tmpUser.getEmail().equals(user.getEmail())) {
				// User exists, so -> update
				userDAO.updateUser(user);
			}
		}

		// User doesn't exist -> insert
		userDAO.insertUser(user);
		return true;
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
	 * @return true if successfull
	 */
	public boolean updateUser(CrayonsUser user, String eMail, String firstName,
			String lastName) {
		return userDAO.updateUser(user, eMail, firstName, lastName);
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
	public boolean removeUser(CrayonsUser user) {
		userDAO.deleteUser(user);
		return true;
	}

	/**
	 * Returns an List of User of DB finding by Name (FirstName & LastName)
	 * 
	 * @param firstName
	 *            of User
	 * @param lastName
	 *            of User
	 * @return List of User of DB by FirstName & LastName
	 */
	public List<CrayonsUser> findByName(String firstName, String lastName) {
		List<CrayonsUser> users = findAll();
		List<CrayonsUser> userWithName = new LinkedList<CrayonsUser>();

		for (CrayonsUser tmpUser : users) {
			if (tmpUser.getFirstName().equals(firstName)
					&& tmpUser.getLastName().equals(lastName)) {
				userWithName.add(tmpUser);
			}
		}

		return userWithName;
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
