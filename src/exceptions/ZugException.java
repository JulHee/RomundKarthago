package exceptions;

/**
 * Projekt: Rom und Karthago
 * Author : Julian Heeger, Markus Poell, Christian Bruene, Joern Kabuth
 * Date : 01.07.14
 * Year : 2014
 */
//TODO soll geworfen werden, wenn ein falscher Zug gemacht wird
public class ZugException extends Exception {
    public ZugException(){}

    public ZugException(String s){
        super(s);
    }
}
