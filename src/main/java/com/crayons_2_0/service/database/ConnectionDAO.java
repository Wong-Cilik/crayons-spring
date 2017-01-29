package com.crayons_2_0.service.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.MultiValueMap;

import com.crayons_2_0.model.graph.UnitNode;

public class ConnectionDAO {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public void createDbTable() {
        
    }
    
    public MultiValueMap<UnitNode, UnitNode> findAll() {
        return null;
    }
    
    public void save(UnitNode from, UnitNode to) {
        
    }
}
