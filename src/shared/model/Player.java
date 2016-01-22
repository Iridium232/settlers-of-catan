package shared.model;

import java.util.ArrayList;

import shared.definitions.PieceType;

/**
 * Michael Rhodes
 * CS 340
 * Section 2
 * Dr. Woodfield
 */
public class Player {
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
    private String name;
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
    private ResourceCard resources;
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
        this.newDevCards = null;
        this.oldDevCards = null;
        this.playerIndex = 0;
        this.playedDevCard = false;
        this.playerID = 0;
        this.resources = null;
        this.roads = 15;
        this.settlements = 5;
        this.soldiers = 0;
        this.victoryPoints = 0;
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
    public Player(String color, String name, int playerIndex, int playerID) {
        this.color = color;
        this.name = name;
        this.playerIndex = playerIndex;
        this.playerID = playerID;

        this.cities = 4;
        this.discarded = false;
        this.monuments = 0;
        this.newDevCards = null;
        this.oldDevCards = null;
        this.playedDevCard = false;
        this.resources = null;
        this.roads = 15;
        this.settlements = 5;
        this.soldiers = 0;
        this.victoryPoints = 0;
    }
    
    /**
     * Tells whether this player can afford to buy something with this cost
     * @pre none
     * @post result = true iff the player has this many of the required resources
     * and has the piece nessesary in his collection
     */
    public boolean canAfford(ResourceCard cost, PieceType type)
    {
    	return false;
    }
    
    
    /**
     * Pay resources to  buy something
     * 
     * @pre canAfford() is true and something is being bought
     * @post the player has that many resources deducted
     */
    public void pay(ResourceCard cost) throws Exception
    {
    	
    	
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
    public void recieve(ResourceCard resource) throws Exception
    {
    	
    }
    
    /**
     * Robs a random resource from the player and returns it
     * @pre The player has been legally selected to rob and has at least 1 resource
     * @post result = the resource that was deducted from this player to give to another
     */
    public ResourceCard getRobbed() throws Exception
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
    public ResourceCard getResources() {
        return resources;
    }

    /**
     *
     * Setter for resources
     *
     * @param resources The resource cards this player has
     */
    public void setResources(ResourceCard resources) {
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
    public int getVictoryPoints() {
        return victoryPoints;
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
    public void placeCity() {

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
    public void discard(ResourceList resources) {

    }

    /**
     *
     * Increases the count of the number of monuments played by 1
     *
     * @pre Player must have at least one DevCard of type monument in the DevCardList
     * @post    One DevCard of type monument is removed from the DevCardList
     */
    public void playMonument() {

    }

    /**
     *
     * Reduces the count of DevCardList by 1
     *
     * @pre Player must have at least one DevCard in the DevCardList and the type of the DevCard to be discarded must
     * match the type of at least one DevCard in the DevCardList
     * @post    The number of cards in the DevCardList is reduced by one
     *
     */
    public void playDevCard() {

    }

    /**
     *
     * Reduces number of roads player has left to play by 1
     *
     * @pre The player must have at least 1 road left to play
     * @post    The number of roads left to play is reduced by 1
     */
    public void placeRoad() {

    }

    /**
     *
     * Reduces number of settlements player has left to play by 1
     *
     * @pre The player must have at least 1 settlement left to play
     * @post    The number of settlements left to play is reduced by 1
     */
    public void placeSettlement() {

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
    public void discardResourceCards(ResourceList resources) {

    }

    //******************************************** CanDo Methods *****************************************************//

    /**
     *
     * Determines whether the pre-conditions of placeCity are met
     *
     * @return  Whether a city can be placed
     */
    public boolean canPlaceCity() {
        return true;
    }

    /**
     *
     * Determines whether the pre-conditions of discard are met
     *
     * @param resources   List of Resources to be discarded
     * 
     * @post  result = Whether a card(s) can be discarded
     */
    public boolean canDiscard(ResourceList resources) {
        return true;
    }

    /**
     *
     * Determines whether the pre-conditions of playMonument are met
     *
     * @return  Whether a monument can be played
     */
    public boolean canPlayMonument() {
        return true;
    }

    /**
     *
     * Determines whether the pre-conditions of playDevCard are met
     *
     * @return  Whether a DevCard can be played
     */
    public boolean canPlayDevCard() {
        return true;
    }

    /**
     *
     * Determines whether the pre-conditions of placeRoad are met
     *
     * @return  Whether a road can be placed
     */
    public boolean canPlaceRoad() {
        return true;
    }

    /**
     *
     * Determines whether the pre-conditions of placeSettlement are met
     *
     * @return  Whether a settlement can be placed
     */
    public boolean canPlaceSettlement() {
        return true;
    }

    /**
     *
     * Determines whether the pre-conditions of playSoldier are met
     *
     * @return  Whether a soldier can be played
     */
    public boolean canPlaySoldier() {
        return true;
    }

    /**
     *
     * Determines whether the pre-conditions of discardDevCards are met
     *
     * @param devCards  List of DevCardTypes to be discarded
     * @return  Whether a card(s) can be discarded
     */
    public boolean canDiscardDevCards(DevCardList devCards) {
        return true;
    }

    /**
     *
     * Determines whether the pre-conditions of discardResourceCards are met
     *
     * @param resources  List of Resources to be discarded
     * @return  Whether a card(s) can be discarded
     */
    public boolean canDiscardResourceCards(ResourceList resources) {
        return true;
    }
}
