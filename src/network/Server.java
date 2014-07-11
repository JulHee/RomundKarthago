package network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javafx.scene.control.TextArea;
import logik.Mechanik;
import core.datacontainers.Seite;
import logik.ai.AIPlayer;

public class Server {

    static Seite mySeite = Seite.Kathargo;
    static Integer port;
    static Mechanik myMechanik;
    static AIPlayer ai = null;
    static Boolean activeAI = false;
    private TextArea outputstream = null;

    public Server(Integer port) {

        // Setzen des Ports auf dem der Sever hört
        this.port = port;
        try {
            // Starten des Servers
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Server(Integer port, TextArea outputstream) {
        this.outputstream = outputstream;
        // Setzen des Ports auf dem der Sever hört
        this.port = port;
        try {
            // Starten des Servers
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Server(Integer port,
                  AIPlayer ai) {
        // Setzen des Ports auf dem der Sever hört
        this.port = port;
        this.ai = ai;
        try {
            activeAI = true;
            // Starten des Servers
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Server(Integer port, AIPlayer ai, TextArea outputstream) {
        this.outputstream = outputstream;
        // Setzen des Ports auf dem der Sever hört
        this.port = port;
        this.ai = ai;
        try {
            activeAI = true;
            // Starten des Servers
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Funktionen zum Starten des Servers(Karthago)
     * Empfangen der Map mit anschließender Ausführung
     * des Spieles untereinader
     *
     * @throws Exception
     */
    public void run() throws IOException {

        out("Laden des Servers....");
        try {

            // Initialiseren des Servers
            ServerSocket server = new ServerSocket(port);
            out("Server erfolgreich erstellt..");
            out("Der Server läuft und hört auf Port:" + port);
            if (activeAI) {
                out("Es wurde die AI: " + ai.toString() + " geladen.");
            }

            out("Server wartet auf Verbindungen...");

            // Loop um alle Clients zu bearbeiten
            while (true) {

                // Initialisierung des Socket des Clienten
                Socket client = null;

                // Warten auf einen Clienten und versuch die Verbidnung herzustellen
                try {

                    //Warten auf Client
                    client = server.accept();
                    out("Verbindung hergestellt:" + client.getLocalAddress().toString().substring(0) + ":"
                            + client.getLocalPort());

                    // Abfertigung des Clienten
                    handleClient(client);
                } catch (IOException e) {
                    client.close();
                    server.close();
                    e.printStackTrace();
                } finally {
                    // Wenn die Verbindung des Clienten nicht geschlossen wurde, wird dies nun getan
                    if (client != null) {
                        try {
                            client.close();
                        } catch (IOException e) {
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Funktion zum abfertigen eines verbundenen Clienten
     *
     * @param client Der verbundene Client
     * @throws Exception Falls der Client geschlossen wird
     */

    private void handleClient(Socket client) throws Exception {


        // Öffnen der Streams zum lesen der I/O Eingaben zwischen den Sockets und der Eingabe der Tastatur
        BufferedReader tastatur_input = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter output = new PrintWriter(client.getOutputStream(), true /* autolush */);
        BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));

        // Status ausgeben
        out("Der Client " + client.getLocalAddress() + ":" + client.getLocalPort() + " wurde verbunden");

        out("Warten auf die Map");

        // Einlesen der Map, welche vom Client gesendet wird
        ArrayList<String> map = new ArrayList<String>();
        String line = null;
        Boolean karte = true;
        while (karte) {
            line = input.readLine();
            out(line);

            // Beenden des Map einlesen, fall der erste Zug gesendet wird
            if (line.startsWith("C") | line.startsWith("R") | line.startsWith("X")) {
                break;
            } else {
                map.add(line);
            }
        }

        out("Prüfen der Map");

        /*
        // Prüfen ob die Map inordnung ist
        if (!checkmap(map)) {
            throw new Exception(
                    "Die Map entspricht nicht den Spezifikationen");
        }
        out("Map ok \nFüge sie ins Spiel ein....");
        */

        // Einlesen der Map
        myMechanik = new Mechanik(map);
        if (activeAI) {
            if (ai.getMechanik() == null) {
                ai.setMechanik(myMechanik);
            }
        }


        showMap();

        out("Ok... \nBeginnen des Spiels");

        // Da der Client mit dem Zug beginnt wird zuerst der Zug ausgewertet
        out("Der Gegner macht den Zug: " + line);
        myMechanik.auswerten(line, Seite.Rom);
        showMap();

        // Beginnen des Spiels
        String zug = null;
        while (myMechanik.getSpiel()) {
            if (activeAI) {
                zug = ai.nextZug().toString();
            } else {
                out("Bitte geben Sie ihren Zug ein:");
                zug = tastatur_input.readLine();
            }

            out("Senden des Zuges: " + zug);

            // Zug senden
            output.println(zug);

            // Auswerten des Zuges
            myMechanik.auswerten(zug, mySeite);
            showMap();

            //Falls der letzte Zug das Spiel schon beendet hat
            if (!myMechanik.getSpiel()) {
                break;
            }

            // Lesen des Gegnerzuges
            String zug_gegner = input.readLine();
            out("Der Gegner macht den Zug: " + zug_gegner);

            // Auswerten
            myMechanik.auswerten(zug_gegner, Seite.Rom);
            showMap();
        }
        client.close();

        out("Das Spiel wurde beendet");
    }


    /**
     * Zeigt die aktuelle Map an
     */

    private void showMap() {
        if (outputstream == null) {
            System.out.println("Aktuelle Map: " + myMechanik.getMyGraph().convertToString());
        } else {
            outputstream.appendText("Aktuelle Map: " + myMechanik.getMyGraph().convertToString() + "\n");
        }
    }

    /**
     * Prüfen ob die Map den richtlinien entspricht
     *
     * @param map Die Map als ArrayList<String>
     * @return Boolean
     */
    public Boolean checkmap(ArrayList<String> map) {
        try {
            int anzahl_an_Knoten = Integer.parseInt(map.get(0));
            int gefundene_Knoten = 0;
            map.remove(0);
            for (String s : map) {
                if (s.startsWith("V")) {
                    String[] split = s.split(" ");
                    gefundene_Knoten += 1;
                } else if (s.startsWith("E")) {
                    String[] split = s.split(" ");
                    if (myMechanik.getMyGraph().findKnoten(Integer.parseInt(split[1])) == null
                            | myMechanik.getMyGraph().findKnoten(Integer.parseInt(split[2])) == null) {
                        throw new Exception();
                    }
                } else {
                    throw new Exception();
                }
            }
            if (!(anzahl_an_Knoten == gefundene_Knoten)) {
                throw new Exception();
            }
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    private void out(String message) {
        if (outputstream == null) {
            System.out.println(message);
        } else {
            outputstream.appendText(message + "\n");
        }
    }
}
