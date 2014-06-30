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
public class Client {

	Seite mySeite = Seite.Rom;
	private Integer port = 0;
	private String ip = "";
	private final Mechanik myMechanik;

	public Client(Integer port, String ip, Mechanik m) {
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
				System.out.println("Senden des Zuges: "+zug.toFormat());
				out.writeUTF(zug.toFormat());

				// Auswerten des Zuges
				myMechanik.auswerten(zug.toFormat(), Seite.Rom);
				in.available();
				input = in.readUTF();
				System.out.println("Lesen des Zuges: "+input);
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

			//Stream zu abfragen der Tastatureingaben
			BufferedReader bufferRead = new BufferedReader(
					new InputStreamReader(System.in));

			//Socket Streams
			DataOutputStream out = new DataOutputStream(s.getOutputStream());
			DataInputStream in = new DataInputStream(s.getInputStream());

			// Senden der Map
			ArrayList<String> maptext = myMechanik.getMyGraph().getMaptext();
			for (String l : maptext) {
				out.writeUTF(l);
			}

			//Spiel-Loop
			while (myMechanik.getSpiel()) {
				System.out.println("Bitte Zug angeben:");
				try {

					//Lesen des Zuges
					String zug = bufferRead.readLine();
					System.out.println("Senden des Zuges: "+zug);

					// Lesen des Zuges
					out.writeUTF(zug);

					// Auswerten
					String move = myMechanik.auswerten(zug, Seite.Rom);
					System.out.println(myMechanik.getMyGraph().convertToString());

					// Falls nach dem ersten letzen Zug, dass Spiel beendet wurde
					if (!myMechanik.getSpiel()){
						break;
					}
					in.available();

					// Lesen des Gegnerzuges
					input = in.readUTF();
					System.out.println("Der Gegner machte den Zug: "+input);

					// Auswerten
					myMechanik.auswerten(input, Seite.Kathargo);
					System.out.println(myMechanik.getMyGraph().convertToString());
				} catch (IOException e) {
					e.getStackTrace();
				}
			}
			s.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	/**
	 * Testfunktion um geeignete Kommunikation zwischen Client und Server zu testen
	 */
	public void test() {
		System.out.println("did smth happen");
		try {Socket s = new Socket(this.getIp(),this.getPort());
			System.out.println("TestText");
			//Socket Streams
			DataOutputStream out = new DataOutputStream(s.getOutputStream());
			DataInputStream in = new DataInputStream(s.getInputStream());

			// Senden der Map
			ArrayList<String> maptext = myMechanik.getMyGraph().getMaptext();
			for (String l : maptext) {
				out.writeUTF(l);
			}
			s.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
