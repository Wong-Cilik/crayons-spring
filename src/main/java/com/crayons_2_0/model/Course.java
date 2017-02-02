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
	private String author2;
	


	private List<CrayonsUser> coAuthors;

	private Date creationTime;
	
	private List<UnitNode> Units;		// Struktur!!!!! 
	
	
	private List<Unit> units2 = new ArrayList<Unit>();
	
	
	
	
	
	// --------------------------------------------------------------
	
	public Course (String title, String description, String author) {
		this.setTitle(title);
		this.setDescription(description);
		this.setAuthor2(author);
		
	}
	
	public Course() {
        // TODO Auto-generated constructor stub
    }
	public String getAuthor2() {
		return author2;
	}


	public void setAuthor2(String author2) {
		this.author2 = author2;
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
}