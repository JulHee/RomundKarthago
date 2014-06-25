package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import logik.Mechanik;
import logik.optionalAI;

import org.junit.Test;

import core.datacontainers.Knoten;
import core.datacontainers.Position;
import core.datacontainers.Seite;

public class optionalAITest {

    // Variablen zur Erzeugung der Tests
    Mechanik mecha = new Mechanik("ext/Gameboard.txt");
    optionalAI Pew = new optionalAI(Seite.Rom);

    ArrayList<Knoten> blubb1 = new ArrayList();
    Knoten blubb2 = new Knoten(0, Seite.Kathargo, new Position(350, 125));
    ArrayList<ArrayList<Knoten>> blubb3 = new ArrayList();

    @Test
    public void usedtest1() {
	blubb1.add(blubb2);
	blubb3.add(blubb1);
	assertTrue(Pew.used(blubb2, blubb3));
    }

    @Test
    public void usedtest2() {
	blubb3.add(blubb1);
	assertFalse(Pew.used(blubb2, blubb3));
    }

    @Test
    public void kettetest1() {
	Pew.kette(blubb1, Seite.Kathargo, blubb2);
	assertTrue(blubb1.contains(blubb2));
    }

    @Test
    public void kettetest2() {
	blubb1.add(blubb2);
	Pew.kette(blubb1, Seite.Kathargo, blubb2);
	assertTrue(blubb1.contains(blubb2));
    }
}
