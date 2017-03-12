package com.crayons_2_0.view.login;

import java.util.ResourceBundle;

import com.crayons_2_0.service.Language;
import com.crayons_2_0.service.LanguageService;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * UI content when the user is not logged in yet.
 */
@SuppressWarnings("serial")
@Theme("mytheme")
@SpringView(name = LoginScreen.VIEW_NAME)
@ViewScope
public class LoginScreen extends CssLayout implements View {
	/**
	 * 
	 */

	public static final String VIEW_NAME = "";
	private ResourceBundle lang = LanguageService.getInstance().getRes();

	/**
     * 
     */
	// AccessControl accessControl, LoginListener loginListener
	public LoginScreen() {
	    LanguageService.getInstance().setCurrentLocale(Language.German);
	    lang = LanguageService.getInstance().getRes();
		// this.loginListener = loginListener;
		// this.accessControl = accessControl;
		buildUI();
		// username.focus();
	}

	private void buildUI() {
		addStyleName("login-screen");

		// login form, centered in the available part of the screen
		// Versuch mit Spring
		Component loginForm = new LoginForm();
		// Component loginForm = buildLoginForm();
		// layout to center login form when there is sufficient screen space
		// - see the theme for how this is made responsive for various screen
		// sizes
		VerticalLayout centeringLayout = new VerticalLayout();
		centeringLayout.setStyleName("centering-layout");
		centeringLayout.addComponent(loginForm);
		centeringLayout.setComponentAlignment(loginForm,
				Alignment.MIDDLE_CENTER);

		// information text about logging in
		CssLayout loginInformation = buildLoginInformation();
		// addComponent(loginForm);
		addComponent(centeringLayout);
		addComponent(loginInformation);
	}

	private CssLayout buildLoginInformation() {
		CssLayout loginInformation = new CssLayout();
		loginInformation.setStyleName("login-information");
		Label loginInfoText = new Label(
				"<h1>"
						+ lang.getString("LoginInformation")
						+ "</h1>"
						+ "Welcome to crayons! Crayons 2.0 is a new platform, where lecturers are able to create teaching courses very fast and easily and share them with their students. It is possible to create alternate learningpaths in order to achieve the best learning experience for each individual student.",
				ContentMode.HTML);
		loginInformation.addComponent(loginInfoText);
		return loginInformation;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
