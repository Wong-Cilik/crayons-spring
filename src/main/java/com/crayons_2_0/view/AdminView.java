package com.crayons_2_0.view;

import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import com.crayons_2_0.service.LanguageService;
import com.crayons_2_0.service.UserDisplay;
import com.crayons_2_0.service.database.CourseService;
import com.crayons_2_0.service.database.UserService;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@SpringView(name = AdminView.VIEW_NAME)
@ViewScope
@SpringComponent
final class AdminView extends VerticalLayout implements View {

	/**
	 * 
	 */

	private @Autowired
	UserService userService;

	private @Autowired
	CourseService courseService;

	ResourceBundle lang = LanguageService.getInstance().getRes();
	private List<UserDisplay> collection;
	static final String VIEW_NAME = "AdminView";
	HorizontalLayout root;
	private Table table = new Table();;
	private VerticalLayout content = new VerticalLayout();
	@PropertyId("name")
	private TextField nameField = new TextField(lang.getString("Name"));
	@PropertyId("title")
	private TextField title = new TextField(lang.getString("Title"));
	@PropertyId("rights")
	private NativeSelect rights = new NativeSelect(lang.getString("Rights"));
	@PropertyId("email")
	private TextField emailField = new TextField(lang.getString("Email"));
	@PropertyId("location")
	private TextField locationField = new TextField(lang.getString("Location"));
	@PropertyId("phone")
	private TextField phoneField = new TextField(lang.getString("Phone"));

	public AdminView() {

	}

	

	@Override
	public void enter(final ViewChangeEvent event) {
	}
}
