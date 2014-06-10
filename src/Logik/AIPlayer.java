package Logik;

import Graph.Seite;
import Graph.Zug;
import Logik.Mechanik;


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

}
