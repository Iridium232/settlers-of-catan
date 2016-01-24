package client.communication;

import java.util.List;

import shared.definitions.CatanColor;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import shared.model.Game;
import shared.model.Player;

/**
 * A proxy server that can be implemented to either speak with the server or just return values for testing purposes.
 * @author Doug
 *
 */
public interface IServerProxy {
	/**
	 * @pre the host is running the server on the specified port
	 * @post initializes a ServerProxy to send commands to the Client Communicator
	 * @param host
	 * @param port
	 * @return 
	 */
	public void ServerProxy(String host,int port);
	
	/**
	 * @pre username and password are not null
	 * @post valid username and password combinations will start a session
	 * @param username
	 * @param password
	 */
	public void login(String username, String password);
	
	/**
	 * @pre username and password are not null
	 * @post a new user will be created on the server and the client will log that user in
	 * @param username
	 * @param password
	 */
	public void register(String username, String password);
	
	/**
	 * @post a list of all the current games on the server is returned
	 */
	public List<Game> getGameList();
	
	/**
	 * @pre none of the parameters are null
	 * @post a new game with specified name and conditions is created on the server.
	 * @param name
	 * @param randomTiles
	 * @param randomNumbers
	 * @param randomPorts
	 * @return
	 */
	public Game createGame(String name, boolean randomTiles, boolean randomNumbers, boolean randomPorts);

	/**
	 * @pre user is logged in, a game with the id exists, the color is a valid color
	 * @post the user is added to the game and the board is updated to show that
	 * @param playerinfo username and password
	 * @param id the game ID
	 * @param color one of the acceptable colors
	 */
	public void joinGame(String playerinfo, int id, CatanColor color);
	
	/**
	 * @pre filename is non null and the id is for a current game
	 * @post a save file is created on the server
	 * @param id
	 * @param filename
	 */
	public void saveGame(int id, String filename);
	
	/**
	 * @pre filename exists on the server
	 * @post the game is loaded to reflect the saved state.
	 * @param filename
	 */
	public void loadGame(String filename);
	
	/**
	 * @pre id is for an existing game
	 * @post the model is returned in a JSON object;
	 * @param model
	 */
	public void getModel(int id);
	
	/**
	 * @pre user is currently part of a game
	 * @post game command history is cleared and the players still remain. 
	 * the http response contains the current client model
	 */
	public void reset();
	
	/**
	 * @pre user is part of a game
	 * @post gets a http success or failure response if success contains all of the commands.
	 * @return list of all the commands that have been executed in the game
	 */
	public List<String> getCommands();
	
	/**
	 * @pre user is part of a game
	 * @post the command has been executed and returned an updated model. 
	 * @param commands
	 * @return
	 */
	public String executeCommands(List<String> commands);
	
	//Move commands
	
	/**
	 * @post the message is posted to the chat
	 * @param message
	 */
	public void sendChat(String message);
	
	/**
	 * @pre A trade has been offered, if you accept is true the user has the required resources
	 * @post the trade offer is removed. If accepted resources are exchanged if not nothing happens.
	 * @param accept
	 */
	public void acceptTrade(boolean accept);
	
	/**
	 * @pre status of the client model is discarding, user has over 7 cards, user has chosen cards to discard
	 * @post user no longer has the discarded resources
	 * @param discardedCards
	 */
	public void discardCards(ResourceMultiSet discardedCards);
	
	/**
	 * @pre it is the users turn, the client model's status is rolling
	 * @post client model status changes to discarding, robbing or playing
	 */
	public void rollNumber();
	
	/**
	 * @pre the location is open, it is connected to a road owned by the player, 
	 * @pre it is not on water, the player has the required resources, during setup must be placed by a settlement with no adjacent roads
	 * @post the resources used are no longer in the players hand, the road is placed on the game board, and the game has checked for longest road
	 * @param free is the placement part of setup
	 * @param location where the road will be placed
	 */
	public void buildRoad(boolean free, EdgeLocation roadLocation);
	
	/**
	 * @pre the location is open, it is not on water, it is connected to a road unless during setup, player has the required resources, it is not adjacent to another settlement.
	 * @post player no longer has the used resources, the settlment is placed on the map.
	 * @param free
	 * @param place
	 */
	public void buildSettlement(boolean free, VertexLocation place);
	
	/**
	 * @pre the player has a city at the location and they have the required resources.
	 * @post the resources are gone, the city is on the map and the player got a settlement back.
	 * @param place
	 */
	public void buildCity(VertexLocation place);
	
	/**
	 * @pre the player has the resources they are offering
	 * @post the trade is offered to the other player
	 * @param offer
	 * @param receiver
	 */
	public void offerTrade(ResourceMultiSet offer, Player receiver);
	
	/**
	 * @pre the player has the resource they are giving, they have the correct port if ratio is <4
	 * @post the trade has been executed and the resources have been exchanged. 
	 * @param ratio
	 * @param input
	 * @param output
	 */
	public void maritimeTrade(int ratio, ResourceType input, ResourceType output);
	
	/**
	 * @pre the robber is moving from his starting location, the player being robbed has resource cards
	 * @post the robber is in the new location, the player being robbed gave the player robbing one resource card.
	 * @param location
	 * @param victim
	 */
	public void robPlayer(HexLocation location, Player victim);
	
	/**
	 * post card in the new dev card hand move to the old dev card hand.
	 */
	public void finishTurn();
	
	/**
	 * @pre the player has the required resources
	 * @post the player has a new dev card monuments go in the old hand others go in the new hand
	 */
	public void buyDevCard();
	/**
	 * The following are dev card commands they share the following preconditions
	 * @pre it is the players turn, the client model status is playing, they have the card in their old hand, and they have not played a non monument dev card this turn
	 */
	//dev card commands
	/**
	 * @pre the robber is moving, the player being robbed has cards.
	 * @post the robber moved, the victim gave up a card, check for largest army, set dev card played to true for this turn.
	 * @param place
	 * @param victim
	 */
	public void playSoldier(HexLocation place, Player victim);
	
	/**
	 * @pre the specified resources are in the bank
	 * @post the player gained both specified resources
	 * @param one
	 * @param two
	 */
	public void yearOfPlenty(ResourceType one,ResourceType two);
	
	/**
	 * @pre road location one is connected to a current road, road location two is connected to a current road or location one, neither is water, the player has two unused roads
	 * @post the player has two fewer roads, the new roads are placed on the map, longest road check.
	 * @param one
	 * @param two
	 */
	public void RoadBuilding(EdgeLocation one, EdgeLocation two);
	
	/**
	 * @post all other players have given you all of their resource of type one
	 * @param one
	 */
	public void monopoly(ResourceType one);
	
	/**
	 * @pre you have enough monument cards to reach 10 victory points
	 * @post you gained a victory point
	 */
	public void monument();
}
