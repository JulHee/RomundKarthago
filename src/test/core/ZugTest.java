package test.core;

import core.datacontainers.Zug;
import exceptions.KeinBesetzerException;
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
        try {
            Zug zug = new Zug(test);
        }catch (KeinBesetzerException e) {
            assertEquals("Keine geeigneten Besetzer gefunden",e.getMessage() );
        }
    }

    @Test
    public void testToFormat()throws Exception{
        String tmp= "R X";
        Zug zug = new Zug(tmp);
        assertEquals("R X",zug.toFormat());
    }

}