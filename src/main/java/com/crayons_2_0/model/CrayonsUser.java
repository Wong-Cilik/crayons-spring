package com.crayons_2_0.model;

import java.util.Collection;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.crayons_2_0.service.Language;
import com.crayons_2_0.service.LanguageService;

@SuppressWarnings("serial")
public class CrayonsUser extends User {

	private static final int MAX_PERMISSION = 2; // ToDo: Is 2 right?
	
	private ResourceBundle lang = LanguageService.getInstance().getRes();

	private String firstName;
	private String lastName;
	private String password;
	private Language language;
	private String email;
	private int permission;

	public CrayonsUser(String firstName, String lastName, String email,
			String password, String language, int permission, boolean enabled,
			boolean accountNonExpired, boolean credentialsNonExpired,
			boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(email, password, enabled, accountNonExpired,
				credentialsNonExpired, accountNonLocked, authorities);

		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setEmail(email);
		this.setPassword(password);
		this.setLanguage(language);
		this.setPermission(permission);
	}

	/**
	 * Returns the Email of the user. Email is also used as ID.
	 * 
	 * @return email of user
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * set the EMail of the user
	 * 
	 * @param email
	 *            to set dummy
	 */
	public void setEmail(String email) {
		String regex = "[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@([_A-Za-z0-9-]+\\.)+[A-Za-z]{2,6}";
		Pattern pattern = Pattern.compile(regex);
		if (!(pattern.matcher(email).matches())) {
			throw new IllegalArgumentException(lang.getString("EmailIsNotValid"));
		}
		if (email.length() > 30) {
			throw new IllegalArgumentException(
			        String.format(lang.getString("ShouldBeAtMostNCharactersLong"), 
                    lang.getString("Email"), 30));
		}

		this.email = email;
	}

	/**
	 * Returns the firstName of user
	 * 
	 * @return firstName of user
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the firstName of User
	 * 
	 * @param firstName
	 *            to set
	 */
	public void setFirstName(String firstName) {
		if (firstName.isEmpty()) {
			throw new IllegalArgumentException(
			        String.format(lang.getString("RequiredField"), lang.getString("FirstName")));
		}
		this.firstName = firstName;
	}

	/**
	 * Returns the lastName of user
	 * 
	 * @return lastName of user
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the lastName of User
	 * 
	 * @param lastName
	 *            to set
	 */
	public void setLastName(String lastName) {
		if (lastName.isEmpty()) {
			throw new IllegalArgumentException(
			        String.format(lang.getString("RequiredField"), lang.getString("LastName")));
		}
		this.lastName = lastName;
	}

	/**
	 * Returns the password of user
	 * 
	 * @return password of user
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Set the Password of CrayonUser
	 * 
	 * @param password
	 *            to set. Length must be between 6 until 15 characters long.
	 */
	public void setPassword(String password) {
		if (password.length() < 6) {
			throw new IllegalArgumentException(
			        String.format(lang.getString("ShouldBeAtLeastNCharactersLong"), 
                            lang.getString("Password"), 6));
		} else if (password.length() > 15) {
			throw new IllegalArgumentException(
			        String.format(lang.getString("ShouldBeAtMostNCharactersLong"), 
                            lang.getString("Password"), 15));
		}

		this.password = password;
	}

	/**
	 * Returns the Language of User
	 * 
	 * @returnlanguage of user
	 */
	public Language getLanguage() {
		return language;
	}

	/**
	 * Sets the language of user
	 * 
	 * @param language
	 *            to set
	 */
	public void setLanguage(Language language) {
		if (language == null) {
			throw new IllegalArgumentException("Language can't be null");
		}
		this.language = language;
	}

	/**
	 * Sets the language of user throw a Stringrepresentation of the language.
	 * 
	 * @param language
	 *            to search for enum "Language" and set.
	 */
	public void setLanguage(String language) {
		if (language != null) {
			if (language.equals(Language.German.toString())) {
				this.setLanguage(Language.German);
			} else if (language.equals(Language.English.toString())) {
				this.setLanguage(Language.English);
			} else {
				throw new IllegalArgumentException("Language not known");
			}
		}
	}

	/**
	 * Returns an Stringrepresentation of user
	 */
	@Override
	public String toString() {
		return String
				.format("User[username= '%s', password= '%s', firstname = '%s', 'lastname = '%s', 'language = '%s']",
						getUsername(), getPassword(), getFirstName(),
						getLastName(), getLanguage().toString());
	}

	/**
	 * Returns the permission of user 0 = ?? 1 = ?? 2 = ??
	 * 
	 * @return permission of user
	 */
	public int getPermission() {
		return permission;
	}

	/**
	 * Sets the permission of user
	 * 
	 * @param permission
	 *            to set
	 */
	public void setPermission(int permission) {
		if (permission < 0) {
			throw new IllegalArgumentException("permission can't be negative");
		} else if (permission > MAX_PERMISSION) {
			throw new IllegalArgumentException(
					"permission is not allowed. Max value of permission is: "
							+ MAX_PERMISSION);
		}

		this.permission = permission;
	}
}
