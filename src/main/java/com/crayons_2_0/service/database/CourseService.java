package com.crayons_2_0.service.database;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import com.crayons_2_0.model.Course;

import com.crayons_2_0.model.Course;

public class CourseService {
    
    @Autowired
    private CourseDAO courseDAO;
    
    public List<Course> findAll() {
        List<Course> res = courseDAO.findAll();
        return res;
    }
    
    public Course findCourseId(long courseId) {
        //TODO
        return null;
    }
    
    public Course findCourseByTitle(String courseTitle) {
        //TODO
        return null;
    }
    
    public List<Course> findAllCoursesOfUser(User user) {
        //TODO
        return null;
    }
    
    public boolean insertCourse(String title, User author) {
        
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

}
