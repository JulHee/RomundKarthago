package test.spiellogik_test;

import core.datacontainers.Seite;
import logik.Mechanik;
import org.junit.Test;
import  static org.junit.Assert.*;

public class MechanikTest {

    @Test
    public void testTransition() throws Exception {

    }

    // N C N N // C C R N
    @Test
    public void testRun() throws Exception {
        Mechanik game = new Mechanik("ext/0_3.map.02.txt");
        game.auswerten("R 2", Seite.Rom);
        game.auswerten("C 1",Seite.Kathargo);
        game.auswerten("R 2",Seite.Rom);
        game.auswerten("C 1",Seite.Kathargo);
        game.auswerten("R 3",Seite.Rom);
        game.auswerten("C X",Seite.Kathargo);
        assertEquals("NCNN",game.auswerten("R X",Seite.Rom));

    }
  /*
    @Test
    public void testRun2() throws Exception {
        Mechanik game = new Mechanik();
        game.run("R 1");
        game.run("C 0");
        assertEquals("NRNR",game.run("R X"));
    }

    /*@Test
    public void testRun3() throws Exception {
        Mechanik game = new Mechanik();
        game.run("R 2");
        game.run("C 1");
        game.run("C 0");

        assertEquals("CCNN",game.run("C 1"));

    }*/
   /* @Test
    public void testRun4() throws Exception {
        Mechanik game = new Mechanik();

        assertEquals("CNNR",game.run("R 5"));

    }

    @Test
    public void testRun5() throws Exception {
        Mechanik game = new Mechanik();

        assertEquals("CNNR",game.run("R -1"));

    }
    */
}