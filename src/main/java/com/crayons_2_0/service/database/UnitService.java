package com.crayons_2_0.service.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.crayons_2_0.component.Unit;
import com.crayons_2_0.component.UnitPageLayout;
import com.crayons_2_0.model.Course;
import com.crayons_2_0.model.graph.Graph;
import com.vaadin.spring.annotation.SpringComponent;

@SpringComponent
public class UnitService {

	@Autowired
	private UnitDAO unitDAO;
	@Autowired
	private CourseService courseService;


	public List<Unit> findAll() {
		List<Unit> res = unitDAO.findAll();
		return res;
	}

	/**
	 * Returns all Units of Course
	 * 
	 * @param course
	 *            to find the Units of
	 * @return the Units of the course
	 */	
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
		// Todo
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


	public void storeUnitDummyData(String unitTitle, String courseTitle) {
		unitDAO.insertUnit(unitTitle, courseTitle);
		saveUnitData(new UnitPageLayout(), unitTitle, courseTitle);
	}
	
	public void saveUnitData(UnitPageLayout data, String titleUnit, String titleCourse) {
		File file = new File(titleUnit + ".bin");
		ObjectOutputStream out;
		try {
			out = new ObjectOutputStream(new FileOutputStream(file));
			out.writeObject(data);
			unitDAO.saveData(file, titleUnit, titleCourse);
			out.flush();
			out.close();
			Files.delete(file.toPath());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public UnitPageLayout getUnitData(String unitTitle, String courseTitle) {
		ObjectInputStream in;
		UnitPageLayout layout = null;
		File file = new File(unitTitle + ".bin");
		try {
			unitDAO.getData(unitTitle, courseTitle);
			in = new ObjectInputStream(new FileInputStream(file));
			layout = (UnitPageLayout) in.readObject();
			in.close();
			Files.delete(file.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return layout;
	}
}
