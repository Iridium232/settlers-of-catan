package shared.model.ports;


import shared.definitions.ResourceType;
import shared.model.Vertex;

/**
 * Class that allows trading Wood
 * @author jeyrey
 *
 */
public class WoodPort extends Port 
{

	public WoodPort(Vertex vertex1, int vertX, int vertY, Vertex vertex2,
			int ratio) {
		super(vertex1, vertX, vertY, vertex2, ratio);
		this.resource = ResourceType.WOOD;
	}

	public WoodPort() {
		// TODO Auto-generated constructor stub
	}

}
