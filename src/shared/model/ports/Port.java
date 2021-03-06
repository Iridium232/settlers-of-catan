package shared.model.ports;
import shared.definitions.ResourceType;
import shared.locations.*;
import shared.model.map.Vertex;

/**
 * This class represents a Port
 * Ports have a vertex location on the map
 * Ports have a type depending on what you can trade with them
 * Ports cannot change location or type after the map is generated
 */
public abstract class Port 
{
	protected HexLocation location;
	protected Vertex vertex1;
	protected Vertex vertex2;
	protected int ratio;
	protected ResourceType resource;
	private EdgeDirection direction;

	public Port()
	{
		
	}

	public Port(Vertex vertex1, int vertX, int vertY, Vertex vertex2, int ratio, EdgeDirection direction) {

		this.location = new HexLocation(vertX, vertY);
		this.vertex1 = vertex1;
		this.vertex2 = vertex2;
		this.ratio = ratio;
		this.direction = direction;
	}
	/**
	 * Class constructor
	 * @param resourceType
	 * @param vertX
	 * @param vertY
	 * @param direction
	 * @param ratio
	 */


	/**
	 * Here is the getter for the hex (vertX and vertY location) of the port
	 * @pre none
	 * @post Returns the hex location of the port
	 */
	public HexLocation getLocation() {
		return location;
	}

	/**
	 * Here is the setter for the hex (vertX and vertY location) of the port
	 * @param coordX
	 * @param coordY
	 * @pore none
	 * @post The hex location of this port get now the coordinates as the x and y previously
	 */
	public void setLocation(int coordX, int coordY) {
		this.location = new HexLocation(coordX, coordY);
	}

	/**
	 * Here is the setter for the location of the port
	 * @param location
	 * @pre none
	 * @post The hex location of this port get now the coordinates as the x and y previously passed as a parameter
	 */
	public void setLocation(HexLocation location) {
		this.location = location;
	}

	/**
	 * Here is the getter for the direction of the port
	 * @pre none
	 * @post Returns a string that represents the direction of the port
	 */
	public Vertex getVertex1() {
		return vertex1;
	}

	/**
	 * here is the setter for the direction of the port
	 * @param direction
	 * @pre none
	 * @post This is the string that has the direction of the port compared to the string that was passed in as a parameter
	 */
	public void setVertex1(Vertex direction) {
		this.vertex1 = direction;
	}

	/**
	 * Here is the getter for the trade ratio of the port
	 * @pre none
	 * @post Returns an integer representing the trade ratio for the port
	 */
	public int getRatio() {
		return ratio;
	}

	/**
	 * Here is the setter for the trade ratio fo the port
	 * @param ratio
	 * @pre none
	 * @post integer that equal the ratio passed as a parameter
	 */
	public void setRatio(int ratio) {
		this.ratio = ratio;
	}


	public Vertex getVertex2() 
	{
		return vertex2;
	}

	public void setVertex2(Vertex vertex) {
		vertex2 = vertex;
		
	}

	public ResourceType getResource() {
		return resource;
	}

	public void setResource(ResourceType resource) {
		this.resource = resource;
	}

	public EdgeDirection getEdgeDirection() 
	{
		return direction;
	}
}
