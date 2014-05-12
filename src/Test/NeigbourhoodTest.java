package Test;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Iterator;

import org.junit.Test;

import Graph.*;

public class NeigbourhoodTest {
	Graph graph = new Graph();
	String map3 = "ext/map3.txt";
	String map4 = "ext/map4.txt";
	
	@Test
	public void Vertex0() throws Exception {
		graph.setPath(map3);
		graph.read();
		HashSet<Knoten> x = graph.toHashSet();
		Knoten y = graph.findKnoten(0);
		HashSet<Knoten> resultset = graph.getNachbarschaft(y);
		Knoten blubb = graph.findKnoten(1);
		Boolean result = resultset.contains(blubb);
		assertTrue(result);
	}
	@Test
	public void Vertex1() throws Exception {
		graph.setPath(map3);
		graph.read();
		HashSet<Knoten> x = graph.toHashSet();
		Knoten y = graph.findKnoten(1);
		HashSet<Knoten> resultset = graph.getNachbarschaft(y);
		Knoten blubb = graph.findKnoten(0);
		Knoten blubbb = graph.findKnoten(2);
		Boolean result = resultset.contains(blubb)&&resultset.contains(blubbb);
		assertTrue(result);
	}
	@Test
	public void Vertex2() throws Exception {
		graph.setPath(map3);
		graph.read();
		HashSet<Knoten> x = graph.toHashSet();
		Knoten y = graph.findKnoten(2);
		HashSet<Knoten> resultset = graph.getNachbarschaft(y);
		Knoten blubb = graph.findKnoten(1);
		Knoten blubbb = graph.findKnoten(3);
		Boolean result = resultset.contains(blubb)&&resultset.contains(blubbb);
		assertTrue(result);
	}
	@Test
	public void Vertex3() throws Exception {
		graph.setPath(map3);
		graph.read();
		HashSet<Knoten> x = graph.toHashSet();
		Knoten y = graph.findKnoten(3);
		HashSet<Knoten> resultset = graph.getNachbarschaft(y);
		Knoten blubb = graph.findKnoten(2);
		Boolean result = resultset.contains(blubb);
		assertTrue(result);
	}
}
