package client.communication;

import java.util.Timer;

import shared.model.Player;

/**
 * 
 * @author Doug
 * The ServerPoller calls the server to find out if there have been
 * any changes made to the game so the user's model can be updated.
 * 
 */
public class ServerPoller {
	
	private IServerProxy server;
	private Timer poller=null;
	private ClientCommunicator comm;
	public final static long DEFAULT_POLL_INTERVAL=3000;
	/**
	 * 
	 * @param server
	 * @param user
	 * @pre The client is connected to a server, a user is logged in, the user belongs to a game. 
	 * @post A ServerPoller will call the server every 3 seconds to keep the player's board current.
	 */
	public ServerPoller(IServerProxy server, Player user){
		
	}
	
	/**
	 * @pre the poller is not already running
	 * @post the poller will start calling the server.
	 */
	public void Start(){
		
	}
	
	/**
	 * @post the poller will stop calling the server
	 */
	public void Stop(){
		
	}
}
