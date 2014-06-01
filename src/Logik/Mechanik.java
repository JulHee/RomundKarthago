package Logik;

import Graph.*;

/**
 * Created by peng0in on 01.06.2014.
 */
public class Mechanik {

    Graph myGraph;
    /**
     * Konstruktor zur Instanzierung des Graphen
     */
    public Mechanik() {
    	
    }
    /*
     * Initialisiert den Start Graphen mit welchem
     * das Spiel begonnen wird
     */
    public void setMGraph(String path){
        myGraph = new Graph();
        myGraph.setPath(path);
        myGraph.read();
    }
    /*
     * Funktion zum einlesen von Graph und Zug aus einer Datei
     * gibt den anschließenden veränderten Graphen aus
     */
    public static void Transition (String Graphpath,String Move) throws Exception{
        Graph graph = new Graph();
        Zug zug = Zug.readZugFile(Move);
        graph.setPath(Graphpath);
        graph.read();
        graph.ssuf(graph, zug).ausgeben();
    };


}
