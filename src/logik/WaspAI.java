package logik;

import core.datacontainers.Seite;
import core.datacontainers.Zug;


/**
 * Projekt: Rom und Karthago
 * Author : Julian Heeger, Markus Poell, Christian Bruene, Joern Kabuth
 * Date : 10.06.14
 * Year : 2014
 */

public class WaspAI  extends AIPlayer {

    public WaspAI(Seite s) {
        meineSeite = s;
    }

    public Zug nextZug() {
        Zug erg = new Zug(meineSeite.toString()+" 0");
        return erg;
    }
}
