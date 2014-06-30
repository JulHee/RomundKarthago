package test;

import static org.junit.Assert.*;
import core.datacontainers.Seite;
import logik.Mechanik;
import logik.WaspAI;
import network.AIServer;
import network.Client;
import network.HumanServer;

import org.junit.Test;

public class Client_ServerTest {

    // Variable, welche in mehreren Test benötigt werden
    Mechanik myMechanik = new Mechanik("ext/GameBoard.txt");
    String ip = "127.0.0.1";
    Integer port = 12345;


   @Test
   public void CStest() throws Exception {
	   Thread serverThread = new Thread(){
		 @Override
		 public void run(){
			 HumanServer S = new HumanServer(port);
		 }
	   };
	   serverThread.start();
	   
	   Client C = new Client(port,ip,myMechanik);
	   C.test();
   }
    
    
    /*
    @Test
    public void test_thread() throws Exception {
        Thread serverThread = new Thread() {
            @Override
            public void run() {
                WaspAI myWasp = new WaspAI(Seite.Rom);
                AIServer S = new AIServer(myWasp,port);
            }
        };
        serverThread.start();

        WaspAI myWasp = new WaspAI(Seite.Kathargo);
        myMechanik.game(ip,port,myWasp);
    }
*/

}
