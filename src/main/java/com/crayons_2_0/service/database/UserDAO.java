package com.crayons_2_0.service.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.crayons_2_0.model.CrayonsUser;
import com.crayons_2_0.service.Language;
import com.vaadin.spring.annotation.SpringComponent;

// LINKS:
// http://docs.spring.io/spring/docs/2.0.x/reference/jdbc.html

@SpringComponent
public class UserDAO implements CommandLineRunner {

	private @Autowired
	JdbcTemplate jdbcTemplate;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<CrayonsUser> findAll() {
		String query = "select * from users";
		RowMapper mapper = new RowMapper<Object>() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

				List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
				String mail = rs.getString("email");
				String password = rs.getString("password");
				String firstName = rs.getString("firstname");
				String lastName = rs.getString("lastname");
				String language = rs.getString("language");
				int permission = rs.getInt("permission");
				authorities.add(new SimpleGrantedAuthority("CLIENT"));

				CrayonsUser user = new CrayonsUser(firstName, lastName, mail,
						password, language, permission, true, true, false,
						false, authorities);
				return user;
			}
		};
		return jdbcTemplate.query(query, mapper);
	}

	/*
	 * // FALSCH public void save(CrayonsUser user) { String query =
	 * "insert into users (label) values (?)"; jdbcTemplate.update(query, new
	 * Object[]{user.getUsername()}); }
	 */

	// WICHTIG: realm nicht vergessen, attribute in der sql werden
	// kleingemacht!!

	public void insertUser(CrayonsUser user) {

		String mail = user.getEmail();
		String password = user.getPassword();
		String firstName = user.getFirstName();
		String lastName = user.getLastName();
		String language = user.getLanguage().toString();
		int permission = user.getPermission();

		jdbcTemplate
				.update("insert into users (email, password, firstname, lastname, language, permission) VALUES (?, ?, ?, ?, ?, ?)",
						mail, password, firstName, lastName, language,
						permission);
	}

	/**
	 * 
	 * @param user
	 *            CurrentUser
	 * @param eMail
	 *            New Email to be saved
	 * @param firstName
	 *            New first name to be saved
	 * @param lastName
	 *            New last name o be saved
	 * @param password 
	 * @return true if successfully inserted, false if not
	 */
	public boolean updateUser(CrayonsUser user, String eMail, String firstName,
			String lastName, String password) {
		// TODO bad SQL grammar [UPDATE users SET firstname=?, lastname=? WHERE
		// email=? ]; nested exception is org.postgresql.util.PSQLException:
		// FEHLER: Spalte »firstname« von Relation »users« existiert nicht
		jdbcTemplate
				.update("UPDATE users SET email=?, password=?, firstname=?, lastname=? WHERE email=? ",
						eMail, password, firstName, lastName, user.getEmail());
		return true;
	}

	// Example:
	// http://alvinalexander.com/blog/post/jdbc/java-spring-jdbc-dao-delete-examples-recipes

	public void deleteUser(String user) {
		String deleteStatement = "DELETE FROM users WHERE email=?";
		try {
			jdbcTemplate.update(deleteStatement, user);
		} catch (RuntimeException e) {
			throw new UsernameNotFoundException("User with mail:" + user
					+ "doesnt exists!");
		}
	}

	// public void createTable() {
	// log.info("@@ Creating users table");
	// jdbcTemplate.execute(
	// "CREATE TABLE IF NOT EXISTS users(email VARCHAR(100) NOT NULL, password VARCHAR(100) NOT NULL, firstname VARCHAR(100)  NOT NULL,lastname VARCHAR(100)  NOT NULL, language VARCHAR(100)  NOT NULL, permission int)");
	//
	// log.info("@@ > Done.");
	// }

	@Override
	public void run(String... arg0) throws Exception {
		new ArrayList<GrantedAuthority>();
		//findAll().forEach(entry -> log.info(entry.toString()));

	}

	public boolean updateRights(String eMail, int r) {
		jdbcTemplate.update("UPDATE users SET permission=? WHERE email=?", r,
				eMail);
		return true;
	}

	public boolean updateLanguage(CrayonsUser user, Language newLanguage) {
		jdbcTemplate.update("UPDATE users SET language=? WHERE email=?",
				newLanguage.toString(), user.getEmail());
		return true;
	}
}
