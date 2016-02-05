package shared.communication.fromServer.game;

/**
 * Michael Rhodes
 * CS 340
 * Section 2
 * Team 10
 */
public class Road {
    private int owner;
    private EdgeLocation location;

    public Road(int owner, EdgeLocation location) {
        this.owner = owner;
        this.location = location;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public EdgeLocation getLocation() {
        return location;
    }

    public void setLocation(EdgeLocation location) {
        this.location = location;
    }
}
