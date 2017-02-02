package com.crayons.view.dagred3;

import java.util.ArrayList;
import com.vaadin.shared.ui.JavaScriptComponentState;

@SuppressWarnings("serial")
public class DagreState extends JavaScriptComponentState {
    private ArrayList<String> unitNodes;
    private ArrayList<String> edges;

    public ArrayList<String> getUnitNodes() {
        return unitNodes;
    }

    public ArrayList<String> getEdges() {
        return edges;
    }

    public void setUnitNodes(final ArrayList<String> nodes) {
        this.unitNodes = nodes;
    }
    public void setEdges(final ArrayList<String> edges) {
        this.edges = edges;
    }

    public void setGraph(final ArrayList<String> nodes, final ArrayList<String> edges) {
        this.unitNodes = nodes;
        this.edges = edges;
    }

}
