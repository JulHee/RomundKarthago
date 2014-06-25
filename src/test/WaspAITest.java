package test;

import logik.Mechanik;
import logik.WaspAI;

import org.junit.Test;

import core.datacontainers.Seite;

public class WaspAITest {

    @Test
    public void testNextZug() throws Exception {
	Mechanik game = new Mechanik("Pfad zur Map");
	game.game(new WaspAI(Seite.Rom), new WaspAI(Seite.Kathargo));

    }
}