package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import logik.AIPlayer;
import logik.Mechanik;
import core.datacontainers.Seite;

/**
 * Projekt : RomUndKathargo
 * Author : Julian Heeger
 * Date : 24.06.14
 * Year : 2014
 */
public class Client_R {

    Seite mySeite = Seite.Rom;
    private Integer port = 0;
    private String ip = "";
    private final Mechanik myMechanik;

    public Client_R(Integer port, String ip, Mechanik m) {
	this.port = port;
	this.ip = ip;
	this.myMechanik = m;
    }

    public Integer getPort() {
	return port;
    }

    public String getIp() {
	return ip;
    }

    public void aigegner(AIPlayer ai) {
	try (Socket s = new Socket(ip, port)) {
	    handleSocket_ai(s);
	} catch (IOException e) {
	    e.getStackTrace();
	}
    }

    public void humanEnemy() {
	try (Socket s = new Socket(ip, port)) {
	    handleSocket_hum(s);
	} catch (IOException e) {
	    e.getStackTrace();
	}
    }

    public void handleSocket_ai(Socket s) {
	try {
	    DataOutputStream out = new DataOutputStream(s.getOutputStream());
	    DataInputStream in = new DataInputStream(s.getInputStream());

	    // Senden der Map
	    ArrayList<String> maptext = myMechanik.getMyGraph().getMaptext();
	    for (String l : maptext) {
		out.writeUTF(l);
	    }

	    //

	} catch (IOException e) {
	    e.printStackTrace();
	}

    }

    public void handleSocket_hum(Socket s) {

	try {
	    DataOutputStream out = new DataOutputStream(s.getOutputStream());
	    DataInputStream in = new DataInputStream(s.getInputStream());

	    // Senden der Map
	    ArrayList<String> maptext = myMechanik.getMyGraph().getMaptext();
	    for (String l : maptext) {
		out.writeUTF(l);
	    }

	} catch (IOException e) {
	    e.printStackTrace();
	}

    }
}
