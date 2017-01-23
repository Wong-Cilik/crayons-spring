package com.crayons.view.jointjs;

import java.awt.List;
import java.util.ArrayList;

import com.vaadin.shared.ui.JavaScriptComponentState;

public class GraphTryState extends JavaScriptComponentState {
    
    private ArrayList<Integer> coords;
 
    public ArrayList<Integer> getCoords() {
        return coords;
    }
 
    public void setCoords(final ArrayList<Integer> coords) {
        this.coords = coords;
    }
}
