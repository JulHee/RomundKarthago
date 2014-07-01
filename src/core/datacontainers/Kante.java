package core.datacontainers;
/**
 * Projekt: Rom und Karthago
 * Author : Julian Heeger, Markus Poell, Christian Bruene, Joern Kabuth
 * Date : 26.04.14
 * Year : 2014
 */

/**
 * Die Klasse Kante beschreibt eine Kante,
 * welche zwischen zwei Knoten eines Graphen aufgespannt wird.
 */

public class Kante {

    public Knoten  punkt1;
    public Knoten  punkt2;

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
