package shared.communication.toServer.game;

/**
 * Michael Rhodes
 * CS 340
 * Section 2
 * Team 10
 *
 * The type of AI player to add (currently, LARGEST_ARMY is the only supported type)
 */
public class AddAIRequest {
    /** Only strings returned by /game/listAI are valid */
    private String AIType;

    public AddAIRequest(String AIType) {
        this.AIType = AIType;
    }

    public String getAIType() {
        return AIType;
    }

    public void setAIType(String AIType) {
        this.AIType = AIType;
    }
}
