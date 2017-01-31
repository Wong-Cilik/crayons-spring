package com.crayons.view.dagred3;

import java.awt.List;
import java.util.ArrayList;
import java.util.Set;

import com.crayons_2_0.model.graph.Node;
import com.crayons_2_0.model.graph.UnitNode;
import com.vaadin.annotations.JavaScript;
import com.vaadin.ui.AbstractJavaScriptComponent;

@JavaScript({"style.css","http://cpettitt.github.io/project/dagre-d3/v0.3.0/dagre-d3.js","http://d3js.org/d3.v3.min.js","com_crayons_view_dagred3_Dagre.js" })
public class Dagre extends AbstractJavaScriptComponent {
    public void setGraph(final ArrayList<String> nodes,final ArrayList<String> edges) {
        getState().setGraph(nodes,edges);
    }

    @Override
    public DagreState getState() {
        return (DagreState) super.getState();
    }
    
}