package shared.communication.fromServer.games;

/**
 * Michael Rhodes
 * CS 340
 * Section 2
 * Team 10
 */
public class NewGame {
    private String title;
    private int id;
    private EmptyPlayer[] players;

    public NewGame(String title, int id, EmptyPlayer[] players) {
        this.title = title;
        this.id = id;
        this.players = players;
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

    public EmptyPlayer[] getPlayers() {
        return players;
    }

    public void setPlayers(EmptyPlayer[] players) {
        this.players = players;
    }
}
