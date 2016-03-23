package shared.model;

import java.util.Random;

import shared.communication.EdgeLocation;
import shared.communication.ResourceList;
import shared.definitions.CatanColor;
import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import shared.definitions.TurnStatus;
import shared.locations.EdgeDirection;
import shared.locations.HexLocation;
import shared.model.map.GameMap;
import shared.model.map.Road;
import shared.model.map.Robber;
import shared.model.map.TerrainHex;
import shared.model.map.buildings.Building;
import shared.model.messages.MessageLine;
import shared.model.messages.MessageList;
import shared.model.player.DevCardList;
import shared.model.player.Player;
import shared.model.player.ResourceMultiSet;
import shared.model.player.TradeOffer;
import shared.model.states.IState;
import shared.model.states.PlayingState;
import shared.model.states.RobbingState;
import shared.model.states.TurnTracker;
import client.data.*;

/**
 * The Catan Game all collected is represented in this class
 * This is the model's main container
 * 
 */
public class Game 
{ 
	/**
	 * Map of the board with all the pieces
	 */
	private GameMap map = new GameMap();
	
	/**
	 * Name of the game
	 */
	private String game_name;
	
	/**
	 * Info needed by the GUI
	 */
	private GameInfo gameinfo;
	
	/**
	 * List of current players
	 */
	private Player[] players = new Player[4];
	
	/**
	 * structure to keep track of whose turn it is
	 */
	private TurnTracker  turn_tracker;
	
	/**
	 * index of winning player
	 */
	private int winner = -1;
	
	/**
	 * version of the model to see if we need to update
	 */
	private int version = -1;
	
	/**
	 * a list of chat messages
	 */
	private MessageList chat = new MessageList();
	
	/**
	 * a list of log messages
	 */
	private MessageList log;
	
	/**
	 * a Trade offer outstanding
	 */
	private TradeOffer trade_offer;
	
	/**
	 * list of resource cards owned by nobody
	 */
	private ResourceMultiSet resource_bank = new ResourceMultiSet();
	
	/**
	 * list of development cards owned by nobody
	 */
	private DevCardList development_bank;

	public Game()
	{
		for(int i = 0; i < 4; i++)
		{
			players[i] = new Player();
		}
	}
	
	/**
	 * @return the map
	 */
	public GameMap getMap() {
		return map;
	}

	/**
	 * @param map the map to set
	 */
	public void setMap(GameMap map) {
		this.map = map;
	}

	/**
	 * @return the gameinfo
	 */
	public GameInfo getGameinfo() {
		return gameinfo;
	}

	/**
	 * @param gameinfo the gameinfo to set
	 */
	public void setGameinfo(GameInfo gameinfo) {
		this.gameinfo = gameinfo;
	}

	/**
	 * @return the players
	 */
	public Player[] getPlayers() {
		return players;
	}

	/**
	 * @param players the players to set
	 */
	public void setPlayer(Player player, int index) {
		assert (index >= 0 && index <=4);
		this.players[index] = player;
	}

	/**
	 * @return the turn_tracker
	 */
	public TurnTracker getTurn_tracker() {
		return turn_tracker;
	}

	/**
	 * @param turn_tracker the turn_tracker to set
	 */
	public void setTurn_tracker(TurnTracker turn_tracker) {
		this.turn_tracker = turn_tracker;
	}

	/**
	 * @return the winner
	 */
	public int getWinner() {
		return winner;
	}

	/**
	 * @param winner the winner to set
	 */
	public void setWinner(int winner) {
		this.winner = winner;
	}

	/**
	 * @return the version
	 */
	public int getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(int version) {
		this.version = version;
	}

	/**
	 * @return the chat
	 */
	public MessageList getChat() {
		return chat;
	}

	/**
	 * @param chat the chat to set
	 */
	public void setChat(MessageList chat) {
		this.chat = chat;
	}

