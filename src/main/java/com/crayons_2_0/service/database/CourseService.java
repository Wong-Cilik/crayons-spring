package com.crayons_2_0.service.database;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;

import com.crayons_2_0.component.Unit;
import com.crayons_2_0.model.Course;
import com.crayons_2_0.model.CrayonsUser;
import com.crayons_2_0.model.Course;

public class CourseService {
    
    @Autowired
    private CourseDAO courseDAO;
    
    @Autowired
    private UnitService2 unitService;
    
    public List<Course> findAll() {
        List<Course> res = courseDAO.findAll();
        for (Course tmpCourse : res) {
        	tmpCourse.setUnits2(unitService.findUnitsOfCourse(tmpCourse));
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
    
    
    
    // -------------------------------------------------------------------
    
    public List<Course> findAllCoursesOfUser(CrayonsUser user) {
        //TODO
        return null;
    }
    
    public boolean insertCourse(String title, CrayonsUser author) {
        
        // Wenn Kurs kreiert werden kann, erstelle kurs in DB
        
        // Checke - Kurs Existiert? 
        //ECEPTION werfen todo
        for (Course tmpCourse : courseDAO.findAll()) {
            if (tmpCourse.getTitle().equals(title)) {
                return false;
            }
        }
        
        Course newCourse = new Course(title,author);
    
        return true;
    }   
    
    public boolean removeCourse(Course course) {
        return true;
    }

    public List<Course> getAllCursesOfUser() {
        // TODO Auto-generated method stub
        return null;
    }

}
