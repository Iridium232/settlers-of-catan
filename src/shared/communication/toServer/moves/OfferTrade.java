package shared.communication.toServer.moves;

/**
 * Michael Rhodes
 * CS 340
 * Section 2
 * Team 10
 *
 * offerTrade command object
 */
public class OfferTrade extends Command {
    /** What you get (+) and what you give (- */
    private ResourceList offer;
    /** Who you're offering the trade to (0-3) */
    private int receiver;

    public OfferTrade(int playerIndex, ResourceList offer, int receiver) {
        super("offerTrade", playerIndex);
        this.offer = offer;
        this.receiver = receiver;
    }

    public ResourceList getOffer() {
        return offer;
    }

    public void setOffer(ResourceList offer) {
        this.offer = offer;
    }

    public int getReceiver() {
        return receiver;
    }

    public void setReceiver(int receiver) {
        this.receiver = receiver;
    }
}
