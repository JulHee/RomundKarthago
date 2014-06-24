package network;

import core.datacontainers.Zug;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ClientInfoStatus;

/**
 * Created by Acer on 23.06.2014.
 */
public class Server {
    static ServerSocket serverC;
    private static Boolean spielLaeuft = false;
    public static void main(String[]args){
        try {
            serverC = new ServerSocket(Integer.parseInt(args[0])); // die Portnummer wird als Komandozeilenparameter übergeben
            serverC.setSoTimeout(60000);                           // die max. Wartezeit des Servers wird festgelegt
            while (spielLaeuft) {
               Socket clientR = serverC.accept();
                handleConnection(clientR);
            }
            serverC.close();                                        //beendet die aktuelle Verbindung

        }catch (IOException e){e.getStackTrace();}
    }

    public static Boolean handleConnection(Socket clientR)throws IOException{
        String tmp  = convertStreamToString(clientR.getInputStream());
        Zug seinZug = new Zug(tmp);

        return true;    //TODO Was macht der Server mit der vorhandenne Connection, lesen,handeln,antworten
    }

    /**
     * This function get an InputStream and generates a String with its content
     * @param is
     * @return String
     */
    private static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }



}
