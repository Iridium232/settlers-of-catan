package shared.model.ports;


import shared.definitions.ResourceType;
import shared.locations.EdgeDirection;
import shared.model.map.Vertex;

/**
 * Class that allows trading Ore
 * @author jeyrey
 *
 */
public class OrePort extends Port {

	public OrePort(Vertex vertex1, int vertX, int vertY, Vertex vertex2,
			int ratio, EdgeDirection direction) {
		super(vertex1, vertX, vertY, vertex2, ratio, direction);
		this.resource = ResourceType.ORE;
	}

	public OrePort() {
		// TODO Auto-generated constructor stub
	}
	

}
