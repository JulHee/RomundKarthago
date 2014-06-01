package Logik;

import Graph.*;

/**
 * Created by peng0in on 01.06.2014.
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
