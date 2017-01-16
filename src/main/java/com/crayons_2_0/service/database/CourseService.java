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
		//Todo
		return null;
	}

}
