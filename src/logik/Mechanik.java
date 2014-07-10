package logik;

import core.Graph;
import core.datacontainers.Seite;
import core.datacontainers.Zug;
import core.datacontainers.Zustand;
import exceptions.DateiStrukturException;
import exceptions.KeinBesetzerException;
import exceptions.KnotenException;
import logik.ai.AIPlayer;
import network.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Projekt: Rom und Karthago
 * Author : Julian Heeger, Markus Poell, Christian Bruene, Joern Kabuth
 * Date : 26.04.14
 * Year : 2014
 */
public class Mechanik implements Cloneable {

    String path;
    Graph myGraph;
    Boolean letzterZugAusgesetzt = false;
    Boolean spiel = true;

    public Graph getMyGraph() {
        return myGraph;
    }

    public Boolean getSpiel() {
        return spiel;
    }

    public Boolean getLetzterZugAusgesetzt(){return letzterZugAusgesetzt;}

    /**
     * Initialisiert der Mechanik durch initialisierung des Graphen, über die im Netzwerk gesendete Map
     *
     * @param path Pfad zur Datei, inder die Map beschrieben wird
     */

    public Mechanik(String path) {
        this.path = path;
        myGraph = new Graph();
        try {
            myGraph.setPath(this.path);
            myGraph.read();
        }catch (KeinBesetzerException e){
            System.out.println(e.getMessage());}
        catch (DateiStrukturException e){
            System.out.println(e.getMessage());}
        catch (KnotenException e){
            System.out.println(e.getMessage());}
        catch (IOException e){
            System.out.println(e.getMessage());}
        catch (Exception e){
            System.out.println(e.getMessage()+" dies ist keine ordnungsgemäß gefangene Exception");}
    }

    /**
     * Initialisiert der Mechanik durch initialisierung des Graphen, über die im Netzwerk gesendete Map
     *
     * @param map Die Karte auf der gespielt wird
     */

    public Mechanik(ArrayList<String> map) {
        this.path = "Es wurde eine ArrayList übergeben";
        myGraph = new Graph();
        myGraph.setPath(map);
    }

    /*
     * Funktion zum einlesen von Graph und Zug aus einer Datei
     * gibt den anschließenden veränderten Graphen aus
     */
    public static void Transition(String Graphpath, String Move)
            throws Exception {
        Graph graph = new Graph();
        Zug zug = Zug.readZugFile(Move);
        graph.setPath(Graphpath);
        graph.ssuf(graph, zug).ausgeben();
    }

    /**
     * Führt den Zug aus
     * Der Errorcode wird aus der run-Funktion gelesen und hier verarbeitet
     * Errorcode = 0: Alles in Ordnung
     * Errorcode = 1: Zug wurde ausgesetzt
     * Errorcode = 2: Beende Spiel
     * Errorcode = 3: Stellung wiederholt
     *
     * @param zug     Der Zug als String
     * @param spieler Der Spieler der den Zug macht
     * @return Den String wie der Graph jetzt aussieht
     */
    public String auswerten(String zug, Seite spieler) {
        String error;
        Zustand retrnZustand = myGraph.run(zug, spieler);
        if (retrnZustand.getName() == null
                | retrnZustand.getErrorcode() == null) {
            error = ("Fehler dem Zustand");
        } else {
            switch (retrnZustand.getErrorcode()) {
                case 0:
                    return retrnZustand.getName();
                // break;
                case 1:
                    return retrnZustand.getName();
                // break;
                case 2:
                    spiel = false;
                    return retrnZustand.getName();
                case 3:
                    return retrnZustand.getName();
                // break;
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
        myGraph.map();
        while (spiel) {
            System.out.println(aktuellerSpieler.meineSeite.toString()
                    + " spielt folgenden Zug: ");
            String move = auswerten(aktuellerSpieler.nextZug().toFormat(),
                    aktuellerSpieler.meineSeite);
            System.out.println(move);

            if (aktuellerSpieler.meineSeite == Seite.Rom) {
                aktuellerSpieler = kathargo;
            } else {
                aktuellerSpieler = rom;
            }

        }
        System.out.println("Spiel beendet");
        System.out.println("Rom hat "
                + myGraph.getPunkteStandFuer(Seite.Rom) + " Punkt(e)");
        System.out
                .println("Kathargo hat "
                        + myGraph.getPunkteStandFuer(Seite.Kathargo)
                        + " Punkt(e)");
    }

    public void game(AIPlayer computer) {
        Seite aktuellerSpieler = Seite.Rom;
        myGraph.map();
        System.out.println("Bitte geben sie ob der Computer (R)om oder (C)athargo sein soll:");
        try {
            BufferedReader bufferRead = new BufferedReader(
                    new InputStreamReader(System.in));
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
                    String move = auswerten(computer.nextZug().toFormat(),
                            aktuellerSpieler);
                    System.out.println(move);
                } else if (aktuellerSpieler == Seite.Kathargo) {
                    System.out.println("Bitte Zug angeben:");
                    try {
                        BufferedReader bufferRead = new BufferedReader(
                                new InputStreamReader(System.in));
                        String zug = bufferRead.readLine();
                        String move = auswerten(zug, aktuellerSpieler);
                        System.out.println(move);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                aktuellerSpieler = (aktuellerSpieler == Seite.Rom) ? Seite.Kathargo
                        : Seite.Rom;

            }
        } else {
            while (spiel) {
                if (aktuellerSpieler == Seite.Kathargo) {
                    String move = auswerten(computer.nextZug().toFormat(),
                            aktuellerSpieler);
                    System.out.println(move);
                } else if (aktuellerSpieler == Seite.Rom) {
                    System.out.println("Bitte Zug angeben:");
                    try {
                        BufferedReader bufferRead = new BufferedReader(
                                new InputStreamReader(System.in));
                        String zug = bufferRead.readLine();
                        String move = auswerten(zug, aktuellerSpieler);
                        System.out.println(move);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                aktuellerSpieler = (aktuellerSpieler == Seite.Rom) ? Seite.Kathargo
                        : Seite.Rom;
            }
        }
        System.out.println("Spiel beendet");
        System.out.println("Rom hat "
                + myGraph.getPunkteStandFuer(Seite.Rom) + " Punkt(e)");
        System.out
                .println("Kathargo hat "
                        + myGraph.getPunkteStandFuer(Seite.Kathargo)
                        + " Punkt(e)");
    }

    public void game() {
        Seite aktuellerSpieler = Seite.Rom;
        myGraph.map();
        while (spiel) {
            System.out.println("Bitte Zug angeben:");
            try {
                BufferedReader bufferRead = new BufferedReader(
                        new InputStreamReader(System.in));
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
        System.out.println("Rom hat "
                + myGraph.getPunkteStandFuer(Seite.Rom) + " Punkt(e)");
        System.out
                .println("Kathargo hat "
                        + myGraph.getPunkteStandFuer(Seite.Kathargo)
                        + " Punkt(e)");

    }

    public void game(String ip, Integer port, AIPlayer ai) {
        Client myClient = new Client(port, ip, this);
        myClient.aigegner(ai);
    }

    public void game(String ip, Integer port) {
        Client myClient = new Client(port, ip, this);
        myClient.humanEnemy();
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
