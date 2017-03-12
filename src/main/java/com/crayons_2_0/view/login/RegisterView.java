package com.crayons_2_0.view.login;

import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;

import com.crayons_2_0.controller.RegisterFormListener;
import com.crayons_2_0.service.LanguageService;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.NativeSelect;
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
	private @Autowired
	RegisterFormListener registerFormListener;

	protected static final String VIEW_NAME = "registerView";
	private ResourceBundle lang = LanguageService.getInstance().getRes();

	private TextField email = new TextField();
	private PasswordField password = new PasswordField();
	private TextField firstname = new TextField();
	private TextField lastname = new TextField();
	private NativeSelect selectLanguage = new NativeSelect(
			lang.getString("SelectYourLanguage"));

	

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
