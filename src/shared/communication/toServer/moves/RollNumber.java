package shared.communication.toServer.moves;

/**
 * Michael Rhodes
 * CS 340
 * Section 2
 * Team 10
 *
 * rollNumber command object
 */
public class RollNumber extends Command {
    /**  what number was rolled (2-12) */
    private int number;

    public RollNumber(int playerIndex, int number) {
        super("rollNumber", playerIndex);
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
