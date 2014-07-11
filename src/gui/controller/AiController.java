package gui.controller;

import java.io.File;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import network.Client;
import network.Server;
import core.datacontainers.Seite;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import logik.Mechanik;
import logik.ai.AIPlayer;
import logik.ai.Joernson;
import logik.ai.Killjoy;
import logik.ai.Scrooge;
import logik.ai.Sloth;
import logik.ai.WaspAI;
import org.controlsfx.control.PopOver;


/**
 * Projekt : RomUndKathargo
 * Author  : Julian Heeger
 * Date    : 08.07.14
 * Year    : 2014
 */
public class AiController
{

	//Variablen der Oberfläche

	//datei
	@FXML
	private Menu m_datei;
	//start in datei
	@FXML
	private MenuItem m_datei_start;
	//quit in datei
	@FXML
	private MenuItem m_datei_quit;
	//map
	@FXML
	private Menu m_map;
	//load in Map
	@FXML
	private MenuItem m_map_load;
	//seite
	@FXML
	private Menu m_seite;
	//rom in seite
	@FXML
	private RadioMenuItem m_seite_rom;
	//Karthago in seite
	@FXML
	private RadioMenuItem m_seite_carthage;
	//ki
	@FXML
	private Menu m_ki;
	//joernson in ki
	@FXML
	private RadioMenuItem m_ki_joernson;
	//killjoy in ki
	@FXML
	private RadioMenuItem m_ki_killjoy;
	//sloth in ki
	@FXML
	private RadioMenuItem m_ki_sloth;
	//scrooge in ki
	@FXML
	private RadioMenuItem m_ki_scrooge;
	//wasp in ki
	@FXML
	private RadioMenuItem m_ki_wasp;
	//reset menu
	@FXML
	private Menu m_reset;
	//all in reset
	@FXML
	private MenuItem m_reset_all;

	//button start
	@FXML
	private Button bt_start;
	//button reset
	@FXML
	private Button bt_reset;
	//button close
	@FXML
	private Button bt_close;
	//text area
	@FXML
	private TextArea ta_text;


	/*
	 * Funktionsvariablen um Spiel zu ermöglichen
	 */ FileChooser filechooser = new FileChooser();
	Mechanik mechanik = null;
	//TODO set port/ip
	Integer port = 0;
	String ip = "localhost";

	Seite eigenSeite = null;
	Seite gegner = null;
	Boolean runningGame = false;

	AIPlayer ai = null;
	// Popover für den Server
	PopOver serverPop = null;


	/*
	 * Funktionen, welche die GUI steuern
	 */

	/*
	 * Reset Funktion
	 */

	@FXML
	void do_reset (ActionEvent event)
	{
		runningGame = false;
		mechanik = null;
		eigenSeite = null;
		gegner = null;
		ai = null;


		//deselect all
		m_seite_carthage.setSelected( false );
		m_seite_rom.setSelected( false );
		m_ki_joernson.setSelected( false );
		m_ki_killjoy.setSelected( false );
		m_ki_scrooge.setSelected( false );
		m_ki_sloth.setSelected( false );
		m_ki_wasp.setSelected( false );

		ta_text.clear();
		ta_text.appendText( "Reset durchgefuehrt! \nBitte neue Einstellungen vornehmen \n" );
	}

	/*
	 * TODO Start Funktion - Logik
	 */
	@FXML
	void do_start (ActionEvent event)
	{
		if ( ! runningGame )
		{
			if ( eigenSeite != null & ai != null )
			{
				serverPop = getClientPopover();
				serverPop.show( bt_start );
			} else
			{
				ta_text.appendText( "Fehler! Bitte zuerst alle Einstellungen vornehmen\n" );
			}
		} else
		{
			ta_text.appendText( "Fehler! Das Spiel muss erst zurückgesetzt werden(Reset)\n" );
		}
	}


	/*
	 * schließt das Fenster
	 * Verwendung in datei_quit und button_close
	 */
	@FXML
	void quit_click (ActionEvent event)
	{
		System.exit( 0 );
	}

	/*
	 *  TODO mapload oder besser Auswahl anbieten
	 */
	@FXML
	void mi_load (ActionEvent event)
	{
		File file = filechooser.showOpenDialog( null );
		if ( file != null )
		{
			mechanik = new Mechanik( file.getAbsolutePath() );
		}
		try
		{
			mechanik.getMyGraph().read();
			ta_text.appendText( "Die Map wurde geladen \n" );
		} catch ( Exception e )
		{
			System.out.println( e.getMessage() );
		}
	}

