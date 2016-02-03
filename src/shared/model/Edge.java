package shared.model;
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
	private Vertex end1;
	private Vertex end2;
	
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
	 * @param road the road to set
	 */

	/**
	 * @return the end1
	 */
	public Vertex getEnd1() {
		return end1;
	}
	/**
	 * @param end1 the end1 to set
	 */
	public void setEnd1(Vertex end1) {
		this.end1 = end1;
	}
	/**
	 * @return the end2
	 */
	public Vertex getEnd2() {
		return end2;
	}
	/**
	 * @param end2 the end2 to set
	 */
	public void setEnd2(Vertex end2) {
		this.end2 = end2;
	}
	
}
