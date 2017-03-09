package com.crayons_2_0.view.login;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.crayons_2_0.controller.RegisterFormListener;
import com.crayons_2_0.service.LanguageService;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.UI;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@SpringView(name = RegisterView.VIEW_NAME)
@ViewScope
@SpringComponent
public class RegisterView extends VerticalLayout implements View {
	/**
     * 
     */
	@Autowired
	RegisterFormListener registerFormListener;

	public static final String VIEW_NAME = "registerView";
	private ResourceBundle lang = LanguageService.getInstance().getRes();

	private TextField email = new TextField();
	private PasswordField password = new PasswordField();
	private TextField firstname = new TextField();
	private TextField lastname = new TextField();
	private NativeSelect selectLanguage = new NativeSelect(
			lang.getString("SelectYourLanguage"));

	@PostConstruct
	void init() {
		registerViewBuilder();
	}

	private void registerViewBuilder() {
		List<String> languages = new ArrayList<String>();
		languages.add(lang.getString("English"));
		languages.add(lang.getString("German"));

		for (String obj : languages) {

			selectLanguage.addItem(obj);
		}

		selectLanguage.setNullSelectionAllowed(false);
		selectLanguage.setValue(languages.iterator().next());
		selectLanguage.setImmediate(true);

		getEmail().setRequired(true);
		getPassword().setRequired(true);
		getFirstname().setRequired(true);
		getLastname().setRequired(true);
		addComponent(new Label(lang.getString("FirstName")));

		addComponent(getFirstname());

		addComponent(new Label(lang.getString("LastName")));

		addComponent(getLastname());

		addComponent(new Label(lang.getString("Email")));

		addComponent(getEmail());

		addComponent(new Label(lang.getString("Password")));

		addComponent(getPassword());

		addComponent(getSelectLanguage());

		Button btnInsertUser = new Button(lang.getString("CreateUser"));
		btnInsertUser.addClickListener(registerFormListener);
		// Trivial logic for closing the sub-window

		Button btnCancel = new Button(lang.getString("Cancel"));
		btnCancel.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				UI.getCurrent().getNavigator()
						.navigateTo(LoginScreen.VIEW_NAME);
			}

		});

		setMargin(true);
		setSpacing(true);
		addComponents(btnInsertUser);
		addComponents(btnCancel);
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

	public NativeSelect getSelectLanguage() {
		return selectLanguage;
	}

	public void setSelectLanguage(NativeSelect selectLanguage) {
		this.selectLanguage = selectLanguage;
	}

}
