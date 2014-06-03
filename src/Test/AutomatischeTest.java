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
	/*
	 * Arrays mit path Angaben für die zu testenden Eingaben/Ausgaben 
	 */
	/*ArrayList<String> inarray = new ArrayList<String>();
	ArrayList<String> outarray = new ArrayList<String>();
	public void arraystopfen(){
		inarray.add("ext/TerminalS1.txt");
		inarray.add("ext/TerminalS2.txt");
		inarray.add("ext/TerminalS3.txt");
		outarray.add("ext/output.01.txt");
		outarray.add("ext/output.02.txt");
		outarray.add("ext/output.03.txt");
	};
	 */
	public void autoTest() throws Exception{
		Mechanik myMechanik = new Mechanik();
        Seite aktuellerSpieler = Seite.Rom;
		lesen("ext/TerminalS2.txt","ext/output.02.txt");
		for (int i=0; i <= sollinput.size()-1;i++){
			String graphoutput = myMechanik.run(sollinput.get(i),aktuellerSpieler);
			if (!graphoutput.equals(solloutput.get(i))) {
				throw new Exception("Unterschiede in den Strings gefunden : \n Gefunden: "+ graphoutput + "\n Sollte: "+solloutput.get(i));
			}else{
				System.out.println("Richtig: "+graphoutput);
			}
            if (aktuellerSpieler == Seite.Rom) {
                aktuellerSpieler = Seite.Kathargo;
            } else {
                aktuellerSpieler = Seite.Rom;
            }
		}
	}

}
