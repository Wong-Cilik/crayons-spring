package com.crayons_2_0.service.database;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.crayons_2_0.model.Course;
import com.crayons_2_0.model.CrayonsUser;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.TextField;
@SpringComponent
public class CourseService {
    
    @Autowired
    private CourseDAO courseDAO;
    
    @Autowired
    private UnitService2 unitService;
    
    public List<Course> findAll() {
        List<Course> res = courseDAO.findAll();
        for (Course tmpCourse : res) {
        	tmpCourse.setUnits(unitService.findUnitsOfCourse(tmpCourse));
        }
        return res;
    }
    
    public Course findCourseByTitle(String courseTitle) {
        for (Course tmpCourse : findAll()) {
        	if (tmpCourse.getTitle().equals(courseTitle)) {
        		return tmpCourse;
        	}
        }
        return null;
    }
    
    public List<Course> findAllCoursesOfUser(CrayonsUser user) {
    	List<Course> allCourses = courseDAO.findAll();
    	List<Course> coursesOfUser = new LinkedList<Course>();
    	
    	for (Course tmpCourse : allCourses) {
    		if (tmpCourse.getUsers().contains(user)) {
    			coursesOfUser.add(tmpCourse);
    		}
    	}
    	
    	return coursesOfUser;
    }
    
    public List<Course> findAllAuthorCoursesOfUser(CrayonsUser user) {
    	List<Course> allCourses = courseDAO.findAll();
    	List<Course> authorCoursesOfUser = new LinkedList<Course>();
    	
    	//TODO hier equals richtig ??
    	for (Course tmpCourse : allCourses) {
    		if (tmpCourse.getAuthor().equals(user)) {
    			authorCoursesOfUser.add(tmpCourse);
    		}
    	}
    	
    	return authorCoursesOfUser;
    	
    }
    
    
    public boolean insertCourse(Course course) {
        
        // Checke - Kurs Existiert? 
    	/*
        for (Course tmpCourse : courseDAO.findAll()) {
            if (tmpCourse.getTitle().equals(course.getTitle())) {
            	//Course exists -> update;
                courseDAO.update(tmpCourse);
            	return true;
            }
        }*/
    
        courseDAO.insert(course);
        return true;
    }   
    
    public boolean removeCourse(Course course) {
    	//TODO TryCatch!!!
    	courseDAO.remove(course);
        return true;
    }
    /**
     * 
     * @param courseTitle 	Old course title
     * @param value			new course title
     * @param value2		new description
     */
	public void update(Course course) {
		courseDAO.update(course);
	}


	public void insertStudent(String[] students, String title) {
		String tmp = students[0];
		for (int i = 1; i < students.length; i++) {
			tmp = " " + students[i];
		}
		courseDAO.updateStudents(tmp, title);
	}

	public String[] getStudents() {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteCourse(TextField courseTitleField) {
		// TODO Auto-generated method stub
		
	}
}
