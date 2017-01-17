package com.crayons_2_0.service.database;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.crayons_2_0.model.graph.Connection;
import com.crayons_2_0.model.graph.Unit;

public class ConnectionService {
    
    @Autowired
    private ConnectionDAO connectionDAO;
    
    public List<Connection> findAll() {
        return null;
    }
    
    public boolean insertConnection(Connection connection) {
        return true;
    }
    
    public boolean removeConnection(Connection connection) {
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
