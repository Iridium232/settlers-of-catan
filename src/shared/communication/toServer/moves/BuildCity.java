package shared.communication.toServer.moves;

import shared.locations.VertexLocation;

/**
 * Michael Rhodes
 * CS 340
 * Section 2
 * Team 10
 *
 * buildCity command object
 */
public class BuildCity extends Command {
    private VertexLocation vertexLocation;

    public BuildCity(int playerIndex, VertexLocation vertexLocation) {
        super("buildCity", playerIndex);
        this.vertexLocation = vertexLocation;
    }

    public VertexLocation getVertexLocation() {
        return vertexLocation;
    }

    public void setVertexLocation(VertexLocation vertexLocation) {
        this.vertexLocation = vertexLocation;
    }
}
