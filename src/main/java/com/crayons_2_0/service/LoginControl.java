package com.crayons_2_0.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.security.core.userdetails.User;

import com.crayons_2_0.service.database.DatabaseException;
import com.crayons_2_0.service.database.JDBCConnection;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;

public class LoginControl {
	
	// Klasse vorerst nur zum DB Verständnis
	
	public static void checkAuthentication(String login, String password) throws DatabaseException {
		
		ResultSet set = null;
		
		try {
			// DB Zugriff
			Statement statement = JDBCConnection.getInstance().getStatement();
			set = statement.executeQuery("SELECT * "
					+ "FROM realm.user "
					+ "WHERE realm.user.login = \' " + login + "\'"
					+ "AND realm.user.password = \'" + password + "\'");
		
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Fehler im SQL-Befehl!");
		}
		
		User user = null;
		
		try {
			
			if (set.next()) {
				//user = new User();
				//user.setLogin(set.getString(1));
				//user.setName(set.getString(3));
			} else {
				//ERROR HANDLING NoSuchUserOrPassword
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			JDBCConnection.getInstance().closeConnection();
		}
		
		VaadinSession session = UI.getCurrent().getSession();
		session.setAttribute("currentUser",  user);
		
		UI.getCurrent().getNavigator().navigateTo("blabla");
	}

}
