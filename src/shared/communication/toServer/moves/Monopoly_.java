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
    private String resource;

    public Monopoly_(int playerIndex, String one) {
        super("Monopoly", playerIndex);
        this.resource = one;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }
}
