package com.crayons_2_0.service.database;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import com.crayons_2_0.model.Course;
import com.crayons_2_0.model.CrayonsUser;
import com.crayons_2_0.model.Course;

public class CourseService {
    
    @Autowired
    private CourseDAO courseDAO;
    
    public List<Course> findAll() {
        List<Course> res = courseDAO.findAll();
        return res;
    }
    
    public Course findByCourseId(long courseId) {
        //TODO
        return null;
    }
    
    public Course findCourseByTitle(String courseTitle) {
        //TODO
        return null;
    }
    
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
