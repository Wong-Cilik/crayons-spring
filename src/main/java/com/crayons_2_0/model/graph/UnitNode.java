package com.crayons_2_0.model.graph;

import java.util.HashSet;
import java.util.Set;

public class UnitNode {
    // the graph for the course
    private final Graph graph;
    private String unitNodeTitle;
    // private UnitType unitType;
    // private boolean availability;

    private Set<UnitNode> parentNodes = new HashSet<UnitNode>();
    private Set<UnitNode> childNodes = new HashSet<UnitNode>();
    // TODO: how to store the content?

    // Constructor for Start and EndNodes
    public UnitNode(String unitTitle, Graph graph) {
        this.unitNodeTitle = unitTitle;
        this.graph = graph;
    }

    public UnitNode(String unitTitle, UnitNode parent, UnitNode child, Graph graph) {
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
    /*
     * public UnitType getUnitType() { return unitType; }
     * 
     * public void setUnitType(UnitType unitType) { this.unitType = unitType; }
     * public boolean isAvailability() { return availability; }
     * 
     * public void setAvailability(boolean availability) { this.availability =
     * availability; }
     * 
     * public Set<UnitNode> getNextUnits() { return nextUnits; }
     * 
     * public void addNextUnit(UnitNode unit) { this.nextUnits.add(unit); }
     * 
     * public void removeNextUnit(UnitNode unit) { this.nextUnits.remove(unit);
     * }
     */

    public enum UnitType {
        // START,
        // END,
        LEARNING, TEST
    }
}
