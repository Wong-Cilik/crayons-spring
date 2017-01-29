package com.crayons_2_0.service.database;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.crayons_2_0.model.Course;
import com.crayons_2_0.model.graph.UnitNode;

public class UnitService {

	@Autowired
    private UnitDAO unitDAO;
	
	public List<UnitNode> findAll() {
	    List<UnitNode> res = unitDAO.findAll();
	    return res;
	}
	
	public List<UnitNode> findUnitByTitle(String unitTitle) {
		//Todo
		return null;
	}
	
    public UnitNode findUnitById(long unitId) {
        return null;
    }
    
    public List<UnitNode> findUnitsByGraphId(long graphId) {
        return null;
    }

    public boolean insertUnit(UnitNode unit) {
        return true;
    }
    
    public boolean removeUnit(UnitNode unit) {
        return true;
    }
}
