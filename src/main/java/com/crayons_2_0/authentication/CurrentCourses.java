package com.crayons_2_0.authentication;

import org.springframework.beans.factory.annotation.Autowired;

import com.crayons_2_0.model.Course;
import com.crayons_2_0.service.database.CourseService;
import com.vaadin.spring.annotation.SpringComponent;

@SpringComponent
public class CurrentCourses {

	private @Autowired
	CourseService courseService;
	private Course course;
	private String title;
	private String unitTitle;
	private static CurrentCourses instance = null;

	private CurrentCourses() {

	}

	public static CurrentCourses getInstance() {
		if (instance == null) {
			instance = new CurrentCourses();
		}
		return instance;
	}

	public Course get() {

		if (getInstance().getCourse() == null) {
			getInstance().setCourse(
					courseService.findCourseByTitle(getInstance().getTitle()));
		}
		return getInstance().getCourse();
	}

	public Course getCourse() {

		return course;
	}

	public void setCourse(Course course) {

		this.course = course;
	}

	public String getTitle() {

		return title;
	}

	public void setTitle(String title) {

		this.title = title;
	}

	public String getUnitTitle() {
		return unitTitle;
	}

	public void setUnitTitle(String unitTitle) {
		this.unitTitle = unitTitle;
	}
}
