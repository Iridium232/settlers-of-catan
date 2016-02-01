package shared.communication.toServer.moves;

import shared.locations.HexLocation;

/**
 * Michael Rhodes
 * CS 340
 * Section 2
 * Team 10
 *
 * Soldier command object
 */
public class Soldier_ extends Command {
    /** The index of the player to rob */
    private int victimIndex;
    /**  the new location of the robber */
    private HexLocation location;

    public Soldier_(int playerIndex, int victimIndex, HexLocation location) {
        super("Soldier", playerIndex);
        this.victimIndex = victimIndex;
        this.location = location;
    }

    public int getVictimIndex() {
        return victimIndex;
    }

    public void setVictimIndex(int victimIndex) {
        this.victimIndex = victimIndex;
    }

    public HexLocation getLocation() {
        return location;
    }

    public void setLocation(HexLocation location) {
        this.location = location;
    }
}
