package Logik;

import core.*;

/**
 * Projekt: Rom und Karthago
 * Author : Julian Heeger, Markus Poell, Christian Bruene, Joern Kabuth
 * Date : 10.06.14
 * Year : 2014
 */

public class Sloth extends AIPlayer {
    public Sloth(Seite s) {
        meineSeite = s;
    }

    Zug nextZug() {
        Zug erg = new Zug(meineSeite, berechneZugStadt());
        return erg;
    }

    private Integer berechneZugStadt() {
        int erg = -1;
        Graph graph = mechanik.getMyGraph();
        for (Knoten k : graph.toArrayList(graph.l_knoten)) {
            if (k.getSeite() == Seite.Neutral) {
                erg = k.id;
            }
        }
        return erg;
    }

}
