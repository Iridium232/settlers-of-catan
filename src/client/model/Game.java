package client.model;
import java.util.ArrayList;


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
	private ArrayList<Player> players;
	private Player activePlayer;
}
