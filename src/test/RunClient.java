import core.datacontainers.Seite;
import logik.Mechanik;
import logik.ai.Killjoy;
import logik.ai.Sloth;

/**
 * Projekt : RomUndKathargo
 * Author  : Julian Heeger
 * Date    : 02.07.14
 * Year    : 2014
 */
public class RunClient {

    public static void main(String[] args) {
        Mechanik myMechanik = new Mechanik("ext/Gameboard.txt");
        Killjoy myKilljoy = new Killjoy(Seite.Rom,myMechanik);
        Sloth mySloth = new Sloth(Seite.Rom,myMechanik);

        myMechanik.game("127.0.0.1",9998);
    }
}
