package test;

import core.datacontainers.Seite;
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
        WaspAI myWasp = new WaspAI(Seite.Rom);
        myMechanik.game("127.0.0.1",3000,myWasp);
    }
}