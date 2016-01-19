package client.model;

public class Fascade 
{
	//+++++++++++++++++++++++++++++++++++++++++++++++
	//Purchases and Placement
	//+++++++++++++++++++++++++++++++++++++++++++++++
	/**
	 * 
	 * @param player
	 * @param road_position
	 * @return
	 */
	public boolean canBuildRoad(Player player, Vertex road_from, Vertex road_to)
	{
		return false;//TODO
	}
	
	/**
	 * 
	 * @param player
	 * @param road_position
	 */
	public void buildRoadAt(Player player, Vertex road_from, Vertex road_to)
	{
		//TODO
	}
	
	/**
	 * 
	 * @param player
	 * @return
	 */
	public boolean canBuyProgressCard(Player player)
	{
		return false;//TODO
	}
	
	/**
	 * 
	 * @return
	 */
	public ProgressCard buyProgressCard(Player)
	{
		return null;//TODO
	}
	
	/**
	 * 
	 * @param player
	 * @param location
	 * @return
	 */
	public boolean canBuildSettlement(Player player, Vertex location)
	{
		return false; //TODO
	}
	
	/**
	 * 
	 * @param player
	 * @param location
	 */
	public void buildSettlement(Player player, Vertex location)
	{
		return;//TODO
	}
	
	/**
	 * 
	 * @param player
	 * @param location
	 * @return
	 */
	public boolean canBuildCity(Player player, Vertex location)
	{
		return false;
	}
	
	/**
	 * 
	 * @param player
	 * @param location
	 */
	public void BuildCity(Player player, Vertex location)
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
	public boolean canRollDice(Player player)
	{
		return false;//TODO
	}
	
	/**
	 * 
	 * @param player
	 * @return
	 */
	public int RollDice(Player player)
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
	public boolean mustDiscardHalf(Player player)
	{
		return false;
	}
	
	/**
	 * 
	 * @param player
	 * @return
	 */
	public List<ResourceCard> getCurrentResources(Player player)
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
	public boolean shouldMoveRobber(Player player)
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
	public boolean canRobSomeone(Player player)
	{
		return false; //TODO
	}
	
	/**
	 * 
	 * @param player
	 * @return
	 */
	public List<Player> whoCanBeRobbedBy(Player player)
	{
		return null;//TODO
	}
	
	/**
	 * 
	 * @param player
	 * @return
	 */
	public ResourceCard rob(Player player)
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
	public List<DevelopmentCard> getDevelopmentCards(Player player)
	{
		
	}
	
	/**
	 * 
	 * @param player
	 * @param dev_card
	 */
	public void playDevelopmentCard(Player player, DevelopmentCard dev_card)
	{
		
	}
	
	/**
	 * 
	 * @param player
	 * @param dev_card
	 * @return
	 */
	public boolean canPlayDevelopmentCard(Player player, DevelopmentCard dev_card)
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
	public List<ResourceCard> getResourcesOwnedBy(Player player)
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
	
	
	
	
	
	

