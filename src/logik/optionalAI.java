package logik;

import java.util.ArrayList;
import java.util.HashSet;

import core.*;
import core.datacontainers.Knoten;
import core.datacontainers.Seite;
import core.datacontainers.Zug;

/**
 * optionale KI fuer Ki - Turnier
 * @author Jörn Kabuth
 *
 */

// komplette AI noch in Arbeit!


public class optionalAI extends AIPlayer{
	
	Mechanik mechanik = new Mechanik();
	
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
	 * -zusammenhängende Ketten einer Seite ermitteln + check ob "sicher" (vor Aushungerung) TODO Kettenfunktion getchainz testen
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
	public Graph myGraph = mechanik.getMyGraph();
	public ArrayList<ArrayList<Knoten>> Rchainz = new ArrayList<ArrayList<Knoten>>();
	public ArrayList<ArrayList<Knoten>> Kchainz = new ArrayList<ArrayList<Knoten>>();
	


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
		int zaehl = 0; //sinnfreie zählvariable -> Anzahl der neutralen Knoten (als Test)
		for(Knoten a: myGraph.l_knoten){
			if (a.getSeite() == Seite.Neutral){
				zaehl+=1;
			}else{
				Seite site = a.getSeite();
				if(site==Seite.Rom){
					if(used(a,Rchainz)){
					}else{
						ArrayList<Knoten> chain = new ArrayList<Knoten>();
						kette(chain,site,a);
						Rchainz.add(chain);
					}
				}else if(site==Seite.Kathargo){
					if(used(a,Kchainz)){
					}else{
						ArrayList<Knoten> chain = new ArrayList<Knoten>();
						kette(chain,site,a);
						Kchainz.add(chain);
					}
				}
			}
		};
		System.out.println(zaehl);
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
	public void kette(ArrayList<Knoten> temp,Seite seite,Knoten knot){
		if(temp.contains(knot)){
			
		}else{
			temp.add(knot);
			HashSet<Knoten> blubb = myGraph.getNachbarschaft(knot);
			for(Knoten x: blubb){
				if(x.getSeite()== seite){
					kette(temp,seite,x);
				}
			}
		};
	};
	/**
	 * Hilfsfunktion um mehrfache Überprüfung von bereits verwendeten 
	 * Knoten zu vermeiden
	 * 
	 * @param u zu testender Knoten
	 * @param p zu testende ArrayList von ArrayList
	 */
	public boolean used(Knoten u,ArrayList<ArrayList<Knoten>> p){
		for(ArrayList<Knoten> a: p){
			if(a.contains(u)){
				return true;
			}
		};
		return false;
	};
	/**
	 * Funktion zur Ausgabe der Ketten auf der Konsole (testzwecke)
	 */
	public void getRKchainz(){
		System.out.println("Rchain: ");
		for(ArrayList<Knoten> a: Rchainz){
			System.out.println("R: "+Rchainz.indexOf(a)+" ");
			for(Knoten b: a){
				System.out.println(b);
			}
		}
		System.out.println("Kchain: ");
		for(ArrayList<Knoten> c: Kchainz){
			System.out.println("K: "+Kchainz.indexOf(c)+" ");
			for(Knoten d: c){
				System.out.println(d);
			}
		}
	};
}
