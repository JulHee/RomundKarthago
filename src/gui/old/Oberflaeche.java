package gui.old;

/**
 * Projekt: RomUndKathargo
 * Author : Julian Heeger
 * Date : 06.05.14
 * Year : 2014
 */

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Oberflaeche extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            VBox page = (VBox) FXMLLoader.load(getClass().getResource("gui.fxml"));
            Scene scene = new Scene(page);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Rom und Kathargo");
            primaryStage.show();
        } catch (Exception ex) {
            Logger.getLogger(Oberflaeche.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void main(String[] args) {
		launch(args);
	}
}
