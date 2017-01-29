package com.crayons_2_0.service.database;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;

import com.crayons_2_0.model.graph.UnitNode;

public class ConnectionService {
    
    @Autowired
    private ConnectionDAO connectionDAO;
    
    public MultiValueMap<UnitNode, UnitNode> findAll() {
        return null;
    }
    
    public boolean insertConnection(UnitNode from, UnitNode to) {
        return true;
    }
    
    public boolean removeConnection(UnitNode from, UnitNode to) {
        return true;
    }
    
    public List<UnitNode> findAllPredecessors(UnitNode unit) {
        return null;
    }
    
    public List<UnitNode> findAllSuccessors(UnitNode unit) {
        return null;
    }
    
    public boolean removeAllConnectionsWithUnit(UnitNode unit) {
        return true;
    }
}
