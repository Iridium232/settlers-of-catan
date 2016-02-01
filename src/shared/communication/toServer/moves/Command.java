package shared.communication.toServer.moves;

/**
 * Michael Rhodes
 * CS 340
 * Section 2
 * Team 10
 *
 * abstract class for command objects
 */
public abstract class Command {
    /** String representing the command */
    private String type;
    /** Who's sending this command (0-3) */
    private int playerIndex;

    public Command(String type, int playerIndex) {
        this.type = type;
        this.playerIndex = playerIndex;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }
}
