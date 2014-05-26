package Main;

import Graph.*;


/**
 * Projekt: Rom und Karthago
 * Author : Julian Heeger, Markus Poell, Christian Bruene, Joern Kabuth
 * Date : 26.04.14
 * Year : 2014
 */
public class Run {

	public static void main(String[] args) throws Exception{

		Graph graph = new Graph();

        graph.read();
        /*
        graph.ausgeben();
        Zug zug = new Zug("C 1");
        Zug zug_org = new Zug(Seite.Kathargo,2);
        System.out.println(zug.toString());
        System.out.println(zug_org.toString());

        System.out.println(graph.getBesetztesGebiet(graph.findKnoten(6)));
        System.out.println(graph.getBesetztesGebiet(graph.findKnoten(9)));

	    System.out.println("Punkte fuer Rom:      " + graph.besetztePunkteStandFuer(Seite.Rom));
	    System.out.println("Punkte fuer Karthago: " + graph.besetztePunkteStandFuer(Seite.Kathargo));
        System.out.println(graph.convertToString());
		 */

		/* ------------------------------------- */
		String Zugpath = "ext/Zug1.txt";
		String Graphpath = "ext/map2.txt";
		Transition(Graphpath,Zugpath);
	}
	public static void Transition (String Graphpath,String Move) throws Exception{
		Graph graph = new Graph();
		Zug zug = Zug.readZugFile(Move);
		graph.setPath(Graphpath);
		graph.read();
		graph.spielSituatonUeberfuehren(graph, zug).ausgeben();
	};
}
