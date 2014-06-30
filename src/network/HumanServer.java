package network;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
        this.port = port;
        try {
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
            ServerSocket server = new ServerSocket(port);
            System.out.println("Server erfolgreich erstellt..");
            System.out.println("Der Server läuft und hört auf Port:" + port);
            System.out.println("Server wartet auf Verbindungen...");

            while (true) {

                Socket client = null;

                try {
                    client = server.accept();
                    System.out.println("Verbindung hergestellt:" + client.getLocalAddress().toString().substring(0) + ":" + client.getLocalPort());
                    handleClient(client);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
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
        BufferedReader input = new BufferedReader(
                new InputStreamReader(System.in));
        System.out.println("Der Client " + client.getLocalAddress() + ":"
                + client.getLocalPort() + " wurde verbunden");
        DataOutputStream out = new DataOutputStream(
                client.getOutputStream());
        DataInputStream in = new DataInputStream(client.getInputStream());

        System.out.println("Warten auf die Map");
        // Einlesen der Map, welche vom Client gesendet wird
        ArrayList<String> map = new ArrayList<String>();
        String line = null;
        Boolean karte = true;
        while (karte) {
            line = in.readUTF();
            System.out.println(line);
            if (line.startsWith("C") | line.startsWith("R")) {
                break;
            } else {
                map.add(line);
            }
        }

        System.out.println("Prüfen der Map");

        // Prüfen ob die Map inordnung ist
        if (!checkmap(map)) {
            throw new Exception(
                    "Die Map entspricht nicht den Spezifikationen");
        }
        System.out.println("Map ok \nFüge sie ins Spiel ein....");

        // Einlesen der Map
        myMechanik = new Mechanik(map);

        System.out.println("Ok... \nBeginnen des Spiels");

        // Da der Client mit dem Zug beginnt wird zuerst der Zug ausgewertet
        myMechanik.auswerten(line, Seite.Rom);

        // Beginnen des Spiels
        while (myMechanik.getSpiel()) {
            System.out.println("Bitte geben Sie ihren Zug ein:");
            String zug = input.readLine();
            out.writeUTF(zug);
            myMechanik.auswerten(zug, mySeite);
            System.out.println("Spielstatus: " + myMechanik.getMyGraph().convertToString());
            String zug_gegner = in.readUTF();
            System.out.println("Der Gegner macht den Zug: " + zug_gegner);
            myMechanik.auswerten(zug_gegner, Seite.Rom);
            System.out.println("Spielstatus: " + myMechanik.getMyGraph().convertToString());
        }
        System.out.println("Das Spiel wurde beendet");
    }
}
