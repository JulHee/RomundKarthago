package logik.ai;

import core.*;
import core.datacontainers.Knoten;
import core.datacontainers.Seite;
import core.datacontainers.Zug;
import logik.Mechanik;
import logik.ai.AIPlayer;

/**
 * Projekt: Rom und Karthago
 * Author : Julian Heeger, Markus Poell, Christian Bruene, Joern Kabuth
 * Date : 10.06.14
 * Year : 2014
 */

public class Sloth extends AIPlayer
{
	public Sloth (Seite s,
				  Mechanik m)
	{
		meineSeite = s;
		this.mechanik = m;
	}

	public Zug nextZug ()
	{
		Zug erg = new Zug( meineSeite, berechneZugStadt() );
		return erg;
	}

	private Integer berechneZugStadt ()
	{
		int erg = - 1;
		Graph graph = mechanik.getMyGraph();
		for ( Knoten k : graph.toArrayList( graph.l_knoten ) )
		{
			if ( k.getSeite() == Seite.Neutral )
			{
				erg = k.id;
			}
		}
		return erg;
	}

}
