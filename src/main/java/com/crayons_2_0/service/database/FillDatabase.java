package com.crayons_2_0.service.database;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.spring.annotation.SpringComponent;

@SpringComponent
public class FillDatabase {
	
    @Autowired
    private CourseDAO courseDAO;
    
    @Autowired
    private CourseDAO userDAO;
    
	public void fillDatabase() {
		
	}
}
