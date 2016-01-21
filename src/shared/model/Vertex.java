package shared.model;
import shared.locations.*;
import shared.definitions.*;

/**
 * The vertex class represents the corners of the terrain hexes where
 * a building may be built or a port may exist. 
 * When there is no building, the getBuilding() function will return null;
 * When there is no port, the getPort() function will return null;
 * The location should never change during the game
 */
public class Vertex 
{
	private VertexLocation location = null;
	private Port port = null;
	private Building building = null;
	public VertexLocation getLocation() {
		return location;
	}
	
	public void setLocation(VertexLocation location) {
		assert (location == null);
		this.location = location;
	}
	
	public Port getPort() {
		return port;
	}
	
	public void setPort(Port port) {
		this.port = port;
	}
	
	public Building getBuilding() {
		return building;
	}
	
	public void setBuilding(Building building) {
		this.building = building;
	}
}
