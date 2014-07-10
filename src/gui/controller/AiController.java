package gui.controller;

import java.io.File;

import core.datacontainers.Seite;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import logik.Mechanik;
import logik.ai.AIPlayer;
import logik.ai.Joernson;
import logik.ai.Killjoy;
import logik.ai.Scrooge;
import logik.ai.Sloth;
import logik.ai.WaspAI;


/**
 * Projekt : RomUndKathargo
 * Author  : Julian Heeger
 * Date    : 08.07.14
 * Year    : 2014
 */
public class AiController {

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
	private TextArea ta_text;
	
	
	/*
	 * Funktionsvariablen um Spiel zu ermöglichen
	 */
	FileChooser filechooser = new FileChooser();
	Mechanik mechanik = null;
	Seite eigenSeite = null;
	Seite gegner = null;
	
	AIPlayer ai = null;
	
	//TODO wieso funktionieren die append_text nicht?!
	
	/*
	 * Funktionen, welche die GUI steuern
	 */
	
	/*
	 * Reset Funktion
	 */
	@FXML
	void do_reset(ActionEvent event){
		mechanik = null;
		eigenSeite = null;
		gegner = null;
		ai = null;
		//TODO Filechooser zurücksetzen?
		
		
		//deselect all
		m_seite_carthage.setSelected(false);
		m_seite_rom.setSelected(false);
		m_ki_joernson.setSelected(false);
		m_ki_killjoy.setSelected(false);
		m_ki_scrooge.setSelected(false);
		m_ki_sloth.setSelected(false);
		m_ki_wasp.setSelected(false);
		
		ta_text.appendText("Reset durchgefuehrt! \n Bitte neue Einstellungen vornehmen \n");
	}
	
	
	/*
	 * schließt das Fenster
	 * Verwendung in datei_quit und button_close
	 */
	@FXML
	void quit_click (ActionEvent event){
		System.exit( 0 );
	}
	/*
	 *  TODO mapload oder besser Auswahl anbieten
	 */
	@FXML
    void mi_load(ActionEvent event) {
		File file = filechooser.showOpenDialog(null);
        if (file != null) {
            mechanik = new Mechanik(file.getAbsolutePath());
        }
        try {
			mechanik.getMyGraph().read();
			ta_text.appendText("Die Map wurde geladen \n");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
    }
	
	/*
	 * setzt die Seite Rom in Seite_rom
	 */
	@FXML
	void set_rom(ActionEvent event){
		m_seite_carthage.setSelected(false);
		eigenSeite = Seite.Rom;
		gegner = Seite.Kathargo;
		ta_text.appendText("Rom als Seite ausgewählt \n");
	}
	/*
	 * setzt die Seite Karthago in Seite_Karthago
	 */
	@FXML
	void set_carthage(ActionEvent event){
		m_seite_rom.setSelected(false);
		eigenSeite = Seite.Kathargo;
		gegner = Seite.Rom;
		ta_text.appendText("Kathargo als Seite ausgewählt \n");
	}
	/*
	 * setzen der AI : Joernson
	 */
	@FXML
	void set_joernson(ActionEvent event){
		m_ki_killjoy.setSelected(false);
		m_ki_sloth.setSelected(false);
		m_ki_scrooge.setSelected(false);
		m_ki_wasp.setSelected(false);
		ai = new Joernson(eigenSeite,mechanik);
		
		ta_text.appendText("Ausgewählte AI: Joernson");
	}
	/*
	 * setzen der AI : Killjoy
	 */
	@FXML
	void set_killjoy(ActionEvent event){
		m_ki_joernson.setSelected(false);
		m_ki_sloth.setSelected(false);
		m_ki_scrooge.setSelected(false);
		m_ki_wasp.setSelected(false);
		ai = new Killjoy(eigenSeite,mechanik);
		
		ta_text.appendText("Ausgewählte AI: Killjoy");
	}
	/*
	 * setzen der AI : Sloth
	 */
	@FXML
	void set_sloth(ActionEvent event){
		m_ki_joernson.setSelected(false);
		m_ki_killjoy.setSelected(false);
		m_ki_scrooge.setSelected(false);
		m_ki_wasp.setSelected(false);
		ai = new Sloth(eigenSeite,mechanik);
		
		ta_text.appendText("Ausgewählte AI: Sloth");
	}
	/*
	 * setzen der AI : Scrooge
	 */
	@FXML
	void set_scrooge(ActionEvent event){
		m_ki_joernson.setSelected(false);
		m_ki_killjoy.setSelected(false);
		m_ki_sloth.setSelected(false);
		m_ki_wasp.setSelected(false);
		ai = new Scrooge(eigenSeite,mechanik);
		
		ta_text.appendText("Ausgewählte AI: Scrooge");
	}
	/*
	 * setzen der AI : Wasp   //TODO warum nur Wasp keine mechanik?
	 * Weil die Wasp den ersten möglichen, vllt auch illegalen Move macht. Also "Seite + 0"
	 * Dabei ist keine Mechanik von nöten
	 */
	@FXML
	void set_wasp(ActionEvent event){
		m_ki_joernson.setSelected(false);
		m_ki_killjoy.setSelected(false);
		m_ki_sloth.setSelected(false);
		m_ki_scrooge.setSelected(false);
		ai = new WaspAI(eigenSeite);
		
		ta_text.appendText("Ausgewählte AI: Wasp");
	}
	

}
