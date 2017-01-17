package com.crayons_2_0.service.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.userdetails.User;

import com.crayons_2_0.model.graph.Graph;

public class GraphDAO {
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    public void createDbTable() {
        
    }
    
    public List<Graph> findAll() {
        return null;
    }
    
    public List<Graph> findById(long id) {
        return null;
    }
    
    public boolean insertGraph(Graph graph) {
        return true;
    }
    
    public boolean deleteGraph(Graph graph) {
        return true;
    }
}
