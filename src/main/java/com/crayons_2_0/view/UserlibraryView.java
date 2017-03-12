package com.crayons_2_0.view;

import java.util.ResourceBundle;
import org.springframework.beans.factory.annotation.Autowired;

import com.crayons_2_0.service.LanguageService;
import com.crayons_2_0.service.database.CourseService;
import com.crayons_2_0.service.database.UserService;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

/**
 * User library view represents the courses which are available for current
 * user.
 */
@SuppressWarnings("serial")
@SpringView(name = UserlibraryView.VIEW_NAME) class UserlibraryView extends VerticalLayout implements View {

	/**
     * 
     */

	static final String VIEW_NAME = "Userlibrary";
	ResourceBundle lang = LanguageService.getInstance().getRes();

	private @Autowired
	CourseService courseService;

	private @Autowired
	UserService userService;

	private TabSheet coursesTabSheet;
	public UserlibraryView() {

	}

	/**
	 * Tab sheet getter.
	 * 
	 * @return tabSheet
	 */
	public TabSheet getTabSheet() {
		return this.coursesTabSheet;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		
	}
}
