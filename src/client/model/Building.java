package client.model;
import shared.definitions.*;
import shared.locations.*;


/**
 *Class that holds information about Game Pieces
 *These are Settlements or Cities and not Roads 
 *They have a color to link them to their player
 *They have a location on a vertex of the map
 *
 *@invariants The piece type is either SETTLEMENT or CITY
 */
public class Building 
{
	private VertexLocation location;
	private PieceType type;
	private CatanColor color;
}
