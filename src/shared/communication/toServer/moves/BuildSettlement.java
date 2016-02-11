package shared.communication.toServer.moves;

import shared.communication.fromServer.game.VertexLocation;

/**
 * Michael Rhodes
 * CS 340
 * Section 2
 * Team 10
 *
 * buildSettlement command object
 */
public class BuildSettlement extends Command {
    private VertexLocation vertexLocation;
    private boolean free;

    public BuildSettlement(int playerIndex, VertexLocation vertexLocation, boolean free) {
        super("buildSettlement", playerIndex);
        this.vertexLocation = vertexLocation;
        this.free = free;
    }

    public VertexLocation getVertexLocation() {
        return vertexLocation;
    }

    public void setVertexLocation(VertexLocation vertexLocation) {
        this.vertexLocation = vertexLocation;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }
}
