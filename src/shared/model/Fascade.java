package shared.model;
import client.data.*;
import java.util.*;

/**
 * The Fascade class handles all communication and commands to and from the game model.
 * 
 */
public class Fascade 
{
	//+++++++++++++++++++++++++++++++++++++++++++++++
	//Purchases and Placement
	//+++++++++++++++++++++++++++++++++++++++++++++++
	/**
	 * 
	 * @param player
	 * @param edge
	 * @return
	 */
	public boolean canBuildRoad(PlayerInfo player, Edge edge)
	{
		return false;//TODO
	}
	
	/**
	 * 
	 * 
	 * @param player
	 * @param road_position
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
	 * 
	 * @param player
	 * 
	 * @pre PlayerInfo can buy a development card (see previous function)
	 * @post result = the development card purchased by the player
	 */
	public void buyDevelopmentCard(PlayerInfo player)
	{
		return;//TODO
	}
	
	/**
	 * 
	 * @param player
	 * @param location
	 * @return
	 */
	public boolean canBuildSettlement(PlayerInfo player, Vertex location)
	{
		return false; //TODO
	}
	
	/**
	 * 
	 * @param player
	 * @param location
	 */
	public void buildSettlement(PlayerInfo player, Vertex location)
	{
		return;//TODO
	}
	
	/**
	 * 
	 * @param player
	 * @param location
	 * @return
	 */
	public boolean canBuildCity(PlayerInfo player, Vertex location)
	{
		return false;
	}
	
	/**
	 * 
	 * @param player
	 * @param location
	 */
	public void BuildCity(PlayerInfo player, Vertex location)
	{
		//TODO
	}
	
	//+++++++++++++++++++++++++++++++++++++++++++++++
	//Resource Roll
	//+++++++++++++++++++++++++++++++++++++++++++++++
	
	/**
	 * 
	 * @param player
	 * @return
	 */
	public boolean canRollDice(PlayerInfo player)
	{
		return false;//TODO
	}
	
	/**
	 * 
	 * @param player
	 * @return
	 */
	public int RollDice(PlayerInfo player)
	{
		return -1; //TODO
	}
	
	/**
	 * 
	 * 
	 * @return
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
	 * 
	 * @return
	 */
	public boolean mustDiscardHalf(PlayerInfo player)
	{
		return false;
	}
	
	/**
	 * 
	 * @param player
	 * @return
	 */
	public ResourceList getCurrentResources(PlayerInfo player)
	{
		return null; //TODO
	}
	
	/**
	 * 
	 */
	public void discardResources(ResourceList discard_list)
	{
		return; //TODO
	}
	
	/**
	 * 
	 * @param player
	 * @return
	 */
	public boolean shouldMoveRobber(PlayerInfo player)
	{
		return false;//TODO
	}
	
	/**
	 * 
	 * @param hex
	 */
	public void moveRobber(TerrainHex hex)
	{
		//TODO
	}
	
	/**
	 * 
	 * @param player
	 * @return
	 */
	public boolean canRobSomeone(PlayerInfo player)
	{
		return false; //TODO
	}
	
	/**
	 * 
	 * @param player
	 * @return
	 */
	public ArrayList<Player> whoCanBeRobbedBy(PlayerInfo player)
	{
		return null;//TODO
	}
	
	/**
	 * 
	 * @param player
	 * @return
	 */
	public void rob(Player robber, Player robbed)
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
	
	public void updateModelFromJSON(String json_model)
	{
		//TODO
	}
	
	private String serializeModel()
	{
		return "NOT IMPLEMENTED";
	}
	
	
}
	
	
	
	
	
	

