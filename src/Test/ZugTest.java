package Test;

import core.Seite;
import core.Zug;
import org.junit.Test;

import static org.junit.Assert.*;

public class ZugTest {

    @Test
    public void testKonstruktor() throws Exception {
       String test = "N 1";
       assertNotNull("Object wurde erzeugt",new Zug(test));
    }

    @Test
    public void  testKonstruktorFail() throws Exception{
        String test = "O 2";
        Zug zug = new Zug(test);
        assertNull("Object wurde nicht erzeugt",zug.getSeite());
    }

	@Test
	public void  testKonstruktorFailSeite() throws Exception{
		String test = "R 2";
		Zug zug = new Zug(Seite.Rom, 2);
		assertEquals(Seite.Rom ,zug.getSeite());
	}

	@Test
	public void  testKonstruktorFailCity() throws Exception{
		String test = "R 2";
		Zug zug = new Zug(Seite.Rom, 2);
		assertEquals(2 ,zug.getStadt());
	}
}