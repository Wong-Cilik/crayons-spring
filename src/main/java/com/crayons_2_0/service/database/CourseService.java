package com.crayons_2_0.service.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;

import com.crayons_2_0.authentication.CurrentUser;
import com.crayons_2_0.model.Course;
import com.crayons_2_0.model.CrayonsUser;
import com.crayons_2_0.model.graph.Graph;
import com.crayons_2_0.model.graph.UnitNode;
import com.crayons_2_0.service.CourseDisplay;
import com.vaadin.spring.annotation.SpringComponent;

/**
 * Services for Access of Courses of DB (via CourseDAO)
 *
 *
 */
@SpringComponent
public class CourseService {

	@Autowired
	private CourseDAO courseDAO;

	@Autowired
	private UnitService unitService;

	@Autowired
	private CurrentUser currentUser;

	/**
	 * Returns all Courses of DB
	 * 
	 * @return all courses of DB
	 */
	public List<Course> findAll() {
		List<Course> res = courseDAO.findAll();
		for (Course tmpCourse : res) {
			tmpCourse.setUnits(unitService.findUnitsOfCourse(tmpCourse));
		}
		return res;
	}

	/**
	 * Returns an Course finding by his title
	 * 
	 * @param courseTitle
	 *            of Course
	 * @return course with the title searched for
	 */
	public Course findCourseByTitle(String courseTitle) {
		for (Course tmpCourse : findAll()) {
			if (tmpCourse.getTitle().equals(courseTitle)) {
				return tmpCourse;
			}
		}
		return null;
	}

	/**
	 * Returns all Courses of the User
	 * 
	 * @param user
	 *            to find all courses of
	 * @return all courses of the user searched for
	 */
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

	/**
	 * Return all AuthorCourses of user
	 * 
	 * @param author
	 *            to find all AuthorCourses of
	 * @return all AuthorCourses of the user searched for
	 */
	public List<Course> findAllAuthorCoursesOfUser(String author) {
		List<Course> allCourses = courseDAO.findAll();
		List<Course> authorCoursesOfUser = new LinkedList<Course>();

		for (Course tmpCourse : allCourses) {
			if (tmpCourse.getAuthor().getUsername().equals(author)) {
				authorCoursesOfUser.add(tmpCourse);
			}
		}

		return authorCoursesOfUser;

	}

	/**
	 * Insert an Course to DB
	 * 
	 * @param course
	 *            to insert
	 * @return true if successfull
	 */
	public boolean insertCourse(Course course) {
		courseDAO.insert(course);
		saveCourseData(course.getGraph(), course.getTitle());
		return true;
	}

	/**
	 * Removes an Course from DB
	 * 
	 * @param course
	 * @return true if successfull
	 */
	public boolean removeCourse(Course course) {
		courseDAO.remove(course);
		return true;
	}

	/**
	 * 
	 * @param courseTitle
	 *            Old course title
	 * @param value
	 *            new course title
	 * @param value2
	 *            new description
	 */
	public void update(Course course) {
		courseDAO.update(course);
	}

	/**
	 * Insert students in course
	 * 
	 * @param students
	 *            array with name of students to insert
	 * @param title
	 *            of Course
	 */
	public void insertStudent(String[] students, String title) {
		String tmp2 = "";
		for (String tmp : students) {
			tmp2 = tmp2 + "/" + tmp;
		}
		courseDAO.updateStudents(tmp2, title);
	}

	/**
	 * 
	 * @param title
	 *            of course
	 * @return String[] with students in course
	 */
	public String[] getStudents(String title) {
		String students = findCourseByTitle(title).getStudents();
		String[] studentsArray = students.split("/");
		if (!students.equals("")) {
			return studentsArray;
		} else {
			return null;
		}
	}

