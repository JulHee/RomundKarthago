package Test;
import org.junit.Test;
import Graph.Graph;
import static org.junit.Assert.*;
/**
 * Created by  Christian Bruene  on 22.05.2014.
 */
public class ConvertToStringTest {
    Graph graph = new Graph();
    @Test
    public void testKonvertierung1()throws  Exception{
        graph.setPath("ext/map.txt");
        graph.read();
        assertEquals ("CRNNNNRNNC",graph.convertToString2());
    }
    @Test
    public void testKonvertierung2()throws  Exception{
        graph.setPath("ext/map2.txt");
        graph.read();
        assertEquals ("NNNNRRRNNC",graph.convertToString2());
    }
    @Test
    public void testKonvertierung3()throws  Exception{
        graph.setPath("ext/map3.txt");
        graph.read();
        assertEquals ("CNNR",graph.convertToString2());
    }
    @Test
    public void testKonvertierung4()throws  Exception{
        graph.setPath("ext/map4.txt");
        graph.read();
        assertEquals ("NNNNRRRNNC",graph.convertToString2());
    }
}
