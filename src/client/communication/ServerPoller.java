package client.communication;

import java.util.Timer;
import java.util.TimerTask;

import shared.model.Fascade;
import shared.model.player.Player;

/**
 * 
 * @author Doug
 * The ServerPoller calls the server to find out if there have been
 * any changes made to the game so the user's model can be updated.
 * 
 */
public class ServerPoller {
	
	private static IServerProxy server;
	private static Fascade f;
	private static Timer poller=null;
	private static ClientCommunicator comm;
	public final static long DEFAULT_POLL_INTERVAL=3000;
	private static ServerPoller sp;
	/**
	 * 
	 * @param server
	 * @param user
	 * @pre The client is connected to a server, a user is logged in, the user belongs to a game. 
	 * @post A ServerPoller will call the server every 3 seconds to keep the player's board current.
	 */
	private ServerPoller(){}
	
	public static ServerPoller getServerPoller(){
		if(sp!=null){
			return sp;
		} else{
			sp=new ServerPoller();
			return sp;
		}
	}
	
	public static void setComm(ClientCommunicator c){
		comm=c;
	}
	
	public static void setServer(IServerProxy s){
		server=s;
	}
	
	public static void setFascade(Fascade fas){
		f=fas;
	}
	/**
	 * @pre the poller is not already running
	 * @post the poller will start calling the server.
	 */
	public static void Start(){
		poller = new Timer(true);
		poller.scheduleAtFixedRate(new TimerTask(){
			
			@Override
			public void run(){
					try{
						int version=f.getLatestModelNum();
						server.getModel(version);
					}catch(Exception e){
						
					}
			}
		}, 0, DEFAULT_POLL_INTERVAL);
	}
	
	/**
	 * @post the poller will stop calling the server
	 */
	public static void Stop(){
		
	}
}
