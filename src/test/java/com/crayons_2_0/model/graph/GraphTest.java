package com.crayons_2_0.model.graph;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import com.crayons_2_0.model.Course;
import com.crayons_2_0.model.CrayonsUser;

public class GraphTest {

	static Graph graph;
	static Node start;
	static UnitNode node1;
	static UnitNode node2;
	static UnitNode node3;
	static Set<Node> unitCollection;
	
	@BeforeClass
	public static void onceExecutedBeforeAll(){
		unitCollection = new HashSet<Node>();
		CrayonsUser user = null;
		Course course = new Course ("course", user);
		graph = new Graph(course);
		node1 = new UnitNode("testUnit1", start, graph);
		node2 = new UnitNode("testUnit2", node1, graph);
		node3 = new UnitNode("testUnit3", node2, graph);
	}
	
	@Test
	public void addUnitTest() {
		graph.addUnit(node1, unitCollection);
		graph.addUnit(node2, unitCollection);
		graph.addUnit(node3, unitCollection);
		ArrayList<String> list = graph.getNodeNameList();
		assertTrue(list.size()==3);
		assertTrue(list.get(2).contains("testUnit"));
	}
	
	@Test
	public void deleteUnitTest() {
		graph.addUnit(node1, unitCollection);
		graph.addUnit(node2, unitCollection);
		graph.deleteUnit(node1);
		ArrayList<String> list = graph.getNodeNameList();
		assertTrue(list.size()==1);
		assertTrue(list.get(0).equals("testUnit2"));
	}

	@Test
	public void getEdgeTest() {

	}

}
