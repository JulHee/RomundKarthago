package core.datacontainers;
/**
 * Projekt: Rom und Karthago
 * Author : Julian Heeger, Markus Poell, Christian Bruene, Joern Kabuth
 * Date : 26.04.14
 * Year : 2014
 */

/**
 * Die Enumeration beschriebt die (Belagerungs-) Zustaende,
 * welche eine Stadt (Knoten) im gegebenen Spiel annehmen kann.
 */

public enum Seite {
    Rom{
        public String toString(){
            return "R";
        }
    }, Kathargo{
        public String toString(){
            return "C";
        }
    }, Neutral {
        public String toString() {
            return "N";
        }
    }
}
