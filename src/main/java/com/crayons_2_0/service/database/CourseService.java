package com.crayons_2_0.service.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;

import com.crayons_2_0.model.Course;
import com.crayons_2_0.model.CrayonsUser;
import com.crayons_2_0.model.graph.Graph;
import com.crayons_2_0.model.graph.UnitNode;
import com.vaadin.spring.annotation.SpringComponent;
@SpringComponent
public class CourseService {
    
    @Autowired
    private CourseDAO courseDAO;
    
    @Autowired
    private UnitService unitService;
    
    public List<Course> findAll() {
        List<Course> res = courseDAO.findAll();
        for (Course tmpCourse : res) {
        	tmpCourse.setUnits(unitService.findUnitsOfCourse(tmpCourse));
        }
        return res;
    }
    
    public Course findCourseByTitle(String courseTitle) {
        for (Course tmpCourse : findAll()) {
        	if (tmpCourse.getTitle().equals(courseTitle)) {
        		return tmpCourse;
        	}
        }
        return null;
    }
    
    public List<Course> findAllCoursesOfUser(CrayonsUser user) {
    	List<Course> allCourses = courseDAO.findAll();
    	List<Course> coursesOfUser = new LinkedList<Course>();

    	for (Course tmpCourse : allCourses) {
    		if (tmpCourse.getUsers().contains(user)) {
    			coursesOfUser.add(tmpCourse);
    		}
    	}
    	
    	return coursesOfUser;
    }
    
    public List<Course> findAllAuthorCoursesOfUser(CrayonsUser user) {
    	List<Course> allCourses = courseDAO.findAll();
    	List<Course> authorCoursesOfUser = new LinkedList<Course>();
    	
    	for (Course tmpCourse : allCourses) {
    		if (tmpCourse.getAuthor().equals(user)) {
    			authorCoursesOfUser.add(tmpCourse);
    		}
    	}
    	
    	return authorCoursesOfUser;
    	
    }
    
    
    public boolean insertCourse(Course course) {
        
        // Checke - Kurs Existiert? 
    	/*
        for (Course tmpCourse : courseDAO.findAll()) {
            if (tmpCourse.getTitle().equals(course.getTitle())) {
            	//Course exists -> update;
                courseDAO.update(tmpCourse);
            	return true;
            }
        }*/
    
        courseDAO.insert(course);
        return true;
    }   
    
    public boolean removeCourse(Course course) {
    	courseDAO.remove(course);
        return true;
    }
    /**
     * 
     * @param courseTitle 	Old course title
     * @param value			new course title
     * @param value2		new description
     */
	public void update(Course course) {
		courseDAO.update(course);
	}


	public void insertStudent(String[] students, String title) {
		String tmp2 = "";
		for (String tmp: students) {
			tmp2 = tmp2 + "/" + tmp ;
		}
		courseDAO.updateStudents(tmp2, title);
	}
	
	/**
	 * 
	 * @param title of course
	 * @return String[] with students in course
	 */
	public String[] getStudents(String title) {
		String students = findCourseByTitle(title).getStudents();
		String[] studentsArray = students.split("/");
		if(!students.equals("")) {
			return studentsArray;
		} else {
			return null;
		}
	}
	
	public void saveCourseGraph(Graph data) {
		System.out.print(data.getStartUnit());
		System.out.print(data.getEndUnit());
		System.out.print(data.getCourse());
		
		File file = new File ("tmp.bin");
		ObjectOutputStream out;
		try {
			out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(data);
            courseDAO.saveData(file, data.getCourse().getTitle());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
      }
	

	public Graph getCourseGraph(String title) {
		//courseDAO.getData();
		
		String dummy = "dummy";
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        //CrayonsUser dummyUser = new User(dummy, "pass", true, true, false, false, authorities);
        CrayonsUser dummyUser = new CrayonsUser("first", "last", "dummy", "pass", "German", 2, true, true, false, false, authorities);
        Course dummyCourse = new Course(dummy,dummyUser);
        Graph dummyGraph = new Graph(dummyCourse);
        
        //@DB UnitNodes will be created and added to their courses in the UnitCreationWindow
        UnitNode one = new UnitNode("one", dummyGraph.getStartUnit(), dummyGraph);
        UnitNode two = new UnitNode("two", dummyGraph.getStartUnit(), dummyGraph);
        UnitNode three = new UnitNode("three", two, dummyGraph);
        UnitNode four = new UnitNode("four", two, dummyGraph);
        UnitNode five = new UnitNode("five", three, dummyGraph);
        UnitNode six = new UnitNode("six", four, dummyGraph);
        UnitNode seven = new UnitNode("seven", five, dummyGraph);
        
        dummyGraph.addUnit(one, one.getParentNodes());
        dummyGraph.addUnit(two, two.getParentNodes());
        dummyGraph.addUnit(three, three.getParentNodes());
        dummyGraph.addUnit(four, four.getParentNodes());
        dummyGraph.addUnit(five, five.getParentNodes());
        dummyGraph.addUnit(six, six.getParentNodes());
        dummyGraph.addUnit(seven, seven.getParentNodes());
        
        
        
        return dummyGraph;
		//return new Graph(findCourseByTitle(title));
	}

	public void removeStudent(String title, String user) {
		String updatedStudents = "";
		String[] tmp = getStudents(title);
		for(int i = 1; i < tmp.length ; i++) {
			if(!tmp[i].equals(user)) {
				updatedStudents = updatedStudents + "/" + tmp[i];
			}
		}
		courseDAO.updateStudents(updatedStudents, title);
		
	}
}
