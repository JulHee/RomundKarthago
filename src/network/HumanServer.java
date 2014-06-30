package network;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import logik.Mechanik;
import core.datacontainers.Seite;

public class HumanServer extends Server {

    static Seite mySeite = Seite.Kathargo;
    static Integer port;
    static Mechanik myMechanik;

    public HumanServer(Integer port) {
	super(port);
    }

    /**
     * Funktionen zum Starten des Servers(Karthago)
     * Empfangen der Map mit anschließender Ausführung
     * des Spieles untereinader
     * 
     * @throws Exception
     */
    @Override
    public void run() throws Exception {
	try (ServerSocket ss = new ServerSocket(port)) {
	    System.out.println("Der Server läuft und hört auf Port:" + port);
	    Socket clients = ss.accept();
	    System.out.println("Der Client " + clients.getLocalAddress() + ":"
		    + clients.getLocalPort() + " wurde verbunden");
	    DataOutputStream out = new DataOutputStream(
		    clients.getOutputStream());
	    DataInputStream in = new DataInputStream(clients.getInputStream());

	    // Einlesen der Map, welche vom Client gesendet wird
	    ArrayList<String> map = new ArrayList<String>();
	    String line;
	    while ((line = in.readUTF()).length() > 0) {
		map.add(line);
	    }
	    checkm

	    // TODO Start des eigentlichen Spiels
	    /*
	     * Mechanik in client und Server sinnlos oder?
	     * Server sollte Mechanik haben/kontrollieren bzw gleiche Mechanic?
	     */
	    while (myMechanik.getSpiel()) {
		System.out.println("Bitte geben Sie ihren Zug ein:");
		BufferedReader input = new BufferedReader(
			new InputStreamReader(System.in));
		String zug = input.readLine();
		out.writeUTF(zug);
		myMechanik.auswerten(zug, mySeite);
		in.readUTF();
	    }
	    ss.close();

	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}
