package core.datacontainers;
/**
 * Projekt: Rom und Karthago
 * Author : Julian Heeger, Markus Poell, Christian Bruene, Joern Kabuth
 * Date : 26.04.14
 * Year : 2014
 */


/**
 * Diese Klasse beschreibt die Knotenpunkte eines Graphen,
 * welche durch Kanten (siehe Klasse 'Kante') verbunden sind.
 *
 */

public class Knoten {
    public int  id;
    public Seite seite;
    public Position position;

    public Knoten(int id, Seite seite,Position position){
        this.id = id;
        this.seite = seite;
        this.position = position;
    }

    public void setSeite(Seite seite) {
        this.seite = seite;
    }

    public Seite getSeite() { return seite;}

	@Override
	public String toString() {
		return "Knoten{" +
				"id=" + id +
				", seite=" + seite +
				", position=" + position.toString() +
				'}';
	}

    public Boolean equals(Knoten k){
        Boolean erg = false;
        if(k.id == this.id)erg=true;
        return erg;
    }
}
