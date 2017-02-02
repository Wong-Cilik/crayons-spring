package com.crayons_2_0.service.database;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.crayons_2_0.component.Unit;
import com.crayons_2_0.model.Course;
import com.crayons_2_0.model.graph.UnitNode;
@Component
public class UnitService2 {
	
	@Autowired
    private UnitDAO2 unitDAO2;
	
	public List<Unit> findAll() {
	    List<Unit> res = unitDAO2.findAll();
	    return res;
	}
	
	public List<Unit> findUnitsOfCourse(Course course) {
		List<Unit> allUnits = findAll();
		List<Unit> unitsOfCourse = new LinkedList<Unit>();
		
		// Alternativ mit SQL Fremdschlüssel 
		for (Unit tmpUnit : allUnits) {
			if (tmpUnit.getCourseTitle().equals(course.getTitle())) {
				unitsOfCourse.add(tmpUnit);
			}
			
		}
		return unitsOfCourse;
	}
	
	
	
	// ----------------------------------------------------------------------
	
	
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

	public List<Unit> getUnitsOfCourse(Course course) {
		// TODO Auto-generated method stub
		return null;
	}

}
