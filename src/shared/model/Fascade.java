package shared.model;
import client.data.*;

import shared.communication.toServer.games.*;
import shared.definitions.PieceType;
import shared.definitions.TurnStatus;
import shared.locations.HexLocation;
import shared.model.ports.*;
import shared.definitions.Commands;

/**
 * The Facade class handles all communication and commands to and from the game model.
 * 
 */
public class Fascade 
{
	/**
	 * This is the model
	 */
	private Game game_model;
	
	
	/**
	 * Changes the model when a new model was populated
	 * @pre this new model was instantiated 
	 * @post the fascade points to the new model and the old one
	 * is lost. Java garbage collection will get rid of it.
	 */
	public void changeModel(Game game_model)
	{
		this.game_model = game_model;
	}
	
	
	
	//+++++++++++++++++++++++++++++++++++++++++++++++
	//Purchases and Placement
	//+++++++++++++++++++++++++++++++++++++++++++++++
	/**
	 * Tells whether a player can lay a road on that edge
	 * 
	 * @param player
	 * @param edge
	 * @pre none
	 * @post result is true iff that is a valid road construction
	 */
	public boolean canBuildRoad(int player_index, Edge edge)
	{
		GameMap game_map = game_model.getMap();
		Player player = game_model.getPlayers()[player_index];
		return game_map.canBuildRoad(edge, player_index) &&
				player.canPlaceRoad() && (game_model.getTurnStatus(player_index) == TurnStatus.PLAYING);
	}
	
	/**
	 * Orders the construction of a road on that edge.
	 * 
	 * @param request
	 *
	 *
	 * @pre canBuildRoad() returns true
	 * @post a road is built for that player on that edge. The server is notified.
	 * @post The player's resources are reduced by 1 wood and 1 brick
	 */
	public DataResponse buildRoadAt(DataRequest request) throws Exception
	{

		//TODO

		DataResponse response = new DataResponse(Commands.BUILD_ROAD,false);
		return response;

	}
	
	/**
	 * Tells whether a specified PlayerInfo can buy a Development card right now.
	 * 
	 * @param player
	 * 
	 * @pre None
	 * @post result = True when the player can afford a development card 
	 * and it is their turn to buy things. /result = false otherwise.
	 */
	public boolean canBuyDevelopmentCard(int player_index)
	{
		GameMap game_map = game_model.getMap();
		Player player = game_model.getPlayers()[player_index];
		return player.canBuyDevCard() && (game_model.getTurnStatus(player_index) == TurnStatus.PLAYING);
	}
	
	/**
	 * Buy a development card 
	 * @param player
	 * 
	 * @pre PlayerInfo can buy a development card (see previous function)
	 * @post the player has a new development card.
	 * @post The player's resources are reduced by 1 food and 1 ore and 1 wool
	 */
	public DataResponse buyDevelopmentCard(DataRequest player) throws Exception
	{
		DataResponse response = new DataResponse(Commands.BUY_DEV_CARD,false);
		return response;
	}
	
	/**
	 * Can this player build a settlement here?
	 * 
	 * @param player
	 * @param location
	 * @pre the player is playing the game
	 * @post returns true iff the player can legally put a settlement on that location
	 */
	public boolean canBuildSettlement(int player_index, Vertex location)
	{
		GameMap game_map = game_model.getMap();
		Player player = game_model.getPlayers()[player_index];
		boolean initial_override = game_model.getTurnStatus(player_index) == TurnStatus.FIRSTROUND 
				|| game_model.getTurnStatus(player_index) == TurnStatus.SECONDROUND;
		boolean valid_turn = game_model.getTurnStatus(player_index) == TurnStatus.PLAYING 
				|| initial_override;

		return game_map.canAddSettlement(location, player_index, initial_override) &&
				player.canPlaceSettlement(initial_override) && (valid_turn);
	}
	
