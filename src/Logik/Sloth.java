package Logik;

import Graph.*;

/**
 * Created by Acer on 10.06.2014.
 */
public class Sloth extends AIPlayer {
    @Override
    Zug nextZug() {
        Zug erg = new Zug(meineSeite,berechneZugStadt());
        return erg;
    }

private Integer berechneZugStadt(){
        int erg=-1;
        Graph graph = mechanik.getMyGraph();
        for(Knoten k : graph.toArrayList(graph.l_knoten) ){
            if(k.getSeite() == Seite.Neutral){erg=k.id;}
        }
        return erg;
    }

    void setMeineSeite(Seite s){meineSeite=s;}
}
