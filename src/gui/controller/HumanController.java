package gui.controller;

import core.datacontainers.Kante;
import core.datacontainers.Knoten;
import core.datacontainers.Seite;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.FileChooser;
import logik.Mechanik;
import org.controlsfx.control.PopOver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Projekt : RomUndKathargo
 * Author  : Julian Heeger
 * Date    : 08.07.14
 * Year    : 2014
 */
public class HumanController
{

    /*
	 * Der Controller an sich ist in zwei fast identische Teile geteilt die sich beide Ausschließen. Der eine reagiert
	 * wie der Server der als Kathargo auf eine Verbindung wartet und sich mit dem ersten Klienten der die Verbindung
	 * herstellt verbindet. Davor wurden alle Elemente die zu einem Clienten gehören auf der Oberfläche deaktiviert.
	 * Der andere agiert als Client und wir durch angabe der IP,Port und der Map erstellt. Dieser verbindet sich als
	 * Client an den angebenen Server.
	 *
	 * Allgemein läuft ein Spielaufbau so ab, dass man je nach dem was man spielen möchte einen Server eröffnet oder
	 * auf einen Server verbindet.
	 *
	 * Server:
	 * Der Server bekommt die Map gesendet und erstellt die Mechanik um die Spiellogik zu initialisieren. Dannach wird
	 * das Spielfeld an sich geladen, welches über die initial() funktion läuft. Dabei werden die Städte als Buttons
	 * ein gelesen und die Kanten als Linien zwischen den Buttons. Jeder Button erhält die gleiche Funktion
	 * zugmachen(Button) in der er sich selbst übergibt, wodruch ein direkter Zugriff auch den Button möglich ist.
	 * Man überprüft ob der Spieler der das Spiel an sich spielt überhaupt am Zug, ist da sonst probleme mit der
	 * synconisität mit dem Gegenspieler auftretten. Falls dies nicht so ist kann man aus dem Button den Text lesen
	 * . diesen durch an den Gegner senden und durch die Mechanik auswerten lassen. Durch das Auswerten des Zuges wird
	 * ein String mit der Belegegung der Städte zurück gegeben die man durch die Methode changebuttons() auf das Spiel-
	 * feld überträgt. Dannach wartet man auf die Rückmeldung des Gengners, worch das gleiche Verfahren wieder
	 * angewendet wird. Nachdem die Rückmeldung wieder eingetroffen ist, kann die Oberfläche wieder freigeben und der
	 * Spieler hat die Möglichkeit den nächsten Zug zu machen.
	 *
	 *
	 * Client:
	 * Der Client sendet die Map und den ersten Zug und wartet dannach auf die Rückmeldung des Servers.
     */

	// Variablen abhängig ob Server oder Client läuft
	Seite eigenSeite = null;
	Seite gegner = null;

	// Läuft ein Spiel ?
	Boolean runningGame = false;

	// Ist der Benutzer am zug
	Boolean aktiveHuman = false;

	// Server
	ServerSocket server = null;
	Socket client = null;

	// Verbindung
	String ip = "localhost";
	Integer port = 9998;

	// Popover für den Clienten
	PopOver clientPop = null;

	// Popover für den Server
	PopOver serverPop = null;

	// FileChooser zum auswählen der Map
	FileChooser filechooser = new FileChooser();

	// Anlegen des Graphen

	private Mechanik mechanik = null;

	// Spieler der am Zug ist

	private Seite spieler = Seite.Rom;

	// Button und Path die bei Laufzeit erstellt wird

	private final LinkedList<Button> buttons = new LinkedList<>();
	private final LinkedList<Path> paths = new LinkedList<>();

	// Button größe

	private final double HEIGHT = 25;
	private final double WIDTH = 40;

	// Code für die Oberfläche

	@FXML
	private MenuItem mi_quit;

	@FXML
	private AnchorPane ap_map;

	@FXML
	private Label lb_aktuell_port;

	@FXML
	private Button bt_client;

	@FXML
	private Button bt_spielBeenden;

	@FXML
	private TextArea ta_history;

	@FXML
	private Tab t_log;

	@FXML
	private Label lb_aktuell_ip;

