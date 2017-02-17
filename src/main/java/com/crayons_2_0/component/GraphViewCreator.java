package com.crayons_2_0.component;

import com.crayons_2_0.model.graph.Graph;
import com.vaadin.ui.GridLayout;

import org.springframework.security.core.userdetails.User;

public class GraphViewCreator {
	public GraphViewCreator(Graph graph, User user) {
	}

	/*
	 * generate a graph if user is an author return an author view otherwise
	 * return a student view
	 */
	public GridLayout getGraphView() {
		return getStudentView();
	}

	// the view where some units can be unavailable
	private GridLayout getStudentView() {
		return null;
	}
}
