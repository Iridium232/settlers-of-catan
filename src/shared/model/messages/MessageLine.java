package shared.model.messages;

/**
 * 
 * Message line has a message and a source.
 *
 */
public class MessageLine 
{
	private String message;
	private String source;
	
	/**
	 * constructor
	 * 
	 * @param sender
	 * @param message2
	 */
	public MessageLine(String sender, String message) 
	{
		this.message = message;
		this.source = sender;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}
	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}
}
