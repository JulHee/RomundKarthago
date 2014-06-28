package network;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import core.datacontainers.Seite;
import core.datacontainers.Zug;
import logik.AIPlayer;
import logik.Mechanik;

/**
 * Projekt : RomUndKathargo
 * Author  : Julian Heeger
 * Date    : 24.06.14
 * Year    : 2014
 */
public class Server_C {

	static Seite mySeite = Seite.Kathargo;
	static Integer port;
	static Mechanik myMechanik;

	public Server_C(Integer port, Mechanik mechanik) {
		this.port = port;
		this.myMechanik = mechanik;
	}
	/**
	 * Funktionen zum Starten des Servers(Karthago)
	 * Empfangen der Map mit anschließender Ausführung
	 * des Spieles untereinader
	 * @throws Exception
	 */
	public static void HumServer() throws Exception{
		try (ServerSocket ss = new ServerSocket(port)){
			Socket clients = ss.accept();
			DataOutputStream out = new DataOutputStream(clients.getOutputStream());
			DataInputStream in = new DataInputStream(clients.getInputStream());

			//Einlesen der Map, welche vom Client gesendet wird
			ArrayList<String> map = new ArrayList<String>();
			String line;
			while((line = in.readUTF()).length() > 0){
				map.add(line);
			}
			//TODO Start des eigentlichen Spiels
			/*
			 * Mechanik in client und Server sinnlos oder?
			 * Server sollte Mechanik haben/kontrollieren bzw gleiche Mechanic?
			 */
			while(myMechanik.getSpiel()){
				System.out.println("Bitte geben Sie ihren Zug ein:");
				BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
				String zug = input.readLine();
				out.writeUTF(zug);
				myMechanik.auswerten(zug, mySeite);
				in.readUTF();
			}


		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void AIServer(AIPlayer player){
		try (ServerSocket ss = new ServerSocket(port)){
			Socket clients = ss.accept();
			DataOutputStream out = new DataOutputStream(clients.getOutputStream());
			DataInputStream in = new DataInputStream(clients.getInputStream());

			//Einlesen der Map, welche vom Client gesendet wird
			ArrayList<String> map = new ArrayList<String>();
			String line;
			while((line = in.readUTF()).length() > 0){
				map.add(line);
			}
			//TODO Start des eigentlichen Spiels
			/*
			 * Mechanik in client und Server sinnlos oder?
			 * Server sollte Mechanik haben/kontrollieren bzw gleiche Mechanic?
			 */
			while(myMechanik.getSpiel()){
				Zug zug = player.nextZug();
				out.writeUTF(zug.toFormat());
				myMechanik.auswerten(zug.toFormat(), mySeite);
				in.readUTF();
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) throws Exception {
	}
}
