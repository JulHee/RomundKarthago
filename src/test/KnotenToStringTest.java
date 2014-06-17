package test;
import core.datacontainers.Knoten;
import core.datacontainers.Position;
import core.datacontainers.Seite;
import org.junit.Test;

import static org.junit.Assert.*;
/**
 * Created by  J�rn Kabuth on 22.05.2014.
 */
public class KnotenToStringTest {
	Knoten knoten1 = new Knoten(3, Seite.Neutral,new Position(30,40));
	Knoten knoten2 = new Knoten(8,Seite.Kathargo,new Position(15,25));
	Knoten knoten3 = new Knoten(15,Seite.Neutral,new Position(7,9));
	Knoten knoten4 = new Knoten(0,Seite.Rom,new Position(0,0));

	@Test
	public void testKonvertierung1()throws  Exception{
		String x = knoten1.toString()  ;
		assertEquals(x,"Knoten{id=3, seite=N, position=Paar{x=30, y=40}}");
	}
	@Test
	public void testKonvertierung2()throws  Exception{
		String x = knoten2.toString()  ;
		assertEquals(x,"Knoten{id=8, seite=C, position=Paar{x=15, y=25}}");
	}
	@Test
	public void testKonvertierung3()throws  Exception{
		String x = knoten3.toString()  ;
		assertEquals(x,"Knoten{id=15, seite=N, position=Paar{x=7, y=9}}");
	}
	@Test
	public void testKonvertierung4()throws  Exception{
		String x = knoten4.toString()  ;
		assertEquals(x,"Knoten{id=0, seite=R, position=Paar{x=0, y=0}}");
	}
}

