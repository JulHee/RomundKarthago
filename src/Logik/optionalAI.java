package Logik;

import Graph.*;

/**
 * optionale KI fuer Ki - Turnier
 * @author Jörn Kabuth
 *
 */

// komplette AI noch in Arbeit!


public class optionalAI extends AIPlayer{

	public optionalAI(Seite s){
		meineSeite = s;
	};

	@Override
	Zug nextZug() {
		// TODO allgemeine Def. nextZug
		return null;
	}
	/*
	 * Konzept: Alle möglichen Züge werden bewertet und bekommen einen "Wert" zwischen 0 und 1 
	 * zugewiesen, wobei der höhere Wert größere Erfolgschancen verspricht. 
	 * 
	 * Berechnung des Wertes durch:
	 * 
	 * -mehrere mögliche Züge bewerten und besten ermitteln (wie?) TODO Zugbewertung Zvalue
	 * -zusammenhängende Ketten einer Seite ermitteln + check ob "sicher" (vor Aushungerung) TODO Kettenfunktion getchainz
	 * -Gegnerketten beachten (nur 1 Neutraler Nachbar -> sofort besetzen -> Gegner hungert aus TODO Gegnerketten
	 * -je weiter vom Gegner entfernt, desto mehr Platz für Ausbreitung/ sammeln von Punkten TODO Entfernung berechnen
	 * -allgemein mögliche Stellungen bevorzugen, welche viele Neutrale Nachbarn haben	TODO Stadt-Sicherheit bewerten 
	 * -letzten Züge des Gegners beachten: Züge nah an eigenene Ketten -> Gefahr -> Reaktion? TODO Reaktion auf Geg. Züge
	 * 
	 * 
	 * 
	 * 
	 */


	/**
	 * Berechnung, wie "gut" ein Zug zu bewerten ist.
	 * 
	 */
	public double Zvalue(){
		double value = 0.0;
		return value;
	};
	// nicht fertig!
	/**
	 * Bestimmung der Ketten zusammenhängender Städte einer Fraktion
	 * 
	 */
	public void getchainz(){
		Graph myGraph = mechanik.getMyGraph();
		for(Knoten a: myGraph.l_knoten){
			myGraph.getNachbarschaft(a);
		}
	}
}
