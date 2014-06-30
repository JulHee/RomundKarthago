package network;

import java.util.ArrayList;

import logik.Mechanik;
import core.datacontainers.Seite;

public abstract class Server {

    Seite mySeite = Seite.Kathargo;
    Integer port;
    Mechanik myMechanik;

    abstract public void run() throws Exception;

    public Boolean checkmap(ArrayList<String> map) {
	try {
	    int anzahl_an_Knoten = Integer.parseInt(map.get(0));
	    int gefundene_Knoten = 0;
	    map.remove(0);
	    for (String s : map) {
		if (s.startsWith("V")) {
		    String[] split = s.split(" ");
		    gefundene_Knoten += 1;
		} else if (s.startsWith("E")) {
		    String[] split = s.split(" ");
		    if (myMechanik.getMyGraph().findKnoten(
			    Integer.parseInt(split[1])) == null
			    | myMechanik.getMyGraph().findKnoten(
				    Integer.parseInt(split[2])) == null) {
			throw new Exception();
		    }
		} else {
		    throw new Exception();
		}
	    }
	    if (!(anzahl_an_Knoten == gefundene_Knoten)) {
		throw new Exception();
	    }
	} catch (Exception ex) {
	    return false;
	}
	return true;
    }
}
