package Logik;

import Graph.*;

/**
 * optionale KI fuer Ki - Turnier
 * @author Jörn Kabuth
 *
 */

// TODO --- komplette AI noch in Arbeit!


public class optionalAI extends AIPlayer{

	public optionalAI(Seite s){
		meineSeite = s;
	};

	@Override
	Zug nextZug() {
		// TODO Auto-generated method stub
		return null;
	}
	/*
	 * Konzept: Alle möglichen Züge werden bewertet und bekommen einen "Wert" zwischen 0 und 1 
	 * zugewiesen, wobei der höhere Wert größere Erfolgschancen verspricht. 
	 * 
	 * Berechnung des Wertes durch:
	 * 
	 * -zusammenhängende Ketten einer Seite ermitteln + check ob "sicher" (vor Aushungerung)
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

}
