package com.crayons_2_0.service.database;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.crayons_2_0.model.CrayonsUser;
import com.crayons_2_0.service.Language;
import com.vaadin.spring.annotation.SpringComponent;

/**
 * Class for Service on users of DB
 */
@SpringComponent
public class UserService implements UserDetailsService, CommandLineRunner {

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
		 * ("Admin".equals(username)) { authorities.add(new
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
	 * Insert an user Insert if not exists
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
				return false;
			}
		}
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
	 * @param password
	 *            new password
	 * @return true if successfull
	 */
	public boolean updateUser(CrayonsUser user, String eMail, String firstName,
			String lastName, String password) {
		// Check if eMail is valid
		String regex = "[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@([_A-Za-z0-9-]+\\.)+[A-Za-z]{2,6}";
		Pattern pattern = Pattern.compile(regex);
		if (!(pattern.matcher(eMail).matches())) {
			throw new IllegalArgumentException("Email is not Valid");
		}
		if (!(eMail.equals(user.getEmail()))) {
		    List<CrayonsUser> users = findAll();
	        for (CrayonsUser tmpUser : users) {
	            if (tmpUser.getEmail().equals(eMail)) {
	                return false;
	            }
	        }
		}
		if (eMail.length() > 30) {
			throw new IllegalArgumentException(
					"Email should be at most 30 characters long");
		}

		if (password.length() < 6) {
			throw new IllegalArgumentException(
					"Password should be at least 6 characters long");
		} else if (password.length() > 15) {
			throw new IllegalArgumentException(
					"Password should be at most 15 characters long");
		}

		// Check if firstName is valid
		if (firstName.isEmpty()) {
			throw new IllegalArgumentException("First name cannot be empty");
		}

		// Check if lastName is valid
		if (lastName.isEmpty()) {
			throw new IllegalArgumentException("Last name cannot be empty");
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
		if (role.equals("Author")) {
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
	 * @return true if successful
	 */
	public boolean removeUser(String user) {
		return userDAO.deleteUser(user);
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
		throw new UsernameNotFoundException("User with mail " + eMail
				+ "does not exists");
	}

    @Override
    public void run(String... args) throws Exception {
        this.insertUser(new CrayonsUser("admin", "crayons", "admin@crayons.de", "crayons123", Language.German.toString(), 0, 
                true, true, true, true,  new ArrayList<GrantedAuthority>()));

        
    }

}
