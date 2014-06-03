package Test;

import Graph.Seite;
import Logik.Mechanik;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Projekt: RomUndKathargo
 * Author : Julian Heeger
 * Date : 02.06.14
 * Year : 2014
 */
public class AutomatischeTest {

    ArrayList<String> sollinput = new ArrayList<String>();
    ArrayList<String> solloutput = new ArrayList<String>();

    public void lesen(String input, String output) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(input));
            String zeile;
            while ((zeile = br.readLine()) != null) {
                sollinput.add(zeile);
            }
            BufferedReader brr = new BufferedReader(new FileReader(output));
            while ((zeile = brr.readLine()) != null) {
                solloutput.add(zeile);
            }
        } catch (IOException ex) {
            System.out.printf("Fehler beim lesen einer Zeile der Datei");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void autoTest() throws Exception{
        Mechanik myMechanik = new Mechanik();
        Seite aktuellerSpieler = Seite.Rom;
        lesen("ext/TerminalS3.txt","ext/output.03.txt");
        for (int i=0; i <= sollinput.size()-1;i++){
           String graphoutput = myMechanik.run(sollinput.get(i),aktuellerSpieler);
           if (!graphoutput.equals(solloutput.get(i))) {
               throw new Exception("\nUnterschiede in den Strings gefunden : \nGefunden: "+ graphoutput + "\nSollte: "+solloutput.get(i));
           }
            if (aktuellerSpieler == Seite.Rom) {
                aktuellerSpieler = Seite.Kathargo;
            } else {
                aktuellerSpieler = Seite.Rom;
            }
        }
    }
}
