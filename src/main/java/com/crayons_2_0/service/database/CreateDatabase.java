package com.crayons_2_0.service.database;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.crayons_2_0.authentication.CurrentUser;
import com.crayons_2_0.model.Course;
import com.crayons_2_0.model.CrayonsUser;
import com.vaadin.spring.annotation.SpringComponent;

@SpringComponent
public class CreateDatabase {

	@Autowired
	private CourseDAO courseDAO;

	@Autowired
	private UserDAO userDAO;

	CurrentUser currentUser;

	public void fillDatabase() {
		// userDAO.createTable();
		// courseDAO.createDbTable();

		List<Course> y = courseDAO.findAll();
		for (Course tmpCrs : y) {
			courseDAO.remove(tmpCrs);
		}

		List<CrayonsUser> x = userDAO.findAll();
		for (CrayonsUser tmpUser : x) {
			if (!tmpUser.getEmail().equals(currentUser.get())) {
				userDAO.deleteUser(tmpUser);
			}
		}

	}
}