	/**
	 * Build a settlement on that spot
	 * 
	 * @param player
	 * @param location
	 * @pre canBuildSettlement() is true
	 * @post a settlement is built by that player on the specified location
	 * @post The player's resources are reduced by 1 food, 1 brick, 1 wood, and 1 wool
	 */
	public DataResponse buildSettlement(PlayerInfo player, Vertex location) throws Exception
	{
		DataResponse response = new DataResponse(Commands.BUILD_SETTLEMENT,false);
		return response;
	}
	
	/**
	 * Can this player build a city there?
	 * 
	 * @param player_index
	 * @param location
	 * @pre None
	 * @post result = true iff building a city on that vertex is valid 
	 */
	public boolean canBuildCity(int player_index, Vertex location)
	{
		GameMap game_map = game_model.getMap();
		Player player = game_model.getPlayers()[player_index];
		return game_map.canAddCity(location, player_index) &&
				player.canPlaceCity() && (game_model.getTurnStatus(player_index) == TurnStatus.PLAYING);
	}
	
	/**
	 * Build a city on the Settlement that was previously there
	 * 
	 * @param request
	 * @pre canBuildCity() is true
	 * @post a City replaces the player's settlement on that spot.
	 * @post The player's resources are reduced by 2 food and 3 ore
	 * 
	 */
	public DataResponse BuildCity(DataRequest request) throws Exception
	{
		DataResponse response = new DataResponse(Commands.BUILD_CITY,false);
		return response;
	}
	
	//+++++++++++++++++++++++++++++++++++++++++++++++
	//Resource Roll
	//+++++++++++++++++++++++++++++++++++++++++++++++
	
	/**
	 * Can this player roll the dice now?
	 * 
	 * @param player
	 * 
	 * @pre None
	 * @post returns true iff the player is currently allowed to roll the dice
	 * 
	 */
	public boolean canRollDice(int player_index)
	{
		return (game_model.getTurnStatus(player_index) == TurnStatus.ROLLING);//TODO
	}
	
	/**
	 * Roll the dice and apply the consequences
	 * 
	 * 
	 * @param player
	 * @pre canRollDice() is true
	 * @post the dice are rolled and the consequences are applied to each player
	 * and the game state is advanced to the next turn phase
	 */
	public DataResponse RollDice(PlayerInfo player) throws Exception
	{
		DataResponse response = new DataResponse(Commands.ROLL_NUMBER,false);
		return response;
	}
	
	/**
	 * Gets the most recent roll of the dice to display in the GUI
	 * 
	 * @pre none 
	 * @post result = an array of integers representing the values on each of the 2 di
	 */
	public int[] getCurrentDiceValue()
	{
		int result[] = new int[2];
		
		return result;
	}
	
	//++++++++++++++++++++++++++++++++++++++++++++++
	//Robber Actions
	//++++++++++++++++++++++++++++++++++++++++++++++
	
	
	/**
	 * Gets the resources the player has
	 * 
	 * @param player
	 * @pre none
	 * @post result a list of resources that the player has
	 */
	public ResourceMultiSet getCurrentResources(int player)
	{
		Player pl = game_model.getPlayers()[player];	
		return pl.getResources(); 
	}
	
	/**
	 * 
	 * @param discard_list
	 * @pre the player was forced by a rolled 7 to discard half and 
	 * generated a list of half his resources to discard
	 * @post the chosen resources are returned to the game bank
	 */
	public DataResponse discardResources(ResourceMultiSet discard_list) throws Exception
	{
		DataResponse response = new DataResponse(Commands.DISCARD_CARDS,false);
		return response;
	}
	
	/**
	 * Says if that player can move the robber to that spot
	 * 
	 * @param location
	 * @param player_index
	 * @pre
	 * @post true iff the terrainhex is not the ocean and the player can move the robber
	 */
	public boolean canPlaceRobber(HexLocation location, int player_index)
	{
		GameMap game_map = game_model.getMap();
		Player player = game_model.getPlayers()[player_index];
		return game_map.canPutRobber(location) && (game_model.getTurnStatus(player_index) == TurnStatus.MOVEROBBER);
	}
	
