package logik.ai;

import core.datacontainers.Seite;
import core.datacontainers.Zug;
import exceptions.KeinBesetzerException;
import exceptions.ZugException;
import logik.ai.AIPlayer;


/**
 * Projekt: Rom und Karthago
 * Author : Julian Heeger, Markus Poell, Christian Bruene, Joern Kabuth
 * Date : 10.06.14
 * Year : 2014
 */

public class WaspAI  extends AIPlayer {

    public WaspAI(Seite s) {
        meineSeite = s;
    }

    public Zug nextZug() {
        try {
           Zug erg = new Zug(meineSeite.toString() + " 0");
            return erg;
        }catch (KeinBesetzerException e){
            System.out.println(e.getMessage());}
        catch (ZugException e){
            System.out.println(e.getMessage());}
        return null;
    }
}
