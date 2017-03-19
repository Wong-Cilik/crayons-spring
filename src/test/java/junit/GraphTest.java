package junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.crayons_2_0.model.Course;
import com.crayons_2_0.model.CrayonsUser;
import com.crayons_2_0.model.graph.Graph;
import com.crayons_2_0.model.graph.UnitNode;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
"classpath*:spring/applicationContext.xml",
"classpath*:spring/applicationContext-jpa.xml",
"classpath*:spring/applicationContext-security.xml" })
public class GraphTest {

    Graph testGraph;
    Course dummyCourse;
    CrayonsUser dummyUser;
    List<GrantedAuthority> authorities;
    String dummy = "dummy@web.de";
    UnitNode contentOne;
    UnitNode contentTwo;
    UnitNode contentThree;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        authorities = new ArrayList<GrantedAuthority>();
        dummyUser = new CrayonsUser("first", "last", "dummy@web.de", "123456", "German", 2, true, true, false, false,
                authorities);
        dummyCourse = new Course(dummy, dummyUser);
        testGraph = new Graph(dummyCourse);
        contentOne = new UnitNode("one", testGraph.getStartUnit(), testGraph);
        contentTwo = new UnitNode("two", testGraph.getStartUnit(), testGraph);
        contentThree = new UnitNode("three", testGraph.getStartUnit(), testGraph);
        
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetNodeNameList() {
        final String[] nodeNames = new String[5];
        nodeNames[0] = "Start";
        nodeNames[1] = "one";
        nodeNames[2] = "two";
        nodeNames[3] = "three";
        nodeNames[4] = "End";
        testGraph.addUnit(contentOne, testGraph.getStartUnit(), contentTwo);
        testGraph.addUnit(contentTwo, contentOne, contentThree);
        testGraph.addUnit(contentThree, contentTwo, testGraph.getEndUnit());
        ArrayList<String> nodeNameList = testGraph.getNodeNameList();
        for (int i= 0; i< nodeNameList.size(); i++){
            assertEquals(nodeNames[i],nodeNameList.get(i)); 
        }
    }

    @Test
    public void testGetEdgeSequence() {
        assertTrue(testGraph.addUnit(contentOne, testGraph.getStartUnit(), contentTwo));
        assertTrue(testGraph.addUnit(contentTwo, contentOne, contentThree));
        assertTrue(testGraph.addUnit(contentThree, contentTwo, testGraph.getEndUnit()));
        final String[] edgeSequence = new String[9];
        edgeSequence[0] = "one";
        edgeSequence[1] = "two";
        edgeSequence[2] = "Start";
        edgeSequence[3] = "one";
        edgeSequence[5] = "two";
        edgeSequence[6] = "three";
        edgeSequence[7] = "three";
        edgeSequence[8] = "End";
        for (int i= 0; i< testGraph.getEdgeSequence().size(); i++){
            System.out.println(testGraph.getEdgeSequence().get(i));
            assertEquals(edgeSequence[i],testGraph.getEdgeSequence().get(i)); 
        }
    }

    @Test
    public void testGetNodeByName() {
        testGraph.addUnit(contentOne, testGraph.getStartUnit(), contentTwo);
        testGraph.addUnit(contentTwo, contentOne, contentThree);
        testGraph.addUnit(contentThree, contentTwo, testGraph.getEndUnit());
        assertEquals("one",testGraph.getNodeByName(contentOne.getUnitNodeTitle()));
        assertEquals(contentOne.getUnitNodeTitle(),testGraph.getNodeByName(contentOne.getUnitNodeTitle()));
        assertEquals("two",testGraph.getNodeByName(contentTwo.getUnitNodeTitle()));
        assertEquals(contentTwo.getUnitNodeTitle(),testGraph.getNodeByName(contentTwo.getUnitNodeTitle()));
        assertEquals("three",testGraph.getNodeByName(contentThree.getUnitNodeTitle()));
        assertEquals(contentThree.getUnitNodeTitle(),testGraph.getNodeByName(contentThree.getUnitNodeTitle()));
        assertEquals("End",testGraph.getNodeByName(testGraph.getEndUnit().getUnitNodeTitle()));
        assertEquals(testGraph.getEndUnit().getUnitNodeTitle(),testGraph.getEndUnit().getUnitNodeTitle());
        assertEquals("Start",testGraph.getNodeByName(testGraph.getStartUnit().getUnitNodeTitle()));
        assertEquals(testGraph.getStartUnit().getUnitNodeTitle(),testGraph.getStartUnit().getUnitNodeTitle());
    }

    @Test
    public void testGetUnitCollection() {
        testGraph.addUnit(contentOne, testGraph.getStartUnit(), contentTwo);
        testGraph.addUnit(contentTwo, contentOne, contentThree);
        testGraph.addUnit(contentThree, contentTwo, testGraph.getEndUnit());
    }

   
    @Test
    public void testAddConnection() {
        testGraph.addUnit(contentOne, testGraph.getStartUnit(), contentTwo);
        testGraph.addUnit(contentTwo, contentOne, contentThree);
        testGraph.addUnit(contentThree, contentTwo, testGraph.getEndUnit());
        assertTrue(testGraph.addConnection(contentOne, contentTwo));
    }

    @Test
    public void testDeleteUnit() {
        testGraph.addUnit(contentOne, testGraph.getStartUnit(), contentTwo);
        testGraph.addUnit(contentTwo, contentOne, contentThree);
        testGraph.addUnit(contentThree, contentTwo, testGraph.getEndUnit());
        assertTrue(testGraph.deleteUnit(contentTwo));
        assertTrue(testGraph.isConnected(contentOne, contentThree));
    }

    @Test
    public void testIsConnected() {
        testGraph.addUnit(contentOne, testGraph.getStartUnit(), contentTwo);
        testGraph.addUnit(contentTwo, contentOne, contentThree);
        testGraph.addUnit(contentThree, contentTwo, testGraph.getEndUnit());
        assertTrue(testGraph.isConnected(testGraph.getStartUnit(),contentOne));
        assertTrue(testGraph.isConnected(contentOne, contentTwo));
        assertTrue(testGraph.isConnected(contentTwo, contentThree));
        assertTrue(testGraph.isConnected(contentThree, testGraph.getEndUnit()));
    }

    @Test
    public void testDeleteConnection() {
        testGraph.addUnit(contentOne, testGraph.getStartUnit(), contentTwo);
        testGraph.addUnit(contentTwo, contentOne, contentThree);
        testGraph.addUnit(contentThree, contentTwo, testGraph.getEndUnit());
        assertTrue(testGraph.deleteConnection(contentOne, contentTwo));
        assertFalse(testGraph.isConnected(contentOne, contentTwo));
    }

}