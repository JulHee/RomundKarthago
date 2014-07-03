package test.network;

import core.datacontainers.Seite;
import logik.Mechanik;
import logik.ai.WaspAI;
import network.Server;
import org.junit.Test;
import logik.ai.Scrooge;

public class Client_ServerTest {

    // Variable, welche in mehreren Test benötigt werden
    Mechanik myMechanik = new Mechanik("ext/GameBoard.txt");
    String ip = "127.0.0.1";
    Integer port = 10000;

    
    @Test
    public void test_thread() throws Exception {
        final WaspAI myWasp = new WaspAI(Seite.Kathargo);
        Thread serverThread = new Thread() {
            @Override
            public void run() {
                Server S = new Server(port+1,myWasp);
            }
        };
        serverThread.start();
        myMechanik.game(ip,port+1,myWasp);
    }
	

    @Test
    public void test_thread2() throws Exception {
        Thread serverThread = new Thread() {
            @Override
            public void run() {
                Scrooge myScro = new Scrooge(Seite.Rom, myMechanik);
                Server S = new Server(port+2,myScro);
            }
        };
        serverThread.start();

        Scrooge myScro = new Scrooge(Seite.Kathargo, myMechanik);
        myMechanik.game(ip,port+2,myScro);
    }



}
