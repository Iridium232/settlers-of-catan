package shared.model.player;

import client.data.RobPlayerInfo;
import shared.communication.toServer.moves.BuildSettlement;
import shared.communication.toServer.moves.BuyDevCard;
import shared.definitions.DevCardType;
import shared.definitions.PieceType;
import shared.definitions.ResourceType;

/**
 * Michael Rhodes
 * CS 340
 * Section 2
 * Dr. Woodfield
 */
public class Player {

    public BuyDevCard player_buy_devcard;
    /**
     *  How many cities this player has left to play
     */
    private int cities;
    /**
     *  The color of this player
     */
    private String color;
    /**
     *  Whether this player has discarded or not already this discard phase
     */
    private boolean discarded;
    /**
     *  How many monuments this player has
     */
    private int monuments;
    /**
     *  The name of this player
     */
    private String name = null;
    /**
     *  The dev cards the player bought this turn
     */
    private DevCardList newDevCards;
    /**
     *  The dev cards the player had when the turn started
     */
    private DevCardList oldDevCards;
    /**
     *  What place in the array this player is: 0-3. It determines their turn order
     */
    private int playerIndex;
    /**
     *  Whether the player has played a dev card this turn
     */
    private boolean playedDevCard;
    /**
     *  The unique playerID. This is used to pick the client player apart from the others
     */
    private int playerID;
    /**
     *  The resource cards this player has
     */
    private ResourceMultiSet resources = new ResourceMultiSet();
    /**
     *  How many roads this player has left to play
     */
    private int roads;
    /**
     *  How many settlements this player has left to play
     */
    private int settlements;
    /**
     *  How many soldiers this player has played
     */
    private int soldiers;
    /**
     *  How many victory points this player has
     */
    private int victoryPoints;

    //******************************************* Constructors *******************************************************//

    /**
     * Default constructor. Sets variables with values expected for a player just beginning a game. Has null values for
     * player specific variable like name and playerID.
     */
    public Player() {
        this.cities = 4;
        this.color = null;
        this.discarded = false;
        this.name = null;
        this.monuments = 0;
        this.newDevCards = new DevCardList();
        this.oldDevCards = new DevCardList();
        this.playerIndex = 0;
        this.playedDevCard = false;
        this.playerID = 0;
        this.resources = new ResourceMultiSet();
        this.roads = 15;
        this.settlements = 5;
        this.soldiers = 0;
        this.victoryPoints = 0;
        this.player_buy_devcard = new BuyDevCard(0);
    }

    /**
     *
     * Constructor that sets variables with values expected for a player just beginning a game. Expects parameters for
     * player specific variables like name and playerID.
     *
     * @param color The color of this player
     * @param name  The name of this player
     * @param playerIndex   What place in the array this player is: 0-3. It determines their turn order
     * @param playerID  The unique playerID. This is used to pick the client player apart from the others
     */
    public Player(String color, String name, int playerIndex, int playerID) 
    {
        this.color = color;
        this.name = name;
        this.playerIndex = playerIndex;
        this.playerID = playerID;

        this.cities = 4;
        this.discarded = false;
        this.monuments = 0;
        this.newDevCards = new DevCardList();
        this.oldDevCards = new DevCardList();
        this.playedDevCard = false;
        this.resources = new ResourceMultiSet();
        this.roads = 15;
        this.settlements = 5;
        this.soldiers = 0;
        this.victoryPoints = 0;
    }
    
    /**
     * WARNING this constructor is only to pass information
     *  to the server proxy and created a stub object
     * @param victim
     */
    public Player(RobPlayerInfo victim) 
    {
		this.playerIndex = victim.getPlayerIndex();
	}

    /**
     * Do not use this. It is only for functions that need a player 
     * parameter to get its index only
     * @param victimIndex
     */
	public Player(int victimIndex) 
	{
		this.playerIndex = victimIndex;
	}

