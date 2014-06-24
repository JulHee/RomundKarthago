package network;

import core.datacontainers.Seite;
import logik.Mechanik;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * Projekt : RomUndKathargo
 * Author  : Julian Heeger
 * Date    : 24.06.14
 * Year    : 2014
 */
public class Client_R {

    Seite mySeite = Seite.Rom;
    static Integer port = 0;
    static String ip = "";
    static Mechanik myMechanik;

    public Client_R(Integer port,String ip,Mechanik m) {
        this.port = port;
        this.ip = ip;
        this.myMechanik = m;
    }

    public static Integer getPort() {
        return port;
    }

    public static String getIp() {
        return ip;
    }

    public static void main(String[] args) {
        try(Socket s = new Socket(ip,port)) {
            handleSocket(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void handleSocket(Socket s){
        try {
            DataOutputStream out = new DataOutputStream(s.getOutputStream());
            DataInputStream in = new DataInputStream(s.getInputStream());

            // Senden der Map
            ArrayList<String> maptext = myMechanik.getMyGraph().getMaptext();
            for (String s : maptext){
                out.writeUTF(s);
            }

            //

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
