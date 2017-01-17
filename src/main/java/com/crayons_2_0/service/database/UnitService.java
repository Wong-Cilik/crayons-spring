package com.crayons_2_0.service.database;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.crayons_2_0.model.Course;
import com.crayons_2_0.model.graph.Unit;

public class UnitService {

	@Autowired
    private UnitDAO unitDAO;
	
	public List<Unit> findAll() {
	    List<Unit> res = unitDAO.findAll();
	    return res;
	}
	
	public List<Unit> findUnitByTitle(String unitTitle) {
		//Todo
		return null;
	}
	
    public Unit findUnitById(long unitId) {
        return null;
    }
    
    public List<Unit> findUnitsByGraphId(long graphId) {
        return null;
    }

    public boolean insertUnit(Unit unit) {
        return true;
    }
    
    public boolean removeUnit(Unit unit) {
        return true;
    }
}
