package shared.model;
import shared.definitions.*;

/**
 * The Road is always on an Edge
 * The road is located on a border between hex locations.
 * The road's owner is identified by its color (which will match)
 * These attributes will not change once the road is instantiated.
 */
public class Road 
{
	private Edge location = null;
	private int owner_index = -1;

	private CatanColor color = null;
	private int owner;

	/**
	 * @return the location
	 */
	public Edge getLocation() {
		return location;
	}
	
	/**
	 * @param owner_index to set
	 */
	public void setOwnerIndex(int owner_index) {
		assert (this.location == null);
		this.owner_index = owner_index;
	}
	
	/**
	 * @return the color
	 */
	public int getOwnerIndex() {
		return owner_index;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(Edge location) {
		this.location = location;
	}

	public int getOwner() {
		return owner;
	}

	public void setOwner(int owner) {
		this.owner = owner;
	}

}
