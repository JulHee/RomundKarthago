package test;

import core.Seite;
import logik.Mechanik;
import logik.WaspAI;
import org.junit.Test;

public class WaspAITest {

	@Test
	public void testNextZug() throws Exception {
		Mechanik game = new Mechanik();
		game.aiVsAiGame(new WaspAI(Seite.Rom),new WaspAI(Seite.Kathargo));

	}
}