	@FXML
	private TextArea ta_log;

	@FXML
	private Button bt_server;

	@FXML
	private Label lb_letzerZug;

	/**
	 * Beenden des Programmes
	 *
	 * @param event Event des MenuItems
	 */

	@FXML
	void mi_quit_click (ActionEvent event)
	{
		System.exit( 0 );
	}

	/**
	 * Wenn der Clientbutton geklickt wurde
	 *
	 * @param event Event des Buttons
	 */

	@FXML
	void bt_client_click (ActionEvent event)
	{
		if ( ! runningGame )
		{
			bt_server.setVisible( false );
			clientPop = getClientPopover();
			clientPop.show( bt_client );
			// TODO Clientoperation
		}
	}

	/**
	 * Wenn der Serverbutton geklickt wurde
	 *
	 * @param event Event des Buttons
	 */

	@FXML
	void bt_server_click (ActionEvent event)
	{
		if ( ! runningGame )
		{
			bt_client.setVisible( false );
			serverPop = getServerPopover();
			serverPop.show( bt_server );
		}
	}

	@FXML
	void bt_spielBeenden_click (ActionEvent event)
	{

	}

	/**
	 * Initialisert die Oberfläche in dem sie die Buttons und Linien auf das Pane malt
	 */

	private void initial ()
	{
		// Hinzufügen der Beschriftung in den unteren ButtonBar
		if ( ip != null )
		{
			lb_aktuell_ip.setText( ip );
		}
		if ( port != null )
		{
			lb_aktuell_port.setText( String.valueOf( port ) );
		}

		// Linien hinzufügen

		for ( Kante i : mechanik.getMyGraph().l_kante )
		{
			paths.add( pathanlegen( i, WIDTH, HEIGHT ) );
		}

		// Buttons hinzufügen

		for ( Knoten i : mechanik.getMyGraph().l_knoten )
		{
			buttons.add( buttonalegen( i ) );
		}

		// Elemente auf das Pane legen
		ap_map.getChildren().addAll( paths );
		ap_map.getChildren().addAll( buttons );
	}

	/**
	 * Erzeigt einen Button der einen Knoten represäntiert
	 *
	 * @param knoten Der Knoten der als Button angezeigt werden soll
	 *
	 * @return
	 */

	private Button buttonalegen (Knoten knoten)
	{
		final Button temp = new Button();
		temp.setText( String.valueOf( knoten.id ) + " " + knoten.seite.toString() );
		temp.setLayoutX( knoten.position.x );
		temp.setLayoutY( knoten.position.y );
		temp.setOnAction( new EventHandler<ActionEvent>()
		{
			@Override
			public void handle (ActionEvent e)
			{
				ta_log.appendText( temp.getText() );
				zugmachen( temp );
			}
		} );
		return temp;
	}

	/**
	 * Erzeugt eine Linine zwischen zwei Punkten
	 *
	 * @param kante  Die Kante mit den zwei betreffenden Buttons
	 * @param width  Die breite des Buttons
	 * @param height Die Höhe des Buttons
	 *
	 * @return Ein Path der die Linie zwischen zwei Buttons beschreibt
	 */

	private Path pathanlegen (Kante kante,
							  double width,
							  double height)
	{
		Path path = new Path();

		MoveTo moveTo = new MoveTo();
		moveTo.setX( kante.punkt1.position.x + ( width / 2 ) );
		moveTo.setY( kante.punkt1.position.y + ( height / 2 ) );

		LineTo lineTo = new LineTo();
		lineTo.setX( kante.punkt2.position.x + ( width / 2 ) );
		lineTo.setY( kante.punkt2.position.y + ( height / 2 ) );

		path.getElements().add( moveTo );
		path.getElements().add( lineTo );
		path.setStrokeWidth( 2 );
		path.setStroke( Color.BLACK );
		return path;
	}

	/**
	 * Gibt die andere den Aktuellen Spieler als Seite zurück und setzt direkt
	 * die andere Seite als aktiv
	 *
	 * @return Aktueller Spieler
	 */

