package test.core;

import core.datacontainers.Kante;
import core.datacontainers.Zug;
import logik.Mechanik;
import org.junit.Test;

import core.*;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Projekt: Rom und Karthago
 * Author : Julian Heeger, Markus Poell, Christian Bruene, Joern Kabuth
 * Date : 26.04.14
 * Year : 2014
 */
public class KantenTest {
    @Test
    public void kanteSortierTestTest()throws Exception{
        Mechanik mech = new Mechanik("ext/Gameboard.txt");
        Graph myGraph = mech.getMyGraph();
        String erg = "";
        for(Kante k : myGraph.toArrayListKante(myGraph.l_kante)){
            erg = erg + k.getPunkt1().id+ "--"+k.getPunkt2().id+"; ";
        }
        assertEquals("0--1; 1--2; 2--3; ",erg);
    }

    @Test
    public void vergleichTest(){
        Mechanik mech = new Mechanik("ext/Gameboard.txt");
        Graph myGraph = mech.getMyGraph();
        String erg ="";
        for(Kante k : myGraph.toArrayListKante(myGraph.l_kante)){
            for(Kante j : myGraph.toArrayListKante(myGraph.l_kante)) {
                if (k.getPunkt2().equals(j.getPunkt1())) erg+= k.getPunkt2().id+" ist gleich "+j.getPunkt1().id+";";
            }
        }
        assertEquals("1 ist gleich 1;2 ist gleich 2;",erg);
    }

    @Test
    public void vergleichTest2(){
        Mechanik mech = new Mechanik("ext/Gameboard.txt");
        Graph myGraph = mech.getMyGraph();
        String erg ="";
        for(Kante k : myGraph.toArrayListKante(myGraph.l_kante)){
            for(Kante j : myGraph.toArrayListKante(myGraph.l_kante)) {
                if (k.getPunkt2()==(j.getPunkt1())) erg+= k.getPunkt2().id+" ist gleich "+j.getPunkt1().id+";";
            }
        }
        assertEquals("1 ist gleich 1;2 ist gleich 2;",erg);
    }
}
