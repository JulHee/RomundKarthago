package test;

import core.datacontainers.Seite;
import logik.Mechanik;
import logik.ai.Joernson;
import logik.ai.Killjoy;
import logik.ai.Sloth;

/**
 * Projekt : RomUndKathargo
 * Author  : Julian Heeger
 * Date    : 02.07.14
 * Year    : 2014
 */
public class RunClient {

    public static void main(String[] args) {
        Mechanik myMechanik = new Mechanik("ext/aiTestMap.txt");
        Killjoy myKilljoy = new Killjoy(Seite.Rom,myMechanik);
        Sloth mySloth = new Sloth(Seite.Rom,myMechanik);
        Joernson joern = new Joernson(Seite.Rom,myMechanik);

        myMechanik.game("localhost",9999,joern);
    }
}
