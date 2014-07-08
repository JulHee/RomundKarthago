package test;

import core.datacontainers.Seite;
import logik.Mechanik;
import logik.ai.Killjoy;
import logik.ai.Sloth;
import logik.ai.optionalAI;

/**
 * Projekt : RomUndKathargo
 * Author  : Julian Heeger
 * Date    : 02.07.14
 * Year    : 2014
 */
public class RunClient {

    public static void main(String[] args) {
        Mechanik myMechanik = new Mechanik("ext/map4.txt");
        Killjoy myKilljoy = new Killjoy(Seite.Rom,myMechanik);
        Sloth mySloth = new Sloth(Seite.Rom,myMechanik);
        optionalAI joern = new optionalAI(Seite.Rom,myMechanik);

        myMechanik.game("137.248.56.178",63823,joern);
    }
}
