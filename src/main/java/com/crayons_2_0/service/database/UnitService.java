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
	
	public Course getUnitByTitle(String unitTitle) {
		//Todo
		return null;
	}
	
}
