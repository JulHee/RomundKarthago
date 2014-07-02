package test.spiellogik;

import static org.junit.Assert.*;

import java.util.HashSet;

import org.junit.Test;

import core.Graph;
import core.datacontainers.Knoten;

public class NeighbourhoodVertexSet {
	Graph graph = new Graph();
	Graph graph1 = new Graph();
	HashSet<Knoten> set = new HashSet<Knoten>();
	String map3 = "ext/map3.txt";
	String map4 = "ext/map4.txt";
	
	@Test
	public void sVertex01() throws Exception {
		graph.setPath(map3);
		graph.read();
		Knoten g = graph.findKnoten(0);
		Knoten h = graph.findKnoten(1);
		set.add(g);
		set.add(h);
		HashSet<Knoten> resultset = graph.getNachbarschaft(set);
		Knoten blub = graph.findKnoten(2);
		Boolean result = resultset.contains(blub)&&!resultset.contains(g)&&!resultset.contains(h);
		assertTrue(result);
	}
	@Test
	public void sVertex02() throws Exception {
		graph.setPath(map3);
		graph.read();
		Knoten g = graph.findKnoten(0);
		Knoten h = graph.findKnoten(2);
		set.add(g);
		set.add(h);
		HashSet<Knoten> resultset = graph.getNachbarschaft(set);
		Knoten blub = graph.findKnoten(1);
		Knoten blubb = graph.findKnoten(3);
		Boolean result = resultset.contains(blub)&&resultset.contains(blubb)&&!resultset.contains(g)
				&&!resultset.contains(h);
		assertTrue(result);
	}
	@Test
	public void sVertex12() throws Exception {
		graph.setPath(map3);
		graph.read();
		Knoten g = graph.findKnoten(1);
		Knoten h = graph.findKnoten(2);
		set.add(g);
		set.add(h);
		HashSet<Knoten> resultset = graph.getNachbarschaft(set);
		Knoten blub = graph.findKnoten(0);
		Knoten blubb = graph.findKnoten(3);
		Boolean result = resultset.contains(blub)&&resultset.contains(blubb)&&!resultset.contains(g)
				&&!resultset.contains(h);
		assertTrue(result);
	}
	@Test
	public void bVertex6() throws Exception {
		graph1.setPath(map4);
		graph1.read();
		Knoten g = graph1.findKnoten(6);
		set.add(g);
		HashSet<Knoten> resultset = graph1.getNachbarschaft(set);
		Knoten blub = graph1.findKnoten(1);
		Knoten blubb = graph1.findKnoten(2);
		Knoten blubbb = graph1.findKnoten(3);
		Knoten blubbbb = graph1.findKnoten(4);
		Knoten blubbbbb = graph1.findKnoten(5);
		Knoten blubbbbbb = graph1.findKnoten(7);
		Knoten blubbbbbbb = graph1.findKnoten(8);
		Boolean result = resultset.contains(blub)&&resultset.contains(blubb)&&resultset.contains(blubbb)
				&&resultset.contains(blubbbb)&&resultset.contains(blubbbbb)&&resultset.contains(blubbbbbb)
				&&resultset.contains(blubbbbbbb)&&!resultset.contains(g);
		assertTrue(result);
	}
	@Test
	public void bVertex79() throws Exception {
		graph1.setPath(map4);
		graph1.read();
		Knoten g = graph1.findKnoten(7);
		Knoten h = graph1.findKnoten(9);
		set.add(g);
		set.add(h);
		HashSet<Knoten> resultset = graph1.getNachbarschaft(set);
		Knoten blub = graph1.findKnoten(0);
		Knoten blubb = graph1.findKnoten(4);
		Knoten blubbb = graph1.findKnoten(6);
		Knoten blubbbb = graph1.findKnoten(8);
		Boolean result = resultset.contains(blub)&&resultset.contains(blubb)&&resultset.contains(blubbb)
				&resultset.contains(blubbbb)&&!resultset.contains(g)&&!resultset.contains(h);
		assertTrue(result);
	}

}
