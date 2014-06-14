package gui;

/**
 * Projekt: RomUndKathargo
 * Author : Julian Heeger
 * Date : 07.05.14
 * Year : 2014
 */

import core.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

import java.util.LinkedList;


public class GuiController {

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
        ta_log.appendText(button.getText() + "\n");
    }


    // Code für die Oberfläche


    @FXML
    private TextArea ta_log;

    @FXML
    private MenuItem mi_load;

    @FXML
    private Pane map;

    @FXML
    void mi_load(ActionEvent event) {
        initial();
        map.getChildren().addAll(paths);
        map.getChildren().addAll(buttons);
        ta_log.appendText("Die Daten wurden geladen \n");
    }


}
