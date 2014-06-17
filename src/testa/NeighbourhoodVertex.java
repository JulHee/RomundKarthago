package Test;

import static org.junit.Assert.*;

import java.util.HashSet;

import org.junit.Test;

import core.*;

public class NeighbourhoodVertex {
	Graph graph = new Graph();
	Graph graph1 = new Graph();
	String map3 = "ext/map3.txt";
	String map4 = "ext/map4.txt";
	
	
	@Test
	public void sVertex0() throws Exception {
		graph1.setPath(map3);
		graph1.read();
		Knoten y = graph1.findKnoten(0);
		HashSet<Knoten> resultset = graph1.getNachbarschaft(y);
		Knoten blub = graph1.findKnoten(1);
		Boolean result = resultset.contains(blub);
		assertTrue(result);
	}
	@Test
	public void sVertex1() throws Exception {
		graph1.setPath(map3);
		graph1.read();
		Knoten y = graph1.findKnoten(1);
		HashSet<Knoten> resultset = graph1.getNachbarschaft(y);
		Knoten blub = graph1.findKnoten(0);
		Knoten blubb = graph1.findKnoten(2);
		Boolean result = resultset.contains(blub)&&resultset.contains(blubb);
		assertTrue(result);
	}
	@Test
	public void sVertex2() throws Exception {
		graph1.setPath(map3);
		graph1.read();
		Knoten y = graph1.findKnoten(2);
		HashSet<Knoten> resultset = graph1.getNachbarschaft(y);
		Knoten blub = graph1.findKnoten(1);
		Knoten blubb = graph1.findKnoten(3);
		Boolean result = resultset.contains(blub)&&resultset.contains(blubb);
		assertTrue(result);
	}
	@Test
	public void sVertex3() throws Exception {
		graph1.setPath(map3);
		graph1.read();
		Knoten y = graph1.findKnoten(3);
		HashSet<Knoten> resultset = graph1.getNachbarschaft(y);
		Knoten blub = graph1.findKnoten(2);
		Boolean result = resultset.contains(blub);
		assertTrue(result);
	}
	@Test
	public void bVertex0() throws Exception {
		graph.setPath(map4);
		graph.read();
		Knoten y = graph.findKnoten(0);
		HashSet<Knoten> resultset = graph.getNachbarschaft(y);
		Knoten blub = graph.findKnoten(9);
		Boolean result = resultset.contains(blub);
		assertTrue(result);
	}
	@Test
	public void bVertex1() throws Exception {
		graph.setPath(map4);
		graph.read();
		Knoten y = graph.findKnoten(1);
		HashSet<Knoten> resultset = graph.getNachbarschaft(y);
		Knoten blub = graph.findKnoten(2);
		Knoten blubb = graph.findKnoten(3);
		Knoten blubbb = graph.findKnoten(6);
		Boolean result = resultset.contains(blub)&&resultset.contains(blubb)&&resultset.contains(blubbb);
		assertTrue(result);
	}
	@Test
	public void bVertex2() throws Exception {
		graph.setPath(map4);
		graph.read();
		Knoten y = graph.findKnoten(2);
		HashSet<Knoten> resultset = graph.getNachbarschaft(y);
		Knoten blub = graph.findKnoten(1);
		Knoten blubb = graph.findKnoten(6);
		Knoten blubbb = graph.findKnoten(4);
		Boolean result = resultset.contains(blub)&&resultset.contains(blubb)&&resultset.contains(blubbb);
		assertTrue(result);
	}
	@Test
	public void bVertex3() throws Exception {
		graph.setPath(map4);
		graph.read();
		Knoten y = graph.findKnoten(3);
		HashSet<Knoten> resultset = graph.getNachbarschaft(y);
		Knoten blub = graph.findKnoten(1);
		Knoten blubb = graph.findKnoten(5);
		Knoten blubbb = graph.findKnoten(6);
		Boolean result = resultset.contains(blub)&&resultset.contains(blubb)&&resultset.contains(blubbb);
		assertTrue(result);
	}
	@Test
	public void bVertex4() throws Exception {
		graph.setPath(map4);
		graph.read();
		Knoten y = graph.findKnoten(4);
		HashSet<Knoten> resultset = graph.getNachbarschaft(y);
		Knoten blub = graph.findKnoten(2);
		Knoten blubb = graph.findKnoten(7);
		Knoten blubbb = graph.findKnoten(6);
		Boolean result = resultset.contains(blub)&&resultset.contains(blubb)&&resultset.contains(blubbb);
		assertTrue(result);
	}
	@Test
	public void bVertex5() throws Exception {
		graph.setPath(map4);
		graph.read();
		Knoten y = graph.findKnoten(5);
		HashSet<Knoten> resultset = graph.getNachbarschaft(y);
		Knoten blub = graph.findKnoten(3);
		Knoten blubb = graph.findKnoten(8);
		Knoten blubbb = graph.findKnoten(6);
		Boolean result = resultset.contains(blub)&&resultset.contains(blubb)&&resultset.contains(blubbb);
		assertTrue(result);
	}
	@Test
	public void bVertex6() throws Exception {
		graph.setPath(map4);
		graph.read();
		Knoten y = graph.findKnoten(6);
		HashSet<Knoten> resultset = graph.getNachbarschaft(y);
		Knoten blub = graph.findKnoten(1);
		Knoten blubb = graph.findKnoten(2);
		Knoten blubbb = graph.findKnoten(3);
		Knoten blubbbb = graph.findKnoten(4);
		Knoten blubbbbb = graph.findKnoten(5);
		Knoten blubbbbbb = graph.findKnoten(7);
		Knoten blubbbbbbb = graph.findKnoten(8);
		Knoten blubbbbbbbb = graph.findKnoten(9);
		Boolean result = resultset.contains(blub)&&resultset.contains(blubb)&&resultset.contains(blubbb)&&
				resultset.contains(blubbbb)&&resultset.contains(blubbbbb)&&resultset.contains(blubbbbbb)&&
				resultset.contains(blubbbbbb)&&resultset.contains(blubbbbbbb)&&resultset.contains(blubbbbbbbb);
		assertTrue(result);
	}
	@Test
	public void bVertex7() throws Exception {
		graph.setPath(map4);
		graph.read();
		Knoten y = graph.findKnoten(7);
		HashSet<Knoten> resultset = graph.getNachbarschaft(y);
		Knoten blub = graph.findKnoten(4);
		Knoten blubb = graph.findKnoten(6);
		Knoten blubbb = graph.findKnoten(9);
		Boolean result = resultset.contains(blub)&&resultset.contains(blubb)&&resultset.contains(blubbb);
		assertTrue(result);
	}
	public void bVertex8() throws Exception {
		graph.setPath(map4);
		graph.read();
		Knoten y = graph.findKnoten(8);
		HashSet<Knoten> resultset = graph.getNachbarschaft(y);
		Knoten blub = graph.findKnoten(5);
		Knoten blubb = graph.findKnoten(6);
		Knoten blubbb = graph.findKnoten(9);
		Boolean result = resultset.contains(blub)&&resultset.contains(blubb)&&resultset.contains(blubbb);
		assertTrue(result);
	}
	public void bVertex9() throws Exception {
		graph.setPath(map4);
		graph.read();
		Knoten y = graph.findKnoten(9);
		HashSet<Knoten> resultset = graph.getNachbarschaft(y);
		Knoten blub = graph.findKnoten(7);
		Knoten blubb = graph.findKnoten(6);
		Knoten blubbb = graph.findKnoten(8);
		Knoten blubbbb = graph.findKnoten(0);
		Boolean result = resultset.contains(blub)&&resultset.contains(blubb)&&resultset.contains(blubbb)&&
				resultset.contains(blubbbb);
		assertTrue(result);
	}	
}
