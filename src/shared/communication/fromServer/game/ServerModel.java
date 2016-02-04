package shared.communication.fromServer.game;

import shared.communication.ResourceList;
import shared.model.MessageList;

/**
 * Michael Rhodes
 * CS 340
 * Section 2
 * Team 10
 */
public class ServerModel {
    private DevCardList deck;
    private ResourceList bank;
    private MessageList chat;
    private MessageList log;
    private Map map;
    private Player[] players;
    private TradeOffer tradeOffer;
    private TurnTracker turnTracker;
    private int version;
    private int winner;

    public ServerModel(DevCardList deck, ResourceList bank, MessageList chat, MessageList log, Map map, Player[]
            players, TradeOffer tradeOffer, TurnTracker turnTracker, int version, int winner) {
        this.deck = deck;
        this.bank = bank;
        this.chat = chat;
        this.log = log;
        this.map = map;
        this.players = players;
        this.tradeOffer = tradeOffer;
        this.turnTracker = turnTracker;
        this.version = version;
        this.winner = winner;
    }

    public DevCardList getDeck() {
        return deck;
    }

    public void setDeck(DevCardList deck) {
        this.deck = deck;
    }

    public ResourceList getBank() {
        return bank;
    }

    public void setBank(ResourceList bank) {
        this.bank = bank;
    }

    public MessageList getChat() {
        return chat;
    }

    public void setChat(MessageList chat) {
        this.chat = chat;
    }

    public MessageList getLog() {
        return log;
    }

    public void setLog(MessageList log) {
        this.log = log;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    public TradeOffer getTradeOffer() {
        return tradeOffer;
    }

    public void setTradeOffer(TradeOffer tradeOffer) {
        this.tradeOffer = tradeOffer;
    }

    public TurnTracker getTurnTracker() {
        return turnTracker;
    }

    public void setTurnTracker(TurnTracker turnTracker) {
        this.turnTracker = turnTracker;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getWinner() {
        return winner;
    }

    public void setWinner(int winner) {
        this.winner = winner;
    }
}