	/**
	 * Move the Robber
	 * 
	 * @param hex
	 * @param player
	 * @pre the player just rolled a 7
	 * @post the robber is placed on the designated
	 *  hex and the player's turn phase is set to rob
	 */
	public DataResponse moveRobber(int player, TerrainHex hex) throws Exception
	{
		DataResponse response = new DataResponse(Commands.ROB_PLAYER,false);
		return response;
	}
	
	/**
	 * Get a list of players who can be robbed because of where the robber is
	 * 
	 * @pre none
	 * @post result = an array of indexes to the players that can be robbed. 
	 * Or an empty array if none are possible
	 */
	public DataResponse whoCanBeRobbed()
	{
		DataResponse response = new DataResponse(Commands.ROB_PLAYER,false);
		return response;
	}
	
	/**
	 * Rob the indicated player of a random card
	 * 
	 * @param robber
	 * @param robbed
	 * @pre The player is in the rob phase of their turn and have not yet robbed 
	 * @post The robbed player has one resource taken and given to 
	 * the robber player at random.
	 * @pre robber != robbed
	 * @post the turn phase is advanced
	 */
	public DataResponse rob(int robber, int robbed) throws Exception
	{
		DataResponse response = new DataResponse(Commands.ROB_PLAYER,false);
		return response;
	}
	
	//+++++++++++++++++++++++++++++++++++++++++++++++
	//Development Cards
	//+++++++++++++++++++++++++++++++++++++++++++++++
	
	/**
	 * Get the Develoment cards in that player's hand
	 * 
	 * 
	 * @param player
	 * @pre none
	 * @post Gets a list of the Development cards in the player's hand
	 */
	public DevCardList getDevelopmentCards(int player)
	{
		Player p =  game_model.getPlayers()[player];
		return p.getOldDevCards();
	}
	
	/**
	 * 
	 *  Play this development card
	 * 
	 * @param player
	 * @param dev_card
	 * @pre canPlayDevelopmentCard() is true 
	 * @post the effect of the development card is applied
	 */
	public DataResponse playDevelopmentCard(int player, DevCardList dev_card) throws Exception
	{
		DataResponse response = new DataResponse(Commands.PLAY_DEV_CARD,false);
		return response;
	}
	
	/**
	 * Can this player play this card right now?
	 * 
	 * 
	 * @param player
	 * @param dev_card
	 * @pre none
	 * @post True iff the player can legally play the card he specified now
	 */
	public boolean canPlayDevelopmentCard(int player_index, DevCardList dev_card)
	{
		if(player_index < 0 || player_index > 3)//Check it's a valid 
		{
			return false;
		}
		Player player = game_model.getPlayers()[player_index];
		if (!(dev_card.getTotalCards() == 1))
		{
			return false;
		}
		TurnTracker turn_tracker = game_model.getTurn_tracker();
		//TODO check its his turn
		return player.canPlayDevelopmentCard(dev_card) &&
				(game_model.getTurnStatus(player_index) == TurnStatus.PLAYING) ;
	}
	
	//+++++++++++++++++++++++++++++++++++++++++++++++
	// Resources and Trade
	//+++++++++++++++++++++++++++++++++++++++++++++++
	
	/**
	 * Get the resources this player has
	 * 
	 * @param player
	 * @pre This client is this player
	 * @post result = a list of resources this player has
	 */
	public ResourceMultiSet getResourcesOwnedBy(int player_index)
	{
		if(player_index < 0 || player_index > 3)
		{
			return null;
		}
		Player player = game_model.getPlayers()[player_index];
		return player.getResources();
	}
	
