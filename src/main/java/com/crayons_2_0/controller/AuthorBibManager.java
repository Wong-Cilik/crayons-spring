package com.crayons_2_0.controller;

import java.util.List;

import com.crayons_2_0.model.Course;
import com.crayons_2_0.service.database.CourseService;
import com.vaadin.ui.Component;

public class AuthorBibManager {
	
	/**
	 * Generates Tabs for the View
	 * @param listener
	 * @return
	 */
	public Component getCourseTabs(CourseServiceListener listener) {
		CourseService courseService = new CourseService();
		List<Course> courses = courseService.getAllCursesOfUser();
		
		// Generate Tabs
		return null;
	}

}
