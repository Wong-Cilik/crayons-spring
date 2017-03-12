package com.crayons_2_0.component;

import com.crayons_2_0.model.graph.UnitNode.UnitType;
import com.vaadin.spring.annotation.SpringComponent;

@SpringComponent
public class Unit {

	private String title;

	private String courseTitle;

	private UnitType type;

	private String content;

	public Unit(String title, String courseTitel) {
		this.setTitle(title);
		this.setCourseTitle(courseTitel);
	}

	public Unit() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            to set
	 */
	public void setTitle(String title) {
		if (title.isEmpty()) {
			throw new IllegalArgumentException("Title of unit can't be empty!");
		}

		this.title = title;
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
		if (courseTitle.isEmpty()) {
			throw new IllegalArgumentException(
					"Title of course of unit can't be empty!");
		}

		this.courseTitle = courseTitle;
	}

	/**
	 * @return the type
	 */
	public UnitType getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(UnitType type) {
		if (type == null) {
			throw new IllegalArgumentException("type can't be null!");
		}

		this.type = type;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

}
