package Logik;

import core.Seite;
import core.Zug;


/**
 * Projekt: Rom und Karthago
 * Author : Julian Heeger, Markus Poell, Christian Bruene, Joern Kabuth
 * Date : 10.06.14
 * Year : 2014
 */

public abstract class AIPlayer {
    protected Mechanik mechanik;
    protected Seite meineSeite;

    abstract Zug nextZug();

	public String toString(){
		return meineSeite.toString();
	}

}
