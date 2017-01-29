package com.crayons_2_0.model.graph;

import java.util.HashSet;
import java.util.Set;

import com.crayons_2_0.model.Course;
import com.crayons_2_0.model.CrayonsUser;
import com.crayons_2_0.model.graph.UnitNode.UnitType;

public class Graph {
    private final Course course;
    private final Node startNode; 
    private final Node endNode;
    //this holds not just the start and end node!
    private Set<Node> unitCollection = new HashSet<Node>();
    
    
    //private boolean unitsAvailable;
    
    // create a new graph when a new course is created
    public Graph(Course course) {
        this.course = course;
        //this.unitsAvailable = false;
        this.startNode = new Node("Start");
        this.endNode = new Node("End");
        this.unitCollection.add(startNode);
        this.unitCollection.add(endNode);
    }
    /*
    // initialize a graph using the information from the databases
    public Graph(long graphID) {
        //this.unitsAvailable = false;
        this.startUnit = new UnitNode("Start", UnitType.START, unitsAvailable, this);
        this.endUnit = new UnitNode("End", UnitType.END, unitsAvailable, this);
    }
    */
    public Node getStartUnit() {
        return startNode;
    }

    public Node getEndUnit() {
        return endNode;
    }
    public Set<Node> getUnitCollection() {
        return unitCollection;
    }
/* TODO
    public boolean areUnitsAvailable() {
        return unitsAvailable;
    }

    public void setUnitsAvailable(boolean unitsAvailable) {
        this.unitsAvailable = unitsAvailable;
    }
*/    
    // add a new unit and create for all parents a new childNode
    public boolean addUnit(UnitNode currentNode, Set<Node> set){
        for(Node tmpNode :set){
            tmpNode.addNode(currentNode);
        }
        this.unitCollection.add(currentNode);
        return true;
    }
    
    public boolean deleteUnit(UnitNode learningUnit) {
        return true;
    }
    
   
   /* 
    // remove an old connection if exist (alert?) and add a new one
    public boolean addConnection(UnitNode from, UnitNode to) {
        return true;
    }
    
    public boolean removeConnection(UnitNode from, UnitNode to) {
        return true;
    }
    */
}
