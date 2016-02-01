package shared.model;
import java.util.*;
import shared.locations.*;
import shared.definitions.*;
import shared.model.*;
import shared.model.ports.Port;

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
	public GameMap(String map_json) throws Exception
	{
		
	}
	
	/**
	 * get players' buildings adjoining a Hex
	 * Useful for robbing or for assigning resources
	 * 
	 * @param location
	 * @pre none
	 * @post result = an array of all buildings adjoining the hex
	 * 
	 */
	public Building[] getAdjoiningPlayers(TerrainHex location) throws Exception
	{
		return null;
	}
	
	/**
	 * Gets the terrain hexes benefited by a dice roll
	 * 
	 * 
	 * @param number
	 * @pre number is between 2 and 12
	 * @post result = an array of the 1 or 2 hexes with this number
	 * 
	 */
	public TerrainHex[] getHexesByNumber(int number) throws Exception
	{
		return null;
	}
	
	/**
	 * Gets the resources gained by each player on a roll
	 * 
	 * @param number
	 * @pre number is between 2 and 12
	 * @post gives an array with the same indexes as
	 * the players saying what they each get from a roll
	 */
	public ResourceMultiSet[] whatDoTheyGet(int number) throws Exception
	{
		return null;
	}
	
	/**
	 * Gets a list of Ports accessible to the player by color
	 * 
	 * @pre the index is a real player
	 * @post result = an array of ports accessible to that player based
	 * on where they have buildings or an empty array if that player controlls
	 * no ports
	 */
	public Port[] getPortsAccessibleTo(int player_index) throws Exception
	{
		ArrayList<Port> player_ports = new ArrayList<Port>();
		for(Port port : ports)
		{
			Vertex access1 = port.getVertex1();
			Vertex access2 = port.getVertex2();
			if(this.hasBuildingAt(player_index, access1))
			{
				player_ports.add(port);
			}
			else if (this.hasBuildingAt(player_index, access2))
			{
				player_ports.add(port);
			}
		}
		return (Port[]) player_ports.toArray();	
	}
	
	/**
	 * 
	 * @param player_index
	 * @param vertex
	 * @post true iff the player has a building at that Vertex;
	 */
	public boolean hasBuildingAt(int player_index, Vertex vertex) 
	{
		for(Building building : buildings)
		{
			if (building.getLocation().getNormalizedLocation().equals(vertex.getLocation().getNormalizedLocation()))
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * gets the building the player has at that vertex
	 * @param player_index
	 * @param vertex
	 * @pre
	 * @post returns the building the player has there or null if they have no building there
	 */
	public Building getBuildingAt(int player_index, Vertex vertex)
	{
		for(Building building : buildings)
		{
			if (building.getLocation().getNormalizedLocation().equals(vertex.getLocation().getNormalizedLocation()))
			{
				return building;
			}
		}
		return null;
	}

	/**
	 * Determines whether that edge represents a valid road placement
	 * 
	 * @param edge
	 * @param player_index
	 * @pre the player_color is currently playing and the edge exists in the map
	 * @post returns true iff it is a valid road placement
	 */
	public boolean canBuildRoad(Edge edge, int player_index)
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
	public void moveRobber(HexLocation location) throws Exception
	{
		robber.setLocation(location);
	}
	
	/**
	 * Adds a Road to the map for setup
	 * @param road
	 * @pre this is a valid road not already on the game map
	 * @post the road is registered on the map
	 */
	public void addRoad(Road edge) throws Exception
	{
		//TODO
	}
	

	
	/**
	 * Tells whether the player can put a city there legally
	 * @param location
	 * @param player_index
	 * @pre none
	 * @post true iff they already have a settlement there
	 */
	public boolean canAddCity(Vertex location,int  player_index)
	{
		Building current_occupant = this.getBuildingAt(player_index, location);
		if(current_occupant == null) return false;
		Settlement example = new Settlement();
		return (current_occupant.getClass().equals(example.getClass()));
	}
	
	
	/**
	 * adds a Building to the Map for setup
	 * @param building
	 * @pre this is a valid building not already on the game map
	 * @post the building is registered on the map
	 */
	public void addBuilding(Building building) throws Exception
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

	public boolean canPutRobber(HexLocation location) 
	{
		// TODO Auto-generated method stub
		return true;
	}

	public boolean canAddSettlement(Vertex location, int player_index) 
	{
		return false;
	}
	
	
}
