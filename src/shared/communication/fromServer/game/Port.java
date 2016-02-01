package shared.communication.fromServer.game;

import shared.locations.HexLocation;

/**
 * Michael Rhodes
 * CS 340
 * Section 2
 * Team 10
 */
public class Port {
    private String resource;
    private HexLocation location;
    private String direction;
    private int ratio;

    public Port(String resource, HexLocation location, String direction, int ratio) {
        this.resource = resource;
        this.location = location;
        this.direction = direction;
        this.ratio = ratio;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public HexLocation getLocation() {
        return location;
    }

    public void setLocation(HexLocation location) {
        this.location = location;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int getRatio() {
        return ratio;
    }

    public void setRatio(int ratio) {
        this.ratio = ratio;
    }
}
