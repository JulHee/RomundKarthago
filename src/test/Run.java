package test;

import core.datacontainers.Seite;
import logik.Killjoy;
import logik.Mechanik;
import logik.WaspAI;
import network.HumanServer;

/**
 * Projekt: Rom und Karthago
 * Author : Julian Heeger, Markus Poell, Christian
 * Bruene, Joern Kabuth
 * Date : 26.04.14
 * Year : 2014
 */

public class Run {

    public static void main(String[] args) throws Exception {

        Mechanik myMechanik = new Mechanik("ext/GameBoard.txt");
        final Integer port = 9999;

        Thread test_server = new Thread(){
            @Override
            public void run() {
                HumanServer hum = new HumanServer(port);
            }
        };
        test_server.run();

        Killjoy myKilljoy = new Killjoy(Seite.Rom,myMechanik);
        WaspAI myWasp = new WaspAI(Seite.Rom);

        myMechanik.game("137.248.59.235",port,myWasp);

    }
}
