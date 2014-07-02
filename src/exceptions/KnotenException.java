package exceptions;

/**
 * Projekt: Rom und Karthago
 * Author : Julian Heeger, Markus Poell, Christian Bruene, Joern Kabuth
 * Date : 01.07.14
 * Year : 2014
 */
//TODO soll geworfen werden, wenn ein Knoten nicht gefunden werden kann.
public class KnotenException extends Exception {
    public KnotenException(){}

    public KnotenException(String s){
        super(s);
    }
}
