package logik;

import core.*;
import core.datacontainers.Seite;
import core.datacontainers.Zug;
import core.datacontainers.Zustand;
import network.Client_R;
import network.Server_C;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Projekt: Rom und Karthago
 * Author : Julian Heeger, Markus Poell, Christian Bruene, Joern Kabuth
 * Date : 26.04.14
 * Year : 2014
 */
public class Mechanik implements Cloneable {

    public Graph getMyGraph() {
        return myGraph;
    }

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

    public String auswerten(String zug, Seite spieler) {
        String error;
        Zustand retrnZustand = myGraph.run(zug, spieler);
        if (retrnZustand.getName() == null | retrnZustand.getErrorcode() == null) {
            error = ("Fehler dem Zustand");
        } else {
            switch (retrnZustand.getErrorcode()) {
                case 0:
                    return retrnZustand.getName();
                //   break;
                case 1:
                    return retrnZustand.getName();
                //   break;
                case 2:
                    spiel = false;
                    return retrnZustand.getName();
                case 3:
                    return retrnZustand.getName();
                //   break;
                default:
                    error = "Error";
                    break;
            }
        }
        return error;
    }



    public void game(AIPlayer rom, AIPlayer kathargo) {
        rom.meineSeite = Seite.Rom;
        kathargo.meineSeite = Seite.Kathargo;
        AIPlayer aktuellerSpieler = rom;

        Boolean letzerZugAusgesetzt = false;
        myGraph.map();
        while (spiel) {
            System.out.println(aktuellerSpieler.meineSeite.toString() + " spielt folgenden Zug: ");
            String move = auswerten(aktuellerSpieler.nextZug().toFormat(), aktuellerSpieler.meineSeite);
            System.out.println(move);

            if (aktuellerSpieler.meineSeite == Seite.Rom) {
                aktuellerSpieler = kathargo;
            } else {
                aktuellerSpieler = rom;
            }

        }
        System.out.println("Spiel beendet");
        System.out.println("Rom hat " + myGraph.besetztePunkteStandFuer(Seite.Rom) + " Punkt(e)");
        System.out.println("Kathargo hat " + myGraph.besetztePunkteStandFuer(Seite.Kathargo) + " Punkt(e)");
    }

    public void game(AIPlayer computer) {
        Seite aktuellerSpieler = Seite.Rom;
        Boolean letzerZugAusgesetzt = false;
        myGraph.map();
        System.out.println("Bitte geben sie ob der Computer (R)om oder (C)athargo sein soll:");
        try {
            BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
            String wahl = bufferRead.readLine();
            if (wahl.equals("R")) {
                computer.meineSeite = Seite.Rom;
            } else if (wahl.equals("C")) {
                computer.meineSeite = Seite.Kathargo;
            } else {
                System.out.println("Error");
                System.exit(-1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (computer.meineSeite == Seite.Rom) {
            while (spiel) {
                if (aktuellerSpieler == Seite.Rom) {
                    String move = auswerten(computer.nextZug().toFormat(), aktuellerSpieler);
                    System.out.println(move);
                } else if(aktuellerSpieler == Seite.Kathargo) {
                    System.out.println("Bitte Zug angeben:");
                    try {
                        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
                        String zug = bufferRead.readLine();
                        String move = auswerten(zug, aktuellerSpieler);
                        System.out.println(move);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (aktuellerSpieler == Seite.Rom) {
                    aktuellerSpieler = Seite.Kathargo;
                } else {
                    aktuellerSpieler = Seite.Rom;
                }

            }
        } else {
            if (aktuellerSpieler == Seite.Kathargo) {
                String move = auswerten(computer.nextZug().toFormat(), aktuellerSpieler);
                System.out.println(move);
            } else if(aktuellerSpieler == Seite.Rom) {
                System.out.println("Bitte Zug angeben:");
                try {
                    BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
                    String zug = bufferRead.readLine();
                    String move = auswerten(zug, aktuellerSpieler);
                    System.out.println(move);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (aktuellerSpieler == Seite.Rom) {
                aktuellerSpieler = Seite.Kathargo;
            } else {
                aktuellerSpieler = Seite.Rom;
            }
        }
        System.out.println("Spiel beendet");
        System.out.println("Rom hat " + myGraph.besetztePunkteStandFuer(Seite.Rom) + " Punkt(e)");
        System.out.println("Kathargo hat " + myGraph.besetztePunkteStandFuer(Seite.Kathargo) + " Punkt(e)");
    }

    public void game() {
        Seite aktuellerSpieler = Seite.Rom;
        myGraph.map();
        while (spiel) {
            System.out.println("Bitte Zug angeben:");
            try {
                BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
                String zug = bufferRead.readLine();
                String move = auswerten(zug, aktuellerSpieler);
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
        System.out.println("Rom hat " + myGraph.besetztePunkteStandFuer(Seite.Rom) + " Punkt(e)");
        System.out.println("Kathargo hat " + myGraph.besetztePunkteStandFuer(Seite.Kathargo) + " Punkt(e)");

    }

    public void game(Client_R client,String ip, Integer port){
       Client_R myClient = new Client_R(port,ip,this);
       myClient.main(null);
    }

    public void game(Server_C server,Integer port){
      Server_C myServer = new Server_C(port,this);
    }

    @Override
    public Mechanik clone() {
        try {
            return (Mechanik) super.clone();
        } catch (CloneNotSupportedException e) {
            // Kann eigentlich nicht passieren, da Cloneable
            throw new InternalError();
        }
    }


}