	private Seite aktuellerSpieler ()
	{
		Seite tmp = spieler;
		spieler = spieler == Seite.Rom ? Seite.Kathargo : Seite.Rom;
		return tmp;
	}

	/**
	 * Funktion die den Buttons zugeweisen werden. Jeder Klick auf einen Button simuliert einen Zug
	 *
	 * @param sender Der Button der geklickt wurde
	 */

	private void zugmachen (Button sender)
	{
		if ( aktiveHuman && client != null )
		{
			try
			{
				aktiveHuman = false;

				// Öffnen der Streams zum lesen der I/O Eingaben zwischen den Sockets und der Eingabe der Tastatur
				BufferedReader tastatur_input = new BufferedReader( new InputStreamReader( System.in ) );
				PrintWriter output = new PrintWriter( client.getOutputStream(), true /* autolush */ );
				BufferedReader input = new BufferedReader( new InputStreamReader( client.getInputStream() ) );

				String buttonText = sender.getText();
				String zug = buttonText.charAt( 0 ) + " " + eigenSeite.toString();

				// Zug senden
				output.println( zug );
				refreshHistory();

				// Auswerten des Zuges
				changebuttons( mechanik.auswerten( zug, eigenSeite ) );

				//Falls der letzte Zug das Spiel schon beendet hat
				if ( ! mechanik.getSpiel() )
				{
					// TODO Spiel muss unterbrochen werden, falls mit dem eingenen Zug das Spiel beendet wurde
				}

				// Lesen des Gegnerzuges
				String zug_gegner = input.readLine();

				// Auswerten
				changebuttons( mechanik.auswerten( zug_gegner, Seite.Rom ) );
				refreshHistory();

				aktiveHuman = true;
			} catch ( IOException ex )
			{
				ex.printStackTrace();
			}
		}

	}

	/**
	 * Aktuallisiert die Beschriftung der Buttons
	 *
	 * @param s Beschreibung des Graphen wobei jedes Zeichen einer ID/Button zugeordnet ist
	 */

	private void changebuttons (String s)
	{
		ap_map.getChildren().sorted().stream().filter( n -> n instanceof Button ).forEach( n -> {
			String bttext = ( ( Button ) n ).getText();
			Integer btid = Integer.parseInt( String.valueOf( bttext.charAt( 0 ) ) );
			( ( Button ) n ).setText( btid + " " + String.valueOf( s.charAt( btid ) ) );
			ta_log.appendText( bttext );

		} );
	}

	/**
	 * Aktuallisiert das History-Log mit den aktuellen History
	 */

	private void refreshHistory ()
	{
		ta_history.clear();
		for ( String s : mechanik.getMyGraph().getHistory() )
		{
			ta_history.appendText( s + "\n" );
		}
	}


	/*
	 * Ab hier beginnt der Serverteil die Methoden (getSerberPopover und getServerInfo) sind dafür da, um das Popover
	 * für eine Verbindung herzustellen. Dabei laden sie die letzte IP/Port automatisch wieder in das PopOver, falls
	 * man z.B. die letze Partie wiederholen möchte. Erst wenn man auf das OK im PopOver klickt wird versucht ein Server
	 * mit dem angegbenen Port zu öffnen.
	 */


	/**
	 * Erstellt das Sever PopOver
	 *
	 * @return
	 */

	private PopOver getServerPopover ()
	{
		PopOver tmp = new PopOver();
		tmp.setDetachable( false );
		tmp.setArrowLocation( PopOver.ArrowLocation.TOP_CENTER );
		tmp.setAnchorX( 5 );
		tmp.setAnchorY( 10 );
		tmp.setContentNode( getServerInfo() );
		return tmp;
	}

	/**
	 * Erstellt den Inhalt des PopOvers mit dem Port
	 *
	 * @return Das GridPane mit dem Inhalt der Serverinformationen
	 */

