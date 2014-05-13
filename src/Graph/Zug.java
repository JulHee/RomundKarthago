package Graph;

/**
 * Projekt: RomUndKathargo
 * Author : Julian Heeger
 * Date : 13.05.14
 * Year : 2014
 */
public class Zug {
    private Seite seite;
    private int stadt;

    public Zug(Seite seite, int stadt) {
        this.seite = seite;
        this.stadt = stadt;
    }

    public Seite getSeite() {
        return seite;
    }

    public int getStadt() {
        return stadt;
    }
}
