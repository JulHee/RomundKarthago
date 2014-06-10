package Logik;

import Graph.Seite;
import Graph.Zug;
import Logik.Mechanik;


/**
 * Created by Acer on 09.06.2014.
 */
public abstract class AIPlayer {
    protected Mechanik mechanik;
    protected Seite meineSeite;
    abstract Zug nextZug();
    abstract void setMeineSeite(Seite s);


}
