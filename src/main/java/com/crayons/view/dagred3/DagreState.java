package com.crayons.view.dagred3;

import java.awt.List;
import java.util.ArrayList;
import java.util.Set;

import com.crayons_2_0.model.graph.Node;
import com.crayons_2_0.model.graph.UnitNode;
import com.vaadin.shared.ui.JavaScriptComponentState;

public class DagreState extends JavaScriptComponentState {
    private ArrayList<String> unitNodes;

    public ArrayList<String> getUnitNodes() {
        return unitNodes;
    }
     public void setUnitNodes(final ArrayList<String> set) {
        this.unitNodes = set;
    }

    
   
    
}
