package shared.communication.toServer.moves;

/**
 * Michael Rhodes
 * CS 340
 * Section 2
 * Team 10
 *
 * acceptTrade command object
 */
public class AcceptTrade extends Command {
    /** Whether you accept the trade or not */
    private boolean willAccept;

    public AcceptTrade(int playerIndex, boolean willAccept) {
        super("acceptTrade", playerIndex);
        this.willAccept = willAccept;
    }

    public boolean isWillAccept() {
        return willAccept;
    }

    public void setWillAccept(boolean willAccept) {
        this.willAccept = willAccept;
    }
}
