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
	
	
	/*
	 * Funktionen, welche die GUI steuern
	 */
	
	
	
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
	}
	/*
	 * setzt die Seite Karthago in Seite_Karthago
	 */
	@FXML
	void set_carthage(ActionEvent event){
		m_seite_rom.setSelected(false);
		eigenSeite = Seite.Kathargo;
		gegner = Seite.Rom;
	}


}
