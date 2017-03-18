package com.crayons_2_0.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import com.crayons_2_0.component.Unit;
import com.crayons_2_0.model.graph.Graph;
import com.crayons_2_0.service.LanguageService;

@Component
public class Course {

	private String title;
	private String description;
	private String students;
	private CrayonsUser author;
	private List<CrayonsUser> users;
	private List<Unit> units = new ArrayList<Unit>();
	private Date creationTime;
	private Graph graph;

	private ResourceBundle lang = LanguageService.getInstance().getRes();

	private final int MAX_LENGTH_OF_TITLE = 30; // ?????????????????????

	public Course() {

	}

	public Course(String title, String description, CrayonsUser author,
			String students) {
		this.setTitle(title);
		this.setDescription(description);
		this.setAuthor(author);
		this.setStudents(students);

	}

	public Course(String title, CrayonsUser author) {
		this.setTitle(title);
		this.setAuthor(author);
		creationTime = new Date();
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
	 * Sets the Studens of Course
	 * 
	 * @param students
	 *            of course
	 */
	public void setStudents(String students) {

		// ToDo: Regex zur überprüfung

		this.students = students;
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
	 * Sets the Title of course
	 * 
	 * @param title
	 *            to set
	 */
	public void setTitle(String title) {
		if (title.isEmpty()) {
			throw new IllegalArgumentException(String.format(
					lang.getString("RequiredField"),
					lang.getString("CourseTitle")));
		}

		if (title.length() > MAX_LENGTH_OF_TITLE) {
			throw new IllegalArgumentException(String.format(
					lang.getString("ShouldBeAtMostNCharactersLong"),
					lang.getString("CourseTitle"), MAX_LENGTH_OF_TITLE));
		}

		this.title = title;
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
	 * Sets the description of course
	 * 
	 * @param description
	 *            to set
	 */
	public void setDescription(String description) {
		if (description.isEmpty()) {
			throw new IllegalArgumentException(String.format(
					lang.getString("RequiredField"),
					lang.getString("CourseDescription")));
		}

		this.description = description;
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
	 * Sets the author of course
	 * 
	 * @param author
	 *            to set
	 */
	public void setAuthor(CrayonsUser author) {
		if (author == null) {
			throw new IllegalArgumentException("Course author can't be Null");
		}

		this.author = author;
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
	 * Sets the list of users of course
	 * 
	 * @param users
	 *            list of users to set
	 */
	public void setUsers(List<CrayonsUser> users) {
		if (users == null) {
			throw new IllegalArgumentException("Course users can't be Null");
		}
		
		this.users = users;
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
	 * @param units
	 *            the units to set
	 */
	public void setUnits(List<Unit> units) {
		if (units == null) {
			throw new IllegalArgumentException("Course units can't be Null");
		}
		
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
	 * @param creationTime
	 *            the creationTime to set
	 */
	public void setCreationTime(Date creationTime) {
		if (creationTime == null) {
			throw new IllegalArgumentException("Course creationTime can't be Null");
		}
		
		this.creationTime = creationTime;
	}

	/**
	 * @return the graph
	 */
	public Graph getGraph() {
		return graph;
	}

	/**
	 * @param graph
	 *            the graph to set
	 */
	public void setGraph(Graph graph) {
		if (graph == null) {
			throw new IllegalArgumentException("Course graph can't be Null");
		}
		
		this.graph = graph;
	}

	/**
	 * @return the MAX_LENGTH_OF_TITLE
	 */
	public int getMAX_LENGTH_OF_TITLE() {
		return MAX_LENGTH_OF_TITLE;
	}

	public DbCourse createDbObject() {
		return new DbCourse(title, description, students, author, users, units,
				creationTime, graph);
	}

	public Course loadDbObject(DbCourse dbCourse) {
		if (dbCourse == null) {
			throw new IllegalArgumentException("Course dbCourse to load can't be Null");
		}
		
		this.title = dbCourse.getTitle();
		this.description = dbCourse.getDescription();
		this.students = dbCourse.getStudents();
		this.author = dbCourse.getAuthor();
		this.users = dbCourse.getUsers();
		this.units = dbCourse.getUnits();
		this.creationTime = dbCourse.getCreationTime();
		this.graph = dbCourse.getGraph();
		return this;
	}
}