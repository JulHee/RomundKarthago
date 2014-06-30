package network;

import core.datacontainers.Seite;
import logik.AIPlayer;
import logik.Mechanik;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Projekt  : RomUndKathargo
 * Author   : Julian Heeger
 * Date     : 30.06.2014
 * Year     : 2014
 */
public class AIServer extends Server {

    static Seite mySeite = Seite.Kathargo;
    static Integer port;
    static Mechanik myMechanik;
    static AIPlayer myAI;

    public AIServer(AIPlayer ai, Integer port) {

        // Setzen der AI
        this.myAI = ai;

        // Setzen des Ports auf dem der Sever hört
        this.port = port;
        try {
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
    @Override
    public void run() throws IOException {

        System.out.println("Laden des Servers....");
        try {

            // Initialiseren des Servers
            ServerSocket server = new ServerSocket(port);
            System.out.println("Server erfolgreich erstellt..");
            System.out.println("Der Server läuft und hört auf Port:" + port);
            System.out.println("Server wartet auf Verbindungen...");

            // Loop um alle Clients zu bearbeiten
            while (true) {

                // Initialisierung des Socket des Clienten
                Socket client = null;

                // Warten auf einen Clienten und versuch die Verbidnung herzustellen
                try {

                    //Warten auf Client
                    client = server.accept();
                    System.out.println("Verbindung hergestellt:" + client.getLocalAddress().toString().substring(0) + ":" + client.getLocalPort());

                    // Abfertigung des Clienten
                    handleClient(client);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    // Wenn die Verbindung des Clienten nicht geschlossen wurde, wird dies nun getan
                    if (client != null)
                        try {
                            client.close();
                        } catch (IOException e) {
                        }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void handleClient(Socket client) throws Exception {

        // Öffnen der Streams zum lesen der I/O Eingaben zwischen den Sockets
        DataOutputStream out = new DataOutputStream(
                client.getOutputStream());
        DataInputStream in = new DataInputStream(client.getInputStream());

        // Status ausgeben
        System.out.println("Der Client " + client.getLocalAddress() + ":"
                + client.getLocalPort() + " wurde verbunden");

        System.out.println("Warten auf die Map");

        // Einlesen der Map, welche vom Client gesendet wird
        ArrayList<String> map = new ArrayList<String>();
        String line = null;
        Boolean karte = true;
        while (karte) {
            line = in.readUTF();
            System.out.println(line);

            // Beenden des Map einlesen, fall der erste Zug gesendet wird
            if (line.startsWith("C") | line.startsWith("R")) {
                break;
            } else {
                map.add(line);
            }
        }

        System.out.println("Prüfen der Map");

        /*
        // Prüfen ob die Map inordnung ist
        if (!checkmap(map)) {
            throw new Exception(
                    "Die Map entspricht nicht den Spezifikationen");
        }
        System.out.println("Map ok \nFüge sie ins Spiel ein....");
        */

        // Einlesen der Map
        myMechanik = new Mechanik(map);
        System.out.println("Die Map: "+myMechanik.getMyGraph().convertToString());

        System.out.println("Ok... \nBeginnen des Spiels");

        // Da der Client mit dem Zug beginnt wird zuerst der Zug ausgewertet
        myMechanik.auswerten(line, Seite.Rom);

        // Beginnen des Spiels
        while (myMechanik.getSpiel()) {
            String zug = myAI.nextZug().toFormat();
            System.out.println("Senden des Zuges: " + zug);

            // Zug senden
            out.writeUTF(zug);

            // Auswerten des Zuges
            myMechanik.auswerten(zug, mySeite);
            System.out.println("Spielstatus: " + myMechanik.getMyGraph().convertToString());

            //Falls der letzte Zug das Spiel schon beendet hat
            if (!myMechanik.getSpiel()) {
                break;
            }

            // Lesen des Gegnerzuges
            String zug_gegner = in.readUTF();
            System.out.println("Der Gegner macht den Zug: " + zug_gegner);

            // Auswerten
            myMechanik.auswerten(zug_gegner, Seite.Rom);
            System.out.println("Spielstatus: " + myMechanik.getMyGraph().convertToString());
        }
        client.close();

        System.out.println("Das Spiel wurde beendet");
    }
}
