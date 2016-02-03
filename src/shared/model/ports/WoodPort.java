package shared.model.ports;


import shared.definitions.ResourceType;

/**
 * Class that allows trading Wood
 * @author jeyrey
 *
 */
public class WoodPort extends Port 
{
    public WoodPort(int vertX, int vertY, String direction, int ratio) {
        super(vertX, vertY, direction, ratio);
        this.resource = ResourceType.WOOD;
    }
}
