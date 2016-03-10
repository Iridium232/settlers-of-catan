package server.facade;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import shared.communication.EdgeLocation;
import shared.communication.ResourceList;
import shared.communication.fromServer.game.VertexLocation;
import shared.communication.fromServer.games.Game;
import shared.definitions.CatanColor;
import shared.definitions.ResourceType;
import shared.exceptions.JoinExceptions;
import shared.locations.HexLocation;
import shared.model.Fascade;
import shared.model.player.Player;

/**
 * 
 * Catan Server Facade that gets called by commands
 *
 */
public class ServerFacade implements client.communication.IServerProxy
{
	
	private ArrayList<shared.model.Fascade> games;
	private ArrayList<User> users;
	/**
	 * Constructor for ServerProxy Implements the interface but is unused
	 */
	@Override
	public void ServerProxy(String host, int port, Fascade f) 
	{
		
	}

	/**
	 * Login
	 * 
	 * creates a new game
	 * @pre the user has a valid account
	 * @post The user is given user credentials if the login was valid.
	 * 			Signals access denied if the login is invalid
	 */
	@Override
	public String login(String username, String password) 
	{
		return null;
	}
	
	/**
	 * Register
	 * 
	 * registers a new user with the server and logs them in
	 * @pre none
	 * @post The new user is added and login credentials are returned
	 */
	@Override
	public String register(String username, String password) 
	{
		return null;
	}

	/**
	 * Get the list of available games
	 * 
	 * gets the list of games on the server 
	 * 
	 * @pre the user logged in with a valid account
	 * @post The games are returned
	 */
	@Override
	public List<Game> getGameList() 
	{
		return null;
	}

	
	/**
	 * Create a game
	 * 
	 * creates a new game
	 * @pre the user logged in with a valid account
	 * @post The game is created and added to the list
	 */
	@Override
	public Game createGame(String name, boolean randomTiles,
			boolean randomNumbers, boolean randomPorts) throws JoinExceptions 
	{
		return null;
	}

