package gui.old;

import core.*;
import core.datacontainers.Kante;
import core.datacontainers.Knoten;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;

import java.util.LinkedList;


/**
 * Projekt: Rom und Karthago
 * Author : Julian Heeger, Markus Poell, Christian Bruene, Joern Kabuth
 * Date : 26.04.14
 * Year : 2014
 */
public class Oberflaeche_old extends Application {

    // Anlegen des Graphen

    private Graph graph = new Graph();

    // Button und Path die bei Laufzeit erstellt wird

    private LinkedList<Button> buttons = new LinkedList<Button>();
    private LinkedList<Path> paths = new LinkedList<Path>();

    //Button größe

    private double HEIGHT = 25;
    private double WIDTH = 40;

    private void initial() {
        try {
            this.graph.read();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        // Linien hinzufügen

        for (Kante i : graph.l_kante) {
            paths.add(pathanlegen(i, WIDTH, HEIGHT));
        }

        // Buttons hinzufügen

        for (Knoten i : graph.l_knoten) {
            buttons.add(buttonalegen(i));
        }

    }

    @Override
    public void start(Stage primaryStage) {
        initial();

        // Optionen des JavaFX setzen

        primaryStage.setTitle("core");
        Pane root = new Pane();

        root.getChildren().addAll(paths);
        root.getChildren().addAll(buttons);


        // Abschließend Form zeigen

        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 600, 600));
        primaryStage.show();
    }

    private Button buttonalegen(Knoten knoten) {
        final Button temp = new Button();
        temp.setText(String.valueOf(knoten.id) + " " + knoten.seite.toString());
        temp.setLayoutX(knoten.position.x);
        temp.setLayoutY(knoten.position.y);
        temp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println(temp.getText());
                changelabel(temp);
            }
        });
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

    private void changelabel(Button button) {
        button.setText("Klicked");
    }
    public static void main(String[] args) {
		launch(args);
	}

}
