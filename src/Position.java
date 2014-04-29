/**
 * Projekt: Graph-Visualisieren-Java
 * Author : Julian Heeger
 * Date : 29.04.14
 * Year : 2014
 */
public class Position {
    final int x;
    final int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Paar{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