	/*
	 * setzt die Seite Rom in Seite_rom
	 */
	@FXML
	void set_rom (ActionEvent event)
	{
		m_seite_carthage.setSelected( false );
		eigenSeite = Seite.Rom;
		gegner = Seite.Kathargo;
		ta_text.appendText( "Rom als Seite ausgewählt \n" );
	}

	/*
	 * setzt die Seite Karthago in Seite_Karthago
	 */
	@FXML
	void set_carthage (ActionEvent event)
	{
		m_seite_rom.setSelected( false );
		eigenSeite = Seite.Kathargo;
		gegner = Seite.Rom;
		ta_text.appendText( "Kathargo als Seite ausgewählt \n" );
	}

	/*
	 * setzen der AI : Joernson
	 */
	@FXML
	void set_joernson (ActionEvent event)
	{
		m_ki_killjoy.setSelected( false );
		m_ki_sloth.setSelected( false );
		m_ki_scrooge.setSelected( false );
		m_ki_wasp.setSelected( false );
		ai = new Joernson( eigenSeite, mechanik );

		ta_text.appendText( "Ausgewählte AI: Joernson \n" );
	}

	/*
	 * setzen der AI : Killjoy
	 */
	@FXML
	void set_killjoy (ActionEvent event)
	{
		m_ki_joernson.setSelected( false );
		m_ki_sloth.setSelected( false );
		m_ki_scrooge.setSelected( false );
		m_ki_wasp.setSelected( false );
		ai = new Killjoy( eigenSeite, mechanik );

		ta_text.appendText( "Ausgewählte AI: Killjoy \n" );
	}

	/*
	 * setzen der AI : Sloth
	 */
	@FXML
	void set_sloth (ActionEvent event)
	{
		m_ki_joernson.setSelected( false );
		m_ki_killjoy.setSelected( false );
		m_ki_scrooge.setSelected( false );
		m_ki_wasp.setSelected( false );
		ai = new Sloth( eigenSeite, mechanik );

		ta_text.appendText( "Ausgewählte AI: Sloth \n" );
	}

	/*
	 * setzen der AI : Scrooge
	 */
	@FXML
	void set_scrooge (ActionEvent event)
	{
		m_ki_joernson.setSelected( false );
		m_ki_killjoy.setSelected( false );
		m_ki_sloth.setSelected( false );
		m_ki_wasp.setSelected( false );
		ai = new Scrooge( eigenSeite, mechanik );

		ta_text.appendText( "Ausgewählte AI: Scrooge \n" );
	}

	/*
	 * setzen der AI : Wasp   
	 */
	@FXML
	void set_wasp (ActionEvent event)
	{
		m_ki_joernson.setSelected( false );
		m_ki_killjoy.setSelected( false );
		m_ki_sloth.setSelected( false );
		m_ki_scrooge.setSelected( false );
		ai = new WaspAI( eigenSeite );

		ta_text.appendText( "Ausgewählte AI: Wasp \n" );
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
								   runningGame = true;
								   ta_text.appendText( "Spiel wurde gestartet!\n" );

								   // TODO In Thread auslagern
								   if ( eigenSeite == Seite.Rom )
								   {

									   Client aiplayer = new Client( port, ip, mechanik, ta_text );
								   }
								   if ( eigenSeite == Seite.Kathargo )
								   {
									   Server aiplayer = new Server( port, ai, ta_text );
									   if ( eigenSeite == Seite.Rom )
									   {
										   ip = tf_ip.getText();
									   }
									   port = Integer.valueOf( tf_port.getText() );
									   // TODO Button ok gelickt
								   }

							   }
						   }

						 );
		Button db_abbrechen = new Button( "Abbrechen" );
		db_abbrechen.setOnAction( new EventHandler<ActionEvent>()

								  {
									  @Override
									  public void handle (ActionEvent event)
									  {
										  serverPop.hide();
										  // TODO Button Abbrechen geklickt
									  }

								  }

								);
		if ( eigenSeite == Seite.Kathargo )

		{
			tf_ip.setEditable( false );
			bt_map.setVisible( false );
			l_map.setVisible( false );
		}

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


}
