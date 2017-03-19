package junit;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.crayons_2_0.CrayonsSpringApplication;
import com.crayons_2_0.model.Course;
import com.crayons_2_0.service.database.CourseService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CrayonsSpringApplication.class)
public class CourseServiceTest {

    Course course1, course2;
    @Autowired
    CourseService courseService;
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
//        course1 = new Course(", description, author, students);
        
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testFindAll() {
        fail("Not yet implemented");
    }

    @Test
    public void testFindCourseByTitle() {
        fail("Not yet implemented");
    }

    @Test
    public void testFindCourseByTitleAndAuthor() {
        fail("Not yet implemented");
    }

    @Test
    public void testFindAllCoursesOfUser() {
        fail("Not yet implemented");
    }

    @Test
    public void testFindAllAuthorCoursesOfUser() {
        fail("Not yet implemented");
    }

    @Test
    public void testInsertCourse() {
        fail("Not yet implemented");
    }

    @Test
    public void testRemoveCourse() {
        fail("Not yet implemented");
    }

    @Test
    public void testUpdate() {
        fail("Not yet implemented");
    }

    @Test
    public void testInsertStudent() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetCourseParticipants() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetStudentsWithAuthor() {
        fail("Not yet implemented");
    }

    @Test
    public void testSaveCourseData() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetCourseData() {
        fail("Not yet implemented");
    }

    @Test
    public void testSaveDummyGraph() {
        fail("Not yet implemented");
    }

    @Test
    public void testRemoveStudentFromCourse() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetDummyGraph() {
        fail("Not yet implemented");
    }

    @Test
    public void testSearchAll() {
        fail("Not yet implemented");
    }

}
