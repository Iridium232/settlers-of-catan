package shared.model.ports;

import shared.definitions.ResourceType;
import shared.model.Vertex;
import shared.model.ports.Port;


/**
 * Class that allows trading Misc resources
 * @author jeyrey
 *
 */
public class MiscPort extends Port 
{

	public MiscPort(Vertex vertex1, int vertX, int vertY, Vertex vertex2,
			int ratio) {
		super(vertex1, vertX, vertY, vertex2, ratio);
		this.resource = ResourceType.MISC;
	}

}
