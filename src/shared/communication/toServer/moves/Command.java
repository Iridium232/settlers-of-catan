package shared.communication.toServer.moves;

//import shared.definitions.String;

/**
 * Michael Rhodes
 * CS 340
 * Section 2
 * Team 10
 *
 * abstract String for command Strings
 */
public abstract class Command {
    /** String representing the command */
    private String type;
    /** Who's sending this command (0-3) */
    private int playerIndex;

    public Command(String maritimeTrade, int playerIndex) {
        this.type = maritimeTrade;
        this.playerIndex = playerIndex;
    }

    public Command() {}

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
