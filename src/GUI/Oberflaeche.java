package GUI;

import Graph.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;


/**
 * Projekt: Rom und Karthago
 * Author : Julian Heeger, Markus Poell, Christian Bruene, Joern Kabuth
 * Date : 26.04.14
 * Year : 2014
 */
public class Oberflaeche extends Application {

    @Override
    public void start(Stage primaryStage) {

        // Graph initialisieren
        Graph graph = new Graph();
        try {
            graph.read();
        } catch (Exception ex) {
            System.out.println("Allgemeine Warnlampe");
        }


        //Button größe

        double HEIGHT = 25;
        double WIDTH = 40;

        // Optionen des JavaFX setzen

        primaryStage.setTitle("Graph");
        Pane root = new Pane();

        // Linien hinzufügen

        for (Kante i : graph.l_kante) {

            root.getChildren().add(pathanlegen(i, WIDTH, HEIGHT));
        }

        // Buttons hinzufügen

        for (Knoten i : graph.l_knoten) {
            root.getChildren().add(buttonalegen(i));
        }


        // Abschließend Form zeigen

        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 600, 600));
        primaryStage.show();
    }

    private Button buttonalegen(Knoten knoten) {
        Button temp = new Button();
        temp.setText(String.valueOf(knoten.id) + " " + knoten.seite.toString());
        temp.setLayoutX(knoten.position.x);
        temp.setLayoutY(knoten.position.y);
        return temp;
    }

    private Path pathanlegen(Kante kante, double width, double height) {
        Path path = new Path();

        MoveTo moveTo = new MoveTo();
        moveTo.setX(kante.punkt1.position.x + (width / 2));
        moveTo.setY(kante.punkt1.position.y + (height / 2));

        LineTo lineTo = new LineTo();
        lineTo.setX(kante.punkt2.position.x + (width / 2));
        lineTo.setY(kante.punkt2.position.y + (height / 2));

        path.getElements().add(moveTo);
        path.getElements().add(lineTo);
        path.setStrokeWidth(2);
        path.setStroke(Color.BLACK);
        return path;
    }
}
