package core;

import core.datacontainers.*;
import exceptions.DateiStrukturException;
import exceptions.KnotenException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * Projekt: Rom und Karthago
 * Author : Julian Heeger, Markus Poell, Christian Bruene, Joern Kabuth
 * Date : 26.04.14
 * Year : 2014
 */


/**
 * Diese Klasse verwaltet alle fuer das laufende Spiel notwendigen Informationen
 * bezueglich der Map, auf der gespielt wird, und den Spielverlauf.
 */


public class Graph implements Cloneable {

	// Relativer Pfad zur Datei zum lesen einer Beispieldatei
	private String path = "ext/map2.txt";

    // Konstrukte zum speichern der Informationen bzgl. des Graphen
	public HashSet<Knoten> l_knoten = new HashSet<>();
	public HashSet<Kante> l_kante = new HashSet<>();
	private ArrayList<String> history = new ArrayList<>();
	private boolean letzterZugAusgesetzt = false;
	private ArrayList<String> maptext = new ArrayList<>();

	public Graph() {
	}

	public Graph(String path, HashSet<Knoten> l_knoten, HashSet<Kante> l_kante,
	             ArrayList<String> history, boolean letzterZugAusgesetzt,
	             ArrayList<String> maptext) {
		this.path = path;
		this.l_knoten = l_knoten;
		this.l_kante = l_kante;
		this.history = history;
		this.letzterZugAusgesetzt = letzterZugAusgesetzt;
		this.maptext = maptext;
	}

	/**
	 * Gibt an, an welchem Ort die Textdatei für den Graphen liegt
	 *
	 * @return Speicherort
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Verändert den Path und ließt die Map ein
	 *
	 * @param path Pfad zur datei
	 */
	public void setPath(String path)throws Exception{
		this.path = path;
		this.read();
	}

	/**
	 * Übergabe einer ArrayList<String> als Map. Dannach einlesen der
	 * übergebenen Map
	 *
	 * @param map Die Map als ArrayList<String>
	 */
	public void setPath(ArrayList<String> map) {
		this.path = "Die Map wurde per TCP-IP gesendet !!!!!";
		try {
			this.read(map);
		} catch (Exception e) {
			System.out.println("Fehler beim Lesen der Map");
		}
	}

    public HashSet<Knoten> getL_knoten() {
        return l_knoten;
    }


	/**
	 * Erzeugt aus einem String eine enumeration Seite
	 *
	 * @param x
	 *            Die Seite als String
	 * @return Die Seite
	 * @throws Exception
	 *             Ungültiger String
	 */
	public Seite getSeite(String x) throws Exception {
		Seite resu;
		if (x.equals("N")) {
			resu = Seite.Neutral;
		} else {
			if (x.equals("R")) {
				resu = Seite.Rom;
			} else {
				if (x.equals("C")) {
					resu = Seite.Kathargo;
				} else {
					throw new Exception("Keine geeigneten Besetzer gefunden");
				}
			}
		}
		return resu;
	}

	/**
	 * Sucht den entsprechend zur id passenden Knoten
	 *
	 * @param id
	 *            ID des gesuchten Knoten
	 * @return i oder null Knoten
	 */
	public Knoten findKnoten(int id) throws KnotenException{
		for (Knoten i : l_knoten) {
			if (i.id == id) {
				return i;
			}
		}
		throw new KnotenException("Keine Stadt passend zu der ID:" + id +"gefunden.");
	}

	/**
	 * Konvertierung des Graphen in eine einfache Stringdarstellung.
	 * Bsp.:  tmp = "CNNR"
	 * @return tmp String
	 */
	public String convertToString() {
		String temp = "";
		for (int i = 0; i < l_knoten.size(); i++) {
			for (Knoten k : l_knoten) {
				if (k.id == i) {
					temp = temp + k.seite.toString();
				}
			}
		}
		return temp;
	}

	/**
	 * Soriert ein HashSet<Knoten> nach der ID der Knoten in eine
	 * ArrayList<Knoten>
	 *
	 * @param knotenlist
	 *            Liste mit den Knoten
	 * @return Sortierte Liste mit den Knoten
	 */
	public ArrayList<Knoten> toArrayList(HashSet<Knoten> knotenlist) {
		ArrayList<Knoten> retrn = new ArrayList<>();
		for (int i = 0; i < knotenlist.size(); i++) {
			for (Knoten k : knotenlist) {
				if (k.id == i) {
					retrn.add(k);
				}
			}
		}
		return retrn;
	}

