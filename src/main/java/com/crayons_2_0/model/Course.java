package com.crayons_2_0.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.crayons_2_0.component.Unit;
import com.crayons_2_0.model.graph.Graph;

@SuppressWarnings("serial")
@Component
public class Course implements Serializable {
	/**
	 * 
	 */

	// checked
	private String title;
	private String description;
	private String students;
	private CrayonsUser author;
	private List<CrayonsUser> users;
	private List<Unit> units = new ArrayList<Unit>();
	private Date creationTime;
	private Graph graph;
	
	private final int MAX_LENGTH_OF_TITLE = 30; // ????????????????????? 

	public Course(String title, String description, CrayonsUser author) {
		this.setTitle(title);
		this.setDescription(description);
		this.setAuthor(author);

	}

	public Course(String title, String description, CrayonsUser author,
			String students) {
		this.setTitle(title);
		this.setDescription(description);
		this.setAuthor(author);
		this.setStudents(students);

	}

	public Course() {
		// TODO Auto-generated constructor stub
	}

	public Course(String title, CrayonsUser author) {
		this.setTitle(title);
		this.setAuthor(author);
		creationTime = new Date();
	}
	
	
	/**
	 * Returns the students of course
	 * @return
	 */
	public String getStudents() {
		return students;
	}
	
	/**
	 * Sets the Studens of Course
	 * @param students of course
	 */
	public void setStudents(String students) {
		
		//ToDo: Regex zur überprüfung
		
		this.students = students;
	}

	/**
	 * Returns the title of course
	 * @return title of course
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the Title of course
	 * @param title to set
	 */
	public void setTitle(String title) {
		if (title.isEmpty()) {
			throw new IllegalArgumentException("Title of Course can't be empty");
		}
		
		if (title.length() > MAX_LENGTH_OF_TITLE) {
			throw new IllegalArgumentException("Title of Course is longer than allowed");
		}
		
		this.title = title;
	}

	/**
	 * Returns the description of Course
	 * @return description of course
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description of course
	 * @param description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Returns the author of course
	 * @return the author of course
	 */
	public CrayonsUser getAuthor() {
		return author;
	}

	/**
	 * Sets the author of course
	 * @param author to set
	 */
	public void setAuthor(CrayonsUser author) {
		if (author == null) {
			throw new IllegalArgumentException("Author of Course can't be Null");
		}
		
		this.author = author;
	}

	/**
	 * Returns the list of users of course
	 * @return the users
	 */
	public List<CrayonsUser> getUsers() {
		return users;
	}

	/**
	 * Sets the list of users of course
	 * @param users list of users to set
	 */
	public void setUsers(List<CrayonsUser> users) {
		this.users = users;
	}

	/**
	 * Returns the units of course
	 * @return the units of course
	 */
	public List<Unit> getUnits() {
		return units;
	}

	/**
	 * @param units the units to set
	 */
	public void setUnits(List<Unit> units) {
		this.units = units;
	}

	/**
	 * 
	 * @return the creationTime
	 */
	public Date getCreationTime() {
		return creationTime;
	}

	/**
	 * @param creationTime the creationTime to set
	 */
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	/**
	 * @return the graph
	 */
	public Graph getGraph() {
		return graph;
	}

	/**
	 * @param graph the graph to set
	 */
	public void setGraph(Graph graph) {
		this.graph = graph;
	}

	/**
	 * @return the MAX_LENGTH_OF_TITLE
	 */
	public int getMAX_LENGTH_OF_TITLE() {
		return MAX_LENGTH_OF_TITLE;
	}

	
}