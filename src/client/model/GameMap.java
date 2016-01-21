package client.model;
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
	private HashMap<EdgeLocation, Edge> map_edges;
	private HashMap<VertexLocation, Vertex> map_vertexes;
	private HashMap<HexLocation, TerrainHex> map_hexes;
	private ArrayList<Port> map_ports;
	private Robber robber;
	
	
}
