package client.model;
import java.util.ArrayList;
import client.data.*;

/**
 * The Catan Game all collected is represented in this class
 * It has a Map
 * It has a Player List
 * It has an Active Player (whose turn it is)
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
	private ArrayList<Player> players;
	
	/**
	 * structure to keep track of whose turn it is
	 */
	private TurnTracker turn_tracker;
	
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
	private MessageList chat;
	
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
	private ResourceList resource_bank;
	
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
	public ArrayList<Player> getPlayers() {
		return players;
	}

	/**
	 * @param players the players to set
	 */
	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
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
	public ResourceList getResource_bank() {
		return resource_bank;
	}

	/**
	 * @param resource_bank the resource_bank to set
	 */
	public void setResource_bank(ResourceList resource_bank) {
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
	public void setDevelopment_bank(DevCardList development_bank) {
		this.development_bank = development_bank;
	}
}
