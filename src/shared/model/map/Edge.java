package shared.model.map;
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
	
	public Edge(){}
	
	public Edge(EdgeLocation edgeLoc)
	{
		location = edgeLoc;
		direction = location.getDir();
		HexLocation tile = location.getHexLoc();
		VertexDirection end1direction = null;
		VertexDirection end2direction = null;
		switch(edgeLoc.getDir())
		{
			case North:
				end1direction = VertexDirection.NorthEast;
				end2direction = VertexDirection.NorthWest;
				break;
			case South:
				end1direction = VertexDirection.SouthEast;
				end2direction = VertexDirection.SouthWest;
				break;
			case NorthWest:
				end1direction = VertexDirection.West;
				end2direction = VertexDirection.NorthWest;
				break;
			case NorthEast:
				end1direction = VertexDirection.NorthEast;
				end2direction = VertexDirection.East;
				break;
			case SouthEast:
				end1direction = VertexDirection.East;
				end2direction = VertexDirection.SouthEast;
				break;
			case SouthWest:
				end1direction = VertexDirection.West;
				end2direction = VertexDirection.SouthWest;
				break;
		}
		end1 = new Vertex(new VertexLocation(tile, end1direction));
		end2 = new Vertex(new VertexLocation(tile, end2direction));
	}
	
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
		//assert this.location ==null;
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
		//assert this.direction == null;
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
