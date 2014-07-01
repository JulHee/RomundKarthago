package test;

import core.datacontainers.Seite;
import logik.Killjoy;
import logik.Mechanik;
import logik.WaspAI;

/**
 * Projekt  : RomUndKathargo
 * Author   : Julian Heeger
 * Date     : 30.06.2014
 * Year     : 2014
 */
public class Client_starten {
    public static void main(String[] args) {
        Mechanik myMechanik = new Mechanik("ext/GameBoard.txt");
        Killjoy myWasp = new Killjoy(Seite.Rom,myMechanik);
        myMechanik.game("137.248.59.235",12345,myWasp);
    }
}