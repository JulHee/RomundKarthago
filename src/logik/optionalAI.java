package logik;

import java.util.ArrayList;
import java.util.HashSet;

import core.Graph;
import core.datacontainers.Knoten;
import core.datacontainers.Seite;
import core.datacontainers.Zug;

/**
 * optionale KI fuer Ki - Turnier
 * 
 * @author Jörn Kabuth
 *
 */

// komplette AI noch in Arbeit!

public class optionalAI extends AIPlayer {

	/* TODO
	 * Muss entfernt/umgangen werden da client und server jeweils mechaniken besitzen 
	 * und die Map variabel bleiben muss!!!
	 */
	String gamepath = "ext/Gameboard.txt";
	Mechanik mechanik = new Mechanik(gamepath);


	public Seite meineSeite;

	public optionalAI(Seite s) {
		meineSeite = s;
	};

	//TODO at the end
	@Override
	public Zug nextZug() {
		if (checktarget()){
			Zug tzug = new Zug(meineSeite,Target.id);
			return tzug;
		}else{
			
		}
		return null;
	}

	/*
	 * Konzept: 
	 * 0) Gegner ausgesetzt -> Pkt checken -> AI > Gegner -> ausetzen -> Ende  TODO
	 * 1) Targets zum Aushungern des Gegners sofort besetzen DONE
	 * 2) Strategie TODO
	 * 3) Sicherer Zug. Knoten mit max neutral Nachbarn -> wenn Anzahl = 0 aussetzen ! TODO
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
	public Knoten Target;

	//TODO wenn Konzept fertig
	/**
	 * Berechnung, wie "gut" ein Zug zu bewerten ist.
	 * 
	 */
	public double Zvalue() {
		double value = 0.0;
		return value;

	};
	/**
	 * Finden eines direkten Besetzungspunktes, wenn dadurch ein direktes Aushungern des Gegners
	 * bewirkt wird (aggressives Ziehen!)  
	 */
	public Boolean checktarget(){
		if(this.meineSeite == Seite.Rom){
			if(checkt(Kchainz)){
				return true;};
		}
		else{if(checkt(Rchainz)){
			return true;};
		}
		return false;
	}
	/**
	 * Hilfsfunktion für checktarget
	 * Berechnet die Nachbarschaft einer GegnerKette und zählt 
	 * die neutralen Nachbar. Ist diese Zahl = 1 -> besetzung des
	 * Nachbarn um aushungern einzuleiten
	 * @param listo
	 * @return 
	 */
	public Boolean checkt(ArrayList<ArrayList<Knoten>> listo){
		for (ArrayList<Knoten> a : listo){
			int zaehl = 0;
			ArrayList<Knoten> neuNeigh = new ArrayList<Knoten>();
			for(Knoten b : a){
				HashSet<Knoten> neighbourhood = myGraph.getNachbarschaft(b);
				for (Knoten c : neighbourhood){
					if(c.seite == Seite.Neutral){
						zaehl += 1;
						neuNeigh.add(c);
					}
				}
			}
			if(zaehl == 1){
				Target = neuNeigh.get(0);
				return true;
			}
		}
		return false;

	}

	/**
	 * Bestimmung der Ketten zusammenhängender Städte einer Fraktion
	 * jeweils als ArrayList von ArrayList von Knoten ausgegeben
	 * 
	 */
	public void getchainz() {
		Rchainz.clear();
		Kchainz.clear();

		int zaehl = 0; // sinnfreie zählvariable -> Anzahl der neutralen Knoten
		for (Knoten a : myGraph.l_knoten) {
			Seite site = a.getSeite();
			if (site == Seite.Neutral) {
				zaehl += 1;
			} else {
				if (site == Seite.Rom) {
					if (used(a, Rchainz)) {
					} else {
						ArrayList<Knoten> chain = new ArrayList<Knoten>();
						kette(chain, site, a);
						Rchainz.add(chain);
					}
				} else if (site == Seite.Kathargo) {
					if (used(a, Kchainz)) {
					} else {
						ArrayList<Knoten> chain = new ArrayList<Knoten>();
						kette(chain, site, a);
						Kchainz.add(chain);
					}
				}
			}
		};
		/*
		System.out.println("Anzahl neutraler Städte: "+zaehl);
		getRKchainz();
		 */
	};

	/**
	 * HilfsFunktion zur Erstellen der einzelnen Ketten als
	 * ArrayList<Knoten>
	 * 
	 * @param temp
	 *            Kette, welche es ggf. zu verlängern gilt
	 * @param seite
	 *            Fraktion der derzeitigen Kette
	 * @param knot
	 *            zu betracchtendes Kettenglied
	 * @return ArrayList<Knoten>
	 */
	public void kette(ArrayList<Knoten> temp, Seite seite, Knoten knot) {
		if (checkme(temp,knot)) {

		} else {
			temp.add(knot);
			HashSet<Knoten> blubb = myGraph.getNachbarschaft(knot);
			for (Knoten x : blubb) {
				if (x.getSeite() == seite) {
					kette(temp, seite, x);
				}
			}
		}
		;
	};
	/**
	 * Hilfsfunktion für kette (ersetzt fehlerhaftes contains)
	 * @return 
	 */
	public boolean checkme(ArrayList<Knoten> temp,Knoten k){
		for(Knoten z:temp){
			if(z.equals(k)){
				return true;
			}
		}
		return false;

	}
	/**
	 * Hilfsfunktion um mehrfache Überprüfung von bereits verwendeten
	 * Knoten zu vermeiden
	 * 
	 * @param u
	 *            zu testender Knoten
	 * @param p
	 *            zu testende ArrayList von ArrayList
	 */
	public boolean used(Knoten u, ArrayList<ArrayList<Knoten>> p) {
		for (ArrayList<Knoten> a : p) {
			if (checkme(a, u)) {
				return true;
			}
		}
		return false;

	};

	/**
	 * Funktion zur Ausgabe der Ketten auf der Konsole (testzwecke)
	 */
	public void getRKchainz() {
		System.out.println("Rchain: ");
		for (ArrayList<Knoten> a : Rchainz) {
			System.out.println("R: " + Rchainz.indexOf(a) + " ");
			for (Knoten b : a) {
				System.out.println(b);
			}
		}
		System.out.println("Kchain: ");
		for (ArrayList<Knoten> c : Kchainz) {
			System.out.println("K: " + Kchainz.indexOf(c) + " ");
			for (Knoten d : c) {
				System.out.println(d);
			}
		}
	};
}
