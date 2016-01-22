package shared.model;
import client.data.*;
import java.util.*;

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
	public boolean canBuildRoad(PlayerInfo player, Edge edge)
	{
		return false;//TODO
	}
	
	/**
	 * Orders the construction of a road on that edge.
	 * 
	 * @param player
	 * @param road_position
	 * 
	 * @pre canBuildRoad() returns true
	 * @post a road is built for that player on that edge. The server is notified.
	 * @post The player's resources are reduced by 1 wood and 1 brick
	 */
	public void buildRoadAt(PlayerInfo player, Edge edge) throws Exception
	{
		//TODO
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
	public boolean canBuyDevelopmentCard(PlayerInfo player)
	{
		return false;//TODO
	}
	
	/**
	 * Buy a development card 
	 * @param player
	 * 
	 * @pre PlayerInfo can buy a development card (see previous function)
	 * @post the player has a new development card.
	 * @post The player's resources are reduced by 1 food and 1 ore and 1 wool
	 */
	public void buyDevelopmentCard(PlayerInfo player) throws Exception
	{
		return;//TODO
	}
	
	/**
	 * Can this player build a settlement here?
	 * 
	 * @param player
	 * @param location
	 * @pre the player is playing the game
	 * @post returns true iff the player can legally put a settlement on that location
	 */
	public boolean canBuildSettlement(PlayerInfo player, Vertex location)
	{
		return false; //TODO
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
	public void buildSettlement(PlayerInfo player, Vertex location) throws Exception
	{
		return;//TODO
	}
	
	/**
	 * Can this player build a city there?
	 * 
	 * @param player
	 * @param location
	 * @pre None
	 * @post result = true iff building a city on that vertex is valid 
	 */
	public boolean canBuildCity(PlayerInfo player, Vertex location)
	{
		return false;
	}
	
	/**
	 * Build a city on the Settlement that was previously there
	 * 
	 * @param player
	 * @param location
	 * @pre canBuildCity() is true
	 * @post a City replaces the player's settlement on that spot.
	 * @post The player's resources are reduced by 2 food and 3 ore
	 * 
	 */
	public void BuildCity(PlayerInfo player, Vertex location) throws Exception
	{
		//TODO
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
	public boolean canRollDice(PlayerInfo player)
	{
		return false;//TODO
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
	public int RollDice(PlayerInfo player) throws Exception
	{
		return -1; //TODO
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
	public ResourceList getCurrentResources(PlayerInfo player)
	{
		return null; //TODO
	}
	
	/**
	 * 
	 * @param discard_list
	 * @pre the player was forced by a rolled 7 to discard half and 
	 * generated a list of half his resources to discard
	 * @post the chosen resources are returned to the game bank
	 */
	public void discardResources(ResourceList discard_list) throws Exception
	{
		return; //TODO
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
	public void moveRobber(int player, TerrainHex hex) throws Exception
	{
		//TODO
	}
	
	/**
	 * Get a list of players who can be robbed because of where the robber is
	 * 
	 * @pre none
	 * @post result = an array of indexes to the players that can be robbed. 
	 * Or an empty array if none are possible
	 */
	public int[] whoCanBeRobbed()
	{
		return null;//TODO
	}
	
	/**
	 * Rob the indicated player of a random specified card
	 * 
	 * @param robber
	 * @param robbed
	 * @pre The player is in the rob phase of their turn and have not yet robbed 
	 * @post The robbed player has one resource taken and given to 
	 * the robber player at random.
	 * @pre robber != robbed
	 * @post the turn phase is advanced
	 */
	public void rob(int robber, int robbed) throws Exception
	{
		return; //TODO
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
		return null;//TODO
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
	public void playDevelopmentCard(int player, DevCardList dev_card) throws Exception
	{
		//TODO
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
	public boolean canPlayDevelopmentCard(int player, DevCardList dev_card)
	{
		return false;//TODO
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
	public ResourceList getResourcesOwnedBy(int player)
	{
		
		return null; //TODO
	}

	
	/**
	 * Trade at the Food Harbor
	 * 
	 * @param player
	 * @param desired_card
	 * @pre canTradeAtFoodHarbor() is true.
	 * @pre desired_card is not food
	 * @post the player is charged 2 food and given the desired card
	 * 
	 */
	public void tradeAtFoodHarbor(int player, ResourceList desired_card) throws Exception
	{
		//TODO
	}
	
	/**
	 * Can this player Trade at the Food Harbor?
	 * 
	 * @param player
	 * @pre none
	 * @post True iff this player could use the Food Harbor legally at this time.
	 */
	public boolean canTradeAtFoodHarbor(int player)
	{
		return false;//TODO
	}
	
	/**
	 * Can this player Trade at the Wool Harbor?
	 * 
	 * @param player
	 * @pre none
	 * @post True iff this player could use the Wool Harbor legally at this time.
	 */
	public boolean canTradeAtWoolHarbor(int player)
	{
		return false;//TODO
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
	public void tradeAtWoolHarbor(int player, ResourceList desired_card) throws Exception
	{
		//TODO
	}
	
	/**
	 * Can this player Trade at the Wood Harbor?
	 * 
	 * @param player
	 * @pre none
	 * @post True iff this player could use the Wood Harbor legally at this time.
	 */
	public boolean canTradeAtWoodHarbor(int player)
	{
		return false;//TODO
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
	public void tradeAtWoodHarbor(int player, ResourceList desired_card) throws Exception
	{
		
	}
	
	/**
	 * Can this player Trade at the Ore Harbor?
	 * 
	 * @param player
	 * @pre none
	 * @post True iff this player could use the Ore Harbor legally at this time.
	 */
	public boolean canTradeAtOreHarbor(int player)
	{
		return false;//TODO
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
	public void tradeAtOreHarbor(int player, ResourceList desired_card) throws Exception
	{
		
	}
	
	/**
	 * Can this player Trade at the Grain Harbor?
	 * 
	 * @param player
	 * @pre none
	 * @post True iff this player could use the Grain Harbor legally at this time.
	 */
	public boolean canTradeAtGrainHarbor(int player)
	{
		return false;
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
	public void tradeAtGrainHarbor(int player, ResourceList desired_card) throws Exception
	{
		
	}
 	
	/**
	 * Can this player Trade at the Brick Harbor?
	 * 
	 * @param player
	 * @pre none
	 * @post True iff this player could use the Brick Harbor legally at this time.
	 */
	public boolean canTradeAtBrickHarbor(int player)
	{
		return false;
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
	public void tradeAtBrickHarbor(int player, ResourceList desired_card) throws Exception
	{
		
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
	public boolean canTradeAtMiscHarbor(ResourceList trade_in_card)
	{
		return false;
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
	public void tradeAtMiscHarbor(int player, ResourceList trade_in_cards, ResourceList desired_card) throws Exception
	{
		
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
	public boolean canTradeFourToOne(int player, ResourceList trade_in_cards, ResourceList desired_cards)
	{
		return false;//TODO
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
	public void tradeFourToOne(int player, ResourceList trade_in_card, ResourceList desired_card) throws Exception
	{
		//TODO
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
		return -1; //TODO
	}
	
	/**
	 * Update the model from the JSON string
	 * 
	 * @param json_model
	 * @pre the server has sent an updated version of the model
	 * which must replace the current model
	 * @post The model matches the data sent in the JSON
	 */
	public void updateModelFromJSON(String json_model) throws Exception
	{
		//TODO
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
	 * 
	 * @return
	 */
	public MessageLine[] getMessages()
	{
		return null;
	}
	
	
	public MessageLine[] getLog()
	{
		return null;
	}
	
	public void addMessage(MessageLine message) 
	{
		
	}
	
	public void addLog(MessageLine log)
	{
		
	}
}
	
	
	
	
	
	