	/**
	 * Soriert ein HashSet<Kante> nach der ID des 1.Punktes Knoten in eine
	 * ArrayList<Knoten>
	 *
	 * @param kanteList
	 *            Liste mit den Kante
	 * @return Sortierte Liste mit den Kante
	 */
	public ArrayList<Kante> toArrayListKante(HashSet<Kante> kanteList) {
		ArrayList<Kante> retrn = new ArrayList<>();
		for (int i = 0; i < kanteList.size(); i++) {
			for (Kante k : kanteList) {
				if (k.getPunkt1().id == i)
					retrn.add(k);
			}

		}
		return retrn;
	}

	/**
	 * Methode die den Inhalt der Klasse als Terminalausgabe zeigt
	 */
	public void ausgeben() {
		for (Knoten i : l_knoten) {
			System.out.println(i.toString());
		}
		for (Kante i : l_kante) {
			System.out.println(i.toString());
		}
	}

	public void map() {
		for (Knoten k : toArrayList(l_knoten)) {
			System.out.println(k.id + "(" + k.seite.toString() + ")");
		}
	}

	@Override
	public Graph clone() {
		Graph myGraph = null;
		try {
			myGraph = new Graph(path,
					erzeugeNeueKnotenListe(l_knoten),
					erzeugeNeueKanteListe(l_kante),
					erzeugeNeueHistory(history), letzterZugAusgesetzt,
					erzeugeNeueMaptext(maptext));
		} catch (Exception e) {
			e.getStackTrace();
		}
		return myGraph;
	}

	public HashSet<Knoten> erzeugeNeueKnotenListe(HashSet<Knoten> alte) {
		HashSet<Knoten> neue = new HashSet<>();
		for (Knoten i : alte) {
			neue.add(new Knoten(i.id, i.seite, i.position));
		}
		return neue;
	}

	public HashSet<Kante> erzeugeNeueKanteListe(HashSet<Kante> alte) {
		HashSet<Kante> neue = new HashSet<>();
		for (Kante i : alte) {
			neue.add(new Kante(i.getPunkt1(), i.getPunkt2()));
		}
		return neue;
	}

	public ArrayList<String> erzeugeNeueHistory(ArrayList<String> alte) {
		ArrayList<String> neue = new ArrayList<>();
		for (String i : alte) {
			neue.add(i);
		}
		return neue;
	}

	public ArrayList<String> erzeugeNeueMaptext(ArrayList<String> alte) {
		ArrayList<String> neue = new ArrayList<>();
		for (String i : alte) {
			neue.add(i);
		}
		return neue;
	}

	/**
	 * Setz alle Werte einer vorherigen Spiels zurück
	 */
	private void reset() {
		l_knoten.clear();
		l_kante.clear();
		history.clear();
		letzterZugAusgesetzt = false;
		maptext.clear();
	}

	/**
	 * Ließt eine Datei ein und erstellt daraus einen Graphen.
	 *
	 * @throws Exception
	 *             Fehler, falls die Datei nicht den richtlinien entspricht.
	 */
    //TODO wirft read nun auch KnotenException und DateiStrukturExceptions , da die ja von Exception erben.
	public void read() throws Exception {
			reset();
			BufferedReader br = new BufferedReader(new FileReader(getPath()));
			String zeile;
			int anzahl_an_Knoten = 0;
			int gefundene_Knoten = 0;
			if ((zeile = br.readLine()) != null) {
				anzahl_an_Knoten = Integer.parseInt(zeile);
				maptext.add(String.valueOf(anzahl_an_Knoten));
				while ((zeile = br.readLine()) != null) {
					maptext.add(zeile);
					if (zeile.startsWith("V")) {
						String[] split = zeile.split(" ");

						int split1 = Integer.parseInt(split[1]);
						Seite seite = getSeite(split[2]);
						Position split2 = new Position(
								Integer.parseInt(split[3]),
								Integer.parseInt(split[4]));
						Knoten temp_knoten = new Knoten(split1, seite, split2);
						l_knoten.add(temp_knoten);
						gefundene_Knoten += 1;
					} else if (zeile.startsWith("E")) {
						String[] split = zeile.split(" ");
						Knoten von = findKnoten(Integer.parseInt(split[1]));
						Knoten nach = findKnoten(Integer.parseInt(split[2]));
						l_kante.add(new Kante(von, nach));
					} else {
						throw new DateiStrukturException("Fehler in der Struktur der Datei:"
								+ getPath());
					}
				}
			}
			if (!(anzahl_an_Knoten == gefundene_Knoten)) {
				reset();
				throw new IOException("Die Anzahl der Knoten stimmt nicht mit der überlieferten Zahl überein!");
			}

	}

