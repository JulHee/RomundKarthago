package core;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Projekt: RomUndKathargo
 * Author : Julian Heeger
 * Date : 13.05.14
 * Year : 2014
 */
public class Zug {
    private Seite seite;
    private int stadt;

    public Zug(Seite seite, int stadt) {
        this.seite = seite;
        this.stadt = stadt;
    }

    public Zug(String zug){
        String[] temp = zug.split(" ");
        if (temp.length > 1){
            try{
                this.seite = readSeite(temp[0]);
                if (temp[1].equals("X")){
                    this.stadt = -1;
                } else {
                    this.stadt = Integer.parseInt(temp[1]);
                }
                if (this.seite == null){
                    throw new ExceptionInInitializerError("Fehler beim erstellen des Zuges, ein Wert ist nicht gesetzt");
                }
            } catch (Exception ex){
                System.out.println(ex.getMessage());
            }

        }
    }

    /**
     * Erzeugt aus einem String eine enumeration Seite
     *
     * @param x Die Seite als String
     * @return Die Seite
     * @throws Exception Ungültiger String
     */

    private Seite readSeite(String x) throws Exception {
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
     * Liest aus einer Datei einen Zug
     * @param path Pfad zur Datei
     * @return Einen Zug
     * @throws Exception Falls die Datei nicht der norm enspricht
     */
    public static Zug readZugFile(String path) throws Exception{
    	BufferedReader reado = new BufferedReader(new FileReader(path));
    	String ZugZeile;
    	if ((ZugZeile = reado.readLine()) != null){
    		Zug zug = new Zug(ZugZeile);
    		return zug;
    	} else throw new Exception("File not correct");
    };

    public Seite getSeite() {
        return seite;
    }

    public int getStadt() {
        return stadt;
    }

    @Override
    public String toString() {
        return "Zug{" +
                "seite=" + seite +
                ", stadt=" + stadt +
                '}';
    }

    /**
     * Erzeugt das String Format wie es in z.B. Graph gefordert wird.
     * @return Format des Zuges als abkürzung
     */

    public String toFormat(){
        return seite.toString() + " " + stadt;
    }
    
}
