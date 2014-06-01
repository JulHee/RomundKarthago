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
        graph.setPath("ext/0_3.map.01");
        graph.read();

        System.out.println(graph.convertToString());
        Graph newGraph = graph.ssuf(graph,new Zug("R 2"));
        System.out.println(newGraph.convertToString());
	}
	public static void Transition (String Graphpath,String Move) throws Exception{
		Graph graph = new Graph();
		Zug zug = Zug.readZugFile(Move);
		graph.setPath(Graphpath);
		graph.read();
		graph.ssuf(graph, zug).ausgeben();
	};
}
