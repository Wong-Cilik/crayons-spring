package com.crayons_2_0.view;

import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;

import com.crayons_2_0.service.LanguageService;
import com.crayons_2_0.service.database.UserService;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

// @PreserveOnRefresh
@SuppressWarnings("serial")
@SpringView(name = Preferences.VIEW_NAME)
@ViewScope
@SpringComponent class Preferences extends VerticalLayout implements View {

	/**
	 * 
	 */

	// public static final String ID = "profilepreferenceswindow";
	static final String VIEW_NAME = "Preferences";
	ResourceBundle lang = LanguageService.getInstance().getRes();
	private @Autowired
	UserService userService;

	// private final BeanFieldGroup<User> fieldGroup;
	/*
	 * Fields for editing the User object are defined here as class members.
	 * They are later bound to a FieldGroup by calling
	 * fieldGroup.bindMemberFields(this). The Fields' values don't need to be
	 * explicitly set, calling fieldGroup.setItemDataSource(user) synchronizes
	 * the fields with the user object.
	 */
	@PropertyId("firstName")
	private TextField firstNameField;
	@PropertyId("lastName")
	private TextField lastNameField;
	@PropertyId("title")
	private ComboBox titleField;
	@PropertyId("male")
	private OptionGroup sexField;
	@PropertyId("email")
	private TextField emailField;
	@PropertyId("location")
	private TextField locationField;
	@PropertyId("phone")
	private TextField phoneField;
	@PropertyId("bio")
	private TextArea bioField;

	public Preferences() {

	}

	

	@Override
	public void enter(ViewChangeEvent event) {

	}

}
