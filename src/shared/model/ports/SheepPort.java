package shared.model.ports;


import shared.definitions.ResourceType;

/**
 * Class that allows trading Sheep
 * @author jeyrey
 *
 */
public class SheepPort extends Port {
    public SheepPort(int vertX, int vertY, String direction, int ratio) {
        super(vertX, vertY, direction, ratio);
        this.resource = ResourceType.SHEEP;
    }
}
