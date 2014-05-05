import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Projekt: Rom und Karthago
 * Author : Julian Heeger, Markus Poell, Christian Bruene, Joern Kabuth
 * Date : 26.04.14
 * Year : 2014
 */

public class Graph {


// Auswahlliste bei unterschiedlichen Computern
    private String path = "/Volumes/Macintosh HD/User/peng0in/Developer/Java/RomUndKathargo/ext/map2.txt";
//    private String path = "C:\\Users\\Acer\\Documents\\Uni\\romundkathargoswp\\ext\\map2.txt";
//    private String path = "/Volumes/Data/University/Uni Marburg/4. Semester/IntelliJ_workspace/RomUndKarthagoSWP/ext/map.txt";



    public LinkedList<Knoten> l_knoten = new LinkedList<Knoten>();
    public LinkedList<Kante> l_kante = new LinkedList<Kante>();
    public int anzahl_an_Knoten;

    public void read() throws Exception {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
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
                        throw new Exception("Fehler in der Struktur der Datei:" + path);
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

    public HashSet<Knoten> getNachbarschaft(LinkedList<Knoten> knotenListe) {
        HashSet<Knoten> retrn = new HashSet<Knoten>();
        for (Knoten knot : knotenListe) {
            for (Knoten temp : getNachbarschaft(knot)) {
                retrn.add(temp);
            }
        }

        for (Knoten checkKnoten : retrn) {
            if (knotenListe.contains(checkKnoten)) {
                retrn.remove(checkKnoten);
            }
        }
        return retrn;
    }

    public HashSet<Knoten> getBesetztesGebiet(Knoten knoten) {
        return rekGBG(knoten, null);
    }

    public HashSet<Knoten> rekGBG(Knoten rekKnoten, Knoten rekVorherigerKnoten) {
        HashSet<Knoten> retrn = new HashSet<Knoten>();
        String tmpSeiteStr = rekKnoten.seite.toString();
        HashSet<Knoten> tmpSet = getNachbarschaft(rekKnoten);
        if (rekVorherigerKnoten != null) {
            tmpSet.remove(rekVorherigerKnoten);
        }
        for (Knoten i : tmpSet) {
            if (i.seite.toString() == tmpSeiteStr) {
                retrn.add(i);
                for (Knoten j : rekGBG(i, rekKnoten)) {
                    retrn.add(j);
                }
            }
        }
        return retrn;
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


}
