package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ClientInfoStatus;

/**
 * Created by Acer on 23.06.2014.
 */
public class Server {
    static ServerSocket serverC;

    public static void main(String[]args){
        try {
            serverC = new ServerSocket(Integer.parseInt(args[0])); // die Portnummer wird als Komandozeilenparameter übergeben
            serverC.setSoTimeout(60000);                           // die max. Wartezeit des Servers wird festgelegt
            Socket clientR = serverC.accept();
            handleConnection(clientR);
            clientR.close();                                        //beendet die aktuelle Verbindung

        }catch (IOException e){e.getStackTrace();}
    }

    public static void handleConnection(Socket clientR){
        //TODO Was macht der Server mit der vorhandenne Connection, lesen,handeln,antworten
    }



}
