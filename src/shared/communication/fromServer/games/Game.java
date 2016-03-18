package shared.communication.fromServer.games;

import shared.model.Fascade;

//This class is superfluous as it is the exact same as client.data.GameInfo.java
/**
 * Michael Rhodes
 * CS 340
 * Section 2
 * Team 10
 */
public class Game {
    private String title;
    private int id;
    private Player[] players;

    public Game(String title, int id, Player[] players) {
        this.title = title;
        this.id = id;
        this.players = players;
    }

    /**
     * Constructor to build the game information from a model facade
     * @param facade
     */
    public Game(Fascade facade, int game_id) 
    {
		this.title = facade.getGameName();
		this.id = game_id;
		this.players = facade.getPlayers();
	}

	public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }
}
