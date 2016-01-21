package client.model;
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
	private CatanColor color = null;
	
	/**
	 * @return the location
	 */
	public Edge getLocation() {
		return location;
	}
	
	/**
	 * @param location the location to set
	 */
	public void setLocation(Edge location) {
		assert (this.location == null);
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
		assert (this.color == null);
		this.color = color;
	}
}
