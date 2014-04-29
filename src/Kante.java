/**
 * Projekt: Graph-Visualisieren-Java
 * Author : Julian Heeger
 * Date : 26.04.14
 * Year : 2014
 */


public class Kante {

    Knoten  punkt1;
    Knoten  punkt2;

    public Kante(Knoten punkt1, Knoten punkt2) {
        this.punkt1 = punkt1;
        this.punkt2 = punkt2;
    }

    public Knoten getPunkt2() {

        return punkt2;
    }

    public Knoten getPunkt1() {

        return punkt1;
    }

    @Override
    public String toString() {
        return "Kante{" +
                "Stadt=" + punkt1.toString() +
                ", Stadt=" + punkt2.toString() +
                '}';
    }
}
