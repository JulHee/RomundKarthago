package network;

import core.datacontainers.Seite;
import core.datacontainers.Zug;
import logik.Mechanik;
import logik.ai.AIPlayer;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

import javafx.scene.control.TextArea;

/**
 * Projekt : RomUndKathargo
 * Author : Julian Heeger
 * Date : 24.06.14
 * Year : 2014
 */
public class guiClient
{

	Seite mySeite = Seite.Rom;
	private Integer port = 0;
	private String ip = "";
	private final Mechanik myMechanik;
	private TextArea myTextArea = null;

	public guiClient (Integer port,
					  String ip,
					  Mechanik m,TextArea ta)
	{
		this.port = port;
		this.ip = ip;
		this.myMechanik = m;
		this.myTextArea = ta;
	}

	public Integer getPort ()
	{
		return port;
	}

	public String getIp ()
	{
		return ip;
	}

	/**
	 * Funktion zum Starten eines Spiel mit einer AI
	 *
	 * @param ai Die gewählte AI
	 */

	public void aigegner (AIPlayer ai)
	{
		try (
				Socket s = new Socket( ip, port )
		)
		{
			handleSocket_ai( s, ai );
		} catch ( IOException e )
		{
			e.getStackTrace();
		}
	}

	/**
	 * Funktion zum Starten eines Spiel druch Tastatur-Eingabe
	 */

	public void humanEnemy ()
	{
		try (
				Socket s = new Socket( ip, port )
		)
		{
			handleSocket_hum( s );
		} catch ( IOException e )
		{
			e.getStackTrace();
		}
	}

	/**
	 * Übernimmt die Verbindung mit dem Server als AI-Gegner
	 *
	 * @param s  Übergabe des verbundenen Socket
	 * @param ai Die gewählte AI
	 */

	public void handleSocket_ai (Socket s,
								 AIPlayer ai)
	{
		String in;
		try
		{
			PrintWriter output = new PrintWriter( s.getOutputStream(), true /* autolush */ );
			BufferedReader input = new BufferedReader( new InputStreamReader( s.getInputStream() ) );

			// Senden der Map

			ArrayList<String> maptext = myMechanik.getMyGraph().getMaptext();
			for ( String l : maptext )
			{
				output.println( l );
			}
			while ( myMechanik.getSpiel() )
			{

				// Senden des Zuges
				Zug zug = ai.nextZug();
				myTextArea.appendText( "Senden des Zuges: " + zug.toFormat()+"\n" );
				output.println( zug.toFormat() );
				myTextArea.appendText( "Die Map: " + myMechanik.getMyGraph().convertToString() );

				// Auswerten des Zuges
				myMechanik.auswerten( zug.toFormat(), mySeite );
				in = input.readLine();
				myTextArea.appendText( "Lesen des Zuges: " + in );
				myMechanik.auswerten( in, Seite.Kathargo );
				myTextArea.appendText( "Die Map: " + myMechanik.getMyGraph().convertToString() );
			}
			s.close();

		} catch ( IOException e )
		{
			e.printStackTrace();
		}

	}

	/**
	 * Übernimmt die Kommunikation mit dem Server als menschlicher Gegner
	 *
	 * @param s Übergabe des verbundenen Socket
	 */

	public void handleSocket_hum (Socket s)
	{
		String in;
		try
		{

			//Stream zu abfragen der Tastatureingaben
			BufferedReader bufferRead = new BufferedReader( new InputStreamReader( System.in ) );

			//Socket Streams
			PrintWriter output = new PrintWriter( s.getOutputStream(), true /* autolush */ );
			BufferedReader input = new BufferedReader( new InputStreamReader( s.getInputStream() ) );

			// Senden der Map
			ArrayList<String> maptext = myMechanik.getMyGraph().getMaptext();
			for ( String l : maptext )
			{
				output.println( l );
			}
			myTextArea.appendText( "Die Map: " + myMechanik.getMyGraph().convertToString() );

			//Spiel-Loop
			while ( myMechanik.getSpiel() )
			{
				myTextArea.appendText( "Bitte Zug angeben:" );
				try
				{

					//Lesen des Zuges
					String zug = bufferRead.readLine();
					myTextArea.appendText( "Senden des Zuges: " + zug );


					// Lesen des Zuges
					output.println( zug );

					// Auswerten
					String move = myMechanik.auswerten( zug, mySeite );
					myTextArea.appendText( "Die Map: " + myMechanik.getMyGraph().convertToString() );

					// Falls nach dem ersten letzen Zug, dass Spiel beendet wurde
					if ( ! myMechanik.getSpiel() )
					{
						break;
					}

					// Lesen des Gegnerzuges
					in = input.readLine();
					if ( in == null )
					{

					}
					myTextArea.appendText( "Der Gegner machte den Zug: " + in );

					// Auswerten
					myMechanik.auswerten( in, Seite.Kathargo );
					myTextArea.appendText( "Die Map: " + myMechanik.getMyGraph().convertToString() );
				} catch ( IOException e )
				{
					e.getStackTrace();
				}
			}
			s.close();

		} catch ( IOException e )
		{
			e.printStackTrace();
		}

	}

	/**
	 * Testfunktion um geeignete Kommunikation zwischen Client und Server zu testen
	 * TODO Vorsicht die Streams wurden geändert !!!!! In dieser Weise kann keine Verbundung funktionieren !!!!!
	 */
	public void test ()
	{
		try
		{
			Socket s = new Socket( this.getIp(), this.getPort() );

			//Socket Streams
			DataOutputStream out = new DataOutputStream( s.getOutputStream() );
			DataInputStream in = new DataInputStream( s.getInputStream() );

			// Senden der Map
			ArrayList<String> maptext = myMechanik.getMyGraph().getMaptext();
			for ( String l : maptext )
			{
				out.writeUTF( l );
			}
			out.writeUTF( "R 3" );
			s.close();

		} catch ( IOException e )
		{
			e.printStackTrace();
		}

	}
}