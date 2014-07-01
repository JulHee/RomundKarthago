package test.core_test;

import core.datacontainers.Zug;
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


}