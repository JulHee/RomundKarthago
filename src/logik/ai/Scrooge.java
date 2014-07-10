package logik.ai;

import core.*;
import core.datacontainers.Knoten;
import core.datacontainers.Seite;
import core.datacontainers.Zug;
import exceptions.KeinBesetzerException;
import exceptions.ZugException;
import logik.Mechanik;

/**
 * Projekt : RomUndKathargo
 * Author  : Julian Heeger
 * Date    : 10.06.14
 * Year    : 2014
 */
public class Scrooge extends AIPlayer {
    public Scrooge(Seite s, Mechanik m) {
        meineSeite = s;
        this.mechanik = m;
    }

    public Zug nextZug() {
        return findZug();
    }

    /**
     * Berechnet den Zug mit dem maximalen Punkten.
     *
     * @return Zug
     */
    private Zug findZug() {
        Integer maxPunkte = -1;
        Zug retrn = null;
        Graph myGraph = mechanik.getMyGraph();

        for (Knoten k : myGraph.l_knoten) {
            Zug temp = null;
            try {
                temp = new Zug(meineSeite + " " + k.id);
            } catch (ZugException e) {
                System.out.println(e.getMessage());
            } catch (KeinBesetzerException e) {
                System.out.println(e.getMessage());
            }
            if (maxPunkte < getPunkte(temp, mechanik)) {
                retrn = temp;
            }
        }
        return retrn;
    }

    /**
     * Berechnet aus einem angeblichen Zug die Punkte
     *
     * @param z        Zug
     * @param mechanik Mechanik auf der gespielt wird
     * @return Anzahl an Punkten nach dem Zug
     */

    private Integer getPunkte(Zug z, Mechanik mechanik) {
        Mechanik mymechanik = mechanik.clone();
        mymechanik.auswerten(z.toFormat(), z.getSeite());
        return mymechanik.getMyGraph().getPunkteStandFuer(z.getSeite());
    }
}
