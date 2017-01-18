package com.crayons_2_0.service.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.MultiValueMap;

import com.crayons_2_0.model.graph.Graph;
import com.crayons_2_0.model.graph.Unit;

public class LearningProgressService {

    @Autowired
    private LearningProgressDAO learningProgressDAO;
    
    public MultiValueMap<User, Unit> findAll() {
        return null;
    }
    
    public boolean insertUserUnitPair(User user, Unit unit, boolean available, boolean processed) {
        return true;
    }
    
    public boolean removeUserUnitPair(User user, Unit unit) {
        return true;
    }
    
    public boolean insertAllGraphUnits(User user, Graph graph) {
        return true;
    }
    
    public void setUnitAvailability(User user, Unit unit, boolean available) {
        
    }
    
    public boolean getUnitAvailability(User user, Unit unit) {
        return true;
    }
    
    public void setUnitProgress(User user, Unit unit, boolean processed) {
        
    }
    
    public boolean getUnitProgress(User user, Unit unit) {
        return true;
    }
}
