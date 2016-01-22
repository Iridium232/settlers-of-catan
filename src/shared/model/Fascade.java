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
	public void buildRoadAt(PlayerInfo player, Edge edge)
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
	public void buyDevelopmentCard(PlayerInfo player)
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
	public void buildSettlement(PlayerInfo player, Vertex location)
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
	public void BuildCity(PlayerInfo player, Vertex location)
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
	public int RollDice(PlayerInfo player)
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
	public void discardResources(ResourceList discard_list)
	{
		return; //TODO
	}
	
	/**
	 * Move the Robber
	 * 
	 * @param hex
	 * @pre the player just rolled a 7
	 * @post the robber is placed on the designated
	 *  hex and the player's turn phase is set to rob
	 */
	public void moveRobber(TerrainHex hex)
	{
		//TODO
	}
	
	/**
	 * 
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
	 * Rob the indicated player of the specified card
	 * 
	 * @param robber
	 * @param robbed
	 * @pre The player is in the rob phase of their turn and have not yet robbed 
	 * @post The robbed player has one resource taken and given to 
	 * the robber player at random.
	 * @post the turn phase is advanced
	 */
	public void rob(int robber, int robbed)
	{
		return; //TODO
	}
	
	//+++++++++++++++++++++++++++++++++++++++++++++++
	//Development Cards
	//+++++++++++++++++++++++++++++++++++++++++++++++
	
	/**
	 * 
	 * @param player
	 * @return
	 */
	public DevCardList getDevelopmentCards(Player player)
	{
		return null;//TODO
	}
	
	/**
	 * 
	 * @param player
	 * @param dev_card
	 */
	public void playDevelopmentCard(Player player, DevCardList dev_card)
	{
		//TODO
	}
	
	/**
	 * 
	 * @param player
	 * @param dev_card
	 * @return
	 */
	public boolean canPlayDevelopmentCard(Player player, DevCardList dev_card)
	{
		return false;//TODO
	}
	
	//+++++++++++++++++++++++++++++++++++++++++++++++
	// Resources and Trade
	//+++++++++++++++++++++++++++++++++++++++++++++++
	
	/**
	 * 
	 * @param player
	 * @return
	 */
	public ResourceList getResourcesOwnedBy(PlayerInfo player)
	{
		
		return null; //TODO
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean canTradeAtWoolHarbor()
	{
		return false;//TODO
	}
	
	/**
	 * 
	 * @param desired_card
	 */
	public void tradeAtWoolHarbor(ResourceList desired_card) 
	{
		//TODO
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean canTradeAtLumberHarbor()
	{
		return false;//TODO
	}
	
	/**
	 * 
	 * @param desired_card
	 */
	public void tradeAtLumberHarbor(ResourceList desired_card)
	{
		
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean canTradeAtStoneHarbor()
	{
		return false;//TODO
	}
	
	/**
	 * 
	 * @param desired_card
	 */
	public void tradeAtStoneHarbor(ResourceList desired_card)
	{
		
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean canTradeAtGrainHarbor()
	{
		return false;
	}
	
	/**
	 * 
	 * @param desired_card
	 */
	public void tradeAtGrainHarbor(ResourceList desired_card)
	{
		
	}
 	
	/**
	 * 
	 * @return
	 */
	public boolean canTradeAtBrickHarbor()
	{
		return false;
	}
	
	/**
	 * 
	 * @param desired_card
	 */
	public void tradeAtBrickHarbor(ResourceList desired_card)
	{
		
	}
	
	/**
	 * 
	 * @param trade_in_card
	 * @return
	 */
	public boolean canTradeAtMiscHarbor(ResourceList trade_in_card)
	{
		return false;
	}
	
	/**
	 * 
	 * @param trade_in_card
	 * @param desired_card
	 */
	public void tradeAtMiscHarbor(ResourceList trade_in_cards, ResourceList desired_card)
	{
		
	}
	
	/**
	 * 
	 * @param trade_in_card
	 */
	public boolean canTradeFourToOne(ResourceList trade_in_cards, ResourceList desired_cards)
	{
		return false;//TODO
	}
	
	/**
	 * 
	 * @param trade_in_card
	 * @param desired_card
	 */
	public void tradeFourToOne(ResourceList trade_in_card, ResourceList desired_card)
	{
		//TODO
	}
	
	//+++++++++++++++++++++++++++++++++++++++++++++++
	// Server Communication and Model Instantiation
	//+++++++++++++++++++++++++++++++++++++++++++++++
	
	public int getLatestModelNum()
	{
		return -1; //TODO
	}
	
	/**
	 * 
	 * @param json_model
	 */
	public void updateModelFromJSON(String json_model)
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
	
	
}
	
	
	
	
	
	

