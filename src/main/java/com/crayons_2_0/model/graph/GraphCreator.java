package com.crayons_2_0.model.graph;

import com.vaadin.ui.GridLayout;

public class GraphCreator {
    /* generate a graph by using information from the databases;
     * if user is an author return an author view
     * otherwise return a student view */
    public static GridLayout getGraphView(long graphID, long userID) {
        Graph graph = new Graph(graphID);
        return getStudentView(graph);
    }
    
    // the view where some units can be unavailable 
    private static GridLayout getStudentView(Graph graph) {
        return null;
    }
    
    // all units are available
    private static GridLayout getAuthorView(Graph graph) {
        return null;
    }
}
