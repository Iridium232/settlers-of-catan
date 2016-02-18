package shared.communication.toServer.moves;


import shared.communication.EdgeLocation;

/**
 * Michael Rhodes
 * CS 340
 * Section 2
 * Team 10
 *
 * Road_Building command object
 */
public class Road_Building_ extends Command {
    private EdgeLocation spot1;
    private EdgeLocation spot2;

    public Road_Building_(int playerIndex, EdgeLocation spot1, EdgeLocation spot2) {
        super("Road_Building", playerIndex);
        this.spot1 = spot1;
        this.spot2 = spot2;
    }

    public EdgeLocation getSpot1() {
        return spot1;
    }

    public void setSpot1(EdgeLocation spot1) {
        this.spot1 = spot1;
    }

    public EdgeLocation getSpot2() {
        return spot2;
    }

    public void setSpot2(EdgeLocation spot2) {
        this.spot2 = spot2;
    }
}
