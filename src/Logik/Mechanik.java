package Logik;

import Graph.*;

/**
 * Projekt: Rom und Karthago
 * Author : Julian Heeger, Markus Poell, Christian Bruene, Joern Kabuth
 * Date : 26.04.14
 * Year : 2014
 */
public class Mechanik {

    Graph myGraph;

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
    }

    public String run(String zug){
        String retrn;
        Zug myzug = new Zug(zug);
        if (myzug.getStadt() == -1){
           retrn =  myGraph.convertToString();
        } else {
           myGraph = myGraph.ssuf(myGraph,myzug);
            retrn =   myGraph.convertToString();
        }
        return retrn;
    }

    public void terminalGame(){
        Boolean letzerZugAusgesetzt = false;
        Boolean spiel = true;


        while(spiel){




        }



    }


}
