package server.main;

/**
 * Main Class for Catan Server
 *
 */
public class Server 
{
	/**
	 * Main Function that starts up all the pieces of the server.
	 * @param args
	 */
	public static void main(String[] args)
	{
		String host = "localhost";
		String port = "8081";
		if(args.length > 0)
		{
			host = args[0];
			System.out.print("Running client on host: http://" + host + ".\n");
		}
		else
		{
			System.out.print("\nNo host specified. Using default 'localhost'.");
		}
		if(args.length > 1)
		{
			port = args[1];
			System.out.print("Port: " + port +".\n");
		}
		else
		{
			System.out.print("\nNo port specified. Using default '8081'.");
		}
		
		System.out.print("\nServer Running Happily. Have fun!\n");
		
	}
}
