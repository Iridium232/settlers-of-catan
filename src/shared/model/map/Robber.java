package shared.model.map;

import shared.locations.*;

/**
 * Class representing the Robber. It keeps track of where he is.
 * @invariants The Robber is always on a HexLocation with a
 * TerrainHex on it
 *
 */
public class Robber 
{
	private HexLocation location;

	/**
	 * @return the location
	 */
	public HexLocation getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(HexLocation location) {
		this.location = location;
	}
}
