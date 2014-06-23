package network;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by Acer on 23.06.2014.
 */
public class Client {
    public static void main(String[] args){
        try{
            Socket clientR = new Socket(args[0],Integer.parseInt(args[1]));
            //TODO Was der Client machen soll

        }catch (IOException e){e.getStackTrace();}
    }

}
