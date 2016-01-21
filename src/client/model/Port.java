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
	 * @return the location1
	 */
	public VertexLocation getLocation1() {
		return location1;
	}
	
	/**
	 * @param location the location1 to set
	 */
	public void setLocation1(VertexLocation location) {
		assert this.location1 == null;
		this.location1 = location;
	}
	
	/**
	 * @return the location2
	 */
	public VertexLocation getLocation2() {
		return location2;
	}
	
	/**
	 * @param location the location2 to set
	 */
	public void setLocation2(VertexLocation location) {
		assert this.location2 == null;
		this.location2 = location;
	}
}
