package com.crayons_2_0.view;

import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;

import com.crayons_2_0.component.UnitEditor.CourseEditorListener;
import com.crayons_2_0.service.LanguageService;
import com.crayons_2_0.service.database.CourseService;
import com.crayons_2_0.service.database.UserService;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

/**
 * Author library view represents the courses which were created by current
 * user, allows to modify existing and create new courses.
 */
@SuppressWarnings("serial")
@SpringView(name = Authorlibrary.VIEW_NAME)
@SpringComponent class Authorlibrary extends VerticalLayout implements View,
		CourseEditorListener {
	private @Autowired
	CourseService courseService;
	private @Autowired
	UserService userService;

	static final String VIEW_NAME = "Authorlibrary";
	ResourceBundle lang = LanguageService.getInstance().getRes();
	private TabSheet tabSheet;
	

	// TODO bug: author got no course, gets a course he doesnt have

	

	public Authorlibrary() {

	}

	/**
	 * Tab sheet getter.
	 * 
	 * @return tabSheet
	 */
	private TabSheet getTabSheet() {
		return this.tabSheet;
	}

	@Override
	public void enter(ViewChangeEvent event) {
	}
}