	private GridPane getServerInfo ()
	{
		final GridPane myGridPane = new GridPane();
		myGridPane.setHgap( 10 );
		myGridPane.setVgap( 10 );
		myGridPane.setPadding( new Insets( 10, 10, 10, 10 ) );
		final TextField tf_port = new TextField();
		if ( ! ( port == null ) )
		{
			tf_port.setText( String.valueOf( port ) );
		}
		Label l_port = new Label( "Port" );
		Button db_ok = new Button( "Ok" );
		db_ok.setOnAction( new EventHandler<ActionEvent>()
		{
			@Override
			public void handle (ActionEvent e)
			{
				ta_log.appendText( "OK geklick \n" );
				port = Integer.valueOf( tf_port.getText() );
				ta_log.appendText( tf_port.getText()+"\n" );
				runServer();
			}
		} );
		Button db_abbrechen = new Button( "Abbrechen" );
		db_abbrechen.setOnAction( new EventHandler<ActionEvent>()
		{
			@Override
			public void handle (ActionEvent event)
			{
				serverPop.hide();
				if ( server == null && client == null )
				{
					bt_client.setVisible( true );
				}
			}

		} );
		myGridPane.add( l_port, 1, 0 );
		myGridPane.add( tf_port, 2, 0 );
		GridPane mysubGridPane = new GridPane();
		mysubGridPane.setVgap( 10 );
		mysubGridPane.setHgap( 10 );
		mysubGridPane.setPadding( new Insets( 4, 0, 4, 0 ) );
		mysubGridPane.add( db_ok, 0, 0 );
		mysubGridPane.add( db_abbrechen, 1, 0 );
		myGridPane.add( mysubGridPane, 2, 1 );
		return myGridPane;
	}

	/**
	 * Erstellt das Client PopOver mit dem Port und der IP
	 *
	 * @return Das PopOver mit allen Informationen
	 */

	private PopOver getClientPopover ()
	{
		PopOver tmp = new PopOver();
		tmp.setDetachable( false );
		tmp.setArrowLocation( PopOver.ArrowLocation.TOP_CENTER );
		tmp.setAnchorX( 5 );
		tmp.setAnchorY( 10 );
		tmp.setContentNode( getClientInfo() );
		return tmp;
	}


	/**
	 * Der Inhalt des PopOvers
	 *
	 * @return Inhalt des PopOvers
	 */

	private GridPane getClientInfo ()
	{
		final GridPane myGridPane = new GridPane();
		myGridPane.setHgap( 10 );
		myGridPane.setVgap( 10 );
		myGridPane.setPadding( new Insets( 10, 10, 10, 10 ) );
		final TextField tf_port = new TextField();
		if ( ! ( port == null ) )
		{
			tf_port.setText( String.valueOf( this.port ) );
		}
		final TextField tf_ip = new TextField();
		if ( ! this.ip.isEmpty() )
		{
			tf_ip.setText( this.ip );
		}
		Label l_port = new Label( "Port" );
		Label l_ip = new Label( "IP" );
		Label l_map = new Label( "Map" );
		Button bt_map = new Button( "Pfad zu der Map" );
		bt_map.setOnAction( new EventHandler<ActionEvent>()
		{
			@Override
			public void handle (ActionEvent e)
			{
				File file = filechooser.showOpenDialog( null );
				if ( file != null )
				{
					mechanik = new Mechanik( file.getAbsolutePath() );
				}
			}
		} );
		Button db_ok = new Button( "Ok" );
		db_ok.setOnAction( new EventHandler<ActionEvent>()
		{
			@Override
			public void handle (ActionEvent e)
			{
				ip = tf_ip.getText();
				port = Integer.valueOf( tf_port.getText() );
				runClient();
			}
		} );
		Button db_abbrechen = new Button( "Abbrechen" );
		db_abbrechen.setOnAction( new EventHandler<ActionEvent>()
		{
			@Override
			public void handle (ActionEvent event)
			{
				clientPop.hide();
				if ( server == null && client == null )
				{
					bt_server.setVisible( true );
				}
			}

		} );
		myGridPane.add( l_ip, 1, 0 );
		myGridPane.add( tf_ip, 2, 0 );
		myGridPane.add( l_port, 1, 1 );
		myGridPane.add( tf_port, 2, 1 );
		myGridPane.add( l_map, 1, 2 );
		myGridPane.add( bt_map, 2, 2 );
		GridPane mysubGridPane = new GridPane();
		mysubGridPane.setVgap( 10 );
		mysubGridPane.setHgap( 10 );
		mysubGridPane.setPadding( new Insets( 4, 0, 4, 0 ) );
		mysubGridPane.add( db_ok, 0, 0 );
		mysubGridPane.add( db_abbrechen, 1, 0 );
		myGridPane.add( mysubGridPane, 2, 3 );
		return myGridPane;
	}

