package shared.model.ports;

import shared.definitions.ResourceType;
import shared.locations.EdgeDirection;
import shared.model.map.Vertex;
import shared.model.ports.Port;


/**
 * Class that allows trading Misc resources
 * @author jeyrey
 *
 */
public class MiscPort extends Port 
{

	public MiscPort(Vertex vertex1, int vertX, int vertY, Vertex vertex2,
			int ratio, EdgeDirection direction) {
		super(vertex1, vertX, vertY, vertex2, ratio, direction);
		this.resource = ResourceType.MISC;
	}

	public MiscPort() {
		// TODO Auto-generated constructor stub
	}

}
