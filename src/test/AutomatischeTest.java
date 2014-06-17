package test;

import core.Seite;
import logik.Mechanik;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Projekt: RomUndKathargo
 * Author : Julian Heeger
 * Date : 02.06.14
 * Year : 2014
 */
public class AutomatischeTest {

    ArrayList<String> sollinput = new ArrayList<String>();
    ArrayList<String> solloutput = new ArrayList<String>();

    /*
 * Arrays mit path Angaben für die zu testenden Eingaben/Ausgaben
 */
    ArrayList<String> inarray = new ArrayList<String>();
    ArrayList<String> outarray = new ArrayList<String>();

    public void arraystopfen() {
        inarray.add("ext/TerminalS1.txt");
        inarray.add("ext/TerminalS2.txt");
        inarray.add("ext/TerminalS3.txt");
        outarray.add("ext/output.01.txt");
        outarray.add("ext/output.02.txt");
        outarray.add("ext/output.03.txt");
    }

    ;

    public AutomatischeTest() throws Exception {
        arraystopfen();
        autoTest(inarray, outarray);
    }


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


    public void autoTest(String input, String output) throws Exception {
        Mechanik myMechanik = new Mechanik();
        Seite aktuellerSpieler = Seite.Rom;
        sollinput = new ArrayList<String>();
        solloutput = new ArrayList<String>();
        lesen(input, output);
        for (int i = 0; i <= sollinput.size() - 1; i++) {
            String graphoutput = myMechanik.auswerten(sollinput.get(i), aktuellerSpieler);
            if (!graphoutput.equals(solloutput.get(i))) {
                throw new Exception("Unterschiede in den Strings gefunden : \n Gefunden: " + graphoutput + "\n Sollte: " + solloutput.get(i)
                +"\nin: "+ input + "\nund: "+ output);
            } else {
                System.out.println("Richtig: " + graphoutput);
            }
            if (aktuellerSpieler == Seite.Rom) {
                aktuellerSpieler = Seite.Kathargo;
            } else {
                aktuellerSpieler = Seite.Rom;
            }
        }
    }

    public void autoTest(ArrayList<String> input, ArrayList<String> output) throws Exception {
        if (input.size() != output.size()) {
            throw new Exception("Die Anzahl ist unterschiedlich");
        }
        for (int i = 0; i <= input.size() - 1; i++) {
            autoTest(input.get(i), output.get(i));
        }
    }


}
