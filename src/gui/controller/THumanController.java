package gui.controller;

import core.datacontainers.Kante;
import core.datacontainers.Knoten;
import core.datacontainers.Seite;
import core.datacontainers.Zug;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.FileChooser;
import logik.Mechanik;
import logik.ai.AIPlayer;
import logik.ai.Joernson;

import java.io.File;
import java.util.LinkedList;

/**
 * Projekt : RomUndKathargo
 * Author  : Julian Heeger
 * Date    : 14.07.14
 * Year    : 2014
 */
public class THumanController
{

	// Imports aus dem .fxml

	@FXML
	private TextArea ta_history;

	@FXML
	private TextField tf_ip;

	@FXML
	private Label lb_aktuell_ip;

	@FXML
	private TextArea ta_log;

	@FXML
	private RadioButton rb_network;

	@FXML
	private Label lb_letzerZug;

	@FXML
	private Label lb_map;

	@FXML
	private Label lb_ip;

	@FXML
	private AnchorPane ap_map;

	@FXML
	private Label lb_aktuell_port;

	@FXML
	private RadioButton rb_g_joernson;

	@FXML
	private Button bt_lok_beenden;

	@FXML
	private RadioButton rb_es_rom;

	@FXML
	private Label lb_port;

	@FXML
	private Label lb_punkte;

	@FXML
	private Button bt_map_laden;

	@FXML
	private RadioButton rb_es_kathargo;

	@FXML
	private Button bt_g_map;

	@FXML
	private Button bt_n_server;

	@FXML
	private MenuItem mi_quit;

	@FXML
	private Button bt_n_client;

	@FXML
	private Tab t_log;

	@FXML
	private Button bt_lok_start;

	@FXML
	private RadioButton rb_g_zweispieler;

	@FXML
	private RadioButton rb_gegner;

	@FXML
	private TextField tf_port;

	//

	// FileChooser zum auswählen der Map
	FileChooser filechooser = new FileChooser();
	String path = null;

	// Spielspezifische Variablen
	Mechanik mechanik = null;
	Seite eigeneSeite = null;

	// Netzwerk
	String ip = "localhost";
	Integer port = 0;

	// Spieler der am Zug ist

	private Seite spieler = Seite.Rom;

	// Gegner

	private Seite gegner = null;

	// AI spiel ?
	Boolean isAi = false;

	// AI
	AIPlayer ai = null;

	// Läuft ein Spiel ?
	Boolean runningGame = false;

	// Button und Path die bei Laufzeit erstellt wird

	private LinkedList<Button> buttons = new LinkedList<>();
	private LinkedList<Path> paths = new LinkedList<>();

	// Button größe

	private final double HEIGHT = 25;
	private final double WIDTH = 40;


	// Funktionen die aus dem .fxml geladen werden

	@FXML
	void mi_quit_click (ActionEvent event)
	{
		System.exit( 0 );
	}

	@FXML
	void rb_es_rom_click (ActionEvent event)
	{
		rb_es_kathargo.setSelected( false );
	}

	@FXML
	void rb_es_kathargo_click (ActionEvent event)
	{
		rb_es_rom.setSelected( false );
	}

	@FXML
	void rb_g_joernson_click (ActionEvent event)
	{
		rb_g_zweispieler.setSelected( false );
	}

	@FXML
	void rb_g_zweispieler_klick (ActionEvent event)
	{
		rb_g_joernson.setSelected( false );
	}

	@FXML
	void rb_gegner_click (ActionEvent event)
	{
		rb_network.setSelected( false );
		setNetwork( false );
		setGegner( true );
	}


	@FXML
	void rb_network_click (ActionEvent event)
	{
		rb_gegner.setSelected( false );
		setNetwork( true );
		setGegner( false );
	}


	@FXML
	void bt_lok_start_click (ActionEvent event)
	{
		if ( path == null )
		{
			bt_map_laden_click( event );
		}
		startGame();
	}

