package core.datacontainers;
/**
 * Projekt: Rom und Karthago
 * Author : Julian Heeger, Markus Poell, Christian Bruene, Joern Kabuth
 * Date : 26.04.14
 * Year : 2014
 */

/**
 * Diese Klasse beschreibt eine Position im zweidimensionalen Raum.
 * Sie hat folglich eine 'x' und eine 'y' Koordinate.
 */
public class Position
{
	public final int x;
	public final int y;

	public Position (int x,
					 int y)
	{
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString ()
	{
		return "Paar{" +
			   "x=" + x +
			   ", y=" + y +
			   '}';
	}
}
