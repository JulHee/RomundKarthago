package logik;
import core.*;

/**
 * Projekt : RomUndKathargo
 * Author  : Julian Heeger
 * Date    : 10.06.14
 * Year    : 2014
 */
public class Killjoy extends AIPlayer {
    public Killjoy(Seite s) {
        meineSeite = s;
    }

    Zug nextZug(Mechanik mech) {
        return findZug();
    }

    /**
     * Berechnet den Zug mit dem minimalen Punkten.
     *
     * @return Zug
     */
    private Zug findZug() {
        Integer minPunkte = -1;
        Zug retrn = null;
        Graph myGraph = mechanik.getMyGraph();
        for (Knoten k : myGraph.l_knoten) {
            Zug temp = new Zug(meineSeite, k.id);
            if (minPunkte > getPunkte(temp, mechanik)) {
                retrn = temp;
            }
        }
        return retrn;
    }

    /**
     * Berechnet aus einem angeblichen Zug die  Punkte
     *
     * @param z        Zug
     * @param mechanik Mechanik auf der gespielt wird
     * @return Anzahl an Punkten nach dem Zug
     */

    private Integer getPunkte(Zug z, Mechanik mechanik) {
        Mechanik mymechanik = mechanik.clone();
        mymechanik.auswerten(z.toFormat(), z.getSeite());
        return mymechanik.getMyGraph().besetztePunkteStandFuer(z.getSeite() == Seite.Kathargo ? Seite.Rom : Seite.Kathargo);
    }
}
