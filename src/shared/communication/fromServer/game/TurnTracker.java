package shared.communication.fromServer.game;

/**
 * Michael Rhodes
 * CS 340
 * Section 2
 * Team 10
 */
public class TurnTracker {
    private int currentTurn;
    private String status;
    private int longestRoad;
    private int largestArmy;

    public TurnTracker(int currentTurn, String status, int longestRoad, int largestArmy) {
        this.currentTurn = currentTurn;
        this.status = status;
        this.longestRoad = longestRoad;
        this.largestArmy = largestArmy;
    }

    public int getCurrentTurn() {
        return currentTurn;
    }

    public void setCurrentTurn(int currentTurn) {
        this.currentTurn = currentTurn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getLongestRoad() {
        return longestRoad;
    }

    public void setLongestRoad(int longestRoad) {
        this.longestRoad = longestRoad;
    }

    public int getLargestArmy() {
        return largestArmy;
    }

    public void setLargestArmy(int largestArmy) {
        this.largestArmy = largestArmy;
    }
}
