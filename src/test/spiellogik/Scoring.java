package test.spiellogik;

import static org.junit.Assert.*;



import org.junit.Test;

import core.Graph;

import core.datacontainers.Seite;

public class Scoring {
	Graph graph = new Graph();
	Graph graph1 = new Graph();
	String map3 = "ext/map3.txt";
	String map4 = "ext/map4.txt";
	Seite Rom = Seite.Rom;
	Seite Kathargo = Seite.Kathargo;
	
	@Test
	public void sScR() throws Exception {
		graph.setPath(map3);
		graph.read();
		int x = graph.getPunkteStandFuer(Rom);
		assertEquals(x,1);
	}
	@Test
	public void sScK() throws Exception {
		graph.setPath(map3);
		graph.read();
		int x = graph.getPunkteStandFuer(Kathargo);
		assertEquals(x,1);
	}
	@Test
	public void bScR() throws Exception {
		graph.setPath(map4);
		graph.read();
		int x = graph.getPunkteStandFuer(Rom);
		assertEquals(x,6);
	}
	@Test
	public void bScK() throws Exception {
		graph.setPath(map4);
		graph.read();
		int x = graph.getPunkteStandFuer(Kathargo);
		assertEquals(x,2);
	}
}
