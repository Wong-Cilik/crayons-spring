package com.crayons_2_0.view.login;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import com.crayons_2_0.controller.RegisterFormListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@SpringView(name = RegisterView.VIEW_NAME)
@ViewScope
public class RegisterView extends VerticalLayout implements View {
	/**
     * 
     */
	public static final String VIEW_NAME = "registerView";
	private static final long serialVersionUID = 1L;
	private TextField email = new TextField();
	private PasswordField password = new PasswordField();
	private TextField firstname = new TextField();
	private TextField lastname = new TextField();
	private NativeSelect selectLanguage = new NativeSelect("Select language");
	private NativeSelect selectRole = new NativeSelect("Select a role");

	@PostConstruct
	void init() {
		registerViewBuilder();

	}

	private void registerViewBuilder() {
		List<String> roles = new ArrayList<String>();
		roles.add("user");
		roles.add("admin");

		for (String obj : roles) {

			selectRole.addItem(obj);
		}

		selectRole.setNullSelectionAllowed(false);
		selectRole.setImmediate(true);

		selectRole.addValueChangeListener(e -> Notification.show(
				"Value changed:", String.valueOf(e.getProperty().getValue()),
				Type.TRAY_NOTIFICATION));

		List<String> languages = new ArrayList<String>();
		languages.add("English");
		languages.add("German");

		for (String obj : languages) {

			selectLanguage.addItem(obj);
		}

		selectLanguage.setNullSelectionAllowed(false);
		selectLanguage.setImmediate(true);

		selectLanguage.addValueChangeListener(e -> Notification.show(
				"Value changed:", String.valueOf(e.getProperty().getValue()),
				Type.TRAY_NOTIFICATION));

		getEmail().setRequired(true);
		getPassword().setRequired(true);
		getFirstname().setRequired(true);
		getLastname().setRequired(true);
		addComponent(new Label("firstname"));

		addComponent(getFirstname());

		addComponent(new Label("lastname"));

		addComponent(getLastname());

		addComponent(new Label("eMail:"));

		addComponent(getEmail());

		addComponent(new Label("password"));

		addComponent(getPassword());

		addComponent(getSelectRole());

		addComponent(getSelectLanguage());

		Button btnInsertUser = new Button("create a user");
		btnInsertUser.addClickListener(new RegisterFormListener());
		//
		// Trivial logic for closing the sub-window
		setMargin(true);
		setSpacing(true);
		addComponents(btnInsertUser);
	}

	public TextField getEmail() {
		return email;
	}

	public void setEmail(TextField email) {
		this.email = email;
	}

	public PasswordField getPassword() {
		return password;
	}

	public void setPassword(PasswordField password) {
		this.password = password;
	}

	public TextField getFirstname() {
		return firstname;
	}

	public void setFirstname(TextField firstname) {
		this.firstname = firstname;
	}

	public TextField getLastname() {
		return lastname;
	}

	public void setLastname(TextField lastname) {
		this.lastname = lastname;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

	public NativeSelect getSelectRole() {
		return selectRole;
	}

	public void setSelectRole(NativeSelect selectRole) {
		this.selectRole = selectRole;
	}

	public NativeSelect getSelectLanguage() {
		return selectLanguage;
	}

	public void setSelectLanguage(NativeSelect selectLanguage) {
		this.selectLanguage = selectLanguage;
	}

}
