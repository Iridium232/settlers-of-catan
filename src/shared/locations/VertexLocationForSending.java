package shared.locations;

/**
 * Represents the location of a vertex on a hex map
 */
public class VertexLocationForSending
{
	
	int X;
	int Y;
	private VertexDirection dir;
	
	public VertexLocationForSending(HexLocation hexLoc, VertexDirection dir)
	{
		setHexLoc(hexLoc);
		setDir(dir);
	}
	
	public HexLocation getHexLoc()
	{
		return new HexLocation(X,Y);
	}
	
	private void setHexLoc(HexLocation hexLoc)
	{
		if(hexLoc == null)
		{
			throw new IllegalArgumentException("hexLoc cannot be null");
		}
		this.X = hexLoc.getX();
		this.Y = hexLoc.getY();
	}
	
	public VertexDirection getDir()
	{
		return dir;
	}
	
	private void setDir(VertexDirection direction)
	{
		this.dir = direction;
	}


}

