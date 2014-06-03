package Logik;

import Graph.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Projekt: Rom und Karthago
 * Author : Julian Heeger, Markus Poell, Christian Bruene, Joern Kabuth
 * Date : 26.04.14
 * Year : 2014
 */
public class Mechanik {

    Graph myGraph;
    Boolean letzerZugAusgesetzt = false;
    Boolean spiel = true;

    /*
     * Initialisiert den Start Graphen mit welchem
     * das Spiel begonnen wird
     */
    public void setMGraph(String path) {
        myGraph = new Graph();
        myGraph.setPath(path);
        myGraph.read();
    }

    public Mechanik() {
        setMGraph("ext/GameBoard.txt");
    }

    /*
         * Funktion zum einlesen von Graph und Zug aus einer Datei
         * gibt den anschließenden veränderten Graphen aus
         */
    public static void Transition(String Graphpath, String Move) throws Exception {
        Graph graph = new Graph();
        Zug zug = Zug.readZugFile(Move);
        graph.setPath(Graphpath);
        graph.read();
        graph.ssuf(graph, zug).ausgeben();
    }

    public String run(String zug, Seite spieler) {
        String retrn;
        Zug myzug = new Zug(zug);
        if (myzug.getStadt() == -1 || myzug.getSeite() != spieler) {
            if (letzerZugAusgesetzt) {
                retrn = myGraph.convertToString();
                spiel = false;
            } else {
                retrn = myGraph.convertToString();
                letzerZugAusgesetzt = true;
            }

        } else {
            String letzerZug = myGraph.convertToString();
            myGraph = myGraph.ssuf(myGraph, myzug);
            if (letzerZug.equals(myGraph.convertToString())) {
                letzerZugAusgesetzt = true;
            }
            if (letzerZugAusgesetzt){
                retrn = myGraph.convertToString();
                spiel = false;
            }
            retrn = myGraph.convertToString();
        }
        return retrn;
    }

    public void terminalGame() {
        Seite aktuellerSpieler = Seite.Rom;

        Boolean letzerZugAusgesetzt = false;
        myGraph.map();
        while (spiel) {
            System.out.println("Bitte Zug angeben:");
            try {
                BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
                String zug = bufferRead.readLine();
                String move = run(zug, aktuellerSpieler);
                System.out.println(move);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (aktuellerSpieler == Seite.Rom) {
                aktuellerSpieler = Seite.Kathargo;
            } else {
                aktuellerSpieler = Seite.Rom;
            }

        }
        System.out.println("Spiel beendet");
        System.out.println("Rom hat "+myGraph.besetztePunkteStandFuer(Seite.Rom)+" Punkt(e)");
        System.out.println("Kathargo hat "+myGraph.besetztePunkteStandFuer(Seite.Kathargo)+" Punkt(e)");
    }


}
