package Test;

import core.Seite;
import logik.Mechanik;
import logik.WaspAI;
import org.junit.Test;
import static org.junit.Assert.*;

public class WaspAITest {

	private WaspAI aiR = new WaspAI(Seite.Rom, 2);
	private WaspAI aiK = new WaspAI(Seite.Kathargo, 1);

	@Test
	public void testNextZug() throws Exception {
		Mechanik game = new Mechanik();
		game.aiVsAiGame(aiR,aiK);
		assertEquals("NCRN", game.getMyGraph().convertToString());
	}

	@Test
	public void testNextZug2() throws Exception {
		Mechanik game = new Mechanik();
		game.aiVsAiGame(aiR, aiK);
		assertEquals(game.getMyGraph().getHistory().size(), 6);
	}

}