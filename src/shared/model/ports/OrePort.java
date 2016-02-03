package shared.model.ports;


import shared.definitions.ResourceType;
import shared.model.Vertex;

/**
 * Class that allows trading Ore
 * @author jeyrey
 *
 */
public class OrePort extends Port {

	public OrePort(Vertex vertex1, int vertX, int vertY, Vertex vertex2,
			int ratio) {
		super(vertex1, vertX, vertY, vertex2, ratio);
		this.resource = ResourceType.ORE;
	}
	

}
