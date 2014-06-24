package network;

import logik.Mechanik;

/**
 * Projekt : RomUndKathargo
 * Author  : Julian Heeger
 * Date    : 24.06.14
 * Year    : 2014
 */
public class Server_C {
    Integer port;
    Mechanik mechanik;

    public Server_C(Integer port, Mechanik mechanik) {
        this.port = port;
        this.mechanik = mechanik;
    }
}
