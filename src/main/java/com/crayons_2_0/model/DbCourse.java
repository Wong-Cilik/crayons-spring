package com.crayons_2_0.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.crayons_2_0.component.Unit;
import com.crayons_2_0.model.graph.Graph;

@SuppressWarnings("serial")
public class DbCourse implements Serializable {

	private String title;
	private String description;
	private String students;
	private CrayonsUser author;
	private List<CrayonsUser> users;
	private List<Unit> units = new ArrayList<Unit>();
	private Date creationTime;
	private Graph graph;

	DbCourse(String title, String description, String students,
			CrayonsUser author, List<CrayonsUser> users, List<Unit> units,
			Date creationTime, Graph graph) {
		this.title = title;
		this.description = description;
		this.students = students;
		this.author = author;
		this.users = users;
		this.units = units;
		this.creationTime = creationTime;
		this.graph = graph;

	}

	/**
	 * Returns the students of course
	 * 
	 * @return
	 */
	public String getStudents() {
		return students;
	}

	/**
	 * Returns the title of course
	 * 
	 * @return title of course
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Returns the description of Course
	 * 
	 * @return description of course
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Returns the author of course
	 * 
	 * @return the author of course
	 */
	public CrayonsUser getAuthor() {
		return author;
	}

	/**
	 * Returns the list of users of course
	 * 
	 * @return the users
	 */
	public List<CrayonsUser> getUsers() {
		return users;
	}

	/**
	 * Returns the units of course
	 * 
	 * @return the units of course
	 */
	public List<Unit> getUnits() {
		return units;
	}

	/**
	 * 
	 * @return the creationTime
	 */
	public Date getCreationTime() {
		return creationTime;
	}

	/**
	 * @return the graph
	 */
	public Graph getGraph() {
		return graph;
	}
}