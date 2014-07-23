package test.ai;

import logik.Mechanik;
import logik.ai.Joernson;
import logik.ai.WaspAI;

import org.junit.Test;

import core.datacontainers.Seite;

public class WaspAITest {

    @Test
    public void testNextZug() throws Exception {
	Mechanik game = new Mechanik("ext/map.txt");
	game.game(new WaspAI(Seite.Rom), new Joernson(Seite.Kathargo,game));

    }
}