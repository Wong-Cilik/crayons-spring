package com.crayons_2_0.service.database;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.crayons_2_0.model.graph.Unit;

public class ConnectionDAO {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public void createDbTable() {
        
    }
    
    public Map<Unit, Unit> findAll() {
        return null;
    }
    
    public void save(Unit from, Unit to) {
        
    }
}
