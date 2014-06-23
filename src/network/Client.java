package network;

import java.io.*;
import java.net.Socket;

/**
 * Created by Acer on 23.06.2014.
 */
public class Client {
    public static void main(String[] args){
        try{
            Socket clientR = new Socket(args[0],Integer.parseInt(args[1]));        // Socket(Server-name,Port)
            sendGameBoard (clientR);
            while(true){
                sendZug();
            }
            //TODO Was der Client machen soll

        }catch (IOException e){e.getStackTrace();}
    }



private void sendGameBoard(Socket clientR){
    try {
        BufferedReader in = new BufferedReader(new FileReader("ex/map.txt"));

        OutputStream outS = clientR.getOutputStream();
        BufferedOutputStream out = new BufferedOutputStream(outS);

    }catch (IOException e){e.getStackTrace();}
}
private void sendZug(){
    // TODO ZUG senden
}
}