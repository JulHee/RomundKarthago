package network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import logik.Mechanik;
import core.datacontainers.Seite;

public class HumanServer extends Server {

    static Seite mySeite = Seite.Kathargo;
    static Integer port;
    static Mechanik myMechanik;

    public HumanServer(Integer port) {

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

        // Öffnen der Streams zum lesen der I/O Eingaben zwischen den Sockets und der Eingabe der Tastatur
        BufferedReader tastatur_input = new BufferedReader(
                new InputStreamReader(System.in));
        PrintWriter output = new PrintWriter(client.getOutputStream(),true /* autolush */);
        BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));

        /*

                DataOutputStream out = new DataOutputStream(
                client.getOutputStream());
                DataInputStream in = new DataInputStream(client.getInputStream());
         */


        // Status ausgeben
        System.out.println("Der Client " + client.getLocalAddress() + ":"
                + client.getLocalPort() + " wurde verbunden");

        System.out.println("Warten auf die Map");

        // Einlesen der Map, welche vom Client gesendet wird
        ArrayList<String> map = new ArrayList<String>();
        String line = null;
        Boolean karte = true;
        while (karte) {
            line = input.readLine();
            System.out.println(line);

            // Beenden des Map einlesen, fall der erste Zug gesendet wird
            if (line.startsWith("C") | line.startsWith("R") | line.startsWith("X")) {
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
            System.out.println("Bitte geben Sie ihren Zug ein:");
            String zug = tastatur_input.readLine();
            System.out.println("Senden des Zuges: "+zug);

            // Zug senden
            output.println(zug);

            // Auswerten des Zuges
            myMechanik.auswerten(zug, mySeite);
            System.out.println("Spielstatus: " + myMechanik.getMyGraph().convertToString());

            //Falls der letzte Zug das Spiel schon beendet hat
            if (!myMechanik.getSpiel()){
              break;
            }

            // Lesen des Gegnerzuges
            String zug_gegner = input.readLine();
            System.out.println("Der Gegner macht den Zug: " + zug_gegner);

            // Auswerten
            myMechanik.auswerten(zug_gegner, Seite.Rom);
            System.out.println("Spielstatus: " + myMechanik.getMyGraph().convertToString());
        }
        client.close();

        System.out.println("Das Spiel wurde beendet");
    }
}
