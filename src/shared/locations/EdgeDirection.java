package shared.locations;

public enum EdgeDirection
{
	
	NorthWest, North, NorthEast, SouthEast, South, SouthWest;
	
	private EdgeDirection opposite;
	
	static
	{
		NorthWest.opposite = SouthEast;
		North.opposite = South;
		NorthEast.opposite = SouthWest;
		SouthEast.opposite = NorthWest;
		South.opposite = North;
		SouthWest.opposite = NorthEast;
	}
	
	private String[] strings = {"NW","N", "NE", "SE", "S", "SW"};
	
	@Override
	public String toString()
	{
		return strings[this.ordinal()];
	}
	
	public EdgeDirection getOppositeDirection()
	{
		return opposite;
	}
}

