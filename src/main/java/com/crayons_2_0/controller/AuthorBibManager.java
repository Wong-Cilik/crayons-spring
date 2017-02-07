package com.crayons_2_0.controller;

import java.util.List;

import com.crayons_2_0.config.CurrentUserDummy;
import com.crayons_2_0.model.Course;
import com.crayons_2_0.service.database.CourseService;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class AuthorBibManager {
	
	/**
	 * Generates Tabs for the View
	 * @param listener
	 * @return
	 */
	public Component getCourseTabs(CourseService courseService) {
		
		TabSheet tabSheet = new TabSheet();
		
		
		List<Course> courses = courseService.findAllCoursesOfUser(CurrentUserDummy.get());
		
		for (Course tmpCourse : courses) {
			final VerticalLayout content = new VerticalLayout();
			
			Label description = new Label(tmpCourse.getDescription());
			content.addComponent(description);
			
			Button exampleButton = new Button();
			exampleButton.addClickListener(new ClickListener() {
				
				@Override
				public void buttonClick(ClickEvent event) {
					Notification.show("ToDo", "ExampleButton", Notification.Type.WARNING_MESSAGE);
					
				}
			});
			content.addComponent(exampleButton);
			
		}
		
		// Generate Tabs
		return tabSheet;
	}

}
