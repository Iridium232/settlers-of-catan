package shared.model.ports;

import shared.definitions.ResourceType;

/**
 * Class that allows trading Bricks
 * @author jeyrey
 *
 */
public class BrickPort extends Port {
    public BrickPort(int vertX, int vertY, String direction, int ratio) {
        super(vertX, vertY, direction, ratio);
        this.resource = ResourceType.BRICK;
    }
}
