package shared.model.map.buildings;

import shared.communication.fromServer.game.VertexLocation;
import shared.definitions.CatanColor;



/**
 * Extends the Building Abstract Class
 * Has a catan color representing the player it belongs to
 * Has a VertexLocation representing its location on the map
 */
public class City extends Building 
{

	/**
	 * Constuctor
	 * @param color
	 * @param place
	 * @param index
	 */
	public City(String color, shared.communication.fromServer.game.VertexLocation place, int index) 
	{
		super();
		this.color = CatanColor.valueOf(color.toUpperCase());
		this.location = new shared.locations.VertexLocation(place);
		this.owner = index;
	}

	/**
	 * Default Constructor
	 */
	public City() 
	{
		// TODO Auto-generated constructor stub
	}
}