	/**
     * Tells whether this player can afford to buy something with this cost
     * @pre none
     * @post result = true iff the player has this many of the required resources
     * and has the piece nessesary in his collection
     */
    public boolean canAfford(ResourceMultiSet cost, PieceType type)
    {
    	return false;
    }
    
    /**
     * Tells whether this player can afford this cost
     * @pre none
     * @post result = true iff the player has this many of the required resources
     */
    public boolean canAfford(ResourceMultiSet cost)
    {
    	boolean result = resources.canAfford(cost);
    	return result;
    }
    
    
    /**
     * Pay resources to  buy something
     * 
     * @pre canAfford() is true and something is being bought
     * @post the player has that many resources deducted
     */
    public void pay(ResourceMultiSet cost) throws Exception
    {
        int brick = getResources().getBrick();
        int wheat = getResources().getWheat();
        int wood = getResources().getWood();
        int sheep = getResources().getSheep();
        int ore = getResources().getOre();
        getResources().setBrick(brick - cost.getBrick());
        getResources().setWheat(wheat - cost.getWheat());
        getResources().setWood(wood - cost.getWood());
        getResources().setSheep(sheep - cost.getSheep());
        getResources().setOre(ore - cost.getOre());
    }
    
    /**
     * Builds this thing. The player's piece collection is affected
     * 
     * @pre canAfford(type) was true and this is a legal time to build
     * @param type
     * @post this piece is taken from the player's collection. In the case of a city,
     * a settlement is also returned to the player's collection
     */
    public void build(PieceType type)
    {
    	
    }
    
    /**
     * this player recieves a resource
     * 
     * @param resource
     * @pre none
     * @post The player has this resource added to their stash
     */
    public void recieve(ResourceMultiSet resource) throws Exception
    {
        int brick = getResources().getBrick();
        int wheat = getResources().getWheat();
        int wood = getResources().getWood();
        int sheep = getResources().getSheep();
        int ore = getResources().getOre();
        getResources().setBrick(brick + resource.getBrick());
        getResources().setWheat(wheat + resource.getWheat());
        getResources().setWood(wood + resource.getWood());
        getResources().setSheep(sheep + resource.getSheep());
        getResources().setOre(ore + resource.getOre());
    }
    
    /**
     * Robs a random resource from the player and returns it
     * @pre The player has been legally selected to rob and has at least 1 resource
     * @post result = the resource that was deducted from this player to give to another
     */
    public ResourceMultiSet getRobbed() throws Exception
    {
    	return null;
    }
    
    

    //******************************************* Getters/Setters ****************************************************//

    /**
     *
     * Getter for cities
     *
     * @return  How many cities this player has left to play
     */
    public int getCities() {
        return cities;
    }

    /**
     *
     * Setter for cities
     *
     * @param cities    The number of cities this player has left to play
     */
    public void setCities(int cities) {
        this.cities = cities;
    }

    /**
     *
     * Getter for color
     *
     * @return The color of this player
     */
    public String getColor() {
        return color;
    }

    /**
     *
     * Setter for color
     *
     * @param color The color for this player
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     *
     * Getter for discarded
     *
     * @return  Whether this player has discarded or not already this discard phase
     */
    public boolean isDiscarded() {
        return discarded;
    }

    /**
     *
     * Setter for discarded
     *
     * @param discarded Whether this player has discarded or not already this discard phase
     */
    public void setDiscarded(boolean discarded) {
        this.discarded = discarded;
    }

    /**
     *
     * Getter for monuments
     *
     * @return  How many monuments this player has played
     */
    public int getMonuments() {
        return monuments;
    }

    /**
     *
     * Setter for monumnets
     *
     * @param monuments How many monuments this player has played
     */
    public void setMonuments(int monuments) {
        this.monuments = monuments;
    }

    /**
     *
     * Getter for name
     *
     * @return  The name of this player
     */
    public String getName() {
        return name;
    }

