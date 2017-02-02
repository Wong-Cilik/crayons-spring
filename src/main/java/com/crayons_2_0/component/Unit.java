package com.crayons_2_0.component;

import com.crayons_2_0.model.graph.UnitNode.UnitType;

public class Unit {
	
	private String title;
	
	private String courseTitle;
	
	private UnitType type;
	
	private String content;

	public Unit(String title, UnitType type, String courseTitel, String content) {
		this.setTitle(title);
		this.setType(type);
		this.setCourseTitle(courseTitel);
		this.setContent(content);
	}
	
	
	
	
	
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
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

	/**
	 * @return the type
	 */
	public UnitType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(UnitType type) {
		this.type = type;
	}





	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}





	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	
	
	
	
	
	

}
