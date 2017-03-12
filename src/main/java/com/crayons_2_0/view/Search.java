package com.crayons_2_0.view;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import com.crayons_2_0.service.CourseDisplay;
import com.crayons_2_0.service.LanguageService;
import com.crayons_2_0.service.database.CourseService;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@SpringView(name = Search.VIEW_NAME)
@ViewScope
@SpringComponent
final class Search extends VerticalLayout implements View {

	/**
	 * 
	 */

	private List<CourseDisplay> collection = new ArrayList<CourseDisplay>();
	static final String VIEW_NAME = "Search";
	private static ResourceBundle lang = LanguageService.getInstance().getRes();

	private Table table;

	private @Autowired
	CourseService courseService;

	

	public Search() {

	}

	@Override
	public void enter(final ViewChangeEvent event) {
	}

}