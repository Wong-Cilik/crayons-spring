package com.crayons_2_0.view;

import com.crayons_2_0.config.CurrentUserDummy;

public class CurrentCourseDummy {

	private static final CurrentCourseDummy dummy = new CurrentCourseDummy();
	
	private String courseTitle;
	
	private void CurrentCourseDummy() {
		
	}
	
	public static CurrentCourseDummy getInstance() {
		return dummy;
	}

	/**
	 * @return the courseTitle
	 */
	public String getCourseTitle() {
		return courseTitle;
	}

	/**
	 * @param courseTitle the courseTitle to set
	 */
	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}
	
}
