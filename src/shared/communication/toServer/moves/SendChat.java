package shared.communication.toServer.moves;

/**
 * Michael Rhodes
 * CS 340
 * Section 2
 * Team 10
 *
 * sendChat command object
 */
public class SendChat extends Command {
    /***/
    private String content;

    public SendChat(int playerIndex, String content) {
        super("sendChat", playerIndex);
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
