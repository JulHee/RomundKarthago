package test.spiellogik;

import static org.junit.Assert.*;

import java.util.HashSet;

import core.datacontainers.Knoten;
import org.junit.Test;

import core.*;

public class ConnectedComp {
	Graph graph = new Graph();
	Graph graph1 = new Graph();
	String map3 = "ext/map3.txt";
	String map4 = "ext/map4.txt";
	
	@Test
	public void sCC0() throws Exception {
		graph.setPath(map3);
		graph.read();
		Knoten g = graph.findKnoten(0);
		HashSet<Knoten> set = graph.getBesetztesGebiet(g);
		Knoten x = graph.findKnoten(0);
		Boolean result = set.contains(x);
		assertTrue(result);
	}
	@Test
	public void sCC1() throws Exception {
		graph.setPath(map3);
		graph.read();
		Knoten g = graph.findKnoten(1);
		HashSet<Knoten> set = graph.getBesetztesGebiet(g);
		Knoten x = graph.findKnoten(1);
		Knoten y = graph.findKnoten(2);
		Boolean result = set.contains(x)&&set.contains(y);
		assertTrue(result);
	}
	@Test
	public void sCC3() throws Exception {
		graph.setPath(map3);
		graph.read();
		Knoten g = graph.findKnoten(3);
		HashSet<Knoten> set = graph.getBesetztesGebiet(g);
		Knoten x = graph.findKnoten(3);
		Boolean result = set.contains(x);
		assertTrue(result);
	}
	@Test
	public void bCC1() throws Exception {
		graph.setPath(map4);
		graph.read();
		Knoten g = graph.findKnoten(1);
		HashSet<Knoten> set = graph.getBesetztesGebiet(g);
		Knoten x = graph.findKnoten(1);
		Knoten y = graph.findKnoten(2);
		Knoten z = graph.findKnoten(3);
		Boolean result = set.contains(x)&&set.contains(y)&&set.contains(z);
		assertTrue(result);
	}
	@Test
	public void bCC6() throws Exception {
		graph.setPath(map4);
		graph.read();
		Knoten g = graph.findKnoten(6);
		HashSet<Knoten> set = graph.getBesetztesGebiet(g);
		Knoten x = graph.findKnoten(4);
		Knoten y = graph.findKnoten(5);
		Knoten z = graph.findKnoten(6);
		Boolean result = set.contains(x)&&set.contains(y)&&set.contains(z);
		assertTrue(result);
	}
}
