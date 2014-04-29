/**
 * Projekt: Graph-Visualisieren-Java
 * Author : Julian Heeger
 * Date : 26.04.14
 * Year : 2014
 */
public class Knoten {
    public int  id;
    public Seite seite;
    Position position;

    public Knoten(int id, Seite seite,Position position){
        this.id = id;
        this.seite = seite;
        this.position = position;
    }

    @Override
    public String toString() {
        return "Knoten{" +
                "id=" + id +
                ", seite=" + seite +
                ", position=" + position.toString() +
                '}';
    }
}
