package logik.ai;

import java.util.ArrayList;
import java.util.HashSet;

import core.datacontainers.Knoten;
import core.datacontainers.Seite;
import core.datacontainers.Zug;
import exceptions.KeinBesetzerException;
import exceptions.ZugException;
import logik.Mechanik;

/**
 * optionale KI fuer Ki - Turnier
 * 
 * @author Jörn Kabuth
 *
 */

// komplette AI noch in Arbeit!
//TODO checkTarget bei 2 neutralen Nachbarn ?!
public class Joernson extends AIPlayer {
	
	public Mechanik mechanik;
	public Seite meineSeite;
	/*
	 * Variablen für Zugriffsrechte außerhalb definiert
	 */
	public ArrayList<ArrayList<Knoten>> Rchainz = new ArrayList<ArrayList<Knoten>>();
	public ArrayList<ArrayList<Knoten>> Kchainz = new ArrayList<ArrayList<Knoten>>();
	public Knoten Target;
	
	public Joernson(Seite s, Mechanik mechanik) {
		this.meineSeite = s;
		this.mechanik = mechanik;
	};

    public Joernson(Seite s){
        this.meineSeite = s;
    }

    //TODO at the end
    //TODO exception? selber aushungen lassen?
	@Override
	public Zug nextZug() {
       // System.out.println("Joernson anfang");
        Zug retrn;
		if(mechanik.getLetzterZugAusgesetzt() || mechanik.getMyGraph().getSpielZustandWiederholt()){
          //  System.out.println("GewinnerPfad");
            int me = mechanik.getMyGraph().getPunkteStandFuer(meineSeite);
			int enemy = mechanik.getMyGraph().getPunkteStandFuer(notmysite());
			if(me>enemy){
                try {
                    retrn = new Zug(meineSeite + " X");
                    return retrn;
                }catch (KeinBesetzerException e){
                    System.out.println(e.getMessage());}
                catch (ZugException e){
                    System.out.println(e.getMessage());}
			}
		}
		if (checkTarget()){
         //   System.out.println("CheckTarget");
            retrn = new Zug(meineSeite,Target.id);
			return retrn;
		}
      //  System.out.println("saveMe");
        retrn = saveme();
		return retrn;
	}

	/*
	 * Konzept: 
	 * 0) Gegner ausgesetzt -> Pkt checken -> AI > Gegner -> ausetzen -> Ende  ! Done
	 * 1) Targets zum Aushungern des Gegners sofort besetzen DONE
	 * 2) Strategie TODO Strategie entwickeln
	 * 3) Sicherer Zug. Knoten mit max neutral Nachbarn -> wenn Anzahl = 0 aussetzen ! DONE
	 * 
	 * 
	 */

	


	/*
	 * Hilfsfunktion für GegnerSeite
	 */
	public Seite notmysite(){
		Seite retrn;
		if(meineSeite == Seite.Rom){
			retrn = Seite.Kathargo;
		}else {
			retrn = Seite.Rom;
		}
		return retrn;
	}

	/*
	 * Funktion um einen sicheren Zug zu generieren
	 */
	public Zug saveme(){
		Zug save = null;
		HashSet<Knoten> mapKnoten = mechanik.getMyGraph().l_knoten;
		ArrayList<Knoten> neuKnoten = new ArrayList<Knoten>();
		for(Knoten a : mapKnoten){
			if(a.getSeite()== Seite.Neutral){
				neuKnoten.add(a);
			}
		}
		Knoten kn = null;
		Integer knint = 0;
		for(Knoten b : neuKnoten){
			Integer tempint = 0;
			HashSet<Knoten> neighbours = mechanik.getMyGraph().getNachbarschaft(b);
			for(Knoten c : neighbours){
				if(c.getSeite() == Seite.Neutral){
					tempint+=1;
				}
			}
			if(tempint>=knint  ){ //&& checkHistory(b)
				kn = b;
				knint = tempint;
			}
		}
		save = new Zug(meineSeite,kn.id);
		return save;

	}


	/**
	 * Finden eines direkten Besetzungspunktes, wenn dadurch ein direktes Aushungern des Gegners
	 * bewirkt wird (aggressives Ziehen!)  
	 */
	public Boolean checkTarget(){
        getchainz();
		if(this.meineSeite == Seite.Rom){
			if(checkt(Kchainz)){
				return true;};
		}
		else{if(checkt(Rchainz)){
			return true;};
		}
		return false;
	}
	/**
	 * Hilfsfunktion für checkTarget
	 * Berechnet die Nachbarschaft einer GegnerKette und zählt 
	 * die neutralen Nachbar. Ist diese Zahl = 1 -> besetzung des
	 * Nachbarn um aushungern einzuleiten
	 * @param listo
	 * @return 
	 */
	public Boolean checkt(ArrayList<ArrayList<Knoten>> listo){
		for (ArrayList<Knoten> a : listo){
			int zaehl = 0;
			ArrayList<Knoten> neuNeigh = new ArrayList<Knoten>();
			for(Knoten b : a){
				HashSet<Knoten> neighbourhood = this.mechanik.getMyGraph().getNachbarschaft(b);
				for (Knoten c : neighbourhood){
					if(c.seite == Seite.Neutral){
						zaehl += 1;
						neuNeigh.add(c);
					}
				}
			}
			if(zaehl == 1 && checkHistory(neuNeigh.get(0))){
				Target = neuNeigh.get(0);
				return true;
			}
		}
		return false;

	}