	/**
	 * Erstellt aus der uebergebenen ArrayList einen Graphen
	 * @param map    Die Map als ArrayList<String>
	 * @throws Exception  DateiStrukturException, falls die angegebene Datei fehlerhaft formatiert ist
     *                    IOException, falls das Einlesen fehlerhaft war und die Anz der Knoten vorher und nachher nicht gleich sind
	 */
	public void read(ArrayList<String> map) throws Exception {
		reset();
		int anzahl_an_Knoten = Integer.parseInt(map.get(0));
		int gefundene_Knoten = 0;
		map.remove(0);
		for (String s : map) {
			if (s.startsWith("V")) {
				String[] split = s.split(" ");

				int split1 = Integer.parseInt(split[1]);
				Seite seite = getSeite(split[2]);
				Position split2 = new Position(Integer.parseInt(split[3]),
						Integer.parseInt(split[4]));
				Knoten temp_knoten = new Knoten(split1, seite, split2);
				l_knoten.add(temp_knoten);
				gefundene_Knoten += 1;
			} else if (s.startsWith("E")) {
				String[] split = s.split(" ");
				Knoten von = findKnoten(Integer.parseInt(split[1]));
				Knoten nach = findKnoten(Integer.parseInt(split[2]));
				l_kante.add(new Kante(von, nach));
			} else {
				throw new DateiStrukturException("Fehler in der Struktur der Datei:"
						+ getPath());
			}
		}
		if (!(anzahl_an_Knoten == gefundene_Knoten)) {
			reset();
			throw new IOException(
					"Die Anzahl der Knoten stimmt nicht mit der überlieferten Zahl überein!");
		}
	}

	/**
	 * Berechnet zu einem Knoten alle Nachbarknoten
	 *
	 * @param knoten
	 *            Knoten zu dem die Nachbarschaft berechnet werden soll
	 * @return Menge an Knoten
	 */
	public HashSet<Knoten> getNachbarschaft(Knoten knoten) {
		HashSet<Knoten> retrn = new HashSet<>();
		for (Kante k : this.l_kante) {
			if (k.getPunkt1().equals(knoten)) {
				retrn.add(k.getPunkt2());
			}
			if (k.getPunkt2().equals(knoten)) {
				retrn.add(k.getPunkt1());
			}
		}
		return retrn;
	}

	/**
	 * Berechnet zu einer Menge an Knoten die Nachbarschaft
	 *
	 * @param knotenListe
	 *            Menge an Knoten
	 * @return Nachbarn der Knotenmenge
	 */
	public HashSet<Knoten> getNachbarschaft(HashSet<Knoten> knotenListe) {
		HashSet<Knoten> retrn = new HashSet<>();
		for (Knoten k : knotenListe) {
			for (Knoten temp : getNachbarschaft(k)) {
				retrn.add(temp);
			}
		}
		retrn.removeAll(knotenListe);
		return retrn;
	}

	/**
	 * Berechnet alle verbuendeten Knoten, welche in direkter Linie mit dem Ausgangsknoten verbunden sind.
	 *
	 * @param knoten  Der Knoten von dem aus das besetzte Gebiet bestimmt werden soll
	 * @return Das HashSet mit dem besetzten Gebiet
	 */
	public HashSet<Knoten> getBesetztesGebiet(Knoten knoten) {

        return rekGBG(knoten, new HashSet<Knoten>());
	}

	/**
	 * rekursive Hilfsfunktion zu getBesetztesGebiet()
	 *
	 * @param rekKnoten  Stadt die geprüft wird
	 * @param retrn   Akumulator für die Rekursion
	 * @return retrn Das HashSet mit dem besetzten Gebiet
	 */
	private HashSet<Knoten> rekGBG(Knoten rekKnoten, HashSet retrn) {
		retrn.add(rekKnoten);
		for (Knoten i : getNachbarschaft(rekKnoten)) {
			if (i.seite == rekKnoten.seite && !(retrn.contains(i))) {
				rekGBG(i, retrn);
			}
		}
		return retrn;
	}

