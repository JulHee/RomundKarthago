package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import core.datacontainers.Seite;
import logik.Mechanik;

/**
 * Projekt : RomUndKathargo
 * Author  : Julian Heeger
 * Date    : 24.06.14
 * Year    : 2014
 */
public class Server_C {
    
	  Seite mySeite = Seite.Kathargo;
	  static Integer port;
	  static Mechanik myMechanik;

    public Server_C(Integer port, Mechanik mechanik) {
        this.port = port;
        this.myMechanik = mechanik;
    }
    public static void main(String[] args) {
		try (ServerSocket ss = new ServerSocket(port)){
			Socket clients = ss.accept();
			DataOutputStream out = new DataOutputStream(clients.getOutputStream());
            DataInputStream in = new DataInputStream(clients.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
