package test.spiellogik;

import core.datacontainers.Seite;
import logik.Mechanik;
import org.junit.Test;
import  static org.junit.Assert.*;

public class MechanikTest {

    @Test
    public void testTransition() throws Exception {

    }

    @Test
    public void testRun() throws Exception {
        Mechanik game = new Mechanik("ext/0_3.map.02.txt");
        game.auswerten("R 2",Seite.Rom);
        game.auswerten("C 1",Seite.Kathargo);
        game.auswerten("R 2",Seite.Rom);
        game.auswerten("C 1",Seite.Kathargo);
        game.auswerten("R 3",Seite.Rom);
        game.auswerten("C X",Seite.Kathargo);
        assertEquals("CCCN",game.auswerten("R X",Seite.Rom));

    }
}