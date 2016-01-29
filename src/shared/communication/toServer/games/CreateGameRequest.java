package shared.communication.toServer.games;

/**
 * Michael Rhodes
 * CS 340
 * Section 2
 * Team 10
 *
 * The name and settings to use for the new game. The new game's ID can be read from the response.
 */
public class CreateGameRequest {
    /** whether the tiles should be randomly placed */
    private boolean randomTiles;
    /** whether the numbers should be randomly placed */
    private boolean randomNumbers;
    /** whether the port should be randomly placed */
    private boolean randomPorts;
    /** The name of the game */
    private String name;

    public CreateGameRequest(boolean randomTiles, boolean randomNumbers, boolean randomPorts, String name) {
        this.randomTiles = randomTiles;
        this.randomNumbers = randomNumbers;
        this.randomPorts = randomPorts;
        this.name = name;
    }

    public boolean isRandomTiles() {
        return randomTiles;
    }

    public void setRandomTiles(boolean randomTiles) {
        this.randomTiles = randomTiles;
    }

    public boolean isRandomNumbers() {
        return randomNumbers;
    }

    public void setRandomNumbers(boolean randomNumbers) {
        this.randomNumbers = randomNumbers;
    }

    public boolean isRandomPorts() {
        return randomPorts;
    }

    public void setRandomPorts(boolean randomPorts) {
        this.randomPorts = randomPorts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
