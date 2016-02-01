package shared.communication.toServer.games;

/**
 * Michael Rhodes
 * CS 340
 * Section 2
 * Team 10
 *
 * The ID of the game the player wants to join, and the color they want
 */
public class JoinGameRequest {
    /** The ID of the game to join */
    private int id;
    /**
     * ['red' or 'green' or 'blue' or 'yellow' or 'puce' or 'brown' or 'white' or 'purple' or 'orange']:
     * What color you want to join (or rejoin) as
     */
    private String color;

    public JoinGameRequest(int id, String color) {
        this.id = id;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
