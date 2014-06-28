package test;

import core.Graph;
import core.datacontainers.Seite;
import logik.Mechanik;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by Acer on 28.06.2014.
 */
public class ssufTest {
    @Test
    public void test1(){
        Mechanik mech = new Mechanik("ext/Gameboard.txt");
        Graph myGraph = mech.getMyGraph();
        myGraph.run("R 2", Seite.Rom);
        myGraph.run("C 1", Seite.Kathargo);
        assertEquals("CCNN",myGraph.convertToString());
    }
}
