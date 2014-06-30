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
        super(port);
        try {
            run();
        } catch (IOException e) {
            System.out.println(e.getMessage());
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
        BufferedReader input = new BufferedReader(
                new InputStreamReader(System.in));
        System.out.println("Laden des Servers....");
        try (ServerSocket ss = new ServerSocket(port)) {
            System.out.println("Server erfolgreich erstellt..");
            ss.setSoTimeout(4000);
            System.out.println("Der Server läuft und hört auf Port:" + port);
            Socket clients = ss.accept();
            System.out.println("Der Client " + clients.getLocalAddress() + ":"
                    + clients.getLocalPort() + " wurde verbunden");
            DataOutputStream out = new DataOutputStream(
                    clients.getOutputStream());
            DataInputStream in = new DataInputStream(clients.getInputStream());

            System.out.println("Warten auf die Map");
            // Einlesen der Map, welche vom Client gesendet wird
            ArrayList<String> map = new ArrayList<String>();
            String line;
            while ((line = in.readUTF()).length() > 0) {
                map.add(line);
                System.out.println(line);
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
            ss.close();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
