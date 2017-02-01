package com.crayons_2_0.model.graph;

import static org.junit.Assert.*;

import org.junit.Test;

public class NodeTest {

	@Test
	public void getTitleTest() {
		Node node = new Node ("testNode");
		assertTrue(node.getUnitNodeTitle().equals("testNode"));
	}
	
	@Test
	public void addAndGetNodeTest(){
		Node node1 = new Node ("node1");
		Node node2 = new Node ("node2");
		node1.addNode(node2);
		assertTrue(node1.getNodes().iterator().next().getUnitNodeTitle().equals("node2"));
	}
}
