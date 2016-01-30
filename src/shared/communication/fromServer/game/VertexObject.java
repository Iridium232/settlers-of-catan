package shared.communication.fromServer.game;

import shared.locations.VertexLocation;

/**
 * Michael Rhodes
 * CS 340
 * Section 2
 * Team 10
 */
public class VertexObject {
    private int owner;
    private VertexLocation location;

    public VertexObject(int owner, VertexLocation location) {
        this.owner = owner;
        this.location = location;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public VertexLocation getLocation() {
        return location;
    }

    public void setLocation(VertexLocation location) {
        this.location = location;
    }
}
