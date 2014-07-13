package test.ai;

import core.datacontainers.Seite;
import logik.Mechanik;
import logik.ai.Joernson;
import logik.ai.Scrooge;
import logik.ai.Sloth;
import logik.ai.WaspAI;
import org.junit.Test;

/**
 * Projekt: Rom und Karthago
 * Author : Julian Heeger, Markus Poell, Christian Bruene, Joern Kabuth
 * Date : 26.04.14
 * Year : 2014
 */

public class ZweiAITest {
    @Test
    public void testNextZug() throws Exception {
        Mechanik myMechanik = new Mechanik("ext/aiTestMap.txt");
        Sloth mySloth = new Sloth(Seite.Rom, myMechanik);
        Joernson rom = new Joernson(Seite.Rom,myMechanik);
		Joernson kathargo = new Joernson( Seite.Kathargo, myMechanik );

        myMechanik.game(rom,kathargo);
    }
}
