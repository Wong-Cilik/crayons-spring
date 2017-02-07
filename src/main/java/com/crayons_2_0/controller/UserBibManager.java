package com.crayons_2_0.controller;

import java.util.List;

import com.crayons_2_0.config.CurrentUserDummy;
import com.crayons_2_0.model.Course;
import com.crayons_2_0.service.database.CourseService;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class UserBibManager {
	
	/**
	 * Generates Tabs for the View
	 * @param listener
	 * @return
	 */
	public static TabSheet getCourseTabs(TabSheet tabSheet) {
		
		//TabSheet tabSheet = new TabSheet();
		
		CourseService courseService = new CourseService();
		
		List<Course> courses = courseService.findAllCoursesOfUser(CurrentUserDummy.get()); //DUMMYYYYYYYYYYYYYYYYYYY!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		
		for (Course tmpCourse : courses) {
			VerticalLayout content = createContentForTab(tmpCourse);
			// ShortTitle Einführen?!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			tabSheet.addTab(content, tmpCourse.getTitle());
		}
		
		return tabSheet;
	}

	private static VerticalLayout createContentForTab(Course tmpCourse) {
		VerticalLayout content = new VerticalLayout(); 
		
		// Description
		Label description = new Label(tmpCourse.getDescription());
		content.addComponent(description);
		
		// Example
		Button exampleButton = new Button();
		exampleButton.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				Notification.show("ToDo", "ExampleButton", Notification.Type.WARNING_MESSAGE);
				
			}
		});
		content.addComponent(exampleButton);
		
		// Buttons Delete & Learn
		HorizontalLayout buttons = new HorizontalLayout();
		
		Button buttonLearn = new Button("Learn");
		buttonLearn.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				Notification.show("ToDo", "ExampleButton", Notification.Type.WARNING_MESSAGE);	
			}
		});
		buttons.addComponent(buttonLearn);
		
		Button buttonDelete = new Button("Delete");
		buttonDelete.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				Notification.show("ToDo", "ExampleButton", Notification.Type.WARNING_MESSAGE);	
			}
		});
		buttons.addComponent(buttonLearn);
		
		content.addComponent(buttons);
		
		// Return
		
		return content;
	}


}
