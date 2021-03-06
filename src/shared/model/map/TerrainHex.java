package shared.model.map;
import shared.definitions.*;
import shared.locations.*;

/**
 * The TerrainHex class represents a hexagonal map section
 * It will have a NumberChit unless it is of type DESERT
 * Its location, number and type do not change during the game.
 *
 */
public class TerrainHex 
{
	private HexLocation location = null;
	private HexType type = null;
	private NumberChit number = null ;
	
	/**
	 * Default Constructor
	 * @param i
	 * @param j
	 * @param water
	 */
	public TerrainHex(int i, int j, HexType water) 
	{
		location = new HexLocation(i,j);
		type = water;
	}

	/**
	 * Default Constructor
	 */
	public TerrainHex() 
	{
		
	}

	/**
	 * constructor
	 * @param nextHexType
	 * @param j
	 * @param i
	 */
	public TerrainHex(HexType type, int j, int i, NumberChit number) 
	{
		this.location = new HexLocation(j,i);
		this.type = type;
		this.number = number;
	}

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
		assert (this.location == null);
		this.location = location;
	}
	
	/**
	 * @return the type
	 */
	public HexType getType() {
		return type;
	}
	
	/**
	 * @param type the type to set
	 */
	public void setType(HexType type) {
		assert this.type == null;
		this.type = type;
	}
	
	/**
	 * @return the number
	 */
	public NumberChit getNumber() {
		return number;
	}
	
	/**
	 * @param number the number to set
	 */
	public void setNumber(NumberChit number) {
		assert (this.number == null);
		this.number = number;
	}

	/**
	 * Gets the resource of this hex
	 * 
	 * @pre none
	 * @post returns the resource you get here or null if not applicable
	 * @return
	 */
	public ResourceType getResource() 
	{
		switch (this.type)
		{
		case BRICK:
			return ResourceType.BRICK;
		case ORE:
			return ResourceType.ORE;
		case SHEEP:
			return ResourceType.SHEEP;
		case WHEAT:
			return ResourceType.WHEAT;
		case WOOD:
			return ResourceType.WOOD;
		case WATER:
		case DESERT:
		default:
			return null;
		}
	}
}
