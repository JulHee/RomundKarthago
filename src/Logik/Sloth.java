package logik;

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

    Zug nextZug(Mechanik mech) {
        Zug erg = new Zug(meineSeite, berechneZugStadt(mech.getMyGraph()));
        return erg;
    }

    private int berechneZugStadt(Graph graph) {
        int erg = -1;
        for (Knoten k : graph.toArrayList()) {
            if (k.getSeite() == Seite.Neutral) {
                return k.id;
            }
        }
        return erg;
    }

}
