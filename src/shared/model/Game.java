package shared.model;

import java.util.Random;

import shared.communication.EdgeLocation;
import shared.communication.ResourceList;
import shared.communication.fromServer.game.VertexLocation;
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
import shared.model.map.Vertex;
import shared.model.map.buildings.Building;
import shared.model.map.buildings.City;
import shared.model.map.buildings.Settlement;
import shared.model.messages.MessageLine;
import shared.model.messages.MessageList;
import shared.model.player.DevCardList;
import shared.model.player.Player;
import shared.model.player.ResourceMultiSet;
import shared.model.player.TradeOffer;
import shared.model.states.FirstRoundState;
import shared.model.states.IState;
import shared.model.states.PlayingState;
import shared.model.states.RobbingState;
import shared.model.states.RollingState;
import shared.model.states.TradingState;
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
	public int getWinner() 
	{
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
	public int getVersion() 
	{
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
	
	/**
	 * creates a new game
	 * 
	 * @pre none
	 * @post builds a new game with either pre-set setup or a random one.
	 * 
	 * @param name
	 * @param randomTiles
	 * @param randomNumbers
	 * @param randomPorts
	 * @throws Exception 
	 */
	public void buildNewGame(String name, boolean randomTiles,
			boolean randomNumbers, boolean randomPorts) throws Exception 
	{
		
		this.game_name = name;
		this.development_bank = new DevCardList(14,5,2,2,2);
		map.buildNewGameMap(randomTiles, randomNumbers, randomPorts);
		this.turn_tracker = new TurnTracker();
		turn_tracker.setActive_player(0);
		turn_tracker.setLargest_army_player(-1);
		turn_tracker.setLongest_road_player(-1);
		turn_tracker.setState(new FirstRoundState());
		turn_tracker.setStatus(TurnStatus.FIRSTROUND);
		this.resource_bank = new ResourceMultiSet(19,19,19,19,19);
		version++;
	}

	/**
	 * Play monument
	 * 
	 * @pre The player has the monument card
	 * @post the player gets a victory point
	 * 
	 * @param commanding_player_index
	 * @throws Exception
	 */
	public void playMonument(int commanding_player_index) throws Exception 
	{
		players[commanding_player_index].playDevCard(DevCardType.MONUMENT);
		players[commanding_player_index].setMonuments(
                players[commanding_player_index].getMonuments() + 1);
		version++;
		log(commanding_player_index,Action.MONUMENT,-1);
	}

	/**
	 * monopoly
	 * 
	 * @pre the player has the monopoly card
	 * @post the player gets all of a certain resource
	 * 
	 * @param commanding_player_index
	 * @param resource
	 * @throws Exception
	 */
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
		players[commanding_player_index].getResource(null, resource, total_found);
		version++;
		log(commanding_player_index,Action.MONOPOLY,-1);
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
		version++;
		log(commanding_player_index,Action.ROAD,-1);
		calculateLongestRoad(commanding_player_index);
	}

	/**
	 * year of plenty
	 * 
	 * @pre the player has the year of plenty card
	 * @post the player gets the 2 resources he wanted and loses the dev card
	 * 
	 * @param commanding_player_index
	 * @param one
	 * @param two
	 * @throws Exception
	 */
	public void playYearOfPlenty(int commanding_player_index, 
			ResourceType one, ResourceType two) throws Exception 
	{
		players[commanding_player_index].playDevCard(DevCardType.YEAR_OF_PLENTY);
		players[commanding_player_index].getResource(this, one, 1);
		players[commanding_player_index].getResource(this, two, 1);
		this.resource_bank.pay(one, 1);
		this.resource_bank.pay(two, 1);
		version++;
		log(commanding_player_index,Action.YEAROFPLENTY,-1);
	}
	
	/**
	 * play soldier
	 * 
	 * @pre this player has a soldier dev card
	 * @post the player robs somebody
	 * 
	 * @param commanding_player_index
	 * @param place
	 * @param victimIndex
	 * @throws Exception
	 */
	public void playSoldier(int commanding_player_index, HexLocation place,
			int victimIndex) throws Exception 
	{
		//move robber
		this.map.moveRobber(place);
		//rob happens
		Player current=players[commanding_player_index];
		current.setSoldiers(current.getSoldiers()+ 1);
		current.playDevCard(DevCardType.SOLDIER);
		if(current.getSoldiers()>2){
			if(turn_tracker.getLargest_army_player()==-1){
				current.incrementVictoryPoints(2);
				turn_tracker.setLargest_army_player(commanding_player_index);
				this.log(commanding_player_index, Action.LARGESTARMY, -1);
			}
			else{
				Player largest=players[turn_tracker.getLargest_army_player()];
				if(largest.getPlayerID()!=current.getPlayerID()&&largest.getSoldiers()<current.getSoldiers()){
					largest.incrementVictoryPoints(-2);
					turn_tracker.setLargest_army_player(commanding_player_index);
					current.incrementVictoryPoints(2);
				}
			}
		}

		
		if(victimIndex == -1)
		{
			return;
		}
		ResourceType resource = null;
		ResourceMultiSet victim_stuff = players[victimIndex].getResources();
		if (victim_stuff.total() < 1)
		{
			//throw new Exception("ERROR! This victim had no resources!");
			return;
		}
		Random rand = new Random();
		do
		{
			resource = ResourceType.values()[rand.nextInt(6)];
		}
		while (!victim_stuff.has(resource));
	
		players[commanding_player_index].getResource(null, resource, 1);
		players[victimIndex].pay(resource, 1);
		
		version++;
		log(commanding_player_index,Action.SOLDIER,victimIndex);
		current.setPlayedDevCard(true);
	}

	/**
	 * Finishes that player's turn
	 * 
	 * @pre none
	 * @post next player is the active player and rolling
	 * 
	 * @param commanding_player_index
	 * @throws Exception
	 */
	public void finishTurn(int commanding_player_index) throws Exception 
	{
		this.turn_tracker.advanceActivePlayer(commanding_player_index);
		Player player = players[commanding_player_index];
		player.getOldDevCards().add(player.getNewDevCards());
		player.setNewDevCards(new DevCardList());
		player.setPlayedDevCard(false);
		version++;
		log(commanding_player_index,Action.FINISH,-1);
	}
	
	
	/**
	 * rob and move robber
	 * 
	 * @pre none
	 * @post robber is moved and the other guy is robbed a random resource
	 * 
	 * @param commanding_player_index
	 * @param victim
	 * @param location
	 * @throws Exception
	 */
	public void rob(int commanding_player_index, Player victim,
			HexLocation location) throws Exception 
	{
		int victimIndex = victim.getPlayerIndex();
		
		//move robber
		this.map.moveRobber(location);
		//rob happens
		if(victimIndex == -1)
		{
			turn_tracker.setState(new PlayingState());
			return;
		}
		
		
		ResourceType resource = null;
		ResourceMultiSet victim_stuff = players[victimIndex].getResources();
		if (victim_stuff.total() < 1)
		{
			//throw new Exception("ERROR! This victim had no resources!");
			return;
		}
		Random rand = new Random();
		do
		{
			resource = ResourceType.values()[rand.nextInt(6)];
		}
		while (!victim_stuff.has(resource));
	
		players[commanding_player_index].getResource(null, resource, 1);
		players[victimIndex].pay(resource, 1);
		
		turn_tracker.getState().finishPhase(turn_tracker, commanding_player_index);
		version++;
		log(commanding_player_index,Action.ROB,victimIndex);
	}

	public void maritimeTrade(int commanding_player_index, int ratio,
			ResourceType input, ResourceType output) throws Exception 
	{
		this.resource_bank.pay(output, 1);
		players[commanding_player_index].pay(input, ratio);
		this.resource_bank.add(input, ratio);
		players[commanding_player_index].getResource(this, output, 1);
		version++;
	}

	/**
	 * offers a trade
	 * 
	 * @pre none
	 * @post the trade offer is out there and all go into waiting for the response
	 * 
	 * @param commanding_player_index
	 * @param offering
	 * @param playerIndex
	 */
	public void offerTrade(int commanding_player_index, ResourceList offering,
			int playerIndex) 
	{
		TradeOffer offer = new TradeOffer(commanding_player_index, playerIndex);
		offer.translateOffer(new ResourceMultiSet(offering));
		this.trade_offer = offer;
		turn_tracker.setState(new PlayingState());
		version++;
	}
	
	/**
	 * 
	 * @pre none
	 * @post  the player buys a dev card and adds it to his hand
	 * 
	 * @param player
	 * @throws Exception 
	 */
	public void buyDevCard(int player) throws Exception
	{
		Player buyer = players[player];
		buyer.pay(ResourceType.SHEEP, 1);
		buyer.pay(ResourceType.ORE, 1);
		buyer.pay(ResourceType.WHEAT, 1);
		DevCardType type = development_bank.getRandomCard();
		if(type==DevCardType.MONUMENT){
			buyer.getOldDevCards().add(type,1);
		}else{
			buyer.getNewDevCards().add(type, 1);
		}
		development_bank.play(type);
	}
	
	public void calculateLongestRoad(int commanding_player_index){
		Player current=players[commanding_player_index];
		int roads=15-current.getRoads();
		if(roads>=5){
			if(turn_tracker.getLongest_road_player()==-1){
				turn_tracker.setLongest_road_player(commanding_player_index);
				current.incrementVictoryPoints(2);
				this.log(commanding_player_index, Action.LONGESTROAD, -1);
			}
			else
			{
				Player longestOwner=players[turn_tracker.getLongest_road_player()];
				int longest=15-longestOwner.getRoads();
				if(roads>longest&&longestOwner.getPlayerID()!=current.getPlayerID()){
					longestOwner.incrementVictoryPoints(-2);
				
//					longestOwner.setVictoryPoints(longestOwner.getVictoryPoints()-2);
					turn_tracker.setLongest_road_player(commanding_player_index);
					current.incrementVictoryPoints(2);
					this.log(commanding_player_index, Action.LONGESTROAD, -1);
//					current.setVictoryPoints(current.getVictoryPoints()+2);
				}
			}
		}
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
				roadLocation.getY()), edgeLocationFromStr(
                        roadLocation.getDirection()));
		
		map.addRoad(new Road(place, commanding_player_index, getCatanColor(players[commanding_player_index])));
		
		players[commanding_player_index].placeRoad();
		version++;
		log(commanding_player_index,Action.ROAD,-1);
		calculateLongestRoad(commanding_player_index);
	}

	/**
	 * Reply to Trade
	 * 
	 * @pre there is a trade offer to this player
	 * @post the trade is replied to and may happen
	 * 
	 * @param commanding_player_index
	 * @param accept
	 * @throws Exception 
	 */
	public void replyToTrade(int commanding_player_index, boolean accept) throws Exception 
	{
		if(commanding_player_index != trade_offer.getReciever())
		{
			throw new Exception("ERROR: this person is not part of the trade");
		}
		if(!accept)
		{
			turn_tracker.setState(new PlayingState());
		}
		else
		{
			Player sender = players[trade_offer.getReciever()];
			Player reciever =  players[trade_offer.getSender()];
			ResourceMultiSet sender_gives = trade_offer.getSender_gives();
			ResourceMultiSet reciever_gives = trade_offer.getReciever_gives();
			reciever.pay(reciever_gives);
			reciever.recieve(sender_gives);
			sender.pay(sender_gives);
			sender.recieve(reciever_gives);
		}
        this.trade_offer = null;
		turn_tracker.setState(new PlayingState());
		version++;
		return;
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
		version++;
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
		version++;
		log(player_slot,Action.JOIN,-1);
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
		log(turn_tracker.getActive_player(),Action.ROLL,result);
		if(result != 7)
		{
			TerrainHex[] lucky_spots = map.getHexesByNumber(result);
			for(int i = 0; i < 2; i++)
			{
				TerrainHex lucky_spot = lucky_spots[i];
				if(i == 1 && lucky_spots[i] == null)continue;
				if(this.map.getRobber().getLocation().equals(lucky_spot.getLocation()))
				{
					continue;
				}
				Building[] buildings = map.getAdjoiningPlayers(lucky_spot.getLocation());
				for(Building building : buildings)
				{
					//System.out.print("\nBuilding by this hex is a : " + building.getClass().getName() + "\n");
					if(building.getClass().getName().equals("shared.model.map.buildings.City"))
					{
						//System.out.print("\nCity Here\n");
						players[building.getOwner()].getResource(
							this, lucky_spot.getResource(), 2);
						resource_bank.pay(lucky_spot.getResource(), 2);
					}
					else
					{
						players[building.getOwner()].getResource(
							this, lucky_spot.getResource(), 1);
						resource_bank.pay(lucky_spot.getResource(), 1);
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
		version++;
		
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
		version++;
	}

	/**
	 * @pre none
	 * @post the player gets a city on that spot and gets a settlement back
	 * @param player_index
	 * @param place
	 * @throws Exception
	 */
	public void buildCity(int player_index, VertexLocation place) throws Exception
	{
		players[player_index].pay(ResourceType.WHEAT, 2);
		players[player_index].pay(ResourceType.ORE, 3);
		players[player_index].placeCity();
		map.addBuilding(new City(players[player_index].getColor(),
                place, player_index));
		players[player_index].setSettlements(players[player_index].getSettlements() + 1);
		version++;
		log(player_index,Action.CITY,-1);
	}

	/**
	 * builds a settlement
	 * 
	 * @pre none
	 * @post the settlement is placed there
	 * @param player_index
	 * @param place
	 * @throws Exception
	 */
	public void buildSettlement(int player_index, VertexLocation place, boolean free) throws Exception
	{
        if (!free) {
            players[player_index].pay(ResourceType.BRICK, 1);
            players[player_index].pay(ResourceType.SHEEP, 1);
            players[player_index].pay(ResourceType.WOOD, 1);
            players[player_index].pay(ResourceType.WHEAT, 1);
        }
        players[player_index].placeSettlement();
		map.addBuilding(new Settlement(player_index, place, players[player_index].getColor()));
		version++;
		if(this.turn_tracker.getState().getState() == TurnStatus.SECONDROUND)
		{
			shared.locations.VertexLocation spot = new shared.locations.VertexLocation(place);
			Vertex locale = new Vertex(spot);
			HexLocation[] adjacents = locale.getNeigborHexLocations(map);
			for( HexLocation location : adjacents)
			{
				TerrainHex hex = map.getHexAt(location.getX(), location.getY());
				if (hex == null)
				{
					continue;
				}
				ResourceType reward = hex.getResource();
				if (reward == null)
				{
					continue;
				}
				players[player_index].getResource(null, reward, 1);
			}
		}
		log(player_index,Action.SETTLEMENT,-1);
	}
	
	enum Action 
	{
		ROAD,SETTLEMENT,CITY,ROB,FINISH,BUYDEV,ROLL,MONOPOLY,YEAROFPLENTY,
		SOLDIER,MONUMENT,JOIN,REJOIN,LARGESTARMY,LONGESTROAD
	};
	
	
	/**
	 * Hidden function to log actions
	 * also checks if someone has won.
	 * @pre none
	 * @post there is a log of that action
	 * @param player_index
	 * @param action
	 * @param roll_or_victim
	 */
	private void log(int player_index, Action action, int roll_or_victim)
	{
		if(log == null)
		{
			log = new MessageList();
		}
		String player = players[player_index].getName();
		String message = player + " ";
		switch(action)
		{
		case BUYDEV:
			message += "bought a development card.";
			break;
		case CITY:
			message += "built a city.";
			break;
		case FINISH:
			message += "ended his or her turn.";
			break;
		case MONOPOLY:
			message += "played the monopoly card.";
			break;
		case MONUMENT:
			message += "played a monument card.";
			break;
		case REJOIN:
			message += "rejoined the game.";
			break;
		case ROAD:
			message += "built a road.";
			break;
		case ROB:
			if(roll_or_victim < 0)
			{
				message += "did not rob anyone.";
				break;
			}
			message += "robbed " + players[roll_or_victim].getName() + ".";
			break;
		case ROLL:
			message += "rolled a " + Integer.toString(roll_or_victim) + ".";
			break;
		case SETTLEMENT:
			message += "placed a settlement.";
			break;
		case SOLDIER:
			if(roll_or_victim < 0)
			{
				message += "played the soldier but did not rob anyone.";
				break;
			}
			message += "played the soldier and robbed " + 
					players[roll_or_victim].getName() + ".";
			break;
		case YEAROFPLENTY:
			message += "played the year of plenty card.";
			break;
		case JOIN:
			message += "joined the game.";
			break;
		case LARGESTARMY:
			message += "got the largest army.";
			break;
		case LONGESTROAD:
			message += "got the longest road.";
			break;
		default:
			return;
		}
		this.log.addMessage(new MessageLine(players[player_index].getName(),message));
		for (Player p : players)
		{
			if (p.getVictoryPoints() >= 10)
			{
				this.log.addMessage(new MessageLine(p.getName(),p.getName() + 
						" has won the game!"));
				this.winner = p.getPlayerIndex();
			}
		}
	}

	/**
	 * get EdgeLocation from String
	 * @pre none
	 * @post translates edge locations
	 * @param str
	 * @return
	 */
    private EdgeDirection edgeLocationFromStr(String str) {
        if (str.toLowerCase().equals("nw") || str.toLowerCase().equals("northwest")) {
            return EdgeDirection.NorthWest;
        }
        if (str.toLowerCase().equals("n") || str.toLowerCase().equals("north")) {
            return EdgeDirection.North;
        }
        if (str.toLowerCase().equals("ne") || str.toLowerCase().equals("northeast")) {
            return EdgeDirection.NorthEast;
        }
        if (str.toLowerCase().equals("sw") || str.toLowerCase().equals("southwest")) {
            return EdgeDirection.SouthWest;
        }
        if (str.toLowerCase().equals("s") || str.toLowerCase().equals("south")) {
            return EdgeDirection.South;
        }
        if (str.toLowerCase().equals("se") || str.toLowerCase().equals("southeast")) {
            return EdgeDirection.SouthEast;
        }
        return null;
    }

    /**
     * Translates strings to catancolors
     * @pre  none
     * @post returns the catancolor that goes with the string
     * 
     * @param player
     * @return
     */
    private CatanColor getCatanColor(shared.model.player.Player player) {
        String color = player.getColor().toLowerCase();
        if (color.equals("red")) return CatanColor.RED;
        if (color.equals("orange")) return CatanColor.ORANGE;
        if (color.equals("yellow")) return CatanColor.YELLOW;
        if (color.equals("blue")) return CatanColor.BLUE;
        if (color.equals("green")) return CatanColor.GREEN;
        if (color.equals("purple")) return CatanColor.PURPLE;
        if (color.equals("puce")) return CatanColor.PUCE;
        if (color.equals("white")) return CatanColor.WHITE;
        return CatanColor.BROWN;
    }
    
    /**
     * Rejoin recolors the player who rejoins to match
     * @pre none
     * @post the player that rejoined now uses this color
     */
    public void reJoin(int index, CatanColor new_color)
    {
    	CatanColor old_color = CatanColor.valueOf(players[index].getColor().toUpperCase());
    	players[index].setColor(new_color.name());
    	map.recolor(old_color, new_color);
    	this.log(index, Action.REJOIN, -1);
    }
}
