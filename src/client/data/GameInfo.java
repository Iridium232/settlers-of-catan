package client.data;

import java.util.*;

/**
 * Used to pass game information into views<br>
 * <br>
 * PROPERTIES:<br>
 * <ul>
 * <li>Id: Unique game ID</li>
 * <li>Title: Game title (non-empty string)</li>
 * <li>Players: List of players who have joined the game (can be empty)</li>
 * </ul>
 * 
 */
public class GameInfo
{
	private int id;
	private String title;
	private List<PlayerInfo> players;
	
	public GameInfo()
	{
		setId(-1);
		setTitle("");
		players = new ArrayList<PlayerInfo>();
	}
	
	public int getId()
	{
		return id;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	/**
	 * Adds a player to a game. There should be 4 players in each game.
	 * 
	 * @pre There are fewer than 4 players in the game.
	 * @post There is one more player in the game list. There are not more than 4 players
	 */
	public void addPlayer(PlayerInfo newPlayer)
	{
		players.add(newPlayer);
	}
	
	public List<PlayerInfo> getPlayers()
	{
		return Collections.unmodifiableList(players);
	}
}

