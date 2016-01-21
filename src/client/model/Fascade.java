package client.model;
import client.data.*;

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
	public DevelopmentCard buyDevelopmentCard(PlayerInfo player)
	{
		return null;//TODO
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
		return new int[-1,-1];
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
	public List<ResourceCard> getCurrentResources(PlayerInfo player)
	{
		return void; //TODO
	}
	
	/**
	 * 
	 */
	public void discardResources(List<ResourceCard>)
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
	public List<PlayerInfo> whoCanBeRobbedBy(PlayerInfo player)
	{
		return null;//TODO
	}
	
	/**
	 * 
	 * @param player
	 * @return
	 */
	public ResourceCard rob(PlayerInfo player)
	{
		return null; //TODO
	}
	
	//+++++++++++++++++++++++++++++++++++++++++++++++
	//Development Cards
	//+++++++++++++++++++++++++++++++++++++++++++++++
	
	/**
	 * 
	 * @param player
	 * @return
	 */
	public List<DevelopmentCard> getDevelopmentCards(PlayerInfo player)
	{
		
	}
	
	/**
	 * 
	 * @param player
	 * @param dev_card
	 */
	public void playDevelopmentCard(PlayerInfo player, DevelopmentCard dev_card)
	{
		
	}
	
	/**
	 * 
	 * @param player
	 * @param dev_card
	 * @return
	 */
	public boolean canPlayDevelopmentCard(PlayerInfo player, DevelopmentCard dev_card)
	{
		
	}
	
	//+++++++++++++++++++++++++++++++++++++++++++++++
	// Resources and Trade
	//+++++++++++++++++++++++++++++++++++++++++++++++
	
	/**
	 * 
	 * @param player
	 * @return
	 */
	public List<ResourceCard> getResourcesOwnedBy(PlayerInfo player)
	{
		
		return null; //TODO
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean canTradeAtWoolHarbor()
	{
		
	}
	
	/**
	 * 
	 * @param desired_card
	 */
	public void tradeAtWoolHarbor(ResourceCard desired_card) 
	{
		
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean canTradeAtLumberHarbor()
	{
		
	}
	
	/**
	 * 
	 * @param desired_card
	 */
	public void tradeAtLumberHarbor(ResourceCard desired_card)
	{
		
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean canTradeAtStoneHarbor()
	{
		
	}
	
	/**
	 * 
	 * @param desired_card
	 */
	public void tradeAtStoneHarbor(ResourceCard desired_card)
	{
		
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean canTradeAtGrainHarbor()
	{
		
	}
	
	/**
	 * 
	 * @param desired_card
	 */
	public void tradeAtGrainHarbor(ResourceCard desired_card)
	{
		
	}
 	
	/**
	 * 
	 * @return
	 */
	public boolean canTradeAtBrickHarbor()
	{
		
	}
	
	/**
	 * 
	 * @param desired_card
	 */
	public void tradeAtBrickHarbor(ResourceCard desired_card)
	{
		
	}
	
	/**
	 * 
	 * @param trade_in_card
	 * @return
	 */
	public boolean canTradeAtMiscHarbor(ResourceCard trade_in_card)
	{
		
	}
	
	/**
	 * 
	 * @param trade_in_card
	 * @param desired_card
	 */
	public void tradeAtMiscHarbor(ResourceCard trade_in_card, ResourceCard desired_card)
	{
		
	}
	
	/**
	 * 
	 * @param trade_in_card
	 */
	public void canTradeFourToOne(ResourceCard trade_in_card)
	{
		return;//TODO
	}
	
	/**
	 * 
	 * @param trade_in_card
	 * @param desired_card
	 */
	public void tradeFourToOne(ResourceCard trade_in_card, ResourceCard desired_card)
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
	
	public String getModelAsJSON()
	{
		return "STUB"; //TODO
	}
	
	public void updateModelFromJSON(String json_model)
	{
		//TODO
	}
}
	
	
	
	
	
	

