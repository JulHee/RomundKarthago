package test.ai;

import static org.junit.Assert.*;

import java.util.ArrayList;

import logik.Mechanik;
import logik.ai.optionalAI;

import org.junit.Test;

import core.datacontainers.Knoten;
import core.datacontainers.Position;
import core.datacontainers.Seite;
import core.datacontainers.Zug;

public class optionalAITest {

	// Variablen zur Erzeugung der Test
	Mechanik mecha = new Mechanik("ext/Gameboard.txt");
	optionalAI Pew = new optionalAI(Seite.Rom,mecha);

	ArrayList<Knoten> array = new ArrayList();
	ArrayList<ArrayList<Knoten>> arrayarray = new ArrayList();
	Knoten n0 = new Knoten(0, Seite.Kathargo, new Position(350, 125));
	Knoten n1 = new Knoten(1,Seite.Neutral, new Position(250,275));
	Knoten n2 = new Knoten(2,Seite.Neutral, new Position(350,275));
	Knoten n3 = new Knoten(3,Seite.Rom, new Position(250,425));

	//Testen der Hilfsfunktionen

	//optinalAI.used - tested!
	@Test
	public void usedtest1() {
		array.add(n0);
		arrayarray.add(array);
		assertTrue(Pew.used(n0, arrayarray));
	}

	@Test
	public void usedtest2() {
		arrayarray.add(array);
		assertFalse(Pew.used(n0, arrayarray));
	}
	//optinalAI.kette  - tested!
	@Test
	public void kettetest1() {
		Pew.kette(array, Seite.Kathargo, n0);
		assertTrue(Pew.checkme(array, n0));
	}

	@Test
	public void kettetest2() {
		array.add(n0);
		Pew.kette(array, Seite.Kathargo, n0);
		assertTrue(Pew.checkme(array, n0));
	}

	@Test
	public void kettetest3() {
		Pew.kette(array, Seite.Neutral, n1);
		assertTrue(Pew.checkme(array, n2));
	}
	//optionalAI.getchainz
	@Test
	public void chainztest(){
		Pew.getchainz();
		Boolean result = false;
		for(ArrayList<Knoten> a: Pew.Kchainz){
			for(Knoten b: a){
				if(Pew.checkme(a, n0)){
					result = true;
				}
			}
		}
		assertTrue(result);
	}
	@Test
	public void chainztest2(){
		Pew.getchainz();
		Boolean result = false;
		for(ArrayList<Knoten> a: Pew.Rchainz){
			for(Knoten b: a){
				if(Pew.checkme(a, n3)){
					result = true;
				}
			}
		}
		assertTrue(result);
	}
	@Test
	public void savemetest(){
		Zug resu = Pew.saveme();
		assertTrue((resu.getStadt()== 2) | (resu.getStadt() == 1 ));
	}






}
