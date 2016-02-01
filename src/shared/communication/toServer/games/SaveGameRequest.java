package shared.communication.toServer.games;

/**
 * Michael Rhodes
 * CS 340
 * Section 2
 * Team 10
 *
 * The id of the game to save and the save file name (no extensions please)
 */
public class SaveGameRequest {
    /** The ID of the game to save */
    private int id;
    /** The file name you want to save it under */
    private String name;

    public SaveGameRequest(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