	/**
	 * @return the log
	 */
	public MessageList getLog() {
		return log;
	}

	/**
	 * @param log the log to set
	 */
	public void setLog(MessageList log) {
		this.log = log;
	}

	/**
	 * @return the trade_offer
	 */
	public TradeOffer getTrade_offer() {
		return trade_offer;
	}

	/**
	 * @param trade_offer the trade_offer to set
	 */
	public void setTrade_offer(TradeOffer trade_offer) {
		this.trade_offer = trade_offer;
	}

	/**
	 * @return the resource_bank
	 */
	public ResourceMultiSet getResource_bank() {
		return resource_bank;
	}

	/**
	 * @param resource_bank the resource_bank to set
	 */
	public void setResource_bank(ResourceMultiSet resource_bank) {
		this.resource_bank = resource_bank;
	}

	/**
	 * @return the development_bank
	 */
	public DevCardList getDevelopment_bank() 
	{
		return development_bank;
	}

	/**
	 * @param development_bank the development_bank to set
	 */
	public void setDevelopment_bank(DevCardList development_bank) 
	{
		this.development_bank = development_bank;
	}
	
	/**
	 * advances to the next phase in this player's turn
	 * @pre this turn phase is finished
	 * @post the next turn phase is entered. If that means another player
	 * should become the active player, that player becomes so.
	 */
	public void nextTurnPhase()
	{
		
	}
	
	/**
	 * advances the turn to the next player
	 * 
	 * @pre the player is finished with his turn
	 * @post it is the next player's turn
	 */
	public void endTurn()
	{
		//TODO
	}
	


	/**
	 * Tells which player has the most soldiers in play first
	 * 
	 * @pre none
	 * 
	 * @post the player index of the player with the largest army.
	 * or -1 if the card is not yet awarded
	 */
	public int whoLargestArmy() 
	{
		return turn_tracker.getLargest_army_player();
	}
	
	/**
	 * Gets the turn status of the given player index
	 * 
	 * @pre none
	 * 
	 * @post returns the turn status of the player as an Enum
	 * 
	 */
	public TurnStatus getTurnStatus(int player)
	{
		return turn_tracker.turnStatusOf(player);
	}

	public TerrainHex[][] getHexes() 
	{
		return map.getHexes();
	}

	public Road[] getRoads() 
	{
		return map.getRoads();
	}

	public Robber getRobber() 
	{
		return map.getRobber();
	}

	public IState getTurnState(int player_index) 
	{
		return turn_tracker.getState();
	}

	public String getName() 
	{
		return game_name;
	}	
	
	public void setName(String name) 
	{
		game_name = name;
	}
	//=========================== Action =========================================
	
	public void buildNewGame(String name, boolean randomTiles,
			boolean randomNumbers, boolean randomPorts) 
	{
		// TODO Auto-generated method stub
		
	}

	public void playMonument(int commanding_player_index) throws Exception 
	{
		players[commanding_player_index].playDevCard(DevCardType.MONUMENT);
		players[commanding_player_index].setMonuments(
				players[commanding_player_index].getMonuments() + 1);
	}

	public void playMonopoly(int commanding_player_index, ResourceType resource) throws Exception 
	{
		players[commanding_player_index].playDevCard(DevCardType.MONOPOLY);
		int total_found = 0;
		for (int i = 0; i < 4; i++)
		{
			if (commanding_player_index == i)
			{
				continue;
			}
			int amt_has = players[i].getResources().getAmount(resource);
			if(amt_has > 0)
			{
				total_found += amt_has;
				players[i].pay(resource, amt_has);
			}
		}
		if(total_found < 0)
		{
			total_found = 0;
		}
		players[commanding_player_index].getResource(resource, total_found);
	}

