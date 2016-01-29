package shared.communication.toServer.moves;

/**
 * Michael Rhodes
 * CS 340
 * Section 2
 * Team 10
 *
 * discardCards command object
 */
public class DiscardCards extends Command {
    private ResourceList discardedCards;

    public DiscardCards(int playerIndex, ResourceList discardedCards) {
        super("discardCards", playerIndex);
        this.discardedCards = discardedCards;
    }

    public ResourceList getDiscardedCards() {
        return discardedCards;
    }

    public void setDiscardedCards(ResourceList discardedCards) {
        this.discardedCards = discardedCards;
    }
}
