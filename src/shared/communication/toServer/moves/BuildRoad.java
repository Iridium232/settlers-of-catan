package shared.communication.toServer.moves;

import shared.locations.EdgeLocation;

/**
 * Michael Rhodes
 * CS 340
 * Section 2
 * Team 10
 *
 * buildRoad command object
 */
public class BuildRoad extends Command {
    private EdgeLocation roadLocation;
    /** Whether this is placed for free (setup) */
    private boolean free;

    public BuildRoad(int playerIndex, EdgeLocation roadLocation, boolean free) {
        super("buildRoad", playerIndex);
        this.roadLocation = roadLocation;
        this.free = free;
    }

    public EdgeLocation getRoadLocation() {
        return roadLocation;
    }

    public void setRoadLocation(EdgeLocation roadLocation) {
        this.roadLocation = roadLocation;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }
}
