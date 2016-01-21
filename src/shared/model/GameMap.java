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
	private HashMap<EdgeLocation, Edge> map_edges = new HashMap<EdgeLocation, Edge>();
	private HashMap<VertexLocation, Vertex> map_vertexes = new HashMap<VertexLocation, Vertex>();
	private HashMap<HexLocation, TerrainHex> map_hexes = new HashMap<HexLocation, TerrainHex>();
	private ArrayList<Port> map_ports = new ArrayList<Port>();
	private Robber robber = new Robber();
	
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
	 * Adds an Edge to the map for setup
	 * @param edge
	 * @param location
	 * @pre this is a valid edge not already on the game map
	 * @post the edge is registered on the map
	 */
	public void addEdge(Edge edge)
	{
		assert !map_edges.containsKey(edge.getLocation());
		map_edges.put(edge.getLocation(), edge);
	}
	
	/**
	 * adds a Vertex to the Map for setup
	 * @param vertex
	 * @param location
	 * @pre this is a valid vertex not already on the game map
	 * @post the vertex is registered on the map
	 */
	public void addVertex(Vertex vertex)
	{
		assert !map_edges.containsKey(vertex.getLocation());
		map_vertexes.put(vertex.getLocation(), vertex);
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
		assert !map_edges.containsKey(hex.getLocation());
		map_hexes.put(hex.getLocation(), hex);
	}
	
	/**
	 * Adds a port to the map for setup
	 * @param port
	 * @pre this is a valid port on a valid location
	 * @post the port is registered on the game map
	 */
	public void addPort(Port port)
	{
		map_ports.add(port);
	}

	/**
	 * @return the map_edges
	 */
	public HashMap<EdgeLocation, Edge> getMap_edges() {
		return map_edges;
	}

	/**
	 * @return the map_vertexes
	 */
	public HashMap<VertexLocation, Vertex> getMap_vertexes() {
		return map_vertexes;
	}

	/**
	 * @return the map_hexes
	 */
	public HashMap<HexLocation, TerrainHex> getMap_hexes() {
		return map_hexes;
	}

	/**
	 * @return the map_ports
	 */
	public ArrayList<Port> getMap_ports() {
		return map_ports;
	}

	/**
	 * @return the robber
	 */
	public Robber getRobber() {
		return robber;
	}
	

	
	
}
