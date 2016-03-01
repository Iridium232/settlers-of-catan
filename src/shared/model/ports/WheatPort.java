package shared.model.ports;


import shared.definitions.ResourceType;
import shared.locations.EdgeDirection;
import shared.model.map.Vertex;

/**
 * Class that allows trading Wheat
 * @author jeyrey
 *
 */
public class WheatPort extends Port
{

	public WheatPort(Vertex vertex1, int vertX, int vertY, Vertex vertex2,
			int ratio, EdgeDirection direction) {
		super(vertex1, vertX, vertY, vertex2, ratio,  direction);
		this.resource = ResourceType.WHEAT;
	}

	public WheatPort() {
		// TODO Auto-generated constructor stub
	}

}
