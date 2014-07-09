package gui.controller;

import core.datacontainers.Kante;
import core.datacontainers.Knoten;
import core.datacontainers.Seite;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.FileChooser;
import logik.Mechanik;
import org.controlsfx.control.PopOver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Projekt : RomUndKathargo
 * Author  : Julian Heeger
 * Date    : 08.07.14
 * Year    : 2014
 */
public class HumanController {

    // Variablen abhängig ob Server oder Client läuft
    Seite eigenSeite = null;
    Seite gegner = null;

    // Läuft ein Spiel ?
    Boolean runningGame = false;

    // Ist der Benutzer am zug
    Boolean aktiveHuman = false;

    // Server
    ServerSocket server = null;
    Socket client = null;

    // Verbindung
    String ip = "localhost";
    Integer port = 2000;

    // Popover für den Clienten
    PopOver clientPop = null;

    // Popover für den Server
    PopOver serverPop = null;

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

    /**
     * Beenden des Programmes
     *
     * @param event Event des MenuItems
     */

    @FXML
    void mi_quit_click(ActionEvent event) {
        System.exit(0);
    }

    /**
     * Wenn der Clientbutton geklickt wurde
     *
     * @param event Event des Buttons
     */

    @FXML
    void bt_client_click(ActionEvent event) {
        if (!runningGame) {
            bt_server.setVisible(false);
            clientPop = getClientPopover();
            clientPop.show(bt_client);
            // TODO Clientoperation
        }
    }

    /**
     * Wenn der Serverbutton geklickt wurde
     *
     * @param event Event des Buttons
     */

    @FXML
    void bt_server_click(ActionEvent event) {
        if (!runningGame) {
            bt_client.setVisible(false);
            serverPop = getServerPopover();
            serverPop.show(bt_server);
        }
    }