	@FXML
	void bt_lok_beenden_click (ActionEvent event)
	{
		spieler = Seite.Rom;
		runningGame = false;
		isAi = false;
		ai = null;
		mechanik = null;
		eigeneSeite = null;
		ap_map.getChildren().clear();
		path = null;
		paths = new LinkedList<>();
		buttons = new LinkedList<>();
		ta_log.clear();
		ta_history.clear();
	}

	@FXML
	void bt_map_laden_click (ActionEvent event)
	{
		File file = filechooser.showOpenDialog( null );
		if ( file != null )
		{
			path = file.getAbsolutePath();
		}
	}


	@FXML
	void bt_g_map_click (ActionEvent event)
	{
		bt_map_laden_click( event );
	}

	private void setNetwork (Boolean bool)
	{
		bt_n_client.setVisible( bool );
		bt_n_server.setVisible( bool );
		bt_map_laden.setVisible( bool );
		lb_ip.setVisible( bool );
		lb_port.setVisible( bool );
		lb_map.setVisible( bool );
		lb_ip.setVisible( bool );
		lb_port.setVisible( bool );
		tf_ip.setVisible( bool );
		tf_port.setVisible( bool );

	}

	private void setGegner (Boolean bool)
	{
		bt_lok_beenden.setVisible( bool );
		bt_lok_start.setVisible( bool );
		rb_g_joernson.setVisible( bool );
		rb_g_zweispieler.setVisible( bool );
		bt_g_map.setVisible( bool );
	}

	private void startGame ()
	{
		if ( ! runningGame )
		{
			mechanik = new Mechanik( path );
			if ( rb_es_rom.isSelected() )
			{
				eigeneSeite = Seite.Rom;
				gegner = Seite.Kathargo;
			} else
			{
				eigeneSeite = Seite.Kathargo;
				gegner = Seite.Rom;
			}
			if ( rb_g_joernson.isSelected() )
			{
				isAi = true;
				ai = new Joernson( gegner, mechanik );
				initial();
				runningGame = true;
				if ( gegner == Seite.Rom )
				{

					// Die AI ist Rom daher muss diese zuerst ziehen
					Zug aizug = ai.nextZug();
					ta_log.appendText( "Die AI zieht: " + aizug.toFormat() + "\n" );
					String neuerGraph = mechanik.auswerten( aizug.toFormat(), gegner );
					changeButtons( neuerGraph );
					ta_log.appendText( neuerGraph + "\n" );
					lb_letzerZug.setText( aizug.toFormat() );
					refreshHistory();
				}
			} else
			{
				initial();
				runningGame = true;
				isAi = false;
			}
		}
	}

