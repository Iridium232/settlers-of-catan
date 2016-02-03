package shared.communication.toServer.moves;

import shared.definitions.ResourceType;

/**
 * Michael Rhodes
 * CS 340
 * Section 2
 * Team 10
 *
 * Monopoly command object
 */
public class Monopoly_ extends Command {
    private ResourceType resource;

    public Monopoly_(int playerIndex, ResourceType one) {
        super("Monopoly", playerIndex);
        this.resource = one;
    }

    public ResourceType getResource() {
        return resource;
    }

    public void setResource(ResourceType resource) {
        this.resource = resource;
    }
}
