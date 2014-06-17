package logik;

import core.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Projekt: Rom und Karthago
 * Author : Julian Heeger, Markus Poell, Christian Bruene, Joern Kabuth
 * Date : 26.04.14
 * Year : 2014
 */
public class Mechanik implements Cloneable{

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

    public String auswerten(String zug, Seite spieler){
        String error;
        Zustand retrnZustand =  myGraph.run(zug,spieler);
        if (retrnZustand.getName() == null | retrnZustand.getErrorcode() == null) {
            error= ("Fehler dem Zustand");
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
                    error= "Error";
                    break;
            }
        }
        return error;
    }

	public void aiVsAiGame(AIPlayer ai1, AIPlayer ai2){
		ai1.meineSeite = Seite.Rom;
		ai2.meineSeite = Seite.Kathargo;
		AIPlayer aktuellerSpieler = ai1;

		Boolean letzerZugAusgesetzt = false;
		myGraph.map();
		while (spiel) {
			System.out.println(aktuellerSpieler.meineSeite.toString() + " spielt folgenden Zug: ");
			String move = auswerten(aktuellerSpieler.nextZug().toFormat(), aktuellerSpieler.meineSeite);
			System.out.println(move);

			if (aktuellerSpieler.meineSeite == Seite.Rom) {
				aktuellerSpieler = ai2;
			} else {
				aktuellerSpieler = ai1;
			}

		}
		System.out.println("Spiel beendet");
		System.out.println("Rom hat "+myGraph.besetztePunkteStandFuer(Seite.Rom)+" Punkt(e)");
		System.out.println("Kathargo hat "+myGraph.besetztePunkteStandFuer(Seite.Kathargo)+" Punkt(e)");
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
        System.out.println("Rom hat "+myGraph.besetztePunkteStandFuer(Seite.Rom)+" Punkt(e)");
        System.out.println("Kathargo hat "+myGraph.besetztePunkteStandFuer(Seite.Kathargo)+" Punkt(e)");
    }

    @Override
    public Mechanik clone()
    {
        try
        {
            return (Mechanik) super.clone();
        }
        catch ( CloneNotSupportedException e ) {
            // Kann eigentlich nicht passieren, da Cloneable
            throw new InternalError();
        }
    }


}