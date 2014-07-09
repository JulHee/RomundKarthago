package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Projekt : RomUndKathargo
 * Author  : Julian Heeger
 * Date    : 08.07.14
 * Year    : 2014
 */
public class AiGUI extends Application {

	@Override
	public void start(Stage arg0) {
		try {
			VBox page = (VBox) FXMLLoader.load(getClass().getResource("AiGUI.fxml"));
            Scene scene = new Scene(page);
            arg0.setScene(scene);
            arg0.setTitle("Rom und Kathargo - AI");
            arg0.show();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	public static void main(String[] args) {
		launch(args);
	}
	
}
