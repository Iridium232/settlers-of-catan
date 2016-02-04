package shared.communication.toServer.moves;

import shared.definitions.ResourceType;

/**
 * Michael Rhodes
 * CS 340
 * Section 2
 * Team 10
 *
 * maritimeTrade command object
 */
public class MaritimeTrade extends Command {
    /** (Optional) The ratio of the trade your doing as an integer (ie. put 3 for a 3:1 trade) */
    private int ratio;
    /** (Optional) What type of resource you're giving */
    private ResourceType inputResource;
    /** (Optional) What type of resource you're getting */
    private ResourceType outputResource;

    public MaritimeTrade(int playerIndex, int ratio, ResourceType inputResource, ResourceType outputResource) {
        super("maritimeTrade", playerIndex);
        this.ratio = ratio;
        this.inputResource = inputResource;
        this.outputResource = outputResource;
    }

    public MaritimeTrade(int playerIndex) {
        super("maritimeTrade", playerIndex);
        this.ratio = 0;
        this.inputResource = null;
        this.outputResource = null;
    }

    public int getRatio() {
        return ratio;
    }

    public void setRatio(int ratio) {
        this.ratio = ratio;
    }

    public ResourceType getInputResource() {
        return inputResource;
    }

    public void setInputResource(ResourceType inputResource) {
        this.inputResource = inputResource;
    }

    public ResourceType getOutputResource() {
        return outputResource;
    }

    public void setOutputResource(ResourceType outputResource) {
        this.outputResource = outputResource;
    }
}
