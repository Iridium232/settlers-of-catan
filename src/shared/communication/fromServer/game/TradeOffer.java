package shared.communication.fromServer.game;

import shared.communication.ResourceList;

/**
 * Michael Rhodes
 * CS 340
 * Section 2
 * Team 10
 */
public class TradeOffer {
    private int sender;
    private int receiver;
    private ResourceList offer;

    public TradeOffer(int sender, int receiver, ResourceList offer) {
        this.sender = sender;
        this.receiver = receiver;
        this.offer = offer;
    }

    public int getSender() {
        return sender;
    }

    public void setSender(int sender) {
        this.sender = sender;
    }

    public int getReceiver() {
        return receiver;
    }

    public void setReceiver(int receiver) {
        this.receiver = receiver;
    }

    public ResourceList getOffer() {
        return offer;
    }

    public void setOffer(ResourceList offer) {
        this.offer = offer;
    }
}
