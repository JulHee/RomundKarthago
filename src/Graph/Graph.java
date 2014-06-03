package Graph;

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

public class Graph {

    // Relativer Pfad zur Datei zum lesen einer Beispieldatei
    private String path = "ext/map2.txt";

    // Konstrukte zum speichern der Informationen bzgl. des Graphen
    public HashSet<Knoten> l_knoten = new HashSet<Knoten>();
    public HashSet<Kante> l_kante = new HashSet<Kante>();

    /**
     * Ließt eine Datei ein und erstellt daraus einen Graphen.
     *
     * @throws Exception Fehler, falls die Datei nicht den richtlinien entspricht.
     */
    public void read() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(getPath()));
            LinkedList<String> datei = new LinkedList<String>();
            String zeile;
            int anzahl_an_Knoten = 0;
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
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    /**
     * Konvertiert die Knoten in ein Set
     *
     * @return Alle Knoten des Graphen
     */

    public HashSet<Knoten> toHashSet() {
        return l_knoten;
    }

    /**
     * Berechnet zu einem Knoten alle Nachbarknoten
     *
     * @param knoten Knoten zu dem die Nachbarschaft berechnet werden soll
     * @return Menge an Knoten
     */

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

    /**
     * Berechnet zu einer Menge an Knoten die Nachbarschaft
     *
     * @param knotenListe Menge an Knoten
     * @return Nachbarn der Knotenmenge
     */

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
     * Sollte eig funktionieren, wenn du getBesetztesGebiet() funktioniert.
     * Die Funktion holt sich, gegeben dem Fall, dass die Stadt neutral ist, das "neutral besetzte" Gebiet.
     * Von diesem wird dann die Nachbarschaft ermittelt und diese darauf geprueft, ob alle zur eigenen
     * Besetzungsmacht gehoeren. Gehoert auch nur eine einzelne Stadt dem Gegner, koennen keine Punkte fuer
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

    /**
     * Generiert aus der ID des Knoten die Klasse Knoten.
     *
     * @param id ID des gesuchten Knoten
     * @return Den Knoten
     */

    public Knoten findKnoten(int id) {
        for (Knoten i : l_knoten) {
            if (i.id == id) {
                return i;
            }
        }
        System.out.println("Error: Keine Stadt passend zu der ID:" + id + " gefunden.");
        return null;
    }

    /**
     * Erzeugt aus einem String eine enumeratione Seite
     *
     * @param x Die Seite als String
     * @return Die Seite
     * @throws Exception Ungültiger String
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

    public Graph ssuf(Graph g, Zug z) {
        try {
            Knoten aktKnoten = g.findKnoten(z.getStadt());
            if (aktKnoten == null) {
                return g;
            } else if (aktKnoten.seite == Seite.Neutral) {
                HashSet<Knoten> nachbarn = getNachbarschaft(aktKnoten);
                Seite gegner;

                if (z.getSeite() == Seite.Kathargo){
                    gegner = Seite.Rom;
                } else {
                    gegner = Seite.Kathargo;
                }

          /*      // Checken ob die Stadt komplett umzingelt ist
                Boolean existiertkeinGegner = false;
                for (Knoten k : nachbarn) {
                    if (k.seite != gegner ) existiertkeinGegner = true;
                }
                if (!existiertkeinGegner) {
                    return g;
                }*/
                aktKnoten.setSeite(z.getSeite());

                // Prüfen ob andere Stadt dadruch aushungert

                //TODO Aushungern der Städte: Ab wann sind Städte oder Gruppen von Städten von der Außenwelt abgeschnitten ?

               checkAushungern(aktKnoten,z.getSeite());

                }

            return g;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return g;
        }

    }

    /**
     * Prüft ob ein Knoten aushungert oder nicht
     * @param k Knoten der geprüft werden soll
     * @return Seite die der Knoten nach dem Aushungern hat
     */

    private void checkAushungern(Knoten k, Seite spieler) {
        Seite gegner;
        if (spieler == Seite.Kathargo){
            gegner = Seite.Rom;
        } else {
            gegner = Seite.Kathargo;
        }
        HashSet<Knoten> nachbarnumursprung = getNachbarschaft(k);

        for (Knoten kn : nachbarnumursprung) {
            if (kn.seite == gegner) {
                HashSet<Knoten> alleeigenen = getBesetztesGebiet(kn);
                HashSet<Knoten> gebiet = getNachbarschaft(alleeigenen);
                boolean neutralgefunden = false;
                for (Knoten kno : gebiet) {
                    if (kno.seite == Seite.Neutral) {
                        neutralgefunden = true;
                    }
                }
                if (neutralgefunden == false) {
                    for (Knoten knot : alleeigenen) {
                        knot.seite = Seite.Neutral;
                    }
                }
            }
        }




        /*
        } else {
            HashSet<Knoten> neueNachbarn = getNachbarschaft(k);
            neueNachbarn.remove(k);
            boolean istGegner = true;
            boolean neutralgefunden = false;

            Seite gegner;

            if (k.seite == Seite.Kathargo){
                gegner = Seite.Rom;
            } else {
                gegner = Seite.Kathargo;
            }

            for (Knoten kn : neueNachbarn) {
              //  if (kn.seite != gegner) istGegner = false;
                if (kn.seite == Seite.Neutral) neutralgefunden = true;
            }
            if (neutralgefunden == false) {
                return Seite.Neutral;
            } else {
                return k.seite;
            }
        }
        */
    }

    /**
     * Soriert ein HashSet<Knoten> nach der ID der Knoten in eine ArrayList<Knoten>
     * @param knotenlist Liste mit den Knoten
     * @return Sortierte Liste mit den Knoten
     */

    private ArrayList<Knoten> toLinkedList(HashSet<Knoten> knotenlist){
        ArrayList<Knoten> retrn = new ArrayList<Knoten>();
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
     * Gibt an, an welchem Ort die Textdatei für den Graphen liegt
     *
     * @return Speicherort
     */
    public String getPath() {
        return path;
    }

    /**
     * Verändert den Path
     *
     * @param path Pfad zur datei
     */

    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Erzeugt einen String aus allen Knoten wobei nur der Besetzer angezeigt wird
     *
     * @return String mit allen Besetzern
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
    public void map(){
        for (Knoten k : l_knoten){
            System.out.println(k.id+"("+k.seite.toString()+")");
        }
    }
}