    /**
     *
     * Setter for name
     *
     * @param name  The name of this player
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * Getter for newDevCards
     *
     * @return  The dev cards the player bought this turn
     */
    public DevCardList getNewDevCards() {
        return newDevCards;
    }

    /**
     *
     * Setter for newDevCards
     *
     * @param newDevCards   The dev cards the player bought this turn
     */
    public void setNewDevCards(DevCardList newDevCards) {
        this.newDevCards = newDevCards;
    }

    /**
     *
     * Getter for oldDevCards
     *
     * @return  The dev cards the player had when the turn started
     */
    public DevCardList getOldDevCards() {
        return oldDevCards;
    }

    /**
     *
     * Setter for oldDevCards
     *
     * @param oldDevCards   The dev cards the player had when the turn started
     */
    public void setOldDevCards(DevCardList oldDevCards) {
        this.oldDevCards = oldDevCards;
    }

    /**
     *
     * Getter for playerIndex
     *
     * @return  What place in the array this player is: 0-3
     */
    public int getPlayerIndex() {
        return playerIndex;
    }

    /**
     *
     * Setter for playerIndex
     *
     * @param playerIndex   What place in the array this player is: 0-3
     */
    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }

    /**
     *
     * Getter for playedDevCard
     *
     * @return  Whether the player has played a dev card this turn
     */
    public boolean isPlayedDevCard() {
        return playedDevCard;
    }

    /**
     *
     * Setter for playedDevCard
     *
     * @param playedDevCard Whether the player has played a dev card this turn
     */
    public void setPlayedDevCard(boolean playedDevCard) {
        this.playedDevCard = playedDevCard;
    }

    /**
     *
     * Getter for playerID
     *
     * @return  The unique playerID
     */
    public int getPlayerID() {
        return playerID;
    }

    /**
     *
     * Setter for playerID
     *
     * @param playerID  The unique playerID
     */
    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    /**
     *
     * Getter for resources
     *
     * @return  The resource cards this player has
     */
    public ResourceMultiSet getResources() {
        return resources;
    }

    /**
     *
     * Setter for resources
     *
     * @param resources The resource cards this player has
     */
    public void setResources(ResourceMultiSet resources) {
        this.resources = resources;
    }

    /**
     *
     * Getter for roads
     *
     * @return  How many roads this player has left to play
     */
    public int getRoads() {
        return roads;
    }

    /**
     *
     * Setter for roads
     *
     * @param roads How many roads this player has left to play
     */
    public void setRoads(int roads) {
        this.roads = roads;
    }

    /**
     *
     * Getter for settlements
     *
     * @return  How many settlements this player has left to play
     */
    public int getSettlements() {
        return settlements;
    }

    /**
     *
     * Setter for settlements
     *
     * @param settlements   How many settlements this player has left to play
     */
    public void setSettlements(int settlements) {
        this.settlements = settlements;
    }

    /**
     *
     * Getter for soldiers
     *
     * @return  How many soldiers this player has played
     */
    public int getSoldiers() {
        return soldiers;
    }

    /**
     *
     * Setter for soldiers
     *
     * @param soldiers  How many soldiers this player has played
     */
    public void setSoldiers(int soldiers) {
        this.soldiers = soldiers;
    }

    /**
     *
     * Getter for victoryPoints
     *
     * @return  How many victory points this player has
     */
    public int getVictoryPoints() 
    {
    	int citycount = 4 - cities;
    	int settlecount = 5 - settlements;
    	
        return citycount*2 + settlecount + monuments;
    }

    /**
     *
     * Setter for victoryPoints
     *
     * @param victoryPoints How many victory points this player has
     */
    public void setVictoryPoints(int victoryPoints) {
        this.victoryPoints = victoryPoints;
    }

    //***************************************** public Methods *******************************************************//

    /**
     *
     * Reduces number of cities player has left to play by 1
     *
     * @pre The player must have at least 1 city left to play
     * @post    The number of cities left to play is reduced by 1
     */
    public void placeCity() 
    {
    	this.cities--;
    }

    /**
     *
     * Removes a certain card(s) from the player's ResourceCardList
     *
     * @param resources  A list of Resources to be discarded from ResourceCardList
     *
     * @pre Number of cards to be discarded can not exceed number of cards in the ResourceCardList and player must have
     * at least one card to discard
     * @post    The number of cards in the ResourceCardList is reduced by the number of cards being discarded
     */
    public void discard(ResourceMultiSet resources) 
    {

    }


    /**
     *
     * Reduces the count of DevCardList by 1
     * @param roadBuild 
     * @throws Exception 
     *
     * @pre Player must have at least one DevCard in the DevCardList and the type of the DevCard to be discarded must
     * match the type of at least one DevCard in the DevCardList
     * @post    The number of cards in the DevCardList is reduced by one
     *
     */
    public void playDevCard(DevCardType devcard) throws Exception 
    {
    	oldDevCards.play(devcard);
    }

    /**
     *
     * Reduces number of roads player has left to play by 1
     *
     * @pre The player must have at least 1 road left to play
     * @post    The number of roads left to play is reduced by 1
     */
    public void placeRoad() 
    {
    	roads--;
    }

    /**
     *
     * Reduces number of settlements player has left to play by 1
     *
     * @pre The player must have at least 1 settlement left to play
     * @post    The number of settlements left to play is reduced by 1
     */
    public void placeSettlement() 
    {
    	this.settlements--;
    }

    /**
     *
     * Increases the count of the number of soldiers played by 1
     *
     * @pre Player must have at least one DevCard of type soldier in the DevCardList
     * @post    One DevCard of type soldier is removed from the DevCardList
     */
    public void playSoldier() {

    }

    /**
     *
     * Removes a certain card(s) from the player's DevCardList
     *
     * @param devCards  A list of DevCardTypes to be discarded from DevCardList
     *
     * @pre Number of cards to be discarded can not exceed number of cards in the DevCardList and player must have at
     * least one card to discard
     * @post    The number of cards in the DevCardList is reduced by the number of cards being discarded
     */
    public void discardDevCards(DevCardList devCards) {

    }

    /**
     *
     * Removes a certain card(s) from the player's ResourceCardList
     *
     * @param resources  A list of Resources to be discarded from ResourceCardList
     *
     * @pre Number of cards to be discarded can not exceed number of cards in the ResourceCardList and player must have
     * at least one card to discard
     * @post    The number of cards in the ResourceCardList is reduced by the number of cards being discarded
     */
    public void discardResourceCards(ResourceMultiSet resources) {

    }

    //******************************************** CanDo Methods *****************************************************//

    /**
     *
     * Determines whether the pre-conditions of placeCity are met
     *
     * @return  Whether a city can be placed
     */
    public boolean canPlaceCity() 
    {
    	ResourceMultiSet cost = new ResourceMultiSet();
    	cost.setWheat(2);
    	cost.setOre(3);
        return resources.canAfford(cost) && cities > 0;
    }

    /**
     *
     * Determines whether the pre-conditions of discard are met
     *
     * @param resources   List of Resources to be discarded
     * 
     * @post  result = Whether a card(s) can be discarded
     */
    public boolean canDiscard(ResourceMultiSet resources) 
    {
        return this.resources.canAfford(resources);
    }

    /**
     *
     * Determines whether the pre-conditions of playMonument are met
     *
     * @return  Whether a monument can be played
     */
    public boolean canPlayMonument() 
    {
    	DevCardList cost = new DevCardList();
    	cost.setMonument(1);
        return oldDevCards.includes(cost) && !this.playedDevCard;
    }
    
    /**
    *
    * Determines whether the pre-conditions of playYearOfPlenty are met
    *
    * @pre 
    * @post
    */
   public boolean canPlayRoadBuilding() 
   {
   	DevCardList cost = new DevCardList();
   	cost.setRoad_building(1);
       return oldDevCards.includes(cost) && !this.playedDevCard;
   }
   
   /**
   *
   * Determines whether the pre-conditions of playMonument are met
   *
   * @return  Whether a monument can be played
   */
  public boolean canPlayYearOfPlenty() 
  {
	
  	DevCardList cost = new DevCardList();
  	cost.setYear_of_plenty(1);
      return oldDevCards.includes(cost) && !this.playedDevCard;
  }

    /**
     *
     * Determines whether the pre-conditions of playDevCard are met
     *
     * @pre none
     * @post  Whether a DevCard can be played
     * 
     */
    public boolean canPlayDevelopmentCard(DevCardList card) 
    {
    	return this.oldDevCards.includes(card) && !this.playedDevCard;
    }

    /**
     *
     * Determines whether the pre-conditions of placeRoad are met
     *
     * @pre none
     * @post  Whether a road can be placed
     */
    public boolean canPlaceRoad() 
    {
    	ResourceMultiSet cost = new ResourceMultiSet();
    	cost.setWood(1);
    	cost.setBrick(1);
        return resources.canAfford(cost) && roads > 0;
    }

    /**
     *
     * Determines whether the pre-conditions of placeSettlement are met
     * 
     * @pre none
     * @post  Whether a settlement can be placed
     */
    public boolean canPlaceSettlement(boolean initial_override) 
    {
    	if(initial_override)//You can always play a city in the first round or the second.
    	{
    		return true;
    	}
    	ResourceMultiSet settlement_cost = new ResourceMultiSet();
    	settlement_cost.setBrick(1);
    	settlement_cost.setSheep(1);
    	settlement_cost.setWheat(1);
    	settlement_cost.setWood(1);
        return resources.canAfford(settlement_cost) && settlements > 0; 
        
    }

    /**
     *
     * Determines whether the pre-conditions of playSoldier are met
     *
     * @return  Whether a soldier can be played
     */
    public boolean canPlaySoldier() 
    {
    	DevCardList soldier_cost = new DevCardList();
    	soldier_cost.setSoldier(1);
        return oldDevCards.includes(soldier_cost) && !playedDevCard;
    }

    /**
     *
     * Determines whether the pre-conditions of discardDevCards are met
     *
     * @param devCards  List of DevCardTypes to be discarded
     * @return  Whether a card(s) can be discarded
     */
    public boolean canDiscardDevCards(DevCardList devCards) 
    {
        return oldDevCards.includes(devCards);
    }

    /**
     *
     * Determines whether the pre-conditions of discardResourceCards are met
     *
     * @param resources  List of Resources to be discarded
     * @return  Whether a card(s) can be discarded
     */
    public boolean canDiscardResourceCards(ResourceMultiSet resources) 
    {
        return resources.canAfford(resources);
    }

    /**
     * @pre
     * @post
     */
	public boolean canBuyDevCard() 
	{
		ResourceMultiSet cost = new ResourceMultiSet();
		cost.setSheep(1);
		cost.setWheat(1);
		cost.setOre(1);
		return resources.canAfford(cost);
	}

	/**
	 * Tells whether a player can play the monopoly card 
	 * 
	 * @pre none
	 * @post true iff the player hasnt played a dev card and has the monopoly card to play
	 */
	public boolean canPlayMonopoly() 
	{
    	DevCardList soldier_cost = new DevCardList();
    	soldier_cost.setMonopoly(1);
        return oldDevCards.includes(soldier_cost) && !playedDevCard;
	}

	public void getResource(ResourceType resource, int quantity) throws Exception 
	{
		this.resources.add(resource, quantity);
	}

	public void pay(ResourceType resource, int quantity) throws Exception 
	{
		resources.pay(resource, quantity);
	}
}
