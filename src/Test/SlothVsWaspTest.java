package Test;

import core.Seite;
import logik.Mechanik;
import logik.Sloth;
import logik.WaspAI;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SlothVsWaspTest {

	private WaspAI aiR = new WaspAI(Seite.Rom, 2);
	private Sloth aiK = new Sloth(Seite.Kathargo);

	@Test
	public void testNextZug() throws Exception {
		Mechanik game = new Mechanik();
		game.aiVsAiGame(aiR,aiK);
		assertEquals("NNRN", game.getMyGraph().convertToString());
	}

	@Test
	public void testNextZug2() throws Exception {
		Mechanik game = new Mechanik();
		game.aiVsAiGame(aiR, aiK);
		assertEquals(game.getMyGraph().getHistory().size(), 6);
	}
}