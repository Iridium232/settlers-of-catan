package shared.communication.toServer.moves;

/**
 * Michael Rhodes
 * CS 340
 * Section 2
 * Team 10
 *
 * finishTurn command object
 */
public class FinishTurn extends Command {

    public FinishTurn(int playerIndex) {
        super("finishTurn", playerIndex);
    }
}
