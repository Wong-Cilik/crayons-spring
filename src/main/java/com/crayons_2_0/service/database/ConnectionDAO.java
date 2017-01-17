package com.crayons_2_0.service.database;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.crayons_2_0.model.graph.Connection;

public class ConnectionDAO {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public void createDbTable() {
        
    }
    
    public List<Connection> findAll() {
        return null;
    }
    
    public void save(Connection connection) {
        
    }
}
