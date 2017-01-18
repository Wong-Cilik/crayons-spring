package com.crayons_2_0.service.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.MultiValueMap;

import com.crayons_2_0.model.graph.Unit;

public class LearningProgressDAO {
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    public void createDbTable() {
        
    }
    
    public MultiValueMap<User, Unit> findAll() {
        return null;
    }
    
    public void save(User user, Unit unit, boolean available, boolean processed) {
        
    }
}
