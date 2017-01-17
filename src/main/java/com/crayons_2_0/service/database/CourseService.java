<<<<<<< HEAD
package com.crayons_2_0.service.database;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;

import com.crayons_2_0.model.Course;

public class CourseService {
	
	@Autowired
    private CourseDAO courseDAO;
	
	public List<Course> findAll() {
	    List<Course> res = courseDAO.findAll();
	    return res;
	}
	
	public Course getCourseByTitle(String courseTitle) {
		//TODO
		return null;
	}
	
	public List<Course> getAllCursesOfUser() {
		//TODO
		return null;
	}
	
	public boolean createNewCourse(String title, User author) {
		
		// Wenn Kurs kreiert werden kann, erstelle kurs in DB
		
		// Checke - Kurs Existiert?
		for (Course tmpCourse : courseDAO.findAll()) {
			if (tmpCourse.getTitle().equals(title)) {
				return false;
			}
		}
		
		Course newCourse = new Course(title, "No description");
		courseDAO.save(newCourse);
	
		return true;
	}
	

}
=======
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
	
	public Course getCourseByTitle(String courseTitle) {
		//TODO
		return null;
	}
	
	public List<Course> getAllCursesOfUser() {
		//TODO
		return null;
	}
	
	public boolean createNewCourse(String title, User author) {
		
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
	

}
>>>>>>> refs/remotes/origin/master