	private void zug (Button button)
	{

		if ( runningGame )
		{
			if ( isAi )
			{

				// Eigener Zug
				if ( mechanik.getSpiel() )
				{
					String zug;
					String buttonText = button.getText();
					if ( buttonText.equals( "Aussetzen" ) )
					{
						zug = eigeneSeite.toString() + " X";
					} else
					{
						String id = buttonText.split( " " )[ 0 ];
						zug = eigeneSeite.toString() + " " + id;
					}
					ta_log.appendText( zug + "\n" );

					// Auswerten des Zuges
					String neuerGraph = mechanik.auswerten( zug, eigeneSeite );
					ta_log.appendText( neuerGraph + "\n" );

					changeButtons( neuerGraph );
					refreshHistory();
				}

				// Zug der AI
				if ( mechanik.getSpiel() )
				{
					Zug aiZug = ai.nextZug();
					ta_log.appendText( aiZug.toFormat() + "\n" );
					String neuerGraph = mechanik.auswerten( aiZug.toFormat(), gegner );
					changeButtons( neuerGraph );
					ta_log.appendText( neuerGraph + "\n" );
					lb_letzerZug.setText( aiZug.toFormat() );
					refreshHistory();
				}
			} else
			{
				// Spiel zwischen zwei Menschen an einem Rechner
				// Eigener Zug
				if ( mechanik.getSpiel() )
				{
					String buttonText = button.getText();
					String id = buttonText.split( " " )[ 0 ];
					String zug = spieler.toString() + " " + id;
					ta_log.appendText( zug + "\n" );

					// Auswerten des Zuges
					String neuerGraph = mechanik.auswerten( zug, spieler );
					ta_log.appendText( neuerGraph + "\n" );

					changeButtons( neuerGraph );
					refreshHistory();
					spieler = spieler == Seite.Rom ? Seite.Kathargo : Seite.Rom;
				}
			}

			if ( ! mechanik.getSpiel() )
			{
				lb_letzerZug.setText(
						"Das Spiel ist vorbei: Rom: " + mechanik.getMyGraph().getPunkteStandFuer( Seite.Rom )
						+ " | Kathargo: " + mechanik.getMyGraph().getPunkteStandFuer( Seite.Kathargo ) );
				ta_log.appendText( "Rom: " + mechanik.getMyGraph().getPunkteStandFuer( Seite.Rom ) + "\n" );
				ta_log.appendText( "Kathargo: " + mechanik.getMyGraph().getPunkteStandFuer( Seite.Kathargo ) + "\n" );
				runningGame = false;
			}

		}

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

		// AussetzenButton hinzufügen

		final Button bt_aussetzen = new Button();
		bt_aussetzen.setText( "Aussetzen" );
		bt_aussetzen.setLayoutX( 5 );
		bt_aussetzen.setLayoutY( 5 );
		bt_aussetzen.setOnAction( new EventHandler<ActionEvent>()
		{
			@Override
			public void handle (ActionEvent e)
			{
				zug( bt_aussetzen );
			}
		} );

		ap_map.getChildren().add( bt_aussetzen );
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

		switch ( knoten.seite.toString().toLowerCase() )
		{

			case "n":
				temp.setTextFill( Paint.valueOf( "green" ) );
				break;

			case "r":
				temp.setTextFill( Paint.valueOf( "red" ) );
				break;
			case "c":
				temp.setTextFill( Paint.valueOf( "blue" ) );
				break;

		}

		temp.setLayoutX( knoten.position.x );
		temp.setLayoutY( knoten.position.y );
		temp.setOnAction( new EventHandler<ActionEvent>()
		{
			@Override
			public void handle (ActionEvent e)
			{
				zug( temp );
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
	 * Aktuallisiert die Beschriftung der Buttons
	 *
	 * @param s Beschreibung des Graphen wobei jedes Zeichen einer ID/Button zugeordnet ist
	 */

	private void changeButtons (String s)
	{
		ap_map.getChildren().sorted().stream().filter( n -> n instanceof Button ).forEach( n -> {
			String bttext = ( ( Button ) n ).getText();
			if ( ! bttext.equals( "Aussetzen" ) )
			{
				String id = bttext.split( " " )[ 0 ];
				Integer btid = Integer.parseInt( String.valueOf( id ) );
				String seite = String.valueOf( s.charAt( btid ) );

				switch ( seite.toLowerCase() )
				{

					case "n":
						( ( Button ) n ).setTextFill( Paint.valueOf( "green" ) );
						break;

					case "r":
						( ( Button ) n ).setTextFill( Paint.valueOf( "red" ) );
						break;
					case "c":
						( ( Button ) n ).setTextFill( Paint.valueOf( "blue" ) );
						break;

				}
				( ( Button ) n ).setText( btid + " " + seite );
			}

		} );

		// Setzen der Punkte
		lb_punkte.setText( mechanik.getMyGraph().getPunkteStandFuer( Seite.Rom )
						   + " : " + mechanik.getMyGraph().getPunkteStandFuer( Seite.Kathargo ) );
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

}

