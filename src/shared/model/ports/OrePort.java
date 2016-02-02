package shared.model.ports;


import shared.definitions.ResourceType;

/**
 * Class that allows trading Ore
 * @author jeyrey
 *
 */
public class OrePort extends Port {
    public OrePort(int vertX, int vertY, String direction, int ratio) {
        super(vertX, vertY, direction, ratio);
        this.resource = ResourceType.ORE;
    }
}
