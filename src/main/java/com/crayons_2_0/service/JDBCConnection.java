package com.crayons_2_0.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;

import org.apache.log4j.Logger;

// Link:     https://vimeo.com/123438169

public class JDBCConnection {
	
	// Als Singleton realisiert:
	
	private static JDBCConnection connection = null;
	
	/*
	private String url = "jdbc:postgresql://dumbo.inf.fh-bonn-rhein-sieg.de/salda2m";
	private Connection conn;
	private String login = "salda2m";
	private String password = "xxx";
	*/
	
	private String url = "jdbc:postgresql://localhost:2323/dbCrayons";
	private Connection conn;
	private String login = "postgres";
	private String password = "Schwan";
	
	
	public static JDBCConnection getInstance() throws DatabaseException {
		
		if (connection == null) {
			connection = new JDBCConnection();
		}
		
		return connection;
	}
	
	private JDBCConnection() throws DatabaseException {
		this.initConnection();
	}
	
	
	public void initConnection() throws DatabaseException {
		
		try {
			DriverManager.registerDriver(new org.postgresql.Driver());
		} catch (SQLException ex) {
			Logger.getLogger(JDBCConnection.class.getName()).log(null, ex);  //Level.SEVERE
		}
		
		this.openConnection();
		
	}
	
	public void openConnection() throws DatabaseException {
		
		Properties props = new Properties();
		props.setProperty("user", this.login);
		props.setProperty("password", this.password);
		
		try {
			this.conn = DriverManager.getConnection(this.url, props);
		} catch (SQLException ex) {
			Logger.getLogger(JDBCConnection.class.getName()).log(null, ex);  //Level.SEVERE
			throw new DatabaseException("Fehler beim Zugriff auf die Datenbank! Sichere Verbindung vorhanden?!");
		}
		
	}
	
	public Statement getStatement() throws DatabaseException {
		
		try {
			
			if(this.conn.isClosed()) {
				this.openConnection();
			}
			
			return this.conn.createStatement();
			
		} catch (SQLException ex) {
			Logger.getLogger(JDBCConnection.class.getName()).log(null, ex);  //Level.SEVERE
			return null;
		}
		
	}
	
	public void closeConnection() {
		try {
			this.conn.close();
		} catch (SQLException ex) {
			Logger.getLogger(JDBCConnection.class.getName()).log(null, ex);  //Level.SEVERE
		}
	}
	
	// ----------------------------------------------------------------------
	
	/*-----------------------------------------------------------------------
	public static void main(String[] args) {
		JDBCConnection connection = new JDBCConnection();
		try {
			connection.test();
		} catch (SQLException ex) {
			Logger.getLogger(JDBCConnection.class.getName()).log(null, ex);  //Level.SEVERE
		}
	}

	public void test() throws SQLException {
		
		DriverManager.registerDriver(new org.postgresql.Driver());
		String url = "jdbc:postgresql://dumbo.inf.fh-bonn-rhein-sieg.de/salda2m";
		Properties props = new Properties();
		props.setProperty("user", "salda2m");
		props.setProperty("password", "xxx");
		
		Connection conn = DriverManager.getConnection(url, props);
		
		Statement st;
		st = conn.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM realm.user ");
		
		while (rs.next()) {
			System.out.println("Login:" + rs.getString(1));
		}
		
		conn.close();
	}
	
	//-----------------------------------------------------------------
	*/
}