	/**
	 * Can this player Trade at the Wool Harbor?
	 * 
	 * @param player
	 * @pre none
	 * @post True iff this player could use the Wool Harbor legally at this time.
	 */
	public boolean canTradeAtWoolHarbor(int player_index, ResourceMultiSet trade_in_cards)
	{
		if(player_index < 0 || player_index > 3)
		{
			return false;
		}
		Player player = game_model.getPlayers()[player_index];
		GameMap map = game_model.getMap();
		Port[] valid_ports;
		try
		{
			valid_ports = map.getPortsAccessibleTo(player_index);
		}
		catch (Exception e)
		{
			System.err.print("Port list not found!");
			return false;
		}
		boolean matching_port = false;
		SheepPort example = new SheepPort();
		for( Port p : valid_ports)
		{
			if(p.getClass().equals(example.getClass()))
			{
				matching_port = true;
			}
		}
		return player.canAfford(trade_in_cards) && matching_port
				&& (game_model.getTurnStatus(player_index) == TurnStatus.PLAYING);
	}
	
	/**
	 * Trade at the Wool Harbor
	 * 
	 * @param player
	 * @param desired_card
	 * @pre canTradeAtWoolHarbor() is true.
	 * @pre desired_card is not wool
	 * @post the player is charged 2 wool and given the desired card
	 * 
	 */
	public DataResponse tradeAtWoolHarbor(int player, ResourceMultiSet desired_card) throws Exception
	{
		DataResponse response = new DataResponse(Commands.OFFER_TRADE,false);
		return response;
	}
	
	/**
	 * Can this player Trade at the Wood Harbor?
	 * 
	 * @param player
	 * @pre none
	 * @post True iff this player could use the Wood Harbor legally at this time.
	 */
	public boolean canTradeAtWoodHarbor(int player_index, ResourceMultiSet trade_in_cards)
	{
		if(player_index < 0 || player_index > 3)
		{
			return false;
		}
		Player player = game_model.getPlayers()[player_index];
		GameMap map = game_model.getMap();
		Port[] valid_ports;
		try
		{
			valid_ports = map.getPortsAccessibleTo(player_index);
		}
		catch (Exception e)
		{
			System.err.print("Port list not found!");
			return false;
		}
		boolean matching_port = false;
		WoodPort example = new WoodPort();
		for( Port p : valid_ports)
		{
			if(p.getClass().equals(example.getClass()))
			{
				matching_port = true;
			}
		}
		return player.canAfford(trade_in_cards) && matching_port
				&& (game_model.getTurnStatus(player_index) == TurnStatus.PLAYING);
	}
	
	/**
	 * Trade at the Wood Harbor
	 * 
	 * @param player
	 * @param desired_card
	 * @pre canTradeAtWoodHarbor() is true.
	 * @pre desired_card is not wood
	 * @post the player is charged 2 wood and given the desired card
	 * 
	 */
	public DataResponse tradeAtWoodHarbor(int player, ResourceMultiSet desired_card) throws Exception
	{
		DataResponse response = new DataResponse(Commands.ACCEPT_TRADE,false);
		return response;
	}
	
	/**
	 * Can this player Trade at the Ore Harbor?
	 * 
	 * @param player
	 * @pre none
	 * @post True iff this player could use the Ore Harbor legally at this time.
	 */
	public boolean canTradeAtOreHarbor(int player_index, ResourceMultiSet trade_in_cards)
	{
		if(player_index < 0 || player_index > 3)
		{
			return false;
		}
		Player player = game_model.getPlayers()[player_index];
		GameMap map = game_model.getMap();
		Port[] valid_ports;
		try
		{
			valid_ports = map.getPortsAccessibleTo(player_index);
		}
		catch (Exception e)
		{
			System.err.print("Port list not found!");
			return false;
		}
		boolean matching_port = false;
		OrePort example = new OrePort();
		for( Port p : valid_ports)
		{
			if(p.getClass().equals(example.getClass()))
			{
				matching_port = true;
			}
		}
		return player.canAfford(trade_in_cards) && matching_port 
				&& (game_model.getTurnStatus(player_index) == TurnStatus.PLAYING);
	}
	