	/**
	 * Builds 2 roads for free 
	 * @param commanding_player_index
	 * @param one
	 * @param two
	 * @throws Exception 
	 */
	public void playRoadBuiding(int commanding_player_index, 
			EdgeLocation one, EdgeLocation two) throws Exception 
	{
		players[commanding_player_index].playDevCard(DevCardType.ROAD_BUILD);
		this.buildRoadAt(commanding_player_index, one, true);
		this.buildRoadAt(commanding_player_index, two, true);
	}

	public void playYearOfPlenty(int commanding_player_index, 
			ResourceType one, ResourceType two) throws Exception 
	{
		players[commanding_player_index].playDevCard(DevCardType.YEAR_OF_PLENTY);
		players[commanding_player_index].getResource(one, 1);
		players[commanding_player_index].getResource(two, 1);
		this.resource_bank.pay(one, 1);
		this.resource_bank.pay(two, 1);
	}
	

	public void playSoldier(int commanding_player_index, HexLocation place,
			int victimIndex) throws Exception 
	{
		//move robber
		this.map.moveRobber(place);
		//rob happens
		if(victimIndex == -1)
		{
			return;
		}
		ResourceType resource = null;
		ResourceMultiSet victim_stuff = players[victimIndex].getResources();
		if (victim_stuff.total() < 1)
		{
			throw new Exception("ERROR! This victim had no resources!");
		}
		Random rand = new Random();
		do
		{
			resource = ResourceType.values()[rand.nextInt(6)];
		}
		while (!victim_stuff.has(resource));
	
		players[commanding_player_index].getResource(resource, 1);
		players[victimIndex].pay(resource, 1);
	}

	public void finishTurn(int commanding_player_index) throws Exception 
	{
		this.turn_tracker.advanceActivePlayer(commanding_player_index);
	}

	public void rob(int commanding_player_index, Player victim,
			HexLocation location) throws Exception 
	{
		int victimIndex = victim.getPlayerIndex();
		
		//move robber
		this.map.moveRobber(location);
		//rob happens
		if(victimIndex == -1)
		{
			return;
		}
		
		
		ResourceType resource = null;
		ResourceMultiSet victim_stuff = players[victimIndex].getResources();
		if (victim_stuff.total() < 1)
		{
			throw new Exception("ERROR! This victim had no resources!");
		}
		Random rand = new Random();
		do
		{
			resource = ResourceType.values()[rand.nextInt(6)];
		}
		while (!victim_stuff.has(resource));
	
		players[commanding_player_index].getResource(resource, 1);
		players[victimIndex].pay(resource, 1);
		
		turn_tracker.getState().finishPhase(turn_tracker, commanding_player_index);
	}

	public void maritimeTrade(int commanding_player_index, int ratio,
			ResourceType input, ResourceType output) throws Exception 
	{
		this.resource_bank.pay(output, 1);
		players[commanding_player_index].pay(input, ratio);
		this.resource_bank.add(input, ratio);
		players[commanding_player_index].getResource(output, 1);
	}

	public void offerTrade(int commanding_player_index, ResourceList offer,
			int playerIndex) 
	{
		// TODO Auto-generated method stub
		
	}

	/**
	 * 
	 * Build a road at that spot
	 * 
	 * @pre canBuildRoad
	 * @post a road is put on that spot by that player 
	 *
	 * 
	 * @param commanding_player_index
	 * @param roadLocation
	 * @param free
	 * @throws Exception
	 */
	public void buildRoadAt(int commanding_player_index,
			EdgeLocation roadLocation, boolean free) throws Exception 
	{
		if(!free)
		{
			players[commanding_player_index].pay(ResourceType.WOOD, 1);
			players[commanding_player_index].pay(ResourceType.BRICK, 1);
		}
		
		shared.locations.EdgeLocation place = 
				new shared.locations.EdgeLocation(
				new HexLocation(roadLocation.getX(),
				roadLocation.getY()), EdgeDirection.valueOf(
						roadLocation.getDirection()));
		
		map.addRoad(new Road(place, commanding_player_index, CatanColor.valueOf(
				players[commanding_player_index].getColor()))); 
		
		players[commanding_player_index].placeRoad();
	}