	/**
	 * Prüft ob ein Knoten aushungert oder nicht
	 *
	 * @param k
	 *            Knoten der geprüft werden soll
	 * @return Seite die der Knoten nach dem Aushungern hat
	 */
	private void checkAushungern(Knoten k, Seite spieler) throws KnotenException{
		Boolean kHungertAus = true;
		k = findKnoten(k.id); // setzte k auf den richtigen knoten aus lKnoten
		/*
		 * Seite gegner; // Gegner wird ermittelt
		 * if (spieler == Seite.Kathargo) {
		 * gegner = Seite.Rom;
		 * } else {
		 * gegner = Seite.Kathargo;
		 * }
		 */
		HashSet<Knoten> nachbarnUmUrsprung = getNachbarschaft(k);
		for (Knoten kn : nachbarnUmUrsprung) {
			Knoten lKnotenKn = findKnoten(kn.id); // ermittle über kn den richtigen Knoten aus lKnoten
			if (lKnotenKn.seite == Seite.Neutral) {
				kHungertAus = false;
			}
			if (lKnotenKn.seite == spieler) {
				HashSet<Knoten> alleMeins = getBesetztesGebiet(lKnotenKn);
				HashSet<Knoten> meineNachbarn = getNachbarschaft(alleMeins);
				boolean neutralGefunden = false;
				for (Knoten kno : meineNachbarn) {
					if (kno.seite == Seite.Neutral) {
						neutralGefunden = true;
					}
				}
				if (!neutralGefunden) {
					kHungertAus = false;
					for (Knoten knot : alleMeins) {
						knot.setSeite(Seite.Neutral);
					}
				}
			}
		}
		if (kHungertAus) {
			k.setSeite(Seite.Neutral);
		}
	}

	/**
	 *  Checkt, ob der uebergebene Knoten aufgrund der aktuellen Spielsituation aushungert.
	 * @param k
	 */
	private void checkAushungernOhneGegner(Knoten k) {
		Boolean kHungertAus = true;
		HashSet<Knoten> besetztesGebiet = getBesetztesGebiet(k);
		for (Knoten i : getNachbarschaft(besetztesGebiet))
			if (i.seite == Seite.Neutral) {
				kHungertAus = false;
			}
		if (kHungertAus) {
			k.setSeite(Seite.Neutral);
		}
	}

	/**
	 * Gibt einen benachtbarten gegnerischen Knoten zurück
	 *
	 * @param aktKnoten
	 * @return Knoten . Einen benachtbarten Knoten. Falls keiner existiert null
	 */
	public Knoten getEinenBenachbartenGegner(Knoten aktKnoten) throws KnotenException{
		Seite gegner;
		if (aktKnoten.getSeite() == Seite.Kathargo) {
			gegner = Seite.Rom;
		} else {
			gegner = Seite.Kathargo;
		}

		for (Knoten i : getNachbarschaft(aktKnoten)) {
			if (i.getSeite() == gegner)
				return i;
		}
		throw new KnotenException("Es gibt keinen benachbarten Gegner");
	}

	public ArrayList<String> getMaptext() {
		return maptext;
	}

	/**
	 * Analysiert (und fuehrt ggf. aus) einen Zug und gibt einen Errorcode aus, der weiter geleitet wird
	 * Errorcode = 0: Alles in Ordnung
	 * Errorcode = 1: Zug wurde ausgesetzt
	 * Errorcode = 2: Beende Spiel
	 * Errorcode = 3: Stellung wiederholt
	 *
	 * @param zug
	 * @param spieler
	 * @return
	 */
	public Zustand run(String zug, Seite spieler) {
        Zustand retrnZustand = new Zustand(0);
        try {
            Zug myzug = new Zug(zug);
            Graph temp = this.clone();
            if (myzug.getStadt() == -1 || myzug.getSeite() != spieler) {
                retrnZustand.setName(this.convertToString());
                if (letzterZugAusgesetzt) {
                    retrnZustand.setErrorcode(2);
                } else {
                    retrnZustand.setErrorcode(1);
                    letzterZugAusgesetzt = true;
                }

            } else if (history.contains(temp.ssuf(temp, myzug).convertToString())) {
                retrnZustand.setName(this.convertToString());
                retrnZustand.setErrorcode(3);
                letzterZugAusgesetzt = true;
            } else {
                String letzterZug = this.convertToString();
                this.ssuf(myzug);
                if (letzterZug.equals(this.convertToString())) {
                    if (letzterZugAusgesetzt) {
                        retrnZustand.setErrorcode(2);
                    }
                    retrnZustand.setErrorcode(1);
                    letzterZugAusgesetzt = true;
                }
                retrnZustand.setName(this.convertToString());
            }
            history.add(retrnZustand.getName());

        }catch (KnotenException kE){
            System.out.println(kE.getMessage());}
        finally {
            return retrnZustand;
        }
    }