	/**
	 * Trade at the Ore Harbor
	 * 
	 * @param player
	 * @param desired_card
	 * @pre canTradeAtOreHarbor() is true.
	 * @pre desired_card is not ore
	 * @post the player is charged 2 ore and given the desired card
	 * 
	 */
	public DataResponse tradeAtOreHarbor(int player, ResourceMultiSet desired_card) throws Exception
	{
		DataResponse response = new DataResponse(Commands.ACCEPT_TRADE,false);
		return response;
	}
	
	/**
	 * Can this player Trade at the Grain Harbor?
	 * 
	 * @param player
	 * @pre none
	 * @post True iff this player could use the Grain Harbor legally at this time.
	 */
	public boolean canTradeAtWheatHarbor(int player_index, ResourceMultiSet trade_in_cards)
	{
		if(player_index < 0 || player_index > 3)
		{
			return false;
		}
		Player player = game_model.getPlayers()[player_index];
		GameMap map = game_model.getMap();
		Port[] valid_ports;
		try
		{
			valid_ports = map.getPortsAccessibleTo(player_index);
		}
		catch (Exception e)
		{
			System.err.print("Port list not found!" + e.getMessage());
			return false;
		}
		boolean matching_port = false;
		WheatPort example = new WheatPort();
		for( Port p : valid_ports)
		{
			if(p.getClass().equals(example.getClass()))
			{
				matching_port = true;
			}
		}
		return player.canAfford(trade_in_cards) && matching_port 
				&& (game_model.getTurnStatus(player_index) == TurnStatus.PLAYING);
	}
	
	/**
	 * Trade at the Grain Harbor
	 * 
	 * @param player
	 * @param desired_card
	 * @pre canTradeAtGrainHarbor() is true.
	 * @pre desired_card is not grain
	 * @post the player is charged 2 grain and given the desired card
	 * 
	 */
	public DataResponse tradeAtWheatHarbor(int player, ResourceMultiSet desired_card) throws Exception
	{
		DataResponse response = new DataResponse(Commands.MARITIME_TRADE,false);
		return response;
	}
 	
	/**
	 * Can this player Trade at the Brick Harbor?
	 * 
	 * @param player
	 * @param trade_in_cards
	 * @pre none
	 * @post True iff this player could use the Brick Harbor legally at this time.
	 */
	public boolean canTradeAtBrickHarbor(int player_index, ResourceMultiSet trade_in_cards)
	{
		if(player_index < 0 || player_index > 3)
		{
			return false;
		}
		Player player = game_model.getPlayers()[player_index];
		GameMap map = game_model.getMap();
		Port[] valid_ports;
		try
		{
			valid_ports = map.getPortsAccessibleTo(player_index);
		}
		catch (Exception e)
		{
			System.err.print("Port list not found!");
			return false;
		}
		boolean matching_port = false;
		BrickPort example = new BrickPort();
		for( Port p : valid_ports)
		{
			if(p.getClass().equals(example.getClass()))
			{
				matching_port = true;
			}
		}
		return player.canAfford(trade_in_cards) && matching_port 
				&& (game_model.getTurnStatus(player_index) == TurnStatus.PLAYING);
	}
	
	/**
	 * Trade at the Brick Harbor
	 * 
	 * @param player
	 * @param desired_card
	 * @pre canTradeAtBrickHarbor() is true.
	 * @pre desired_card is not bricks
	 * @post the player is charged 2 bricks and given the desired card
	 * 
	 */
	public DataResponse tradeAtBrickHarbor(int player, ResourceMultiSet desired_card) throws Exception
	{
		DataResponse response = new DataResponse(Commands.BRICK_TRADE,false);
		return response;
	}
	
	/**
	 * Can this player offer a trade right now?
	 * 
	 * @pre none
	 * @post true iff the player can afford to offer this trade
	 */
	public boolean canOfferTrade(int player_index, ResourceMultiSet card_offered, ResourceMultiSet card_requested)
	{
		if(player_index < 0 || player_index > 3)
		{
			return false;
		}
		Player player = game_model.getPlayers()[player_index];
		return player.canAfford(card_offered) && (game_model.getTurnStatus(player_index) == TurnStatus.TRADING);
	}
	
