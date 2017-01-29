package com.crayons_2_0.model.graph;

import java.util.HashSet;
import java.util.Set;

public class UnitNode extends Node {
    //the graph for the course
    private final Graph graph;
    private String unitNodeTitle;
    // private UnitType unitType;
    // private boolean availability;
    
    private Set<Node> parentNodes= new HashSet<Node>();;
    // TODO: how to store the content?
    
    //Constructor for creating a UnitNode with the StartNode as parent
    public UnitNode(String unitTitle, Node startNode, Graph graph) {
        this.unitNodeTitle = unitTitle;
        // this.unitType = unitType;
        // this.availability = availability;
        this.graph = graph;
        this.parentNodes.add(startNode); 
        
        }
    //Constructor for creating a UnitNode with a single parent
    public UnitNode(String unitTitle, UnitNode Node, Graph graph) {
        this.unitNodeTitle = unitTitle;
        // this.unitType = unitType;
        // this.availability = availability;
        this.graph = graph;
        this.parentNodes.add(Node); 
        
        }
    //Constructor for creating a UnitNode with multiple parents
    public UnitNode(String unitTitle, Set<UnitNode> Nodes, Graph graph) {
        this.unitNodeTitle = unitTitle;
        // this.unitType = unitType;
        // this.availability = availability;
        for(UnitNode tmpNode:Nodes){
            this.parentNodes.add(tmpNode); 
        }
 
        this.graph = graph;
        //this.nextUnits = new HashSet<UnitNode>();
    }
    
    public Graph getGraph() {
        return graph;
    }

    public String getUnitNodeTitle() {
        return unitNodeTitle;
    }

  

    public Set<Node> getParentNodes() {
        return parentNodes;
    }

    public void addParentNode(UnitNode parentNode) {
        this.parentNodes.add(parentNode);
    }

    public void setUnitNodeTitle(String unitNodeTitle) {
        this.unitNodeTitle = unitNodeTitle;
    }

    public void setUnitTitle(String unitTitle) {
        this.unitNodeTitle = unitTitle;
    }
/*
    public UnitType getUnitType() {
        return unitType;
    }

    public void setUnitType(UnitType unitType) {
        this.unitType = unitType;
    }
    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }
   
    public Set<UnitNode> getNextUnits() {
        return nextUnits;
    }

    public void addNextUnit(UnitNode unit) {
        this.nextUnits.add(unit);
    }
    
    public void removeNextUnit(UnitNode unit) {
        this.nextUnits.remove(unit);
    }
*/ 
    
    
    public enum UnitType {
        //START, 
       // END, 
        LEARNING, TEST
    }
}
