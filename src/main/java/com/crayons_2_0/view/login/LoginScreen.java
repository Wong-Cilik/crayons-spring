package com.crayons_2_0.view.login;

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

	/**
     * 
     */
	// AccessControl accessControl, LoginListener loginListener
	public LoginScreen() {
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
				"<h1>Login Information</h1>"
						+ "Log in as &quot;admin&quot; to have full access. Log in with any other username to have read-only access. For all users, any password is fine",
				ContentMode.HTML);
		loginInformation.addComponent(loginInfoText);
		return loginInformation;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
