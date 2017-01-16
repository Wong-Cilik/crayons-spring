package com.crayons_2_0.model;

import java.util.Date;
import java.util.LinkedList;

import org.springframework.security.core.userdetails.User;

import com.crayons_2_0.model.graph.Graph;

public class Course {
	
	private String title;
	private String description;
	private Date creationTime;
	private LinkedList<User> authors;
	//private LinkedList<Unit> Units;		// Struktur!!!!! 
	private Graph graph;
	
	public Course(String title, String description) {
	    this.title = title;
	    this.description = description;
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
    
    public LinkedList<User> getAuthors() {
        return authors;
    }
    
    public void addAuthor(User author) {
        this.authors.add(author);
    }
    
    public void removeAuthor(User author) {
        this.authors.remove(author);
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
}