	/**
	 * Can this player trade 3-to-1 on these parameters?
	 * 
	 * @param player
	 * @param trade_in_card
	 * @param desired_cards
	 * 
	 * @pre none
	 * @post True iff the player can legally make this trade at this time
	 * 
	 */
	public boolean canTradeAtMiscHarbor(int player_index, ResourceMultiSet trade_in_cards)
	{
		if(player_index < 0 || player_index > 3)
		{
			return false;
		}
		Player player = game_model.getPlayers()[player_index];
		GameMap map = game_model.getMap();
		Port[] valid_ports;
		try
		{
			valid_ports = map.getPortsAccessibleTo(player_index);
		}
		catch (Exception e)
		{
			System.err.print("Port list not found!");
			return false;
		}
		boolean matching_port = false;
		MiscPort example = new MiscPort();
		for( Port p : valid_ports)
		{
			if(p.getClass().equals(example.getClass()))
			{
				matching_port = true;
			}
		}
		return player.canAfford(trade_in_cards) && matching_port 
				&& (game_model.getTurnStatus(player_index) == TurnStatus.PLAYING);
	}
	
	/**
	 * 
	 * use the 3-to-1 trade at a miscilaneous harbor
	 * 
	 * @param trade_in_card
	 * @param desired_card
	 * @param player
	 * @pre canTradeAtMiscHarbor() is true. The trade-in list has only 3 of the same resourse to trade in
	 * @pre the desired card is a list of a single card different from what they traded in
	 * @post the player's resources are reduced by those 3 resources and increased by 1 of the desired card.
	 * 
	 */
	public DataResponse tradeAtMiscHarbor(int player, ResourceMultiSet trade_in_cards, ResourceMultiSet desired_card) throws Exception
	{
		DataResponse response = new DataResponse(Commands.MISC_TRADE,false);
		return response;
	}
	
	/**
	 * Can this player trade 4-to-1 on these parameters?
	 * 
	 * @param player
	 * @param trade_in_card
	 * @param desired_cards
	 * 
	 * @pre none
	 * @post True iff the player can legally make this trade at this time
	 * 
	 */
	public boolean canTradeFourToOne(int player_index, ResourceMultiSet trade_in_cards, ResourceMultiSet desired_cards)
	{
		if(player_index < 0 || player_index > 3)
		{
			return false;
		}
		Player player = game_model.getPlayers()[player_index];
		return player.canAfford(trade_in_cards) && (game_model.getTurnStatus(player_index) 
				== TurnStatus.PLAYING);
	}
	
	/**
	 * 
	 * use the 4-to-1 trade
	 * 
	 * @param trade_in_card
	 * @param desired_card
	 * @param player
	 * @pre canTradeFourToOne() is true. The trade in has only 4 of the same resourse to trade in
	 * @pre the desired card is a list with a single card different from what they traded in
	 * @post the player's resources are reduced by those 4 resources and increased by 1 of the desired card.
	 * 
	 */
	public DataResponse tradeFourToOne(int player, ResourceMultiSet trade_in_card, ResourceMultiSet desired_card) throws Exception
	{
		DataResponse response = new DataResponse(Commands.ACCEPT_TRADE,false);
		return response;
	}
	
	//+++++++++++++++++++++++++++++++++++++++++++++++
	// Server Communication and Model Instantiation
	//+++++++++++++++++++++++++++++++++++++++++++++++
	
	/**
	 * Get the version number of the model
	 * 
	 * @pre none
	 * @post result = the model number of the current version of the model
	 */
	public int getLatestModelNum()
	{
		return game_model.getVersion();
	}
	
	/**
	 * Update the model from the JSON string
	 * 
	 * @param json_model
	 * @pre the server has sent an updated version of the model
	 * which must replace the current model
	 * @post The model matches the data sent in the JSON
	 */
	public DataResponse updateModelFromJSON(String json_model) throws Exception
	{
		DataResponse response = new DataResponse(Commands.FINISH_TURN,false);
		return response;
	}
	
