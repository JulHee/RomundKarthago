package Test;

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
        lesen("ext/TerminalS2.txt","ext/output.02.txt");
        for (int i=0; i <= sollinput.size()-1;i++){
           String graphoutput = myMechanik.run(sollinput.get(i));
           if (!graphoutput.equals(solloutput.get(i))) {
               throw new Exception("Unterschiede in den Strings gefunden : \n Gefunden: "+ graphoutput + "\n Sollte: "+solloutput.get(i));
           }
        }
    }
}
