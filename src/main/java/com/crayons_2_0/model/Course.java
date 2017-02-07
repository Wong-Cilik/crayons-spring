package com.crayons_2_0.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.crayons_2_0.component.Unit;
import com.crayons_2_0.model.graph.Graph;
import com.crayons_2_0.model.graph.UnitNode;
@Component
public class Course {
	
	private String title;
	private String description;
	
	private CrayonsUser author;
	//private String authorName;
	






	private List<CrayonsUser> coAuthors;
	
	private List<CrayonsUser> students;

	private Date creationTime;
	
	private List<UnitNode> Units;		// Struktur!!!!! 
	
	
	private List<Unit> units2 = new ArrayList<Unit>();
	
	
	
	
	
	// --------------------------------------------------------------
	
	public Course (String title, String description, CrayonsUser author) {
		this.setTitle(title);
		this.setDescription(description);
		this.setAuthor(author);
		
	}
	
	public Course() {
        // TODO Auto-generated constructor stub
    }
	
	
	public void setUnits2(List<Unit> units) {
		this.units2 = units;
	}
	
	public List<Unit> getUnits2() {
		return units2;
	}
	
	// --------------------------------------------------------------
	
	
	
	
	
	public List<String> getUnitsTitle() {
		//TODO
		return null; //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	}
	
	
	
	

	private Graph graph;
	
	public Course(String title, CrayonsUser author) {
	    this.title = title;
	    this.setAuthor(author);
	}
	
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Graph getGraph() {
        return graph;
    }
    
    public void setGraph(Graph graph) {
        this.graph = graph;
    }
    
    public Date getCreationTime() {
        return creationTime;
    }

	/**
	 * @return the author
	 */
	public CrayonsUser getAuthor() {
		return author;
	}

	/**
	 * @param author the author to set
	 */
	public void setAuthor(CrayonsUser author) {
		this.author = author;
	}

	/**
	 * @return the units
	 */
	public List<UnitNode> getUnits() {
		return Units;
	}

	/**
	 * @param units the units to set
	 */
	public void setUnits(List<UnitNode> units) {
		Units = units;
	}

	/**
	 * @return the coAuthors
	 */
	public List<CrayonsUser> getCoAuthors() {
		return coAuthors;
	}

	/**
	 * @param coAuthors the coAuthors to set
	 */
	public void setCoAuthors(List<CrayonsUser> coAuthors) {
		this.coAuthors = coAuthors;
	}

	/**
	 * @return the students
	 */
	public List<CrayonsUser> getStudents() {
		return students;
	}

	/**
	 * @param students the students to set
	 */
	public void setStudents(List<CrayonsUser> students) {
		this.students = students;
	}
	

}