	/**
	 * Serializes the entire model
	 * 
	 * @pre None
	 * @post result is a serialized version of the entire model
	 */
	private String serializeModel()
	{
		return "NOT IMPLEMENTED";
	}
	//===========================
	//Records and Chat
	//===========================
	
	/**
	 * Gets the current game messages
	 * 
	 * @pre none
	 * @post result = an array of the messages sent this game
	 */
	public MessageLine[] getMessages()
	{
		return null;
	}
	
	/**
	 * Gets the current game log
	 * 
	 * @pre none
	 * @post result = an array of the logs logged this game
	 */
	public MessageLine[] getLog()
	{
		return null;
	}
	
	/**
	 * Adds a message to the game messages
	 * 
	 * @param message
	 * @pre none
	 * @post the message is added to the list
	 * 
	 */
	public void addMessage(MessageLine message) 
	{
		
	}
	
	/**
	 * Adds a log to the game logs
	 * 
	 * @param log
	 * @pre none
	 * @post the log is added to the log list
	 * 
	 */
	public void addLog(MessageLine log)
	{
		
	}
	
	/**
	 * Who has the longest road?
	 * This info is stored in the turn-tracker class
	 * The idea for this function came from another
	 * group in another section who were penalized for
	 * not having this in their design
	 * 
	 * @pre none
	 * @post result = the player index with the most roads played
	 * or -1 if nobody controlls that honor
	 */
	public int whoLongestRoad()
	{
		TurnTracker tst = game_model.getTurn_tracker();
		return tst.getLongest_road_player();
	}
	
	/**
	 * Who has the largest army?
	 * This info is stored in the turn-tracker class
	 * The idea for this function came from another
	 * group in another section who were penalized for
	 * not having this in their design
	 * 
	 * @pre none
	 * @post result = the player index with the largest army
	 * or -1 if nobody controls that honor
	 */
	public int whoLargestArmy()
	{
		return game_model.whoLargestArmy();
	}
	
	/**
	 * Tells whether the player can use ANY port
	 * 
	 * @pre none
	 * 
	 * @post true iff the player has a city or settlement at a port
	 */
	public boolean canMaritimeTrade(int player_index)
	{
		return this.canTradeAtBrickHarbor(player_index, new ResourceMultiSet())
				|| canTradeAtWoolHarbor(player_index, new ResourceMultiSet())
				|| canTradeAtWheatHarbor(player_index, new ResourceMultiSet())
				|| canTradeAtOreHarbor(player_index, new ResourceMultiSet())
				|| canTradeAtWoodHarbor(player_index, new ResourceMultiSet())
				|| canTradeAtMiscHarbor(player_index, new ResourceMultiSet());
	}
	
	/**
	 * Whether the player play a monument card
	 * @param player_index
	 * @pre none
	 * @post true iff the player has that card and can legally play it
	 * @post also it is his turn
	 */
	public boolean canUseMonument(int player_index)
	{
		TurnTracker tt = game_model.getTurn_tracker();
		if(player_index < 0 || player_index > 3)
		{
			return false;
		}
		Player player = game_model.getPlayers()[player_index];
		return player.canPlayMonument() && tt.turnStatusOf(player_index) == TurnStatus.PLAYING;
	}
	
	/**
	 * Whether the player play a monopoly card
	 * @param player_index
	 * @pre none
	 * @post true iff the player has that card and can legally play it
	 * @post also it is his turn
	 */
	public boolean canUseMonopoly(int player_index)
	{
		TurnTracker tt = game_model.getTurn_tracker();
		if(player_index < 0 || player_index > 3)
		{
			return false;
		}
		Player player = game_model.getPlayers()[player_index];
		return player.canPlayMonopoly() && tt.turnStatusOf(player_index) == TurnStatus.PLAYING;
	}
	
	
	/**
	 * Whether the player play a Soldier card
	 * @param player_index
	 * @pre none
	 * @post true iff the player has that card and can legally play it
	 * @post also it is his turn
	 */
	public boolean canUseSoldier(int player_index)
	{
		TurnTracker tt = game_model.getTurn_tracker();
		if(player_index < 0 || player_index > 3)
		{
			return false;
		}
		Player player = game_model.getPlayers()[player_index];
		return player.canPlaySoldier() && tt.turnStatusOf(player_index) == TurnStatus.PLAYING;
	}
	
