package network;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

import logik.AIPlayer;
import logik.Mechanik;
import core.datacontainers.Seite;
import core.datacontainers.Zug;

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
	    handleSocket_ai(s, ai);
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

    public void handleSocket_ai(Socket s, AIPlayer ai) {
	String input;
	try {
	    DataOutputStream out = new DataOutputStream(s.getOutputStream());
	    DataInputStream in = new DataInputStream(s.getInputStream());

	    // Senden der Map

	    ArrayList<String> maptext = myMechanik.getMyGraph().getMaptext();
	    for (String l : maptext) {
		out.writeUTF(l);
	    }
	    while (myMechanik.getSpiel()) {
		// Senden des Zuges
		Zug zug = ai.nextZug();
		out.writeUTF(zug.toFormat());

		// Auswerten des Zuges
		myMechanik.auswerten(zug.toFormat(), Seite.Rom);
		in.available();
		input = in.readUTF();
		myMechanik.auswerten(input, Seite.Kathargo);
	    }
	    s.close();

	} catch (IOException e) {
	    e.printStackTrace();
	}

    }

    public void handleSocket_hum(Socket s) {
	String input;
	try {
	    DataOutputStream out = new DataOutputStream(s.getOutputStream());
	    DataInputStream in = new DataInputStream(s.getInputStream());

	    // Senden der Map
	    ArrayList<String> maptext = myMechanik.getMyGraph().getMaptext();
	    for (String l : maptext) {
		out.writeUTF(l);
	    }
	    while (myMechanik.getSpiel()) {
		System.out.println("Bitte Zug angeben:");
		try {
		    BufferedReader bufferRead = new BufferedReader(
			    new InputStreamReader(System.in));
		    String zug = bufferRead.readLine();
		    out.writeUTF(zug);
		    String move = myMechanik.auswerten(zug, Seite.Rom);
		    in.available();
		    input = in.readUTF();
		    myMechanik.auswerten(input, Seite.Kathargo);
		} catch (IOException e) {
		    e.getStackTrace();
		}
	    }
	    s.close();

	} catch (IOException e) {
	    e.printStackTrace();
	}

    }
}
