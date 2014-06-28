package core;

import core.datacontainers.*;

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

public class Graph implements Cloneable{

    // Relativer Pfad zur Datei zum lesen einer Beispieldatei
    private String path = "ext/map2.txt";

    // Konstrukte zum speichern der Informationen bzgl. des Graphen
    public HashSet<Knoten> l_knoten = new HashSet<Knoten>();
    public HashSet<Kante> l_kante = new HashSet<Kante>();
    private ArrayList<String> history = new ArrayList<String>();
    private boolean letzterZugAusgesetzt = false;
    private ArrayList<String> maptext = new ArrayList<String>();

    public Graph() {}

    public Graph(String path, HashSet<Knoten> l_knoten, HashSet<Kante> l_kante, ArrayList<String> history, boolean letzterZugAusgesetzt, ArrayList<String> maptext) {
        this.path = path;
        this.l_knoten = l_knoten;
        this.l_kante = l_kante;
        this.history = history;
        this.letzterZugAusgesetzt = letzterZugAusgesetzt;
        this.maptext = maptext;
    }

    /**
     * Ließt eine Datei ein und erstellt daraus einen Graphen.
     *
     * @throws Exception Fehler, falls die Datei nicht den richtlinien entspricht.
     */
    public void read() {
        try {
            reset();
            BufferedReader br = new BufferedReader(new FileReader(getPath()));
            LinkedList<String> datei = new LinkedList<String>();
            String zeile;
            int anzahl_an_Knoten = 0;
            if ((zeile = br.readLine()) != null) {
                anzahl_an_Knoten = Integer.parseInt(zeile);

                while ((zeile = br.readLine()) != null) {
                    maptext.add(zeile);
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
     * @param knotenListe Menge an Knoten
     * @return Nachbarn der Knotenmenge
     */

    public HashSet<Knoten> getNachbarschaft(HashSet<Knoten> knotenListe) {
        HashSet<Knoten> retrn = new HashSet<Knoten>();
        for (Knoten k : knotenListe) {
            for (Knoten temp : getNachbarschaft(k)) {
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

    /**
     * checkNachbarschaft gibt alle Städte zurück, die Punkte einbringen.
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
     * Erzeugt aus einem String eine enumeration Seite
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

    /**
     * Anwenden der Funktion ssuf mit der eigenen Klasse
     *
     * @param z Der Zug
     * @return Der neue Graph nach dem Zug
     */

    public Graph ssuf(Zug z) {
        return ssuf(this, z);
    }

    /**
     * Anwenden eines Zuges auf einen Graphen g
     *
     * @param g Der Graph g auf den z angewendet werden soll
     * @param z Der Zug
     * @return Der neue Graph nachdem der Zug angewendet wurde
     */

    public Graph ssuf(Graph g, Zug z) {
        try {
            Knoten aktKnoten = g.findKnoten(z.getStadt());
            if (aktKnoten == null) {
                return g;
            } else if (aktKnoten.seite == Seite.Neutral) {
                HashSet<Knoten> nachbarn = getNachbarschaft(aktKnoten);
                Seite gegner;

                if (z.getSeite() == Seite.Kathargo) {
                    gegner = Seite.Rom;
                } else {
                    gegner = Seite.Kathargo;
                }

                // Checken ob die Stadt komplett umzingelt ist
                Boolean existiertKeinGegner = false;
                for (Knoten k : nachbarn) {
                    if (k.seite != gegner) existiertKeinGegner = true;
                }
                if (!existiertKeinGegner) {
                    checkAushungern(getEinenBenachbartenGegner(aktKnoten),gegner);
                    return g;
                }
                aktKnoten.setSeite(z.getSeite());                            // Die Seite des Knoten wird gesetzt

                // Prüfen ob andere Stadt dadruch aushungert
                Knoten einNachbarGegner = getEinenBenachbartenGegner(aktKnoten);
                if(einNachbarGegner== null){
                    checkAushungern(aktKnoten, z.getSeite());
                }else {
                    checkAushungern(einNachbarGegner, gegner);
                    checkAushungern(aktKnoten, z.getSeite());
                }
            }
            return g;
        } catch (Exception e) {
            System.out.println(e.toString());
            return g;
        }

    }


    /**
     * Führt den Zug aus und gibt einen Errorcode aus, der weiter geleitet wird
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
        String retrn;
        Zustand retrnZustand = new Zustand(0);
        Zug myzug = new Zug(zug);
        Graph temp = this.clone();
        if (myzug.getStadt() == -1 || myzug.getSeite() != spieler) {
            if (letzterZugAusgesetzt) {
                retrnZustand.setName(this.convertToString());
                retrnZustand.setErrorcode(2);
            } else {
                retrnZustand.setName(this.convertToString());
                retrnZustand.setErrorcode(1);
                letzterZugAusgesetzt = true;
            }

        } else if (history.contains(temp.ssuf(temp,myzug).convertToString())) {
            retrnZustand.setErrorcode(3);
            letzterZugAusgesetzt = true;
            retrnZustand.setName(this.convertToString());
        } else {
            String letzterZug = this.convertToString();
            ssuf(myzug);
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
        return retrnZustand;
    }

    /**
     * Prüft ob ein Knoten aushungert oder nicht
     *
     * @param k Knoten der geprüft werden soll
     * @return Seite die der Knoten nach dem Aushungern hat
     */

    private void checkAushungern(Knoten k, Seite spieler) {
        Seite gegner;
        Boolean kHungertAus = true;
        if (spieler == Seite.Kathargo) {
            gegner = Seite.Rom;
        } else {
            gegner = Seite.Kathargo;
        }
        HashSet<Knoten> nachbarnUmUrsprung = getNachbarschaft(k);

        for (Knoten kn : nachbarnUmUrsprung) {
            if(kn.seite == Seite.Neutral){kHungertAus=false;}
            if (kn.seite == gegner) {
                HashSet<Knoten> alleGegner = getBesetztesGebiet(kn);
                HashSet<Knoten> gebiet = getNachbarschaft(alleGegner);
                boolean neutralGefunden = false;
                for (Knoten kno : gebiet) {
                    if (kno.seite == Seite.Neutral) {
                        neutralGefunden = true;
                    }
                }
                if (!neutralGefunden) {
                    kHungertAus=false;
                    for (Knoten knot : alleGegner) {
                        knot.seite = Seite.Neutral;
                    }
                }
            }
        }
        if(kHungertAus){
            k.setSeite(Seite.Neutral);
        }
    }

    /**
     * Gibt einen benachtbarten Knoten zurück
     * @param aktKnoten
     * @return Knoten . Einen benachtbarten Knoten. Falls keiner existiert null
     */
    public Knoten getEinenBenachbartenGegner(Knoten aktKnoten){
        Seite gegner;
        if (aktKnoten.getSeite() == Seite.Kathargo) {
            gegner = Seite.Rom;
        } else {
            gegner = Seite.Kathargo;
        }

        for(Knoten i : getNachbarschaft(aktKnoten)){
            if(i.getSeite()==gegner)return i;
        }
        return  null;
    }

    /**
     * Soriert ein HashSet<Knoten> nach der ID der Knoten in eine ArrayList<Knoten>
     *
     * @param knotenlist Liste mit den Knoten
     * @return Sortierte Liste mit den Knoten
     */

    public ArrayList<Knoten> toArrayList(HashSet<Knoten> knotenlist) {
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
     * Soriert ein HashSet<Kante> nach der ID des 1.Punktes Knoten in eine ArrayList<Knoten>
     *
     * @param kanteList Liste mit den Kante
     * @return Sortierte Liste mit den Kante
     */

    public ArrayList<Kante> toArrayListKante(HashSet<Kante> kanteList) {
        ArrayList<Kante> retrn = new ArrayList<Kante>();
        for (int i = 0; i < kanteList.size(); i++) {
            for(Kante k :kanteList){
                if(k.getPunkt1().id == i) retrn.add(k);
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

    public void map() {
        for (Knoten k : toArrayList(l_knoten)) {
            System.out.println(k.id + "(" + k.seite.toString() + ")");
        }
    }

    @Override
    public Graph clone(){
        Graph myGraph = null;
        try{
            myGraph =  new Graph(path,erzeugeNeueKnotenListe(l_knoten),erzeugeNeueKanteListe(l_kante),
                    erzeugeNeueHistory(history), letzterZugAusgesetzt,  erzeugeNeueMaptext(maptext));
        }catch ( Exception e ) {e.getStackTrace();}
        return  myGraph;
    }

    public HashSet<Knoten> erzeugeNeueKnotenListe(HashSet<Knoten> alte){
        HashSet<Knoten> neue = new HashSet<Knoten>();
        for(Knoten i : alte){
            neue.add(new Knoten(i.id,i.seite,i.position));
        }
        return neue;
    }

    public HashSet<Kante> erzeugeNeueKanteListe(HashSet<Kante> alte){
        HashSet<Kante> neue = new HashSet<Kante>();
        for(Kante i : alte){
            neue.add(new Kante(i.getPunkt1(),i.getPunkt2()));
        }
        return neue;
    }

    public ArrayList<String> erzeugeNeueHistory(ArrayList<String> alte){
        ArrayList<String> neue = new ArrayList<String>();
        for(String i : alte){
            neue.add(i);
        }
        return neue;
    }

    public ArrayList<String> erzeugeNeueMaptext(ArrayList<String> alte){
        ArrayList<String> neue = new ArrayList<String>();
        for(String i : alte){
            neue.add(i);
        }
        return neue;
    }


    public ArrayList<String> getMaptext() {
        return maptext;
    }
}
