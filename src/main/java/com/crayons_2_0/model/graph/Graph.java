package com.crayons_2_0.model.graph;

import com.crayons_2_0.model.graph.Unit.UnitType;

public class Graph {
    private final Unit startUnit; 
    private final Unit endUnit;
    private boolean unitsAvailable;
    
    // create a new graph when a new course is created
    public Graph() {
        this.unitsAvailable = false;
        this.startUnit = new Unit("Start", UnitType.START, unitsAvailable, this);
        this.endUnit = new Unit("End", UnitType.END, unitsAvailable, this);
    }
    
    // initialize a graph using the information from the databases
    public Graph(long graphID) {
        this.unitsAvailable = false;
        this.startUnit = new Unit("Start", UnitType.START, unitsAvailable, this);
        this.endUnit = new Unit("End", UnitType.END, unitsAvailable, this);
    }
    
    public Unit getStartUnit() {
        return startUnit;
    }

    public Unit getEndUnit() {
        return endUnit;
    }

    public boolean areUnitsAvailable() {
        return unitsAvailable;
    }

    public void setUnitsAvailable(boolean unitsAvailable) {
        this.unitsAvailable = unitsAvailable;
    }
    
    // add a new unit and connect to the start
    public boolean addUnit(String unitName, UnitType unitType){
        Unit newUnit = new Unit(unitName, unitType, unitsAvailable, this);
        return true;
    }
    
    public boolean deleteUnit(Unit learningUnit) {
        return true;
    }
    
    // remove an old connection if exist (alert?) and add a new one
    public boolean addConnection(Unit from, Unit to) {
        return true;
    }
    
    public boolean removeConnection(Unit from, Unit to) {
        return true;
    }
}
