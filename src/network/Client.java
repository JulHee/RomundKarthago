package network;

import core.datacontainers.Seite;
import logik.AIPlayer;
import core.datacontainers.Zug;
import logik.Killjoy;

import java.io.*;
import java.net.Socket;

/**
 * Created by Acer on 23.06.2014.
 */
public class Client {
    private static Boolean spielLaeuft = false;
    public static void main(String[] args){
        try{
            Socket clientR = new Socket(args[0],Integer.parseInt(args[1]));        // Socket(Server-name,Port)
            sendGameBoard(clientR,"ext/map.txt");
            spielLaeuft=true;
            AIPlayer player = new Killjoy(Seite.Kathargo);                      // AIPlayer wird angelegt
            sendZug(clientR,player);
            while(spielLaeuft){
                spielLaeuft=warteAufZug(clientR);
                sendZug(clientR,player);
            }
            clientR.close();

            //TODO Was der Client machen soll

        }catch (IOException e){e.getStackTrace();}
    }



    private static void sendGameBoard(Socket clientR,String myPath){
        try {
            BufferedReader in = new BufferedReader(new FileReader(myPath));
            BufferedOutputStream out = new BufferedOutputStream(clientR.getOutputStream());
            while(in.readLine() != null) {
                out.write(in.readLine().getBytes());        //Die Zeilen der Map.txt werden in den OutputStream des Socket gegeben
            }
        }catch (IOException e){e.getStackTrace();}
    }

    private static void sendZug(Socket clientR, AIPlayer spieler)throws IOException{
        BufferedOutputStream out = new BufferedOutputStream(clientR.getOutputStream());
        out.write(spieler.nextZug.toFormat.getBytes()); // hier wird die abstrakte Methode nextZug aufgerufen, um den Zug zu übergeben
        // TODO abstract nextZug funktioniert nicht
    }

    /**
     * empfängt den Zug und falls etwas nicht stimmt gibt es false zurück, wodurch das Spiel beendent wird
     *
     * @return Boolean
     */
    private static Boolean warteAufZug(Socket clientR){
        return true;    //TODO bekommt einen Zug vom Server und verarbeitet ihn
    }

}