package pub;

import core.datacontainers.Seite;
import logik.Mechanik;
import logik.ai.Joernson;
import network.Client;
import network.Server;

/**
 * Projekt : RomUndKathargo
 * Author  : Julian Heeger
 * Date    : 15.07.14
 * Year    : 2014
 */
public class ClientJar
{
	public static void main (String[] args)
	{
		if (args.length > 0){
			String path = args[0];
			String ip = args[1];
			Integer port = Integer.valueOf( args[2] );
			Mechanik mechanik = new Mechanik( path );
			Joernson joernson = new Joernson( Seite.Rom,mechanik );
			Client client = new Client(port,ip,mechanik);
			client.aigegner( joernson );
		}
	}
}
