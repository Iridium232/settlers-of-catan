package client.model;
import shared.definitions.*;
import shared.locations.*;

/**
 * This class represents a Port
 * Ports have a vertex location on the map
 * Ports have a type depending on what you can trade with them
 * Ports cannot change location or type after the map is generated
 */
public class Port 
{
	private PortType type = null;
	private VertexLocation location1 = null;
	private VertexLocation location2 = null;
	
	
	/**
	 * @return the type
	 */
	public PortType getType() {
		return type;
	}
	
	/**
	 * @param type the type to set
	 */
	public void setType(PortType type) {
		assert this.type == null;
		this.type = type;
	}
	
	/**
	 * @return the location
	 */
	public VertexLocation getLocation() {
		return location;
	}
	
	/**
	 * @param location the location to set
	 */
	public void setLocation(VertexLocation location) {
		assert this.location == null;
		this.location = location;
	}
	
}
