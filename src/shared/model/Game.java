package shared.model;
import java.util.ArrayList;

import shared.definitions.TurnStatus;
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
	private GameMap map;
	
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
	public DevCardList getDevelopment_bank() {
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
		
	}
	
	/**
	 * 
	 * @pre the active player has rolled the dice in turn and achieved this result
	 * @post players recieve resources and are forced to discard if they have
	 * too many resources.
	 * @post if the player rolled a 7, they are put into the ROB phase of their turn.
	 */
	public void applyDiceRoll(int result)
	{
		
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
	
}
