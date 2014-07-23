package logik.ai;

import core.*;
import core.datacontainers.Knoten;
import core.datacontainers.Seite;
import core.datacontainers.Zug;
import exceptions.KeinBesetzerException;
import exceptions.ZugException;
import logik.Mechanik;

/**
 * Projekt : RomUndKathargo
 * Author  : Julian Heeger
 * Date    : 10.06.14
 * Year    : 2014
 */
public class Killjoy extends AIPlayer
{
	public Killjoy (Seite s,
					Mechanik m)
	{
		this.mechanik = m;
		meineSeite = s;
	}

	public Zug nextZug ()
	{
		return findZug();
	}

	/**
	 * Berechnet den Zug mit dem minimalen Punkten.
	 *
	 * @return Zug
	 */
	private Zug findZug ()
	{
		Integer minPunkte = - 1;
		Zug retrn = null;
		Graph myGraph = mechanik.getMyGraph();
		try
		{
			for ( Knoten k : myGraph.l_knoten )
			{
				Zug temp = new Zug( meineSeite + " " + k.id );
				if ( minPunkte > getPunkte( temp, mechanik ) )
				{
					retrn = temp;
				}
				return retrn;
			}
		} catch ( KeinBesetzerException e )
		{
			System.out.println( e.getMessage() );
		} catch ( ZugException e )
		{
			System.out.println( e.getMessage() );
		}
		return null;    //kann nicht vor kommen, da falls der try-catch block ausgeführt wird ein return enthalten ist,
		// und falls nicht eine Exception das Programm beendet
	}

	/**
	 * Berechnet aus einem angeblichen Zug die  Punkte
	 *
	 * @param z        Zug
	 * @param mechanik Mechanik auf der gespielt wird
	 *
	 * @return Anzahl an Punkten nach dem Zug
	 */

	private Integer getPunkte (Zug z,
							   Mechanik mechanik)
	{
		Mechanik mymechanik = mechanik.clone();
		mymechanik.auswerten( z.toFormat(), z.getSeite() );
		return mymechanik.getMyGraph()
						 .getPunkteStandFuer( z.getSeite() == Seite.Kathargo ? Seite.Rom : Seite.Kathargo );
	}
}
