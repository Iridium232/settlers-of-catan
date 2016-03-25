package shared.model.map.buildings;

import shared.communication.fromServer.game.VertexLocation;
import shared.definitions.CatanColor;



/**
 * Extends the Building Abstract Class
 * Has a catan color representing the player it belongs to
 * Has a VertexLocation representing its location on the map
 */
public class Settlement extends Building {

	/**
	 * constructor
	 * @param player_index
	 * @param place
	 * @param color
	 */
	public Settlement(int player_index, VertexLocation place, String color) 
	{
		this.location = new shared.locations.VertexLocation(place);
		this.color = CatanColor.valueOf(color.toUpperCase());
		this.owner = player_index;
	}

	/**
	 * default constuctor
	 */
	public Settlement() 
	{
		// TODO Auto-generated constructor stub
	}
}
