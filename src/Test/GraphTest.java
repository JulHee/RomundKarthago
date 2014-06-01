package Test;
import org.junit.Test;

import Graph.*;
import static org.junit.Assert.*;

/**
 * Projekt: Rom und Karthago
 * Author : Julian Heeger, Markus Poell, Christian Bruene, Joern Kabuth
 * Date : 26.04.14
 * Year : 2014
 */

public class GraphTest {

    @Test
    public void testGetL_knoten() throws Exception {
        Graph myGraph = new Graph();
        myGraph.read();
       assertEquals(10,myGraph.l_knoten.size());
    }

    @Test
    public void testGetL_kante() throws Exception {
        Graph myGraph = new Graph();
        myGraph.read();
        assertEquals(22,myGraph.l_kante.size());
    }

    @Test
    public void testFindKnoten() throws Exception {
        Graph myGraph = new Graph();
        myGraph.read();
        assertTrue(myGraph.findKnoten(5) != null);
    }

    @Test
    public void testconvertToString() throws Exception{
        Graph myGraph = new Graph();
        myGraph.read();
        assertEquals("NNNNRRRNNC",myGraph.convertToString());
    }
    @Test
    public void testssuf_map01() throws Exception{
        Graph myGraph = new Graph();
        myGraph.setPath("ext/0_3.map.txt");
        Zug myZug = new Zug("R 2");
        myGraph.read();
        Graph newGraph = myGraph.ssuf(myGraph,myZug);
        assertEquals("CNRR",newGraph.convertToString());
    }
    @Test
    public void testssuf_map02() throws Exception{
        Graph myGraph = new Graph();
        myGraph.setPath("ext/0_3.map.txt");
        Zug myZug = new Zug("C 2");
        myGraph.read();
        Graph newGraph = myGraph.ssuf(myGraph,myZug);
        assertEquals("CNCN",newGraph.convertToString());
    }
    @Test
    public void testssuf_map03() throws Exception{
        Graph myGraph = new Graph();
        myGraph.setPath("ext/0_3.map.02.txt");
        Zug myZug = new Zug("R 3");
        myGraph.read();
        Graph newGraph = myGraph.ssuf(myGraph,myZug);
        assertEquals("CNCN",newGraph.convertToString());
    }
    @Test
    public void testssuf_map04() throws Exception{
        Graph myGraph = new Graph();
        myGraph.setPath("ext/0_3.map.02.txt");
        Zug myZug = new Zug("R 1");
        myGraph.read();
        Graph newGraph = myGraph.ssuf(myGraph,myZug);
        assertEquals("NRCN",newGraph.convertToString());
    }
}