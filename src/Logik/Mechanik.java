package Logik;

import Graph.*;

/**
 * Created by peng0in on 01.06.2014.
 */
public class Mechanik {

    Graph myGraph;
    String path;
    /**
     * Konstruktor zur Instanzierung des Graphen
     */
    public Mechanik(String p) {
    	path = p;
        myGraph = new Graph();
        myGraph.setPath(path);
        myGraph.read();
    }

    public static void Transition (String Graphpath,String Move) throws Exception{
        Graph graph = new Graph();
        Zug zug = Zug.readZugFile(Move);
        graph.setPath(Graphpath);
        graph.read();
        graph.ssuf(graph, zug).ausgeben();
    };


}
