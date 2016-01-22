package client.communication;

import org.json.JSONObject;

/**
 * Handles communication between the client and server
 * @author Doug
 *
 */
public class ClientCommunicator {
	
	/**
	 * Calls login on the server
	 * @param username
	 * @param password
	 * @return JSONObject containing name, password, and ID
	 * @pre username and password are not null
	 * @post receives the http response and sets the user cookie.
	 */
	public JSONObject login(String username, String password){
		
	}
	
	/**
	 * Calls register on the server
	 * @param username
	 * @param password
	 * @return JSONObject containing name,password, and ID used to set user.cookie
	 * @pre username and password are not null and the username isn't already in use. 
	 * @post new user account is created, receive a http response, set the user cookie
	 */
	public JSONObject register(String username,String password){
		
	}
	
	/**
	 * Calls games/list on the server
	 * @post got a list of games from the server
	 * @return a JSON array containing a list of objects containing information about the server's games.
	 */
	public JSONObject gamesList(){
		
	}
	
	/**
	 * 
	 * @param name
	 * @param randomTiles
	 * @param randomNumbers
	 * @param randomPorts
	 * @return a JSONObject containing all of the information for the game board including game name, game id and an array for four empty player objects.
	 * @pre name is not null, has values for randomTiles, randomNumbers, and randomPorts.
	 * @post receive a HTTP response for success or failure 
	 */
	public JSONObject createGame(String name, boolean randomTiles, boolean randomNumbers, boolean randomPorts){
		
	}
	
	/**
	 * 
	 * @param ID
	 * @param color
	 * @return 
	 * @pre the user is logged in, the player is already in the specified game or the game has space for an extra player, the ID is valid, and the color is valid
	 * @post the player is added to the game with the desired color, 
	 */
	public JSONObject joinGame(int ID, String color){
		
	}
}
