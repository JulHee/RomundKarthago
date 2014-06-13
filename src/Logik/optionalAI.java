package Logik;

import java.util.ArrayList;
import java.util.HashSet;

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
	 * Konzept: Alle möglichen Züge werden bewertet und bekommen einen "Wert" (evtl. zwischen 0 und 1) 
	 * zugewiesen, wobei der höhere Wert größere Erfolgs/Gewinnchancen verspricht. 
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
	/*
	 * Variablen für Zugriffsrechte außerhalb definiert
	 */
	Graph myGraph = mechanik.getMyGraph();
	ArrayList<ArrayList<Knoten>> Rchainz;
	ArrayList<ArrayList<Knoten>> Kchainz;
	

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
	 * jeweils als ArrayList von ArrayList von Knoten ausgegeben
	 *  
	 */
	public void getchainz(){
		ArrayList<ArrayList<Knoten>> Rchainz = new ArrayList<ArrayList<Knoten>>();
		ArrayList<ArrayList<Knoten>> Kchainz = new ArrayList<ArrayList<Knoten>>();
		int zaehl = 0; //sinnfreie zählvariable -> Anzahl der neutralen Knoten (als Test)
		for(Knoten a: myGraph.l_knoten){
			if (a.getSeite() == Seite.Neutral){
				zaehl+=1;
			}else{
				ArrayList<Knoten> chain = new ArrayList<Knoten>();
				Seite site = a.getSeite();
				kette(chain,site,a);
				if(site==Seite.Rom){
					Rchainz.add(chain);
				}else{
					Kchainz.add(chain);
				}
			}
		};
	};
	/**
	 * HilfsFunktion zur Erstellen der einzelnen Ketten als 
	 * ArrayList<Knoten>
	 * 
	 * @param temp Kette, welche es ggf. zu verlängern gilt
	 * @param seite	Fraktion der derzeitigen Kette
	 * @param knot	zu betracchtendes Kettenglied
	 * @return ArrayList<Knoten>
	 */
	//TODO Testen ob Knoten bereits betrachtet (letzter Knoten -> in Nachbarschaft von jetzigem)
	public void kette(ArrayList<Knoten> temp,Seite seite,Knoten knot){
		if(temp.contains(knot)) 
		temp.add(knot);
		HashSet<Knoten> blubb = myGraph.getNachbarschaft(knot);
		for(Knoten x: blubb){
			if(x.getSeite()== seite){
				kette(temp,seite,x);
			}
		};
		
	};
	
}
