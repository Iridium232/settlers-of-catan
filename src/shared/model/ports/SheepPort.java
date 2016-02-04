package shared.model.ports;


import shared.definitions.ResourceType;
import shared.model.Vertex;

/**
 * Class that allows trading Sheep
 * @author jeyrey
 *
 */
public class SheepPort extends Port {

	public SheepPort(Vertex vertex1, int vertX, int vertY, Vertex vertex2,
			int ratio) {
		super(vertex1, vertX, vertY, vertex2, ratio);
		this.resource = ResourceType.SHEEP;
	}

	public SheepPort() {
		// TODO Auto-generated constructor stub
	}

}