	/**
	 * Join a game
	 * 
	 * Adds a user to the game
	 * @pre the user is already in the game or there is space for them
	 * @post the player is added to the game and gets information about the game.
	 */
	@Override
	public String joinGame(int id, CatanColor color) throws JoinExceptions {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Get Model
	 * 
	 * gets the currrently stored model
	 * 
	 * @pre the model is a game that the user has joined
	 * @post the model is returned serialized 
	 * 			unless there are no changes to report
	 */
	@Override
	public String getModel(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Save Game
	 * 
	 * saves the game state
	 * 
	 * @pre the active user is in the game
	 * @post the game is saved for later in a file named by the second parameter.
	 * 
	 */
	@Override
	public void saveGame(UUID game_id, String file_name) throws JoinExceptions {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Load Game
	 * 
	 * loads a saved game from a file.
	 * 
	 * @pre there is a game at that file address.
	 * @post the game is reloaded into the server memory.
	 * 
	 */
	@Override
	public void loadGame(String file_name) throws JoinExceptions {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Add AI Player
	 * 
	 * adds an AI player to the current game.
	 * 
	 * @pre this game has room for more players
	 * @post an AI player is added to the game.
	 * 
	 */
	@Override
	public String addAIPlayer(String AiType) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Get AI Type
	 * 
	 * gets the ai type choices
	 * 
	 * @pre none
	 * @post the list of enabled AI types is returned
	 * 
	 */
	@Override
	public String getAITypes() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Send Chat
	 * 
	 * sends a chat message
	 * 
	 * @pre the user is in a game
	 * @post the game chat list has this message added
	 * 
	 */
	@Override
	public String sendChat(int playerIndex, String message) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Accept Trade
	 * 
	 * accepts a domestic trade
	 * 
	 * @pre there is a domestic trade available to the player
	 * @post the trade is accepted or denied based on the boolean
	 * 
	 */
	@Override
	public String acceptTrade(int playerIndex, boolean accept) 
	{
		return null;
	}

	/**
	 * Discard Cards
	 * 
	 * discards the specified cards
	 * 
	 * @pre this player has to discard after a 7
	 * @post the player has these resources removed.
	 * 
	 */
	@Override
	public String discardCards(ResourceList discardedCards) 
	{
		return null;
	}

	/**
	 * Roll Number
	 * 
	 * applies a roll of the dice to the model
	 * 
	 * @pre the active player rolled the dice. The number is between 2 and 12
	 * @post everybody gets their resources and discarding and robbing are 
	 * triggered on a 7 roll
	 * 
	 */
	@Override
	public String rollNumber(int number) 
	{
		return null;
	}

	/**
	 * Build Road
	 * 
	 * builds a road an puts it in the model
	 * 
	 * @pre It is a valid edgelocation
	 * @post a road is placed there
	 * 
	 */
	@Override
	public String buildRoad(boolean free, EdgeLocation roadLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Build Settlement
	 * 
	 * builds a settlement in that place
	 * 
	 * @pre this is a valid settlement location
	 * @post the model has the settlement played there
	 * 
	 */
	@Override
	public String buildSettlement(boolean free, VertexLocation place) 
	{
		return null;
	}

	/**
	 * Build city
	 * 
	 * builds a city in that place
	 * 
	 * @pre this is a valid city location where the player already has a 
	 * 		settlement
	 * @post the model has the city played there
	 * 
	 */
	@Override
	public String buildCity(VertexLocation place) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Build Settlement
	 * 
	 * builds a settlement in that place
	 * 
	 * @pre this is a valid settlement location
	 * @post the model has the settlement played there
	 * 
	 */
	@Override
	public String offerTrade(ResourceList offer, Player receiver) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Maritime Trade
	 * 
	 * trades resources at the given exchange rate
	 * 
	 * @pre none
	 * @post the player trades these resources.
	 * 
	 */
	@Override
	public String maritimeTrade(int ratio, ResourceType input,
			ResourceType output) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Rob Player
	 * 
	 * the player sending the command robs the other
	 * 
	 * @pre none
	 * @post the victim is robbed of a random resource.
	 * 
	 */
	@Override
	public String robPlayer(HexLocation location, Player victim) 
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Finish Turn
	 * 
	 * ends the current player's turn
	 * 
	 * @pre none
	 * @post the next player is queued up to roll
	 * 
	 */
	@Override
	public String finishTurn() 
	{
		return null;
	}

	/**
	 * Buy Development Card
	 * 
	 * buys a development card for the player
	 * 
	 * @pre there are still dev-cards left to be drawn
	 * @post the player has the new card in his new_dev_cards hand
	 * 
	 */
	@Override
	public String buyDevCard() 
	{
		return null;
	}

	/**
	 * Play the Soldier dev Card
	 * 
	 * plays the Soldier Card
	 * 
	 * @pre none
	 * @post the soldier card is played and the player's army increases and the
	 * player gets to rob.
	 * 
	 */
	@Override
	public String playSoldier(HexLocation place, Player victim) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	/**
	 * Play the Year of Plenty dev Card
	 * 
	 * plays the Year of Plenty
	 * 
	 * @pre none
	 * @post the Year of Plenty card is played and the player gets to get
	 * resources. One each of the 2 types sent.
	 * 
	 */
	@Override
	public String yearOfPlenty(ResourceType one, ResourceType two) 
	{
		return null;
	}

	/**
	 * Play the Road Building dev Card
	 * 
	 * plays the Road Building
	 * 
	 * @pre none
	 * @post the Road Building card is played and the player gets to place
	 * 2 roads.
	 * 
	 */
	@Override
	public String RoadBuilding(EdgeLocation one, EdgeLocation two) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Play the Monopoly dev Card
	 * 
	 * plays the Monopoly
	 * 
	 * @pre none
	 * @post the Monopoly card is played and the player gets to steal all of 
	 * one type of resource.
	 * 
	 */
	@Override
	public String monopoly(ResourceType one) 
	{
		return null;
	}

	/**
	 * Play the Monument dev Card
	 * 
	 * plays the Monument
	 * 
	 * @pre none
	 * @post the Monument card is played and the player gets a victory point
	 * 
	 */
	@Override
	public String monument() 
	{
		return null;
	}

}
