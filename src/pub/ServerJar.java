package pub;

import core.datacontainers.Seite;
import logik.ai.Joernson;
import network.Server;

/**
 * Projekt : RomUndKathargo
 * Author  : Julian Heeger
 * Date    : 15.07.14
 * Year    : 2014
 */
public class ServerJar
{
	public static void main (String[] args)
	{
		if (args.length > 0){
			Integer port = Integer.valueOf( args[0] );
			Joernson joernson = new Joernson( Seite.Kathargo );
			Server server = new Server(port,joernson);
		}
	}
}
