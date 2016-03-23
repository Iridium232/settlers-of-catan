package shared.communication.fromServer.game;

import shared.locations.HexLocation;

/**
 * Michael Rhodes
 * CS 340
 * Section 2
 * Team 10
 */
public class Hex {
    private HexLocation location;
    /**
     * (Optional) ['Wood' or 'Brick' or 'Sheep' or 'Wheat' or 'Ore']: What resource this tile gives
     * - it's only here if the tile is not desert
     */
    private String resource;
    /** (Optional)  What number is on this tile. It's omitted if this is a desert hex */
    private Integer number;

    public Hex(HexLocation location, String resource, int number) {
        this.location = location;
        this.resource = resource;
        this.number = number;
    }

    public Hex(HexLocation location, String resource) {
        this.location = location;
        this.resource = resource;
        this.number = null;
    }

    public Hex(HexLocation location) {
        this.location = location;
        this.resource = null;
        this.number = null;
    }

    public HexLocation getLocation() {
        return location;
    }

    public void setLocation(HexLocation location) {
        this.location = location;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public int getNumber() {
        if (number != null) {
            return number;
        } else {
            return 0;
        }
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
