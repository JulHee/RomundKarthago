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
        graph.ausgeben();
	    //System.out.println(graph.punkteStandFuer(Seite.Kathargo));
	    //System.out.println(graph.besetztePunkteStandFuer(Seite.Kathargo));
        System.out.println(graph.getBesetztesGebiet(graph.findKnoten(6)));
        //    System.out.println(graph.recPunkteStandFuer(Seite.Kathargo));
	    //System.out.println(graph.punkteStandFuer(Seite.Rom));
	    //System.out.println(graph.besetztePunkteStandFuer(Seite.Rom));
        System.out.println(graph.getBesetztesGebiet(graph.findKnoten(9)));
        //    System.out.println(graph.recPunkteStandFuer(Seite.Rom));
    }
}
