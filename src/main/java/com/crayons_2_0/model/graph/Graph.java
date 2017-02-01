package com.crayons_2_0.model.graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.crayons_2_0.model.Course;
import com.crayons_2_0.model.CrayonsUser;
import com.crayons_2_0.model.graph.UnitNode.UnitType;

public class Graph {
    private final Course course;
    private final Node startNode;
    private final Node endNode;
    // this holds not just the start and end node!
    private Set<Node> unitCollection = new HashSet<Node>();

    // private boolean unitsAvailable;

    // create a new graph when a new course is created
    public Graph(Course course) {
        this.course = course;
        // this.unitsAvailable = false;
        this.startNode = new Node("Start");
        this.endNode = new Node("End");
        this.unitCollection.add(startNode);
        this.unitCollection.add(endNode);
    }

    /*
     * // initialize a graph using the information from the databases public
     * Graph(long graphID) { //this.unitsAvailable = false; this.startUnit = new
     * UnitNode("Start", UnitType.START, unitsAvailable, this); this.endUnit =
     * new UnitNode("End", UnitType.END, unitsAvailable, this); }
     */
    public ArrayList<String> getNodeNameList() {
        ArrayList<String> tmpNodeNameList = new ArrayList<String>();
        for (Node tmpNode : this.getUnitCollection()) {
            tmpNodeNameList.add(tmpNode.getUnitNodeTitle());
        }
        return tmpNodeNameList;
    }

    public ArrayList<String> getEdgeSequence() {
        ArrayList<String> tmpEdgeSequence = new ArrayList<String>();
        Set<Node> markedNodes = this.unitCollection;
        for (Node currentNode : markedNodes) {
            // assert tmpEdgSequence.size() is even

            if (currentNode.getUnitNodeTitle() == "Start") {
                for (Node currentStartNode : startNode.getNodes()) {
                    tmpEdgeSequence.add("Start");
                    tmpEdgeSequence.add(currentStartNode.getUnitNodeTitle());
                }
            }
      
            if (currentNode.getNodes().isEmpty() && currentNode.getUnitNodeTitle() != "End") {
                tmpEdgeSequence.add(currentNode.getUnitNodeTitle());
                tmpEdgeSequence.add("End");
            }
            if (currentNode.getUnitNodeTitle() != "Start" && currentNode.getUnitNodeTitle() != "End"
                    && !(currentNode.getNodes().isEmpty()))
                for (Node currentChildNode : currentNode.getNodes()) {
                    tmpEdgeSequence.add(currentNode.getUnitNodeTitle());
                    tmpEdgeSequence.add(currentChildNode.getUnitNodeTitle());

                }
        }
        for(int i =0;i<tmpEdgeSequence.size();i++){
        System.out.println(tmpEdgeSequence.get(i));
        }
        return tmpEdgeSequence;

    }

    public Node getStartUnit() {
        return startNode;
    }

    public Node getEndUnit() {
        return endNode;
    }

    public Set<Node> getUnitCollection() {
        return unitCollection;
    }

    /*
     * TODO public boolean areUnitsAvailable() { return unitsAvailable; }
     * 
     * public void setUnitsAvailable(boolean unitsAvailable) {
     * this.unitsAvailable = unitsAvailable; }
     */
    
    // add a new unit and create for all parents a new childNode
    public boolean addUnit(Node currentNode, Set<Node> setParent,Set<UnitNode> setChildren) {
        for (Node tmpNode : setParent) {
            tmpNode.addNode(currentNode);
        }
        for (UnitNode tmpNode : setChildren) {
            tmpNode.addParentNode(currentNode);
        }
        this.unitCollection.add(currentNode);
        return true;
    }
    public boolean addUnit(Node currentNode, UnitNode parent, UnitNode child){
        parent.addNode(currentNode);
        child.addParentNode(child);
        this.unitCollection.add(currentNode);
        return true;
    }
    public boolean addUnit(Node currentNode, Set<Node> setParent) {
        for (Node tmpNode : setParent) {
            tmpNode.addNode(currentNode);
        }
        
        this.unitCollection.add(currentNode);
        return true;
    }

    public boolean deleteUnit(UnitNode learningUnit) {
        return true;
    }

    /*
     * // remove an old connection if exist (alert?) and add a new one public
     * boolean addConnection(UnitNode from, UnitNode to) { return true; }
     * 
     * public boolean removeConnection(UnitNode from, UnitNode to) { return
     * true; }
     */
}
