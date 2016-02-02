package shared.model.ports;

import shared.model.ports.Port;


/**
 * Class that allows trading Misc resources
 * @author jeyrey
 *
 */
public class MiscPort extends Port 
{
    public MiscPort(int vertX, int vertY, String direction, int ratio) {
        super(vertX, vertY, direction, ratio);
        this.resource = null;
    }
}
