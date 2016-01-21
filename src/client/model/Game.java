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
	private GameMap map;
	private GameInfo gameinfo;
	private ArrayList<Player> players;
	private int activePlayer;
	private int winner = -1;
	private int version = -1;
	private MessageList chat;
	private MessageList log;
}
