import org.junit.Test;

import static org.junit.Assert.*;

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
}