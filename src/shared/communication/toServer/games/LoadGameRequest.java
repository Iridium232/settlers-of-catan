package shared.communication.toServer.games;

/**
 * Michael Rhodes
 * CS 340
 * Section 2
 * Team 10
 *
 * The game file to load
 */
public class LoadGameRequest {
    /** The name of the saved game file that you want to load. (The game's ID is restored as well.) */
    private String name;

    public LoadGameRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