    private void initial() {
        try {
            mechanik.getMyGraph().read();
        } catch (Exception ex) {
            ta_log.appendText(ex.getMessage());
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
     *
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
                ta_log.appendText(temp.getText());
                zugmachen(temp);
            }
        });
        return temp;
    }

    /**
     * Erzeugt eine Linine zwischen zwei Punkten
     *
     * @param kante  Die Kante mit den zwei betreffenden Buttons
     * @param width  Die breite des Buttons
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
     *
     * @param sender Der Button der geklickt wurde
     */

    private void zugmachen(Button sender) {
        if (aktiveHuman && client != null) {
            try {
                aktiveHuman = false;
                // Öffnen der Streams zum lesen der I/O Eingaben zwischen den Sockets und der Eingabe der Tastatur
                BufferedReader tastatur_input = new BufferedReader(
                        new InputStreamReader(System.in));
                PrintWriter output = new PrintWriter(client.getOutputStream(), true /* autolush */);
                BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));

                String buttonText = sender.getText();
                String zug = buttonText.charAt(0) + " " + eigenSeite.toString();

                // Zug senden
                output.println(zug);

                // Auswerten des Zuges
                changebuttons(mechanik.auswerten(zug, eigenSeite));

                //Falls der letzte Zug das Spiel schon beendet hat
                if (!mechanik.getSpiel()) {
                    // TODO Spiel muss unterbrochen werden, falls mit dem eingenen Zug das Spiel beendet wurde
                }

                // Lesen des Gegnerzuges
                String zug_gegner = input.readLine();

                // Auswerten
                changebuttons(mechanik.auswerten(zug_gegner, Seite.Rom));

                aktiveHuman = true;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    /**
     * Aktuallisiert die Beschriftung der Buttons
     *
     * @param s Beschreibung des Graphen wobei jedes Zeichen einer ID/Button zugeordnet ist
     */

    private void changebuttons(String s) {
        ap_map.getChildren().sorted().stream().filter(n -> n instanceof Button).forEach(n -> {
            String bttext = ((Button) n).getText();
            Integer btid = Integer.parseInt(String.valueOf(bttext.charAt(0)));
            ((Button) n).setText(btid + " " + String.valueOf(s.charAt(btid)));
            ta_log.appendText(bttext);

        });
    }

    /**
     * Aktuallisiert das History-Log mit den aktuellen History
     */

    private void refreshHistory() {
        ta_history.clear();
        for (String s : mechanik.getMyGraph().getHistory()) {
            ta_history.appendText(s + "\n");
        }
    }

    /**
     * Erstellt das Sever PopOver
     *
     * @return
     */

    private PopOver getServerPopover() {
        PopOver tmp = new PopOver();
        tmp.setDetachable(false);
        tmp.setArrowLocation(PopOver.ArrowLocation.TOP_CENTER);
        tmp.setAnchorX(5);
        tmp.setAnchorY(10);
        tmp.setContentNode(getServerInfo());
        return tmp;
    }

    /**
     * Erstellt den Inhalt des PopOvers mit dem Port
     *
     * @return Das GridPane mit dem Inhalt der Serverinformationen
     */

    private GridPane getServerInfo() {
        final GridPane myGridPane = new GridPane();
        myGridPane.setHgap(10);
        myGridPane.setVgap(10);
        myGridPane.setPadding(new Insets(10, 10, 10, 10));
        final TextField tf_port = new TextField();
        if (!(port == null)) {
            tf_port.setText(String.valueOf(port));
        }
        Label l_port = new Label("Port");
        Button db_ok = new Button("Ok");
        db_ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                port = Integer.valueOf(tf_port.getText());
                runServer();
            }
        });
        Button db_abbrechen = new Button("Abbrechen");
        db_abbrechen.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                serverPop.hide();
            }

        });
        myGridPane.add(l_port, 1, 0);
        myGridPane.add(tf_port, 2, 0);
        GridPane mysubGridPane = new GridPane();
        mysubGridPane.setVgap(10);
        mysubGridPane.setHgap(10);
        mysubGridPane.setPadding(new Insets(4, 0, 4, 0));
        mysubGridPane.add(db_ok, 0, 0);
        mysubGridPane.add(db_abbrechen, 1, 0);
        myGridPane.add(mysubGridPane, 2, 1);
        return myGridPane;
    }

    /**
     * Erstellt das Client PopOver mit dem Port und der IP
     *
     * @return Das PopOver mit allen Informationen
     */

    private PopOver getClientPopover() {
        PopOver tmp = new PopOver();
        tmp.setDetachable(false);
        tmp.setArrowLocation(PopOver.ArrowLocation.TOP_CENTER);
        tmp.setAnchorX(5);
        tmp.setAnchorY(10);
        tmp.setContentNode(getClientInfo());
        return tmp;
    }


    /**
     * Der Inhalt des PopOvers
     *
     * @return Inhalt des PopOvers
     */

    private GridPane getClientInfo() {
        final GridPane myGridPane = new GridPane();
        myGridPane.setHgap(10);
        myGridPane.setVgap(10);
        myGridPane.setPadding(new Insets(10, 10, 10, 10));
        final TextField tf_port = new TextField();
        if (!(port == null)) {
            tf_port.setText(String.valueOf(this.port));
        }
        final TextField tf_ip = new TextField();
        if (!this.ip.isEmpty()) {
            tf_ip.setText(this.ip);
        }
        Label l_port = new Label("Port");
        Label l_ip = new Label("IP");
        Label l_map = new Label("Map");
        Button bt_map = new Button("Pfad zu der Map");
        bt_map.setOnAction(new EventHandler<ActionEvent>() {
                               @Override
                               public void handle(ActionEvent e) {
                                   File file = filechooser.showOpenDialog(null);
                                   if (file != null) {
                                       mechanik = new Mechanik(file.getAbsolutePath());
                                   }
                               }
                           }
        );
        Button db_ok = new Button("Ok");
        db_ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                ip = tf_ip.getText();
                port = Integer.valueOf(tf_port.getText());
                runClient();
            }
        });
        Button db_abbrechen = new Button("Abbrechen");
        db_abbrechen.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                serverPop.hide();
            }

        });
        myGridPane.add(l_ip, 1, 0);
        myGridPane.add(tf_ip, 2, 0);
        myGridPane.add(l_port, 1, 1);
        myGridPane.add(tf_port, 2, 1);
        myGridPane.add(l_map,1,2);
        myGridPane.add(bt_map,2,2);
        GridPane mysubGridPane = new GridPane();
        mysubGridPane.setVgap(10);
        mysubGridPane.setHgap(10);
        mysubGridPane.setPadding(new Insets(4, 0, 4, 0));
        mysubGridPane.add(db_ok, 0, 0);
        mysubGridPane.add(db_abbrechen, 1, 0);
        myGridPane.add(mysubGridPane, 2, 3);
        return myGridPane;
    }

    private void runServer() {

        ta_log.appendText("Laden des Servers....");
        try {

            // Initialiseren des Servers
            server = new ServerSocket(port);
            ta_log.appendText("Server erfolgreich erstellt..");
            ta_log.appendText("Der Server läuft und hört auf Port:" + port);
            ta_log.appendText("Server wartet auf Verbindungen...");

            // Warten auf einen Clienten und versuch die Verbidnung herzustellen
            try {

                //Warten auf Client
                client = server.accept();
                ta_log.appendText("Verbindung hergestellt:" + client.getLocalAddress().toString().substring(0) + ":" + client.getLocalPort());

                // Setzen der Seiten
                eigenSeite = Seite.Kathargo;
                gegner = Seite.Rom;

                // Abfertigung des Clienten
                handleClient();
            } catch (IOException e) {
                client.close();
                server.close();
                e.printStackTrace();
            } finally {
                // Wenn die Verbindung des Clienten nicht geschlossen wurde, wird dies nun getan
                if (client != null)
                    try {
                        client.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void handleClient() throws Exception {

        // Zeichenen der Map
        initial();

        // Öffnen der Streams zum lesen der I/O Eingaben zwischen den Sockets und der Eingabe der Tastatur
        BufferedReader tastatur_input = new BufferedReader(
                new InputStreamReader(System.in));
        PrintWriter output = new PrintWriter(client.getOutputStream(), true /* autolush */);
        BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));

        // Status ausgeben
        ta_log.appendText("Der Client " + client.getLocalAddress() + ":"
                + client.getLocalPort() + " wurde verbunden");

        ta_log.appendText("Warten auf die Map");

        // Einlesen der Map, welche vom Client gesendet wird
        ArrayList<String> map = new ArrayList<String>();
        String line = null;
        Boolean karte = true;
        while (karte) {
            line = input.readLine();
            ta_log.appendText(line);

            // Beenden des Map einlesen, fall der erste Zug gesendet wird
            if (line.startsWith("C") | line.startsWith("R") | line.startsWith("X")) {
                break;
            } else {
                map.add(line);
            }
        }

        ta_log.appendText("Prüfen der Map");

        /*
        // Prüfen ob die Map inordnung ist
        if (!checkmap(map)) {
            throw new Exception(
                    "Die Map entspricht nicht den Spezifikationen");
        }
        ta_log.appendTextn("Map ok \nFüge sie ins Spiel ein....");
        */

        // Einlesen der Map
        mechanik = new Mechanik(map);
        initial();

        showMap();

        ta_log.appendText("Ok... \nBeginnen des Spiels");

        // Da der Client mit dem Zug beginnt wird zuerst der Zug ausgewertet
        ta_log.appendText("Der Gegner macht den Zug: " + line);
        changebuttons(mechanik.auswerten(line, Seite.Rom));
        showMap();
        aktiveHuman = true;
    }

    public void runClient(){

    }

    /**
     * Zeigt die aktuelle Map an
     */

    private void showMap() {
        ta_log.appendText("Aktuelle Map: " + mechanik.getMyGraph().convertToString());
    }
}
