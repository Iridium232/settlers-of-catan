package client.model;
import shared.definitions.*;
import shared.locations.*;


/**
 *Class that holds information about Game Pieces
 *These are Settlements or Cities and not Roads 
 *They have a color to link them to their player
 *They have a location on a vertex of the map
 *These attributes cannot be changed once set
 *
 *@invariants The piece type is either SETTLEMENT or CITY
 */
public class Building 
{
	private VertexLocation location;
	private PieceType type;
	private CatanColor color;
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
		this.location = location;
	}
	/**
	 * @return the type
	 */
	public PieceType getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(PieceType type) {
		this.type = type;
	}
	/**
	 * @return the color
	 */
	public CatanColor getColor() {
		return color;
	}
	/**
	 * @param color the color to set
	 */
	public void setColor(CatanColor color) {
		this.color = color;
	}
}
