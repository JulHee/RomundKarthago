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
        assertEquals(myGraph.convertToString(),"NCNRRNRNNN");
    }
    @Test
    public void testspielSituatonUeberfuehren() throws Exception{
        Graph myGraph = new Graph();
        Zug myZug = new Zug("R 2");
        myGraph.read();
        Graph newGraph = myGraph.spielSituatonUeberfuehren(myGraph,myZug);
        assertEquals(newGraph.convertToString(),"NRNRNNNCRR");
    }
}