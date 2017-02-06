package com.crayons_2_0.model.graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.crayons_2_0.model.Course;
import com.crayons_2_0.model.CrayonsUser;
import com.crayons_2_0.model.graph.UnitNode.UnitType;

public class Graph {
    private final Course course;
    private final UnitNode startNode;
    private final UnitNode endNode;
    // this holds not just the start and end node!
    private Set<UnitNode> unitCollection = new HashSet<UnitNode>();

    // private boolean unitsAvailable;

    // create a new graph when a new course is created
    public Graph(Course course) {
        this.course = course;
        // this.unitsAvailable = false;
        this.startNode = new UnitNode("Start",this);
        this.endNode = new UnitNode("End",this);
        this.unitCollection.add(startNode);
        this.unitCollection.add(endNode);
    }

    
    public ArrayList<String> getNodeNameList() {
        ArrayList<String> tmpNodeNameList = new ArrayList<String>();
        for (UnitNode tmpNode : this.getUnitCollection()) {
            tmpNodeNameList.add(tmpNode.getUnitNodeTitle());
        }
        return tmpNodeNameList;
    }

    public ArrayList<String> getEdgeSequence() {
        ArrayList<String> tmpEdgeSequence = new ArrayList<String>();
        // this goes through all the nodes and stores the names of outgoing
        // edges for each node in an array
        // after every incrementation the size of tmpEdgeSequence increases by
        // two: the names of the start and end node of an edge
        // if a node has no children, then a new edge is added, which connects
        // it to the end node
        // later on the edges are added to the javascript render, simply by
        // incrementing through the array by 2 and connecting the names at even
        // indices as outgoing
        // with the odd ones as incoming.
        for (UnitNode currentNode : this.unitCollection) {
            // assert tmpEdgSequence.size() is even

            if (currentNode.getUnitNodeTitle() == "Start") {
                for (UnitNode currentStartNode : startNode.getChildNodes()) {
                    tmpEdgeSequence.add("Start");
                    tmpEdgeSequence.add(currentStartNode.getUnitNodeTitle());
                }
            }

            if (currentNode.getChildNodes().isEmpty() && currentNode.getUnitNodeTitle() != "End") {
                tmpEdgeSequence.add(currentNode.getUnitNodeTitle());
                tmpEdgeSequence.add("End");
            }
            if (currentNode.getUnitNodeTitle() != "Start" && currentNode.getUnitNodeTitle() != "End"
                    && !(currentNode.getChildNodes().isEmpty()))
                for (UnitNode currentChildNode : currentNode.getChildNodes()) {
                    tmpEdgeSequence.add(currentNode.getUnitNodeTitle());
                    tmpEdgeSequence.add(currentChildNode.getUnitNodeTitle());

                }
        }

        return tmpEdgeSequence;

    }

    // TODO Exception fangen
    public UnitNode getNodeByName(String NodeName) {
        for (UnitNode tmp : this.unitCollection) {
            if (NodeName == tmp.getUnitNodeTitle())
            return tmp;
        }
        return null;
    }

    public UnitNode getStartUnit() {
        return startNode;
    }

    public UnitNode getEndUnit() {
        return endNode;
    }

    public Set<UnitNode> getUnitCollection() {
        return unitCollection;
    }

    /*
     * TODO public boolean areUnitsAvailable() { return unitsAvailable; }
     * 
     * public void setUnitsAvailable(boolean unitsAvailable) {
     * this.unitsAvailable = unitsAvailable; }
     */

    
    public boolean addUnit(UnitNode currentNode, Set<UnitNode> setParent, Set<UnitNode> setChildren) {
        for (UnitNode tmpNode : setParent) {
            tmpNode.addChildNode(currentNode);
        }
        for (UnitNode tmpNode : setChildren) {
            tmpNode.addParentNode(currentNode);
        }
        this.unitCollection.add(currentNode);
        return true;
    }

    
    public boolean addUnit(UnitNode currentNode, UnitNode parent, UnitNode child) {
        parent.addChildNode(currentNode);
        child.addParentNode(currentNode);
        this.unitCollection.add(currentNode);
        return true;
    }
    //used for dummygraph
    public boolean addUnit(UnitNode currentNode, UnitNode parent) {
        parent.addChildNode(currentNode);
        this.unitCollection.add(currentNode);
        return true;
    }
    
    public boolean addUnit(UnitNode currentNode, Set<UnitNode> setParent) {
        for (UnitNode tmpNode : setParent) {
            tmpNode.addChildNode(currentNode);
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
