package shared.model.map.buildings;
import shared.definitions.*;
import shared.locations.*;


/**
 *Abstract Class that holds information about Game Pieces
 *These are Settlements or Cities and not Roads
 *Settlements and Cities inherit from this class
 *They have a color to link them to their player
 *They have a location on a vertex of the map
 *These attributes cannot be changed once set
 *
 *
 */
public abstract class Building
{
	protected VertexLocation location;
	protected CatanColor color;
	protected int owner;
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

	public int getOwner() {
		return owner;
	}

	public void setOwner(int owner) {
		this.owner = owner;
	}
}