	/**
	 * Anwenden der Funktion ssuf mit der eigenen Klasse
	 *
	 * @param z
	 *            Der Zug
	 * @return Der neue Graph nach dem Zug
	 */
	public Graph ssuf(Zug z)throws KnotenException{
		return ssuf(this, z);
	}

	/**
	 * Anwenden eines Zuges auf einen Graphen g
	 *
	 * @param g
	 *            Der Graph g auf den z angewendet werden soll
	 * @param z
	 *            Der Zug
	 * @return Der neue Graph nachdem der Zug angewendet wurde
	 */
	public Graph ssuf(Graph g, Zug z) throws KnotenException{

			Knoten aktKnoten = g.findKnoten(z.getStadt());
			if (aktKnoten == null) {
				return g;
			}
			if (aktKnoten.seite == Seite.Neutral) { // wenn der aktKnoten schon
				// besetzt ist C oder R,
				// word einfach g zurück
				// gegeben
				aktKnoten.seite = z.getSeite(); // Die Seite des Knoten wird gesetzt
				HashSet<Knoten> nachbarn = getNachbarschaft(aktKnoten);

				Seite gegner; // setze Gegner
				if (z.getSeite() == Seite.Kathargo) {
					gegner = Seite.Rom;
				} else {
					gegner = Seite.Kathargo;
				}

				Boolean existiertKeinGegner = true; // Checken ob die Stadt an einen oder mehrer Gegner grenzt
				for (Knoten k : nachbarn) {
					if (k.seite == gegner)
						existiertKeinGegner = false;
				}

				if (existiertKeinGegner) {
					checkAushungernOhneGegner(aktKnoten);
					return g;
				}
				HashSet<Knoten> nachbarGegner = new HashSet<Knoten>(); // findet die benachbarten Gegner heraus
				for (Knoten i : getNachbarschaft(aktKnoten)) {
					if (i.getSeite() == gegner)
						nachbarGegner.add(i);
				}
				for (Knoten i : nachbarGegner) {
					checkAushungern(i, gegner); // prüft für alle benachbarten Gegner ob sie aushungern
				}
				checkAushungern(aktKnoten, aktKnoten.getSeite()); // prüft für sich selbst ob man aushungert
			}
			return g;


	}

	/**
	 * Sollte eig funktionieren, wenn du getBesetztesGebiet() funktioniert.
	 * Die Funktion holt sich, gegeben dem Fall, dass die Stadt neutral ist, das
	 * "neutral besetzte" Gebiet.
	 * Von diesem wird dann die Nachbarschaft ermittelt und diese darauf
	 * geprueft, ob alle zur eigenen
	 * Besetzungsmacht gehoeren. Gehoert auch nur eine einzelne Stadt dem
	 * Gegner, koennen keine Punkte fuer
	 * diese neutralen Staedte gezaehlt werden.
	 *
	 * @param spieler
	 * @return Punktestand
	 */
	public int besetztePunkteStandFuer(Seite spieler) {
		HashSet<Knoten> punkteStaedte = new HashSet<Knoten>();
		for (Knoten i : l_knoten) {
			if (i.seite == spieler) {
				punkteStaedte.add(i);
			} else if (i.seite == Seite.Neutral) {
				punkteStaedte.addAll(checkNachbarschaft(i, spieler));
			}
		}
		return punkteStaedte.size();
	}

	/**
	 * checkNachbarschaft gibt alle neutralen Städte zurück, die Punkte fuer den entsprechenden Spieler einbringen.
	 *
	 * @param knot
	 * @param spieler
	 * @return ein HashSet von Knoten
	 */
	private HashSet<Knoten> checkNachbarschaft(Knoten knot, Seite spieler) {
		HashSet<Knoten> umzingeltesGebiet = getBesetztesGebiet(knot);
		HashSet<Knoten> umzingelndeNachbarn = getNachbarschaft(umzingeltesGebiet);
		for (Knoten i : umzingelndeNachbarn) {
			if (i.seite != spieler) {
				return new HashSet<Knoten>();
			}
		}
		return umzingeltesGebiet;
	}

}
