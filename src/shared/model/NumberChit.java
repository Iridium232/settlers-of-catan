package shared.model;
import shared.definitions.*;
import shared.locations.*;

/**
 * A class that holds the number assigned to each terrain hex.
 * Once set this cannot move
 * 
 * @invariants The value of a Number Chit can only be between 2-12
 *
 */
public class NumberChit 
{
	private HexLocation location = null;
	private int value = -1;
	
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
		assert this.location == null;
		this.location = location;
	}
	
	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * @param value the value to set
	 */
	public void setValue(int value) {
		assert this.value == -1;
//		assert value >= 2;
		assert value <= 12;
		this.value = value;
	}
	

}
