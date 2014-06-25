package logik;

import core.datacontainers.Seite;
import core.datacontainers.Zug;

/**
 * Projekt: Rom und Karthago
 * Author : Julian Heeger, Markus Poell, Christian Bruene, Joern Kabuth
 * Date : 10.06.14
 * Year : 2014
 */

public abstract class AIPlayer {
    protected Mechanik mechanik;
    String path;

    protected Seite meineSeite;

    abstract public Zug nextZug();

    @Override
    public String toString() {
	return meineSeite.toString();
    }

}
