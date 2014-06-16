package logik;

import core.Seite;
import core.Zug;


/**
 * Projekt: Rom und Karthago
 * Author : Julian Heeger, Markus Poell, Christian Bruene, Joern Kabuth
 * Date : 10.06.14
 * Year : 2014
 */

public class WaspAI  extends AIPlayer {

	private int myCity = -1;

    public WaspAI(Seite s, int city) {
        meineSeite = s;
	    myCity = city;
    }

    public Zug nextZug(Mechanik mech) {
        Zug erg = new Zug(meineSeite, myCity);
        return erg;
    }
}
