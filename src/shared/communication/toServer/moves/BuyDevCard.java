package shared.communication.toServer.moves;

/**
 * Michael Rhodes
 * CS 340
 * Section 2
 * Team 10
 *
 * buyDevCard command object
 */
public class BuyDevCard extends Command {

    public BuyDevCard(int playerIndex) {
        super("buyDevCard", playerIndex);
    }
}
