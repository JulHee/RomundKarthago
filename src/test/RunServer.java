package test;

import core.datacontainers.Seite;
import logik.ai.Joernson;
import network.Server;

/**
 * Projekt: Rom und Karthago
 * Author : Julian Heeger, Markus Poell, Christian
 * Bruene, Joern Kabuth
 * Date : 26.04.14
 * Year : 2014
 */

public class RunServer {

    public static void main(String[] args) throws Exception {

        // TODO Die Mechanik muss der AI später übergeben werden get und Setter setzen !!!!!!!!

        final Integer port = 9999;
        //Joernson joern = new Joernson(Seite.Kathargo,null);
        Server hum = new Server(port);
    }
}
