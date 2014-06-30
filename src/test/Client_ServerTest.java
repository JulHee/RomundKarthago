package test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.Socket;

import logik.Mechanik;
import network.Client;
import network.HumanServer;

import org.junit.Test;

public class Client_ServerTest {

	// Variable, welche in mehreren Test benötigt werden
	Mechanik myMechanik1 = new Mechanik("ext/GameBoard.txt");
	String ip = "127.0.0.1";
	Integer port = 12345;
	
	
	@Test
	public void test() throws Exception {
		Client C = new Client(port, ip, myMechanik1);
		HumanServer S = new HumanServer(port); 
		S.run();
		Socket csock = new Socket(ip,port);
		C.test(csock);
		
	}

}
