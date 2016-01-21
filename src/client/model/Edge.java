package client.model;
import shared.locations.*;

/**
 * Class That represents the edges of the map (where you can build roads)
 * The location and direction cannot change after setup
 * Once a road is built on the edge it never changes
 */
public class Edge 
{
	private EdgeLocation location = null;
	private EdgeDirection direction = null;
	private Road road = null;
	
	/**
	 * @return the location
	 */
	public EdgeLocation getLocation() {
		return location;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(EdgeLocation location) {
		assert this.location ==null;
		this.location = location;
	}
	/**
	 * @return the direction
	 */
	public EdgeDirection getDirection() {
		return direction;
	}
	/**
	 * @param direction the direction to set
	 */
	public void setDirection(EdgeDirection direction) {
		assert this.direction == null;
		this.direction = direction;
	}
	/**
	 * @return the road
	 */
	public Road getRoad() {
		return road;
	}
	/**
	 * @param road the road to set
	 */
	public void setRoad(Road road) {
		assert this.road == null;
		this.road = road;
	}
	
}
