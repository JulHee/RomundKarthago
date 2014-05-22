package Graph;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * Projekt: Rom und Karthago
 * Author : Julian Heeger, Markus Poell, Christian Bruene, Joern Kabuth
 * Date : 26.04.14
 * Year : 2014
 */

public class Graph {


	// Auswahlliste bei unterschiedlichen Computern
//     private String path = "/Volumes/Macintosh HD/User/peng0in/Developer/Java/RomUndKathargo/ext/map2.txt";
//    private String path = "C:\\Users\\Acer\\Documents\\Uni\\romundkathargoswp\\ext\\map2.txt";
    private String path = "ext/map2.txt";


	public HashSet<Knoten> l_knoten = new HashSet<Knoten>();
	public HashSet<Kante> l_kante = new HashSet<Kante>();
	public int anzahl_an_Knoten;

	public void read() throws Exception {
		try {
			BufferedReader br = new BufferedReader(new FileReader(getPath()));
			LinkedList<String> datei = new LinkedList<String>();
			String zeile;
			if ((zeile = br.readLine()) != null) {
				anzahl_an_Knoten = Integer.parseInt(zeile);

				while ((zeile = br.readLine()) != null) {
					if (zeile.startsWith("V")) {
						String[] split = zeile.split(" ");

						int split1 = Integer.parseInt(split[1]);
						Seite seite = getSeite(split[2]);
						Position split2 = new Position(Integer.parseInt(split[3]), Integer.parseInt(split[4]));
						Knoten temp_knoten = new Knoten(split1, seite, split2);
						l_knoten.add(temp_knoten);
					} else if (zeile.startsWith("E")) {
						String[] split = zeile.split(" ");
						Knoten von = findKnoten(Integer.parseInt(split[1]));
						Knoten nach = findKnoten(Integer.parseInt(split[2]));
						l_kante.add(new Kante(von, nach));
					} else {
						throw new Exception("Fehler in der Struktur der Datei:" + getPath());
					}
				}
			}
		} catch (IOException ex) {
			System.out.printf("Fehler beim lesen einer Zeile der Datei");
		}
	}

	public HashSet<Knoten> toHashSet() {
		HashSet<Knoten> retrn = new HashSet<Knoten>();
		for (Knoten k : l_knoten) {
			retrn.add(k);
		}
		return retrn;
	}

	public HashSet<Knoten> getNachbarschaft(Knoten knoten) {
		HashSet<Knoten> retrn = new HashSet<Knoten>();
		for (Kante k : l_kante) {
			if (k.getPunkt1() == knoten) {
				retrn.add(k.getPunkt2());
			}
			if (k.getPunkt2() == knoten) {
				retrn.add(k.getPunkt1());
			}
		}
		return retrn;
	}

	public HashSet<Knoten> getNachbarschaft(HashSet<Knoten> knotenListe) {
		HashSet<Knoten> retrn = new HashSet<Knoten>();
		for (Knoten knot : knotenListe) {
			for (Knoten temp : getNachbarschaft(knot)) {
				retrn.add(temp);
			}
		}
		retrn.removeAll(knotenListe);
		return retrn;
	}

    public HashSet<Knoten> getBesetztesGebiet(Knoten knoten) {

        return rekGBG(knoten, new HashSet<Knoten>());
    }

    private HashSet<Knoten> rekGBG (Knoten rekKnoten,HashSet retrn){

        retrn.add(rekKnoten);
        for(Knoten i:getNachbarschaft(rekKnoten)){
            if(i.seite == rekKnoten.seite && !(retrn.contains(i))){
                rekGBG(i,retrn);
            }
        }

        return retrn;
    }


	/**
	 * Sollte eig funktionieren, wenn du getBesetztesGebiet() funktioniert.
	 * Die Funktion holt sich, gegeben dem Fall, dass die Stadt neutral ist, das "neutral besetzte" Gebiet.
	 * Von diesem wird dann die Nachbarschaft ermittelt und diese darauf geprueft, ob alle zur eigenen
	 * Besetzungsmacht gehoeren. Gehoert auch nur eine einzelne Stadt dem Gegner, koennen keine Punkte fuer
	 * diese neutralen Staedte gezaehlt werden.
	 * @param spieler
	 * @return
	 */
	public int besetztePunkteStandFuer(Seite spieler){
		HashSet<Knoten> punkteStaedte = new HashSet<Knoten>();
		for (Knoten i : l_knoten) {
			if (i.seite == spieler) {
				punkteStaedte.add(i);
			} else if (i.seite == Seite.Neutral){
				punkteStaedte.addAll(checkNachbarschaft(i, spieler));
			}
		}
		return punkteStaedte.size();
	}

	private HashSet<Knoten> checkNachbarschaft(Knoten knot, Seite spieler){
		HashSet<Knoten> umzingeltesGebiet = getBesetztesGebiet(knot);
		HashSet<Knoten> umzingelndeNachbarn = getNachbarschaft(umzingeltesGebiet);
		for(Knoten i : umzingelndeNachbarn){
			if(i.seite != spieler){
				return new HashSet<Knoten>();
			}
		}
		return umzingeltesGebiet;
	}

	public Knoten findKnoten(int id) {
		for (Knoten i : l_knoten) {
			if (i.id == id) {
				return i;
			}
		}
		System.out.println("Error: Keine Stadt passend zu der ID:" + id + " gefunden.");
		return null;
	}

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

	public void ausgeben() {
		for (Knoten i : l_knoten) {
			System.out.println(i.toString());
		}
		for (Kante i : l_kante) {
			System.out.println(i.toString());
		}
	}
    public String convertToString(){
        String temp = "";
        for (Knoten k : l_knoten){
            temp = temp + k.seite.toString();
        }
        return temp;
    }

    public String convertToString2(){
        String temp = "";
        for(int i=0;i<l_knoten.size();i++) {
            for (Knoten k : l_knoten) {
                if(k.id==i){temp = temp + k.seite.toString();}
            }
        }
        return temp;
    }

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

    public Graph spielSituatonUeberfuehren(Graph g,Zug z) {
        try {
            if (g.findKnoten(z.getStadt()).seite == Seite.Neutral) {
                g.findKnoten(z.getStadt()).seite = z.getSeite();

            }
            return g;
        } catch (Exception e) {
            System.out.println(e);
            return g;
        }

    }
}