	public void saveCourseData(Graph data, String title) {
		File file = new File(title + ".bin");
		ObjectOutputStream out;
		try {
			out = new ObjectOutputStream(new FileOutputStream(file));
			out.writeObject(data);
			courseDAO.saveData(file, title);
			out.flush();
			out.close();
			Files.delete(file.toPath());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Graph getCourseData(String title) {
		ObjectInputStream in;
		Graph graph = null;
		File file = new File(title + ".bin");
		try {
			courseDAO.getData(title);
			in = new ObjectInputStream(new FileInputStream(file));
			graph = (Graph) in.readObject();
			in.close();
			Files.delete(file.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return graph;
	}

	/**
	 * Saves an DummyGraph
	 * 
	 * @param title
	 *            of course to save dummyGraph
	 */
	public void saveDummyGraph(String title) {
		String dummy = "dummy";
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		CrayonsUser dummyUser = new CrayonsUser("first", "last", "dummy",
				"pass", "German", 2, true, true, false, false, authorities);
		Course dummyCourse = new Course(dummy, dummyUser);
		Graph dummyGraph = new Graph(dummyCourse);

		UnitNode one = new UnitNode("1-" + title, dummyGraph.getStartUnit(), dummyGraph);
		UnitNode two = new UnitNode("2-" + title, one, dummyGraph);
		UnitNode three = new UnitNode("3-" + title, two, dummyGraph);

		/*one.setLayout(unitService.getDummyLayout());
		two.setLayout(unitService.getDummyLayout());
		three.setLayout(unitService.getDummyLayout());*/
		
		unitService.storeUnitDummyData(one.getUnitNodeTitle(), title);
		unitService.storeUnitDummyData(two.getUnitNodeTitle(), title);
		unitService.storeUnitDummyData(three.getUnitNodeTitle(), title);

		dummyGraph.addUnit(one, one.getParentNodes());
		dummyGraph.addUnit(two, two.getParentNodes());
		dummyGraph.addUnit(three, three.getParentNodes());

		saveCourseData(dummyGraph, title);
	}

	/**
	 * Removes an Student of Course
	 * 
	 * @param title
	 *            of Course
	 * @param user
	 *            id (email) of user to delete
	 */
	public void removeStudent(String title, String user) {
		String updatedStudents = "";
		String[] tmp = getStudents(title);
		for (int i = 1; i < tmp.length; i++) {
			if (!tmp[i].equals(user)) {
				updatedStudents = updatedStudents + "/" + tmp[i];
			}
		}
		courseDAO.updateStudents(updatedStudents, title);

	}

	public Graph getDummyGraph() {
		String dummy = "dummy";
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		// CrayonsUser dummyUser = new User(dummy, "pass", true, true, false,
		// false, authorities);
		CrayonsUser dummyUser = new CrayonsUser("first", "last", "dummy",
				"pass", "German", 2, true, true, false, false, authorities);
		Course dummyCourse = new Course(dummy, dummyUser);
		Graph dummyGraph = new Graph(dummyCourse);

		// @DB UnitNodes will be created and added to their courses in the
		// UnitCreationWindow
		UnitNode one = new UnitNode("1.1", dummyGraph.getStartUnit(),
				dummyGraph);
		UnitNode three = new UnitNode("2.1", one, dummyGraph);

		dummyGraph.addUnit(one, one.getParentNodes());
		dummyGraph.addUnit(three, three.getParentNodes());
		return dummyGraph;
	}

	public Collection<CourseDisplay> searchAll(String input) {
		Collection<CourseDisplay> collector = new ArrayList<CourseDisplay>();
		List<Course> courseList = new ArrayList<Course>();
		courseList.addAll(courseDAO.searchAll(input, "title"));
		courseList.addAll(courseDAO.searchAll(input, "description"));
		for (Course tmpCourse : courseList) {
			String status = "";
			if (currentUser.get().getEmail().equals(tmpCourse.getAuthor())) {
				status = "Autor";
			} else if (tmpCourse.getUsers().contains(currentUser.get())) {
				status = "Beigetreten";
			} else {
				status = "Privat";
			}
			collector.add(new CourseDisplay("", tmpCourse.getTitle(), tmpCourse
					.getAuthor().getFirstName()
					+ " "
					+ tmpCourse.getAuthor().getLastName(), status));
		}
		if (collector.size() == 0) {
			collector.add(new CourseDisplay("", "", "", ""));
		}
		return collector;
	}
}
