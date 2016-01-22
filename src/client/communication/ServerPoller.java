package client.communication;

/**
 * 
 * @author Doug
 * The ServerPoller calls the server to find out if there have been
 * any changes made to the game so the user's model can be updated.
 * 
 */
public class ServerPoller {

	/**
	 * 
	 * @param server
	 * @param user
	 * @pre The client is connected to a server, a user is logged in, the user belongs to a game. 
	 * @post A ServerPoller will call the server every few seconds to keep the player's board current.
	 */
	public ServerPoller(Server server, User user){
		
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