	/**
	 * Reply to Trade
	 * 
	 * @pre there is a trade offer to this player
	 * @post the trade is replied to and may happen
	 * 
	 * @param commanding_player_index
	 * @param accept
	 */
	public void replyToTrade(int commanding_player_index, boolean accept) 
	{
		// TODO Auto-generated method stub
		
	}

	/**
	 * Sends a chat message
	 * 
	 * 
	 * @pre none
	 * @post a message is sent to all of the other players.
	 * @param commanding_player_index
	 * @param message
	 * @throws Exception 
	 */
	public void sendChat(int commanding_player_index, String message) throws Exception 
	{
		if(commanding_player_index > 3 || commanding_player_index < 0)
		{
			throw new Exception("ERROR: INVALID MESSAGE SENDER");
		}
		String sender = players[commanding_player_index].getName();
		this.chat.addMessage(new MessageLine(sender, message));
	}

	/**
	 * Adds a player to the game
	 * 
	 * @pre  none
	 * @post adds the player. Throws an error on failure
	 * @param name
	 * @param color
	 * @param playerID
	 * @throws Exception
	 */
	public void addPlayer(String name, CatanColor color, int playerID) throws Exception 
	{
		int player_slot = findOpening();
		if(player_slot == -1)
		{
			throw new Exception("ERROR: no available slots for player");
		}
		Player new_player = new Player(color.name(), name, player_slot, playerID);
		players[player_slot] = new_player;
	}
	
	/**
	 * 
	 * @throws Exception 
	 * @pre the active player has rolled the dice in turn and achieved this result
	 * @post players recieve resources and are forced to discard if they have
	 * too many resources.
	 * @post if the player rolled a 7, they are put into the ROB phase of their turn.
	 */
	public void applyDiceRoll(int result) throws Exception
	{
		if(result != 7)
		{
			TerrainHex[] lucky_spots = map.getHexesByNumber(result);
			for(int i = 0; i < 2; i++)
			{
				TerrainHex lucky_spot = lucky_spots[i];
				Building[] buildings = map.getAdjoiningPlayers(lucky_spot.getLocation());
				for(Building building : buildings)
				{
					if(building.getClass().getName().equals("City"))
					{
						players[building.getOwner()].getResource(
							lucky_spot.getResource(), 2);
					}
					else
					{
						players[building.getOwner()].getResource(
							lucky_spot.getResource(), 1);
					}	
				}
			}
			turn_tracker.setState(new PlayingState());
		}
		else //Brigand Attack!
		{
			for (Player player : players)
			{
				if (player.getResources().total() > 7)
				{
					turn_tracker.getState().forceDiscard(turn_tracker, -1);
					return;
				}
			}
			turn_tracker.setState(new RobbingState());
			return;
		}
	}
	
	
	/**
	 * 
	 * @pre none
	 * @post returns the index of a player opening or -1 if there is none
	 */
	private int findOpening() 
	{
		for(int i = 0; i < 4 ; i++)
		{
			if(players[i] == null || players[i].getName() == null)
			{
				return i;
			}
		}
		return -1;
		
	}

	/**
	 * Discard resources
	 * 
	 * @pre none
	 * @post the resources are discarded and the game goes into playing 
	 * for the active player
	 * @param discardedCards
	 */
	public void discard(int player_index, ResourceMultiSet discardedCards) 
	{
		players[player_index].discard(discardedCards);
		players[player_index].setDiscarded(true);
		for( Player player : players)
		{
			if (player.getResources().total() > 7 && !player.isDiscarded())
			{
				return;
			}
		}
		turn_tracker.getState().finishPhase(turn_tracker, -1);
		for(Player player : players)
		{
			player.setDiscarded(false);
		}
	}
}
