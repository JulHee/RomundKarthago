package gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import java.io.File;

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
	private Button bt_map_laden;

	@FXML
	private Label lb_aktuell_ip;

	@FXML
	private TextArea ta_log;

	@FXML
	private RadioButton rb_es_kathargo;

	@FXML
	private Label lb_letzerZug;

	@FXML
	private Button bt_n_server;

	@FXML
	private MenuItem mi_quit;

	@FXML
	private AnchorPane ap_map;

	@FXML
	private Label lb_aktuell_port;

	@FXML
	private RadioButton rb_g_joernson;

	@FXML
	private Button bt_lok_beenden;

	@FXML
	private Button bt_n_client;

	@FXML
	private Tab t_log;

	@FXML
	private Button bt_lok_start;

	@FXML
	private RadioButton rb_es_rom;

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


	// Funktionen die aus dem .fxml geladen werden

	@FXML
	void mi_quit_click(ActionEvent event) {
		System.exit( 0 );

	}

	@FXML
	void rb_es_rom_click(ActionEvent event) {
		rb_es_kathargo.setSelected( false );
	}

	@FXML
	void rb_es_kathargo_click(ActionEvent event) {
	   rb_es_rom.setSelected( false );
	}

	@FXML
	void rb_gegner_click(ActionEvent event) {

	}

	@FXML
	void rb_g_joernson_click(ActionEvent event) {
	  rb_g_zweispieler.setSelected( false );
	}

	@FXML
	void rb_g_zweispieler_klick(ActionEvent event) {
	   rb_g_joernson.setSelected( false );
	}

	@FXML
	void bt_lok_start_click(ActionEvent event) {
		// TODO Lokales Spiel starten
	}

	@FXML
	void bt_lok_beenden_click(ActionEvent event) {
	   // TODO Lokales Spiel beenden
	}

	@FXML
	void bt_map_laden_click(ActionEvent event) {
		File file = filechooser.showOpenDialog(null);
		if (file != null) {
			path = file.getAbsolutePath();
		}
	}

}

