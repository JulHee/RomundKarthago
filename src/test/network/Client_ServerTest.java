package test.network;

import core.datacontainers.Seite;
import logik.Mechanik;
import logik.ai.Killjoy;
import logik.ai.WaspAI;
import network.Server;

import org.junit.Test;

public class Client_ServerTest {

    // Variable, welche in mehreren Test benötigt werden
    Mechanik myMechanik = new Mechanik("ext/GameBoard.txt");
    String ip = "127.0.0.1";
    Integer port = 523;

    //Funktionieren jeweils einzeln.... irgendeine art close fehlt!
/*
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
    
  */  
    
    @Test
    public void test_thread() throws Exception {
        final WaspAI myWasp = new WaspAI(Seite.Kathargo);
        Thread serverThread = new Thread() {
            @Override
            public void run() {
                Server S = new Server(port,myWasp);
            }
        };
        serverThread.start();
        myMechanik.game(ip,port,myWasp);
    }
	
    /*
    @Test
    public void test_thread2() throws Exception {
        Thread serverThread = new Thread() {
            @Override
            public void run() {
                Scrooge myScro = new Scrooge(Seite.Rom, myMechanik);
                Server S = new Server(port,myScro);
            }
        };
        serverThread.start();

        Scrooge myScro = new Scrooge(Seite.Kathargo, myMechanik);
        myMechanik.game(ip,port,myScro);
    }
    */


}
