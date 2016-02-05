package shared.model;
import java.util.*;

/**
 * 
 * A container for all messages in the game. It is 
 * used to make the log and the chat.
 * 
 */
public class MessageList 
{
	private ArrayList<MessageLine> lines = new ArrayList<MessageLine>();
	
	/**
	 * Adds a message to the chat
	 * 
	 * @param message
	 * @pre this is a valid message sent by a player in the game
	 * @post the message is added to the chat
	 */
	public void addMessage (MessageLine message)
	{
		lines.add(message);
	}

	public ArrayList<MessageLine> getMessages() {
		return lines;
	}
}
