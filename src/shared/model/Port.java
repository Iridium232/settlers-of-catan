package shared.model;
import shared.locations.*;

/**
 * This class represents a Port
 * Ports have a vertex location on the map
 * Ports have a type depending on what you can trade with them
 * Ports cannot change location or type after the map is generated
 */
public class Port 
{
	private String resource;
	private HexLocation location;
	private String direction;
	private int ratio;


	public Port(String resourceType, int vertX, int vertY, String direction, int ratio) {
		this.resource = resourceType;
		this.location = new HexLocation(vertX, vertY);
		this.direction = direction;
		this.ratio = ratio;
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
	 * This is the getter for the type of the resource that port class can trade with
	 * @pre none
	 * @post Returns a string representing the type of resource that the port can trade
	 */

	public String getResourceType() {
		return resource;
	}

	/**
	 * Here is the setter for the type of the resource that port class can trade
	 * @param resourceType
	 * @pre none
	 * @post string is passed as a parameter
	 */
	public void setResourceType(String resourceType) {
		this.resource = resourceType;
	}

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
	public String getDirection() {
		return direction;
	}

	/**
	 * here is the setter for the direction of the port
	 * @param direction
	 * @pre none
	 * @post This is the string that has the direction of the port compared to the string that was passed in as a parameter
	 */
	public void setDirection(String direction) {
		this.direction = direction;
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
}