	/**
	 * Bestimmung der Ketten zusammenhängender Städte einer Fraktion
	 * jeweils als ArrayList von ArrayList von Knoten ausgegeben
	 * 
	 */
	public void getchainz() {
		Rchainz.clear();
		Kchainz.clear();

		int zaehl = 0; // sinnfreie zählvariable -> Anzahl der neutralen Knoten
		for (Knoten a : this.mechanik.getMyGraph().l_knoten) {
			Seite site = a.getSeite();
			if (site == Seite.Neutral) {
				zaehl += 1;
			} else {
				if (site == Seite.Rom) {
					if (used(a, Rchainz)) {
					} else {
						ArrayList<Knoten> chain = new ArrayList<Knoten>();
						kette(chain, site, a);
						Rchainz.add(chain);
					}
				} else if (site == Seite.Kathargo) {
					if (used(a, Kchainz)) {
					} else {
						ArrayList<Knoten> chain = new ArrayList<Knoten>();
						kette(chain, site, a);
						Kchainz.add(chain);
					}
				}
			}
		};
		/*
		System.out.println("Anzahl neutraler Städte: "+zaehl);
		getRKchainz();
		 */
	};

	/**
	 * HilfsFunktion zur Erstellen der einzelnen Ketten als
	 * ArrayList<Knoten>
	 * 
	 * @param temp
	 *            Kette, welche es ggf. zu verlängern gilt
	 * @param seite
	 *            Fraktion der derzeitigen Kette
	 * @param knot
	 *            zu betracchtendes Kettenglied
	 * @return ArrayList<Knoten>
	 */
	public void kette(ArrayList<Knoten> temp, Seite seite, Knoten knot) {
		if (checkme(temp,knot)) {

		} else {
			temp.add(knot);
			HashSet<Knoten> blubb = this.mechanik.getMyGraph().getNachbarschaft(knot);
			for (Knoten x : blubb) {
				if (x.getSeite() == seite) {
					kette(temp, seite, x);
				}
			}
		}
		;
	};
	/**
	 * Hilfsfunktion für kette (ersetzt fehlerhaftes contains)
	 * @return 
	 */
	public boolean checkme(ArrayList<Knoten> temp,Knoten k){
		for(Knoten z:temp){
			if(z.equals(k)){
				return true;
			}
		}
		return false;

	}
	/**
	 * Hilfsfunktion um mehrfache Überprüfung von bereits verwendeten
	 * Knoten zu vermeiden
	 * 
	 * @param u
	 *            zu testender Knoten
	 * @param p
	 *            zu testende ArrayList von ArrayList
	 */
	public boolean used(Knoten u, ArrayList<ArrayList<Knoten>> p) {
		for (ArrayList<Knoten> a : p) {
			if (checkme(a, u)) {
				return true;
			}
		}
		return false;

	};

	/**
	 * Funktion zur Ausgabe der Ketten auf der Konsole (testzwecke)
	 */
	public void getRKchainz() {
		System.out.println("Rchain: ");
		for (ArrayList<Knoten> a : Rchainz) {
			System.out.println("R: " + Rchainz.indexOf(a) + " ");
			for (Knoten b : a) {
				System.out.println(b);
			}
		}
		System.out.println("Kchain: ");
		for (ArrayList<Knoten> c : Kchainz) {
			System.out.println("K: " + Kchainz.indexOf(c) + " ");
			for (Knoten d : c) {
				System.out.println(d);
			}
		}
	}

   public Boolean checkHistory(Knoten b){
       try{
       return !(mechanik.getMyGraph().getHistory().contains(mechanik.getMyGraph().ssuf(new Zug(meineSeite.toString()+" "+b.id)).convertToString()));
       }catch (Exception e){
           System.out.println(e.getStackTrace());}
       return  false;
   }

	@Override
	public String toString ()
	{
		return "Joernson";
	}

	public void setMechanik (Mechanik mechanik)
	{
		this.mechanik = mechanik;
	}

	public Mechanik getMechanik ()
	{
		return mechanik;
	}
}
