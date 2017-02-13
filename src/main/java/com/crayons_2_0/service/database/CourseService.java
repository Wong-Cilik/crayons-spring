package com.crayons_2_0.service.database;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.crayons_2_0.model.Course;
import com.crayons_2_0.model.CrayonsUser;
import com.vaadin.spring.annotation.SpringComponent;
@SpringComponent
public class CourseService {
    
    @Autowired
    private CourseDAO courseDAO;
    
    @Autowired
    private UnitService2 unitService;
    
    /**
     * 
     * @return
     */
    public List<Course> findAll() {
        List<Course> res = courseDAO.findAll();
        for (Course tmpCourse : res) {
        	tmpCourse.setUnits(unitService.findUnitsOfCourse(tmpCourse));
        }
        return res;
    }
    
    /**
     * 
     * @param courseTitle
     * @return
     */
    public Course findCourseByTitle(String courseTitle) {
        for (Course tmpCourse : findAll()) {
        	if (tmpCourse.getTitle().equals(courseTitle)) {
        		return tmpCourse;
        	}
        }
        return null;
    }
    
    
    
    // -------------------------------------------------------------------
    
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
        
        // Wenn Kurs kreiert werden kann, erstelle kurs in DB
    	courseDAO.createDbTable();
        // Checke - Kurs Existiert? 
    	/*
        for (Course tmpCourse : courseDAO.findAll()) {
            if (tmpCourse.getTitle().equals(course.getTitle())) {
            	//Course exists -> update;
                courseDAO.update(tmpCourse);
            	return true;
            }
        }*/
    
        // Course exists not -> insert
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
		//TODO rückmeldung success failed
	}
}
