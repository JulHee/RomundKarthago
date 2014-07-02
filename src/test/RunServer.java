package test;

import network.HumanServer;

/**
 * Projekt: Rom und Karthago
 * Author : Julian Heeger, Markus Poell, Christian
 * Bruene, Joern Kabuth
 * Date : 26.04.14
 * Year : 2014
 */

public class RunServer {

    public static void main(String[] args) throws Exception {

        final Integer port = 9999;
        HumanServer hum = new HumanServer(port);
    }
}
