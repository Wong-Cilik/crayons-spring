package com.crayons_2_0.model.graph;

public class Connection {
    private Unit from;
    private Unit to; 
    
    public Connection(Unit from, Unit to) {
        this.from = from;
        this.to = to;
    }
        
    public Unit getFrom() {
        return from;
    }
    
    public void setFrom(Unit from) {
        this.from = from;
    }
    
    public Unit getTo() {
        return to;
    }
    
    public void setTo(Unit to) {
        this.to = to;
    }
}
