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
	
	/**
	 * Constructor for ServerProxy Implements the interface but is unused
	 */
	@Override
	public void ServerProxy(String host, int port, Fascade f) 
	{
		// TODO Auto-generated method stub
		
	}


	@Override
	public String login(String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String register(String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Game> getGameList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Game createGame(String name, boolean randomTiles,
			boolean randomNumbers, boolean randomPorts) throws JoinExceptions {
		// TODO Auto-generated method stub
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

	@Override
	public String getModel(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveGame(UUID game_id, String file_name) throws JoinExceptions {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadGame(String file_name) throws JoinExceptions {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String addAIPlayer(String AiType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAITypes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String sendChat(int playerIndex, String message) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String acceptTrade(int playerIndex, boolean accept) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String discardCards(ResourceList discardedCards) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String rollNumber(int number) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String buildRoad(boolean free, EdgeLocation roadLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String buildSettlement(boolean free, VertexLocation place) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String buildCity(VertexLocation place) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String offerTrade(ResourceList offer, Player receiver) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String maritimeTrade(int ratio, ResourceType input,
			ResourceType output) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String robPlayer(HexLocation location, Player victim) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String finishTurn() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String buyDevCard() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String playSoldier(HexLocation place, Player victim) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String yearOfPlenty(ResourceType one, ResourceType two) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String RoadBuilding(EdgeLocation one, EdgeLocation two) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String monopoly(ResourceType one) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String monument() {
		// TODO Auto-generated method stub
		return null;
	}

}
