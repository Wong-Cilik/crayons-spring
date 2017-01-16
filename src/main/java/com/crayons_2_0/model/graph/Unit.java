package com.crayons_2_0.model.graph;

import java.util.HashSet;
import java.util.Set;

public class Unit {
    private final Graph graph;
    private UnitType unitType;
    private boolean availability;
    private Set<Unit> nextUnits;
    // TODO: how to store the content?
    
    public Unit(UnitType unitType, boolean availability, Graph graph) {
        this.unitType = unitType;
        this.availability = availability;
        this.graph = graph;
        this.nextUnits = new HashSet<Unit>();
    }
    
    public Graph getGraph() {
        return graph;
    }

    public UnitType getUnitType() {
        return unitType;
    }

    public void setUnitType(UnitType unitType) {
        this.unitType = unitType;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }
    
    public Set<Unit> getNextUnits() {
        return nextUnits;
    }

    public void addNextUnit(Unit unit) {
        this.nextUnits.add(unit);
    }
    
    public void removeNextUnit(Unit unit) {
        this.nextUnits.remove(unit);
    }

    public enum UnitType {
        START, END, LEARNING, TEST
    }
}
