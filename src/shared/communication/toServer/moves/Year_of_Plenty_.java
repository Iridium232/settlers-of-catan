package shared.communication.toServer.moves;

import shared.definitions.ResourceType;

/**
 * Michael Rhodes
 * CS 340
 * Section 2
 * Team 10
 *
 * Year_of_Plenty command object
 */
public class Year_of_Plenty_ extends Command {
    private ResourceType resource1;
    private ResourceType resource2;

    public Year_of_Plenty_(int playerIndex, ResourceType one, ResourceType two) {
        super("Year_of_Plenty", playerIndex);
        this.resource1 = one;
        this.resource2 = two;
    }

    public String getResource1() {
        return resource1;
    }

    public void setResource1(String resource1) {
        this.resource1 = resource1;
    }

    public String getResource2() {
        return resource2;
    }

    public void setResource2(String resource2) {
        this.resource2 = resource2;
    }
}
