package com.crayons_2_0.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.crayons_2_0.model.CrayonsUser;
import com.crayons_2_0.service.LanguageService;
import com.crayons_2_0.service.database.UserService;
import com.crayons_2_0.view.login.LoginScreen;
import com.crayons_2_0.view.login.RegisterView;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.Notification.Type;

@SuppressWarnings("serial")
@SpringComponent
public class RegisterFormListener implements Button.ClickListener {

	/**
	 * 
	 */
	@Autowired
	private UserService userService;

	private ResourceBundle lang = LanguageService.getInstance().getRes();

	@Override
	public void buttonClick(Button.ClickEvent event) {
		try {
			Button source = event.getButton();
			RegisterView parent = (RegisterView) source.getParent();
			String firstname = parent.getFirstname().getValue().trim();
			if (firstname.isEmpty()) {
				throw new IllegalArgumentException(String.format(
						lang.getString("RequiredField"),
						lang.getString("FirstName")));
			}
			String lastname = parent.getLastname().getValue().trim();
			if (lastname.isEmpty()) {
				throw new IllegalArgumentException(String.format(
						lang.getString("RequiredField"),
						lang.getString("LastName")));
			}
			String Nameregex = "[a-zA-Z]+";
			Pattern Namepattern = Pattern.compile(Nameregex);
			if (!(Namepattern.matcher(firstname).matches())
					|| !(Namepattern.matcher(lastname).matches())) {
				throw new IllegalArgumentException(
						lang.getString("FirstnameOrLastnameAreNotValid"));
			}

			String mail = parent.getEmail().getValue();
			if (mail.isEmpty()) {
				throw new IllegalArgumentException(String.format(
						lang.getString("RequiredField"),
						lang.getString("Email")));
			}
			if (mail.length() > 30) {
				throw new IllegalArgumentException(String.format(
						lang.getString("ShouldBeAtMostNCharactersLong"),
						lang.getString("Email"), 30));
			}
			String emailRegex = "[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@([_A-Za-z0-9-]+\\.)+[A-Za-z]{2,6}";
			Pattern emailPattern = Pattern.compile(emailRegex);
			if (!(emailPattern.matcher(mail).matches())) {
				throw new IllegalArgumentException(
						lang.getString("EmailIsNotValid"));
			}
			String password = parent.getPassword().getValue();
			if (password.isEmpty()) {
				throw new IllegalArgumentException(String.format(
						lang.getString("RequiredField"),
						lang.getString("Password")));
			}
			if (password.length() < 6) {
				throw new IllegalArgumentException(String.format(
						lang.getString("ShouldBeAtLeastNCharactersLong"),
						lang.getString("Password"), 6));
			} else if (password.length() > 15) {
				throw new IllegalArgumentException(String.format(
						lang.getString("ShouldBeAtMostNCharactersLong"),
						lang.getString("Password"), 15));
			}
			String language = (String) parent.getSelectLanguage().getValue();
			if (language.equals("Deutsch"))
				language = "German";
			else if (language.equals("Englisch"))
				language = "English";
			int permission = 2;

			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			authorities.add(new SimpleGrantedAuthority("CLIENT"));
			CrayonsUser user = new CrayonsUser(firstname, lastname, mail,
					password, language, permission, true, true, false, false,
					authorities);
			boolean userExists = userService.insertUser(user);

			if (userExists == false) {
				Notification.show(lang.getString("UserCreated"), mail,
						Type.TRAY_NOTIFICATION);

				UI.getCurrent().getNavigator()
						.navigateTo(LoginScreen.VIEW_NAME);
			} else {
				Notification.show(lang.getString("EmailAlreadyExists"));
			}

		} catch (IllegalArgumentException iae) {
			Notification.show(iae.getMessage());
		}

	}

}
