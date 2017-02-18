package com.crayons_2_0.model.graph;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.crayons_2_0.component.UnitPageLayout;

@SuppressWarnings("serial")
public class UnitNode implements Serializable {
	/**
	 * 
	 */

	// the graph for the course
	private final Graph graph;
	private String unitNodeTitle;
	private UnitPageLayout layout;

	private Set<UnitNode> parentNodes = new HashSet<UnitNode>();
	private Set<UnitNode> childNodes = new HashSet<UnitNode>();

	// Constructor for Start and EndNodes
	public UnitNode(String unitTitle, Graph graph) {
		this.unitNodeTitle = unitTitle;
		this.graph = graph;
	}

	public UnitNode(String unitTitle, UnitNode parent, UnitNode child,
			Graph graph) {
		this.unitNodeTitle = unitTitle;
		this.graph = graph;
		this.parentNodes.add(parent);
		this.childNodes.add(child);

	}

	public UnitNode(String unitTitle, UnitNode parent, Graph graph) {
		this.unitNodeTitle = unitTitle;
		this.parentNodes.add(parent);
		this.graph = graph;

	}

	public Graph getGraph() {
		return graph;
	}

	public String getUnitNodeTitle() {
		return unitNodeTitle;
	}

	public Set<UnitNode> getParentNodes() {
		return parentNodes;
	}

	public Set<UnitNode> getChildNodes() {
		return childNodes;
	}

	public void addParentNode(UnitNode parentNode) {
		this.parentNodes.add(parentNode);
	}

	public void addChildNode(UnitNode childNode) {
		this.childNodes.add(childNode);
	}

	public void setUnitTitle(String unitTitle) {
		this.unitNodeTitle = unitTitle;
	}

	public enum UnitType {
		// START,
		// END,
		LEARNING, TEST
	}

	public void removeChildNode(UnitNode child) {
		this.childNodes.remove(child);

	}

	public void removeParentNode(UnitNode parent) {
		this.parentNodes.remove(parent);

	}

	public UnitPageLayout getLayout() {
		return layout;
	}

	public void setLayout(UnitPageLayout layout) {
		this.layout = layout;
	}
}
