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
	private static final int HEXINDEXOFFSET = 3;
	private TerrainHex[][] hexes = new TerrainHex[7][7];
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
	 * Default Constructor
	 */
	public GameMap() {
		ports = new Port[0];
		roads = new Road[0];
		buildings = new Building[0];
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
		//TODO
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
			if(port == null)
			{
				continue;
			}
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
		return player_ports.toArray(new Port[player_ports.size()]);	
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
		ArrayList<Road> roadsAL = new ArrayList<>();
		for (Road rd : getRoads()) {
			if (rd != null) {
				roadsAL.add(rd);
			}
		}
		roadsAL.add(edge);
		Road[] temp = new Road[roadsAL.size()];
		for (int i = 0; i < roadsAL.size(); i++) {
			temp[i] = roadsAL.get(i);
		}
		this.roads = temp;
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
		ArrayList<Building> buildingsAL = new ArrayList<>();
		for (Building bldg : getBuildings()) {
			if (bldg != null) {
				buildingsAL.add(bldg);
			}
		}
		buildingsAL.add(building);
		Building[] temp = new Building[buildingsAL.size()];
		for (int i = 0; i < buildingsAL.size(); i++) {
			temp[i] = buildingsAL.get(i);
		}
		this.buildings = temp;

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
		hexes[hex.getLocation().getX() + this.HEXINDEXOFFSET]
				[hex.getLocation().getY() + this.HEXINDEXOFFSET] = hex;
	}
	
	public TerrainHex getHexAt(int x, int y)
	{
		return hexes[x + this.HEXINDEXOFFSET]
				[y + this.HEXINDEXOFFSET];
	}
	
	/**
	 * Adds a port to the map for setup
	 * @param port
	 * @pre this is a valid port on a valid location
	 * @post the port is registered on the game map
	 */
	public void addPort(Port port)
	{
		if (ports == null)
		{
			ports = new Port[1];
			ports[0] = port;
			return;
		}
		ArrayList<Port> portsAL = new ArrayList<>();
		for (Port prt : getPorts()) {
			if (prt != null) {
				portsAL.add(prt);
			}
		}
		portsAL.add(port);
		Port[] temp = new Port[portsAL.size()];
		for (int i = 0; i < portsAL.size(); i++) {
			temp[i] = portsAL.get(i);
		}
		this.ports = temp;
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
	public TerrainHex[][] getHexes() {
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

	/**
	 * 
	 * @param location
	 * @return
	 */
	public boolean canPutRobber(HexLocation location) 
	{
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * 
	 * @param location
	 * @param player_index
	 * @param initialOverride
	 * @return
	 */
	public boolean canAddSettlement(Vertex location, int player_index, boolean initialOverride) 
	{
		//check that the spot touches land in at least 1 way.
		boolean is_on_land = touchesLand(location);

		//check that the spot is empty.
		for (Building building : buildings)
		{
			if (isOnVertex(building,location))
			{
				return false;
			}
		}
		
		//Make sure that unless it is the inital round, the player has an incoming road
		boolean has_incoming_road = false;
		if(!initialOverride)
		{
			for(Road road : roads)
			{
				boolean touches_vertex = road.getLocation().getEnd1().equals(location) 
						|| road.getLocation().getEnd2().equals(location);
				if(road.getOwnerIndex() == player_index || touches_vertex)
				{
					has_incoming_road = true;
				}
			}
			//check that there is a road to there unless Initial round Override
		}
		else
		{
			has_incoming_road = true;
		}
		
		
		//check that there are no buildings within 1 edge.
		Vertex[] neigbor_vertexes = location.getneighborLocations(this);
		for (Building building : buildings)
		{
			for(Vertex vertex: neigbor_vertexes)
			{
				if (isOnVertex(building, vertex))
				{
					return false;
				}
			}
		}
		
		return has_incoming_road && is_on_land;
	}
	
	/**
	 * Tells whether a vertex is on land.
	 * @param vertex
	 * @pre none
	 * @post returns true iff this vertex touches land in at least 1 place
	 */
	private boolean touchesLand(Vertex vertex)
	{
		HexLocation[] neighbors = vertex.getNeigborHexLocations(this);
		boolean answer = false;
		for (TerrainHex[] terrain_hex_array : hexes)
		{
			for (TerrainHex terrain_hex : terrain_hex_array)
			{
				if(terrain_hex == null)
				{
					continue;
				}
				if(neighbors[0].equals(terrain_hex.getLocation()) 
					&& terrain_hex.getType() != HexType.WATER)
				{
					answer = true;
					break;
				}
				if(neighbors[1].equals(terrain_hex.getLocation()) 
					&& terrain_hex.getType() != HexType.WATER)
				{
					answer = true;
					break;
				}
				if(neighbors[2].equals(terrain_hex.getLocation()) 
					&& terrain_hex.getType() != HexType.WATER)
				{
					answer = true;
					break;
				}
			}
		}
		return answer;
	}
	
	/**
	 * Tells whether a building is on a particular vertex
	 * @param building
	 * @param vertex
	 * @pre none
	 * @post true iff the building is on that vertex
	 */
	private boolean isOnVertex(Building building, Vertex vertex)
	{
		return building.getLocation().getNormalizedLocation()
				.equals(vertex.getLocation().getNormalizedLocation());
	}
	
	/**
	 * Gets the building on this vertex
	 * @param vertex
	 * @pre none
	 * @post returns the building on that spot or null when the place is empty
	 */
	private Building getBuildingOnVertex(Vertex vertex)
	{
		for(Building building: buildings)
		{
			if (building.getLocation().getNormalizedLocation()
				.equals(vertex.getLocation().getNormalizedLocation()))
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
		//check that the spot is on land
		Vertex side1 = edge.getEnd1();
		Vertex side2 = edge.getEnd2();
		if(!(this.touchesLand(side1) || touchesLand(side2)))
		{
			return false;//This means that the road would be over water.
		}
		
		//check that there is not already a road there
		for(Road road : roads)
		{
			if(road.getLocation().equals(edge))
			{
				return false;
			}
		}
		
		//check that there is a building OR road connecting to one of the sides
		boolean road_to_end = false;
		boolean building_at_end  = false;
		
		building_at_end = getBuildingAt(player_index, side1) != null 
				|| getBuildingAt(player_index, side2) != null;
		
		road_to_end = hasRoadTo(player_index, side1)
						|| hasRoadTo(player_index, side2);
		
		
		return road_to_end || building_at_end;
	}

	/**
	 * Tells whether this player has a road to this index
	 * @param player_index
	 * @param vertex
	 * @pre none
	 * @post true iff there is a road to this site that belongs to a player.
	 */
	private boolean hasRoadTo(int player_index, Vertex vertex) 
	{
		for (Road road: roads)
		{
			Vertex side1 = road.getLocation().getEnd1();
			Vertex side2 = road.getLocation().getEnd2();
			if((vertex.getLocation().getNormalizedLocation()
				.equals(side1.getLocation().getNormalizedLocation())))
			{
				return true;
			}
			if((vertex.getLocation().getNormalizedLocation()
				.equals(side2.getLocation().getNormalizedLocation())))
			{
				return true;
			}
		}
		return false;
	}
	
	
}
