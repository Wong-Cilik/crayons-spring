package com.crayons_2_0.authentication;

public class CurrentCourseDummy {

	private static final CurrentCourseDummy dummy = new CurrentCourseDummy();

	private String courseTitle;

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
	 * @param courseTitle
	 *            the courseTitle to set
	 */
	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}

}