	/**
	 * Says whether this player can play a year-of-plenty card right now.
	 * @param player_index
	 * @pre none
	 * @post true iff the player is in the playing phase of his turn and has that card and has not 
	 * already played another.
	 */
	public boolean canUseYearOfPlenty(int player_index)
	{
		TurnTracker tt = game_model.getTurn_tracker();
		if(player_index < 0 || player_index > 3)
		{
			return false;
		}
		Player player = game_model.getPlayers()[player_index];
		return player.canPlayYearOfPlenty() && tt.turnStatusOf(player_index) == TurnStatus.PLAYING;
	}
	
	/**
	 * Says whether this player can play a road_building card right now.
	 * @param player_index
	 * @pre none
	 * @post true iff the player is in the playing phase of his turn and has that card and has not 
	 * already played another.
	 */
	public boolean canUseRoadBuilding(int player_index)
	{
		TurnTracker tt = game_model.getTurn_tracker();
		if(player_index < 0 || player_index > 3)
		{
			return false;
		}
		Player player = game_model.getPlayers()[player_index];
		return player.canPlayRoadBuilding() && tt.turnStatusOf(player_index) == TurnStatus.PLAYING;
	}
	
	/**
	 * Says whether this player can finish his turn right now.
	 * @param player_index
	 * @pre none
	 * @post true iff the player is in the playing phase of his turn
	 */
	public boolean canFinishTurn(int player_index)
	{
		TurnTracker tt = game_model.getTurn_tracker();
		return tt.turnStatusOf(player_index) == TurnStatus.PLAYING;
	}
	
	/**
	 * Says whether this player can accept the offered trade.
	 * @param player_index
	 * @pre none
	 * @post true iff the player is in the playing phase of his turn
	 */
	public boolean canAcceptTrade(int player_index)
	{
		if(player_index < 0 || player_index > 3)
		{
			return false;
		}
		TradeOffer offer = game_model.getTrade_offer();
		if(offer == null)
		{
			return false;
		}
		Player player = game_model.getPlayers()[player_index];
		boolean can_afford = player.canAfford(offer.getReciever_gives());
		TurnTracker tt = game_model.getTurn_tracker();
		return tt.turnStatusOf(player_index) == TurnStatus.TRADING && can_afford;
	}
	
	/**
	 * whether the player can send a message
	 * @param player_index
	 * @param Message
	 * @pre none
	 * @post returns true if this is a valid player in the game. 
	 * All valid players may send a message at any time.
	 */
	public boolean canSendMessage(int player_index, String Message)
	{
		if(player_index < 0 || player_index > 3)
		{
			return false;
		}
		return true;
	}
	
	/**
	 * Whether the player can discard these cards
	 * @param player_index
	 * @param to_discard
	 * @pre none
	 * @post True iff the player is in the discard phase and has these cards
	 */
	public boolean canDiscardCards(int player_index, ResourceMultiSet to_discard)
	{
		if(player_index < 0 || player_index > 3)
		{
			return false;
		}
		Player player = game_model.getPlayers()[player_index];
		boolean can_afford = player.canAfford(to_discard);
		TurnTracker tt = game_model.getTurn_tracker();
		return tt.turnStatusOf(player_index) == TurnStatus.DISCARDING && can_afford;
	}
	
	/**
	 * used for testing purposes only
	 * @pre none
	 * @post none
	 */
	public Game getModel()
	{
		return game_model;
	}
}
	

	
	
	
	
	

