package junit;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.crayons_2_0.CrayonsSpringApplication;
import com.crayons_2_0.authentication.CurrentUser;
import com.crayons_2_0.model.Course;
import com.crayons_2_0.model.CrayonsUser;
import com.crayons_2_0.service.database.CourseDAO;
import com.crayons_2_0.service.database.CourseService;
import com.crayons_2_0.service.database.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CrayonsSpringApplication.class)
public class CourseServiceTest {

    Course course1, course2, course3;
    
    @Autowired
    CourseService courseService;
    
    @Autowired
    CourseDAO courseDAO;
    
    CrayonsUser dummyUser1, dummyUser2;

    @Autowired
    UserService userService;

    List<GrantedAuthority> authorities;
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        authorities = new ArrayList<GrantedAuthority>();

        dummyUser1 = new CrayonsUser("user1", "foo", "user1@web.de", "123456", "German", 2, true, true, false, false,
                authorities);
        dummyUser2 = new CrayonsUser("user2", "boo", "user2@web.de", "123456", "German", 2, true, true, false, false,
                authorities);
        userService.insertUser(dummyUser1);
        CurrentUser.getInstance().seteMail(dummyUser1.getEmail());
        userService.insertUser(dummyUser2);
        course1 = new Course("SWT1", "Very cool course", dummyUser1, "");
        course2 = new Course("SWT2", "Another very cool course", dummyUser2, "");
        course3 = new Course("SWT3", "Another super cool course", dummyUser1, "");
        courseService.insertCourse(course1);
        courseService.insertCourse(course2);
        courseDAO.updateStudents("/user1@web.de", course2.getTitle());
        course2 = courseService.findCourseByTitle(course2.getTitle());
    }

    @After
    public void tearDown() throws Exception {
        userService.removeUser("user1@web.de");
        userService.removeUser("user2@web.de");
        courseService.removeCourse(course1);
        courseService.removeCourse(course2);
    }

    @Test
    public void testFindAll() {
        List<Course> courses = courseService.findAll();
        assertEquals(2, courses.size());
    }

    @Test
    public void testFindCourseByTitle() {
        Course foundCourse = courseService.findCourseByTitle(course1.getTitle());
        assertEquals(foundCourse.getTitle(), course1.getTitle());
        assertEquals(foundCourse.getAuthor(), course1.getAuthor());
    }
    
    @Test
    public void testFindCourseByTitleCourseDoesntExist() {
        Course foundCourse = courseService.findCourseByTitle("Random Name");
        assertNull(foundCourse);
    }

    @Test
    public void testFindCourseByTitleAndAuthor() {
        Course foundCourse = courseService.findCourseByTitleAndAuthor(course1.getTitle());
        assertEquals(foundCourse.getTitle(), course1.getTitle());
        assertEquals(foundCourse.getAuthor(), course1.getAuthor());
    }
    
    @Test
    public void testFindCourseByTitleAndAuthorCourseDoesntExist() {
        Course foundCourse = courseService.findCourseByTitleAndAuthor("Random Name");
        assertNull(foundCourse);
    }

    @Test
    public void testFindAllCoursesOfUser() { 
        List<Course> courses = courseService.findAllCoursesOfUser(dummyUser1);
        assertEquals(1, courses.size());
        assertEquals("SWT2", courses.get(0).getTitle());
    }

    @Test
    public void testFindAllAuthorCoursesOfUser() {
        List<Course> courses = courseService.findAllAuthorCoursesOfUser(dummyUser1.getEmail());
        assertEquals(1, courses.size());
        assertEquals("SWT1", courses.get(0).getTitle());
    }

    @Test
    public void testInsertAndRemoveCourse() {
        List<Course> courses = courseService.findAll();
        boolean exist = false;
        for (Course course: courses) 
            if (course.getTitle().equals("SWT3") && course.getAuthor().getEmail().equals("user1@web.de"))
                exist = true;
        assertFalse(exist);
        
        courseService.insertCourse(course3);
        courses = courseService.findAll();
        for (Course course: courses) 
            if (course.getTitle().equals("SWT3") && course.getAuthor().getEmail().equals("user1@web.de"))
                exist = true;
        assertTrue(exist);
        courseService.removeCourse(course3);
    }
    
    @Test
    public void testRemoveCourseWhichDoesntExist() {
        assertFalse(courseService.removeCourse(course3));
    }
    
    @Test
    public void testCourseWithExistingName() {
        courseService.insertCourse(course3);
        Course course4 = new Course("SWT3", "One more course", dummyUser1, "");
        assertFalse(courseService.insertCourse(course4));
        courseService.removeCourse(course3);
    }

    @Test
    public void testUpdate() {
        courseService.insertCourse(course3);
        Course course4 = new Course("SWT4", "One more super cool course", dummyUser1, "");
        courseService.update(course4, course3.getTitle());
        boolean course3Exist = false;
        boolean course4Exist = false;
        List<Course> courses = courseService.findAll();
        for (Course course: courses) 
            if (course.getTitle().equals("SWT3") && course.getAuthor().getEmail().equals("user1@web.de"))
                course3Exist = true;
            else if (course.getTitle().equals("SWT4") && course.getAuthor().getEmail().equals("user1@web.de"))
                course4Exist = true;
        assertFalse(course3Exist);
        assertTrue(course4Exist);
        courseService.removeCourse(course4);
    }

    @Test
    public void testInsertStudent() {
        courseService.insertCourse(course3);
        String[] students = {"user2@web.de"};
        courseService.insertStudent(students, course3.getTitle());
        course3 = courseService.findCourseByTitle(course3.getTitle());
        String[] participants = courseService.getCourseParticipants(course3);
        String[] course3Part = {"user2@web.de"};
        assertArrayEquals(course3Part, participants);
        courseService.removeCourse(course3);
    }

    @Test
    public void testGetCourseParticipants() {
        String[] participants = courseService.getCourseParticipants(course2);
        String[] course2Part = {"user1@web.de"};
        assertArrayEquals(course2Part, participants);
    }
    
    @Test
    public void testGetCourseParticipantsWhenThereAreNo() {
        String[] participants = courseService.getCourseParticipants(course1);
        assertNull(participants);
    }

    @Test
    public void testGetStudentsWithAuthor() {
        CurrentUser.getInstance().seteMail(dummyUser2.getEmail());
        String[] participants = courseService.getStudentsWithAuthor(course2.getTitle());
        String[] course2Part = {"user1@web.de"};
        assertArrayEquals(course2Part, participants);
        CurrentUser.getInstance().seteMail(dummyUser1.getEmail());
    }
    
    @Test
    public void testGetStudentsWithAuthorWhenThereAreNo() {
        String[] participants = courseService.getStudentsWithAuthor(course1.getTitle());
        assertNull(participants);
    }
}
