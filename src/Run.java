/**
 * Projekt: Graph-Visualisieren-Java
 * Author : Julian Heeger
 * Date : 26.04.14
 * Year : 2014
 */
public class Run {

    public static void main(String[] args) throws Exception{

        Graph graph = new Graph();
        graph.read();
        graph.ausgeben();
    }
}
