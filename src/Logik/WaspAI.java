package Logik;

import Graph.Seite;
import Graph.Zug;


/**
 * Created by Acer on 09.06.2014.
 */
public class WaspAI  extends AIPlayer {

    public Zug nextZug() {

        Zug erg = new Zug("0"+meineSeite.toString());
        return erg;
    }
    void setMeineSeite(Seite s){meineSeite=s;}


}