	private void runServer ()
	{

		ta_log.appendText( "Laden des Servers....\n" );
		try
		{

			Thread serverThread = new Thread()
			{
				@Override
				public void run ()
				{
					try
					{
						server = new ServerSocket( port );
					} catch ( IOException e )
					{
						e.printStackTrace();
					}
				}
			};
			serverThread.start();
			ta_log.appendText( "Server erfolgreich erstellt..\n" );
			ta_log.appendText( "Der Server läuft und hört auf Port:" + port +"\n");
			ta_log.appendText( "Server wartet auf Verbindungen...\n" );

			// Warten auf einen Clienten und versuch die Verbidnung herzustellen
			try
			{

				//Warten auf Client
				client = server.accept();
				ta_log.appendText(
						"Verbindung hergestellt:" + client.getLocalAddress().toString().substring( 0 ) + ":" + client
								.getLocalPort()+"\n" );

				// Setzen der Seiten
				eigenSeite = Seite.Kathargo;
				gegner = Seite.Rom;

				// Abfertigung des Clienten
				handleClient();
			} catch ( IOException e )
			{
				client.close();
				server.close();
				e.printStackTrace();
			} finally
			{
				// Wenn die Verbindung des Clienten nicht geschlossen wurde, wird dies nun getan
				if ( client != null )
				{
					try
					{
						client.close();
					} catch ( IOException e )
					{
						e.printStackTrace();
					}
				}
			}
		} catch ( Exception ex )
		{
			ex.printStackTrace();
		}
	}

	private void handleClient () throws Exception
	{

		// Zeichenen der Map
		initial();

		// Öffnen der Streams zum lesen der I/O Eingaben zwischen den Sockets und der Eingabe der Tastatur
		BufferedReader tastatur_input = new BufferedReader( new InputStreamReader( System.in ) );
		PrintWriter output = new PrintWriter( client.getOutputStream(), true /* autolush */ );
		BufferedReader input = new BufferedReader( new InputStreamReader( client.getInputStream() ) );

		// Status ausgeben
		ta_log.appendText(
				"Der Client " + client.getLocalAddress() + ":" + client.getLocalPort() + " wurde verbunden" );

		ta_log.appendText( "Warten auf die Map" );

		// Einlesen der Map, welche vom Client gesendet wird
		ArrayList<String> map = new ArrayList<String>();
		String line = null;
		Boolean karte = true;
		while ( karte )
		{
			line = input.readLine();
			ta_log.appendText( line );

			// Beenden des Map einlesen, fall der erste Zug gesendet wird
			if ( line.startsWith( "C" ) | line.startsWith( "R" ) | line.startsWith( "X" ) )
			{
				break;
			} else
			{
				map.add( line );
			}
		}

		ta_log.appendText( "Prüfen der Map" );

        /*
		// Prüfen ob die Map inordnung ist
        if (!checkmap(map)) {
            throw new Exception(
                    "Die Map entspricht nicht den Spezifikationen");
        }
        ta_log.appendTextn("Map ok \nFüge sie ins Spiel ein....");
        */

		// Einlesen der Map
		mechanik = new Mechanik( map );
		initial();

		showMap();

		ta_log.appendText( "Ok... \nBeginnen des Spiels" );

		// Da der Client mit dem Zug beginnt wird zuerst der Zug ausgewertet
		ta_log.appendText( "Der Gegner macht den Zug: " + line );
		changebuttons( mechanik.auswerten( line, Seite.Rom ) );
		showMap();
		aktiveHuman = true;
	}

	public void runClient ()
	{

	}

	/**
	 * Zeigt die aktuelle Map an
	 */

	private void showMap ()
	{
		ta_log.appendText( "Aktuelle Map: " + mechanik.getMyGraph().convertToString() );
	}
}
