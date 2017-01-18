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
    
    public boolean insertUnit(User user, Unit unit, boolean available, boolean processed) {
        return true;
    }
    
    public boolean removeUnit(User user, Unit unit) {
        return true;
    }
    
    public boolean insertAllGraphUnits(User user, Graph graph) {
        return true;
    }
    
    public void setAvailability(User user, Unit unit, boolean available) {
        
    }
    
    public boolean getAvailability(User user, Unit unit) {
        return true;
    }
    
    public void setProgress(User user, Unit unit, boolean processed) {
        
    }
    
    public boolean getProgress(User user, Unit unit) {
        return true;
    }
}
