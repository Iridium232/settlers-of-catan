package shared.model.ports;


import shared.definitions.ResourceType;

/**
 * Class that allows trading Wheat
 * @author jeyrey
 *
 */
public class WheatPort extends Port
{
    public WheatPort(int vertX, int vertY, String direction, int ratio) {
        super(vertX, vertY, direction, ratio);
        this.resource = ResourceType.WHEAT;
    }
}
