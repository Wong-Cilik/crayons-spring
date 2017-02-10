package com.crayons_2_0.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.crayons_2_0.component.Unit;
import com.crayons_2_0.model.graph.Graph;
import com.vaadin.spring.annotation.SpringComponent;
@Component
public class Course {
	//checked
	private String title;
	private String description;
	private CrayonsUser author;
	private List<CrayonsUser> users;
	private List<Unit> units = new ArrayList<Unit>();
	private Date creationTime;
	private Graph graph;
	
	public Course (String title, String description, CrayonsUser author) {
		this.setTitle(title);
		this.setDescription(description);
		this.setAuthor(author);
		
	}
	
	public Course() {
        // TODO Auto-generated constructor stub
    }

	public Course(String title, CrayonsUser author) {
	    this.title = title;
	    this.setAuthor(author);
	    creationTime = new Date();
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
    
    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

	public CrayonsUser getAuthor() {
		return author;
	}

	public void setAuthor(CrayonsUser author) {
		this.author = author;
	}

	public List<Unit> getUnits() {
		return units;
	}

	public void setUnits(List<Unit> units) {
		this.units = units;
	}

	public List<CrayonsUser> getUsers() {
		return users;
	}

	public void setUsers(List<CrayonsUser> users) {
		this.users = users;
	}
}