package com.crayons_2_0.service.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import com.crayons_2_0.model.graph.UnitNode;
@Component
public class LearningProgressDAO {
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    public void createDbTable() {
        
    }
    
    public MultiValueMap<User, UnitNode> findAll() {
        return null;
    }
    
    public void save(User user, UnitNode unit, boolean available, boolean processed) {
        
    }
}
