package shared.model.ports;

import shared.definitions.ResourceType;
import shared.model.Vertex;

/**
 * Class that allows trading Bricks
 * @author jeyrey
 *
 */
public class BrickPort extends Port 
{

	public BrickPort(Vertex vertex1, int vertX, int vertY, Vertex vertex2,
			int ratio) {
		super(vertex1, vertX, vertY, vertex2, ratio);
		this.resource = ResourceType.BRICK;
	}

	public BrickPort() {
		// TODO Auto-generated constructor stub
	}

}
