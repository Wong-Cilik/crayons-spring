package com.crayons_2_0.authentication;

import com.crayons_2_0.model.graph.Graph;
import com.vaadin.spring.annotation.SpringComponent;

@SpringComponent
public class CurrentGraph {

	private Graph graph;
	private static String courseTitle;
	private String unitTitle;
	private static CurrentGraph instance = null;

	private CurrentGraph() {

	}

	public static CurrentGraph getInstance() {
		if (instance == null) {
			instance = new CurrentGraph();
		}
		return instance;
	}

	public Graph getGraph() {

		return graph;
	}

	public void setGraph(Graph graph) {

		this.graph = graph;
	}

	public static String getCourseTitle() {
		return courseTitle;
	}

	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}

	public String getUnitTitle() {
		return unitTitle;
	}

	public void setUnitTitle(String unitTitle) {
		this.unitTitle = unitTitle;
	}
}
