package gui.controller;

import core.datacontainers.Kante;
import core.datacontainers.Knoten;
import core.datacontainers.Seite;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.FileChooser;
import logik.Mechanik;

import java.util.LinkedList;

/**
 * Projekt : RomUndKathargo
 * Author  : Julian Heeger
 * Date    : 08.07.14
 * Year    : 2014
 */
public class HumanController {

    // FileChooser zum auswählen der Map
    FileChooser filechooser = new FileChooser();

    // Anlegen des Graphen

    private Mechanik mechanik = null;

    // Spieler der am Zug ist

    private Seite spieler = Seite.Rom;

    // Button und Path die bei Laufzeit erstellt wird

    private final LinkedList<Button> buttons = new LinkedList<Button>();
    private final LinkedList<Path> paths = new LinkedList<Path>();

    // Button größe

    private final double HEIGHT = 25;
    private final double WIDTH = 40;

    // Code für die Oberfläche

    @FXML
    private MenuItem mi_quit;

    @FXML
    private AnchorPane ap_map;

    @FXML
    private Button bt_client;

    @FXML
    private TextArea ta_history;

    @FXML
    private TextArea ta_log;

    @FXML
    private Button bt_server;

    @FXML
    void mi_quit_click(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void bt_client_click(ActionEvent event) {

    }

    @FXML
    void bt_server_click(ActionEvent event) {

    }

    private void initial() {
        try {
            mechanik.getMyGraph().read();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        // Linien hinzufügen

        for (Kante i : mechanik.getMyGraph().l_kante) {
            paths.add(pathanlegen(i, WIDTH, HEIGHT));
        }

        // Buttons hinzufügen

        for (Knoten i : mechanik.getMyGraph().l_knoten) {
            buttons.add(buttonalegen(i));
        }

    }

    /**
     * Erzeigt einen Button der einen Knoten represäntiert
     * @param knoten Der Knoten der als Button angezeigt werden soll
     * @return
     */

    private Button buttonalegen(Knoten knoten) {
        final Button temp = new Button();
        temp.setText(String.valueOf(knoten.id) + " " + knoten.seite.toString());
        temp.setLayoutX(knoten.position.x);
        temp.setLayoutY(knoten.position.y);
        temp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println(temp.getText());
                zugmachen(temp);
            }
        });
        return temp;
    }

    /**
     * Erzeugt eine Linine zwischen zwei Punkten
     * @param kante Die Kante mit den zwei betreffenden Buttons
     * @param width Die breite des Buttons
     * @param height Die Höhe des Buttons
     * @return Ein Path der die Linie zwischen zwei Buttons beschreibt
     */

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

    /**
     * Gibt die andere den Aktuellen Spieler als Seite zurück und setzt direkt
     * die andere Seite als aktiv
     *
     * @return Aktueller Spieler
     */

    private Seite aktuellerSpieler() {
        Seite tmp = spieler;
        spieler = spieler == Seite.Rom ? Seite.Kathargo : Seite.Rom;
        return tmp;
    }

    /**
     * Funktion die den Buttons zugeweisen werden. Jeder Klick auf einen Button simuliert einen Zug
     * @param sender Der Button der geklickt wurde
     */

    private void zugmachen(Button sender) {
        ta_log.appendText(sender.getText() + "\n");
        Seite spieler = aktuellerSpieler();
        ta_log.appendText("Es spielt: " + spieler.toString() + "\n");
        String belegung = mechanik.auswerten(
                String.valueOf(spieler.toString() + " "
                        + sender.getText().charAt(0)), spieler);
        ta_log.appendText("Die aktuelle Karte:" + belegung + "\n");
        changebuttons(belegung);
        refreshHistory();
    }

    /**
     * Aktuallisiert die Beschriftung der Buttons
     * @param s Beschreibung des Graphen wobei jedes Zeichen einer ID/Button zugeordnet ist
     */

    private void changebuttons(String s) {
        ap_map.getChildren().sorted().stream().filter(n -> n instanceof Button).forEach(n -> {
            String bttext = ((Button) n).getText();
            Integer btid = Integer.parseInt(String.valueOf(bttext.charAt(0)));
            ((Button) n).setText(btid + " " + String.valueOf(s.charAt(btid)));
            System.out.println(bttext);

        });
    }

    /**
     * Aktuallisiert das History-Log mit den aktuellen History
     */

    private void refreshHistory(){
        ta_history.clear();
        for (String s : mechanik.getMyGraph().getHistory()){
            ta_history.appendText(s+"\n");
        }
    }
}
