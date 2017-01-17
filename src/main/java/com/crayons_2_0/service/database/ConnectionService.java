package com.crayons_2_0.service.database;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;

import com.crayons_2_0.model.graph.Unit;

public class ConnectionService {
    
    @Autowired
    private ConnectionDAO connectionDAO;
    
    public MultiValueMap<Unit, Unit> findAll() {
        return null;
    }
    
    public boolean insertConnection(Unit from, Unit to) {
        return true;
    }
    
    public boolean removeConnection(Unit from, Unit to) {
        return true;
    }
    
    public List<Unit> findAllPredecessors(Unit unit) {
        return null;
    }
    
    public List<Unit> findAllSuccessors(Unit unit) {
        return null;
    }
    
    public boolean removeAllConnectionsWithUnit(Unit unit) {
        return true;
    }
}
