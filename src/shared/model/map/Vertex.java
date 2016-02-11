package shared.model.map;
import shared.locations.*;

/**
 * The vertex class represents the corners of the terrain hexes where
 * a building may be built or a port may exist. 
 * 
 */
public class Vertex 
{
	private VertexLocation location = null;

	public Vertex(VertexLocation vertex_location)
	{
		location = vertex_location;
	}
	
	public VertexLocation getLocation() {
		return location;
	}
	
	public void setLocation(VertexLocation location) {
		assert (location == null);
		this.location = location;
	}
	
	public HexLocation[] getNeigborHexLocations(GameMap map_pointer)
	{
		HexLocation[] neighbors = new HexLocation[3];
		neighbors[0] = location.getNormalizedLocation().getHexLoc();//lower hex
		int lowerX = neighbors[0].getX();
		int lowerY = neighbors[0].getY();
		neighbors[1] = map_pointer.getHexAt(lowerX, lowerY + 1).getLocation();
		if(this.getLocation().getDir() == VertexDirection.NorthEast)
		{
			neighbors[2] = map_pointer.getHexAt(lowerX - 1,lowerY + 1).getLocation();//Upper Left
		}
		else if (this.getLocation().getDir() == VertexDirection.NorthWest)
		{
			neighbors[2] = map_pointer.getHexAt(lowerX + 1,lowerY).getLocation();//Upper Right
		}
		return neighbors;
	}
	
	public Vertex[] getneighborLocations(GameMap map_pointer)
	{
		Vertex[] neighbors = new Vertex[3];
		HexLocation[] neighbor_hexes = getNeigborHexLocations(map_pointer);
		if(location.getNormalizedLocation().getDir().equals(VertexDirection.NorthEast))
		{
			neighbors[0] = new Vertex(new VertexLocation(location.getHexLoc(), VertexDirection.NorthWest));
			neighbors[1] = new Vertex(new VertexLocation(location.getHexLoc(), VertexDirection.East));
			neighbors[2] = new Vertex(new VertexLocation(neighbor_hexes[1], VertexDirection.East));
		}
		else
		{
			neighbors[0] = new Vertex(new VertexLocation(location.getHexLoc(), VertexDirection.NorthEast));
			neighbors[1] = new Vertex(new VertexLocation(location.getHexLoc(), VertexDirection.West));
			neighbors[2] = new Vertex(new VertexLocation(neighbor_hexes[1], VertexDirection.West));
		}
		
		
		
		return neighbors;
	}
	
}
