package shared.communication.fromServer.game;

import shared.communication.ResourceList;

/**
 * Michael Rhodes
 * CS 340
 * Section 2
 * Team 10
 */
public class Player {
    private int cities;
    private String color;
    private boolean discarded;
    private int monuments;
    private String name;
    private DevCardList newDevCards;
    private DevCardList oldDevCards;
    private int playerIndex;
    private boolean playedDevCard;
    private int playerID;
    private ResourceList resources;
    private int roads;
    private int settlements;
    private int soldiers;
    private int victoryPoints;

    public Player(int cities, String color, boolean discarded, int monuments, String name, DevCardList newDevCards,
                  DevCardList oldDevCards, int playerIndex, boolean playedDevCard, int playerID, ResourceList resources,
                  int roads, int settlements, int soldiers, int victoryPoints) {
        this.cities = cities;
        this.color = color;
        this.discarded = discarded;
        this.monuments = monuments;
        this.name = name;
        this.newDevCards = newDevCards;
        this.oldDevCards = oldDevCards;
        this.playerIndex = playerIndex;
        this.playedDevCard = playedDevCard;
        this.playerID = playerID;
        this.resources = resources;
        this.roads = roads;
        this.settlements = settlements;
        this.soldiers = soldiers;
        this.victoryPoints = victoryPoints;
    }

    public Player() {
        this.cities = 0;
        this.color = "";
        this.discarded = false;
        this.monuments = 0;
        this.name = "";
        this.newDevCards = null;
        this.oldDevCards = null;
        this.playerIndex = 0;
        this.playedDevCard = false;
        this.playerID = 0;
        this.resources = null;
        this.roads = 0;
        this.settlements = 0;
        this.soldiers = 0;
        this.victoryPoints = 0;
    }

    public int getCities() {
        return cities;
    }

    public void setCities(int cities) {
        this.cities = cities;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isDiscarded() {
        return discarded;
    }

    public void setDiscarded(boolean discarded) {
        this.discarded = discarded;
    }

    public int getMonuments() {
        return monuments;
    }

    public void setMonuments(int monuments) {
        this.monuments = monuments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DevCardList getNewDevCards() {
        return newDevCards;
    }

    public void setNewDevCards(DevCardList newDevCards) {
        this.newDevCards = newDevCards;
    }

    public DevCardList getOldDevCards() {
        return oldDevCards;
    }

    public void setOldDevCards(DevCardList oldDevCards) {
        this.oldDevCards = oldDevCards;
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }

    public boolean isPlayedDevCard() {
        return playedDevCard;
    }

    public void setPlayedDevCard(boolean playedDevCard) {
        this.playedDevCard = playedDevCard;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public ResourceList getResources() {
        return resources;
    }

    public void setResources(ResourceList resources) {
        this.resources = resources;
    }

    public int getRoads() {
        return roads;
    }

    public void setRoads(int roads) {
        this.roads = roads;
    }

    public int getSettlements() {
        return settlements;
    }

    public void setSettlements(int settlements) {
        this.settlements = settlements;
    }

    public int getSoldiers() {
        return soldiers;
    }

    public void setSoldiers(int soldiers) {
        this.soldiers = soldiers;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public void setVictoryPoints(int victoryPoints) {
        this.victoryPoints = victoryPoints;
    }
}
