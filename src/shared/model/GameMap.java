package shared.model;
import java.util.*;
import shared.locations.*;
import shared.definitions.*;

/**
 * The class that represents the map of the game. 
 * It knows where all the hexes are and what their number chits are.
 * It knows where all the harbors and edges and vertexes are and who
 * has built on them. It is mostly a container class.
 * 
 * @invariants There is only one port of each type and two misc ports
 * 
 */
public class GameMap 
{
	private TerrainHex[] hexes;
	private Port[] ports;
	private Road[] roads;
	private Building[] buildings;
	private Robber robber = new Robber();
	private int radius;
	
	/**
	 * Builds a GameMap based on a JSON string
	 * 
	 * @pre map_json is a valid standardized form json object for a Catan map
	 * @post a map is created and reflects the data serialized
	 */
	public GameMap(String map_json)
	{
		
	}
	
	/**
	 * Gets a list of Ports accessible to the player by color
	 * 
	 * @pre the color represents a real player of the game
	 * @post result = an array of ports accessible to that player based
	 * on where they have buildings
	 */
	public Port[] getPortsAccessibleTo(CatanColor player_color)
	{
			return null;	
	}
	
	/**
	 * Determines whether that edge represents a valid road placement
	 * 
	 * @param edge
	 * @param player_color
	 * @pre the player_color is currently playing and the edge exists in the map
	 * @post returns true iff it is a valid road placement
	 */
	public boolean canBuildRoad(EdgeLocation edge, CatanColor player_color)
	{
		return false;//TODO
	}
	
	/**
	 * Moves the robber to the specified hex location
	 * @param location
	 * @pre the active player is allowed to move the robber right now
	 * @pre the location is a valid location to move the robber
	 * 
	 * @post the robber is on the specified location
	 */
	public void moveRobber(HexLocation location)
	{
		robber.setLocation(location);
	}
	
	/**
	 * Adds a Road to the map for setup
	 * @param road
	 * @pre this is a valid road not already on the game map
	 * @post the road is registered on the map
	 */
	public void addRoad(Road edge)
	{
		//TODO
	}
	
	/**
	 * Tells whether a building can be added by this player at this location
	 * 
	 * @param type
	 * @param location
	 * @param player_color
	 * @pre the player with this color is playing the game
	 * @pre the location is on the map
	 * @post result is True if this is a valid building placement based on existing structures on the map
	 */
	public boolean canAddBuilding(PieceType type, VertexLocation location, CatanColor player_color)
	{
		return false;//TODO
	}
	
	/**
	 * adds a Building to the Map for setup
	 * @param building
	 * @pre this is a valid building not already on the game map
	 * @post the building is registered on the map
	 */
	public void addBuilding(Building building)
	{
		//TODO
	}
	
	/**
	 * adds a TerrainHex to the map for setup
	 * @param hex
	 * @param location
	 * @pre this is a valid terrain hex not already on the game map
	 * @post the terrain hex is registered on the terrain map
	 */
	public void addTerrainHex(TerrainHex hex)
	{
		//TODO;
	}
	
	/**
	 * Adds a port to the map for setup
	 * @param port
	 * @pre this is a valid port on a valid location
	 * @post the port is registered on the game map
	 */
	public void addPort(Port port)
	{
		//TODO
	}

	/**
	 * @return the robber
	 */
	public Robber getRobber() {
		return robber;
	}

	/**
	 * @return the hexes
	 */
	public TerrainHex[] getHexes() {
		return hexes;
	}

	/**
	 * @return the ports
	 */
	public Port[] getPorts() {
		return ports;
	}

	/**
	 * @return the roads
	 */
	public Road[] getRoads() {
		return roads;
	}

	/**
	 * @return the buildings
	 */
	public Building[] getBuildings() {
		return buildings;
	}

	/**
	 * @return the radius
	 */
	public int getRadius() {
		return radius;
	}
	

	
	
}
