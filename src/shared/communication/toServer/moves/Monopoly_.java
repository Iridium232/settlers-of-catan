package shared.communication.toServer.moves;

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

    public Monopoly_(int playerIndex, String resource) {
        super("Monopoly", playerIndex);
        this.resource = resource;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }
}
