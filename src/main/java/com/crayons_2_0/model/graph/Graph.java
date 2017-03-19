package com.crayons_2_0.model.graph;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.crayons_2_0.model.Course;
import com.crayons_2_0.model.DbCourse;

@SuppressWarnings("serial")
public class Graph implements Serializable {
    /**
     * 
     */

    private final DbCourse course;
    private final UnitNode startNode;
    private final UnitNode endNode;
    private Set<UnitNode> unitCollection = new HashSet<UnitNode>();


    /**
     * 
     * Create a new graph, when a new course is created.
     * 
     * @param course
     *            The corresponding course to a graph
     */
    public Graph(Course course) {
        this.course = course.createDbObject();
        this.startNode = new UnitNode("Start", this);
        this.endNode = new UnitNode("End", this);
        this.unitCollection.add(startNode);
        this.unitCollection.add(endNode);
    }

    /**
     * 
     * Goes through the unit collection and puts all names of the unitnodes in
     * an array
     * 
     * @return ArrayList<String> tmpNodeNameList
     */
    public ArrayList<String> getNodeNameList() {
        ArrayList<String> tmpNodeNameList = new ArrayList<String>();
        for (UnitNode tmpNode : this.getUnitCollection()) {
            tmpNodeNameList.add(tmpNode.getUnitNodeTitle());
        }
        return tmpNodeNameList;
    }

    /**
     * 
     * This goes through all the nodes and stores the names of outgoing edges
     * for each node in an array after every incrementation the size of
     * tmpEdgeSequence increases by two: the names of the startnode and end node
     * of an edge later on the edges are added to the javascript render, simply
     * by incrementing through the array by 2 and connecting the names at even
     * indices as outgoing with the odd ones as incoming.
     *
     * @return ArrayList<String> tmpEdgeSequence
     */
    public ArrayList<String> getEdgeSequence() {
        ArrayList<String> tmpEdgeSequence = new ArrayList<String>();
        for (UnitNode currentNode : this.unitCollection) {
            if (!(currentNode.getChildNodes().isEmpty()))
                for (UnitNode currentChildNode : currentNode.getChildNodes()) {
                    tmpEdgeSequence.add(currentNode.getUnitNodeTitle());
                    tmpEdgeSequence.add(currentChildNode.getUnitNodeTitle());
                }
        }
        
        return tmpEdgeSequence;
    }

    /**
     * 
     * @param NodeName
     * @return null if not found or the found UnitNode
     */
    public UnitNode getNodeByName(String NodeName) {
        for (UnitNode tmp : this.unitCollection) {
            if (NodeName.equals(tmp.getUnitNodeTitle()))
                return tmp;
        }
        return null;
    }

    /**
     * 
     * @return startNode
     */

    public UnitNode getStartUnit() {
        return startNode;
    }

    /**
     * 
     * @return endNode
     */
    public UnitNode getEndUnit() {
        return endNode;
    }

    /**
     * 
     * @return unitCollection
     */
    public Set<UnitNode> getUnitCollection() {
        return unitCollection;
    }

   
    /**
     * 
     * @param currentNode
     *            the node which is added
     * @param parent
     *            the parent node of the node which is added
     * @param child
     *            the child node of the node which is added
     * @return boolean
     */
    public boolean addUnit(UnitNode currentNode, UnitNode parent, UnitNode child) {
        parent.addChildNode(currentNode);
        child.addParentNode(currentNode);
        this.unitCollection.add(currentNode);
        return true;
    }

    /**
     * 
     * @param parent
     *            UnitNode
     * @param child
     *            UnitNode
     * @return boolean
     */
    public boolean addConnection(UnitNode parent, UnitNode child) {
        for (UnitNode tmp : this.unitCollection) {
            if (parent.getUnitNodeTitle() == tmp.getUnitNodeTitle())
                tmp.addChildNode(child);
            if (child.getUnitNodeTitle() == tmp.getUnitNodeTitle())
                tmp.addParentNode(parent);
        }
        return true;
    }   
    /**
     * 
     * @param unit
     *          the unit to be deleted
     * @return true if deleted successfully
     */
    public boolean deleteUnit(UnitNode unit) {
        for (UnitNode tmp : unit.getParentNodes()) {
            tmp.removeChildNode(unit);
        }
        for (UnitNode tmp : unit.getChildNodes()) {
            tmp.removeParentNode(unit);
        }
        return this.unitCollection.remove(unit);
    }
    /**
     * 
     * @param parent
     *          the start node of the connection
     * @param child
     *          the end node of the connection
     * @return true if units are connected
     *
     */
    public boolean isConnected(UnitNode parent, UnitNode child) {
        boolean connected;
        if (child.getParentNodes().contains(parent)) {
            connected = true;
        } else {
            connected = false;
        }
        return connected;
    }
    /**
     * 
     * @return course
     */
    public Course getCourse() {
        Course c = new Course();
        return c.loadDbObject(course);
    }

    /**
     * 
     * @param parent
     *            the parent UnitNode
     * @param child
     *            the child UnitNode
     * @return boolean
     */
    public boolean deleteConnection(UnitNode parent, UnitNode child) {
        for (UnitNode tmp : this.unitCollection) {
            if (parent.getUnitNodeTitle() == tmp.getUnitNodeTitle())
                tmp.removeChildNode(child);
            if (child.getUnitNodeTitle() == tmp.getUnitNodeTitle())
                tmp.removeParentNode(parent);
        }
        return true;
    }

}
