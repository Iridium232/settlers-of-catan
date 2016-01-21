package shared.model;

/**
 * 
 * The trade offer currently on the table.
 * It has index references to players
 * 
 * @invariants the sender and reciever are always
 * between 0 and 3 and are different from each other
 *
 */
public class TradeOffer 
{
	private int sender;
	private int reciever;
	private ResourceList offer;
	/**
	 * @return the sender
	 */
	public int getSender() {
		return sender;
	}
	/**
	 * @param sender the sender to set
	 */
	public void setSender(int sender) {
		this.sender = sender;
	}
	/**
	 * @return the reciever
	 */
	public int getReciever() {
		return reciever;
	}
	/**
	 * @param reciever the reciever to set
	 */
	public void setReciever(int reciever) {
		this.reciever = reciever;
	}
	/**
	 * @return the offer
	 */
	public ResourceList getOffer() {
		return offer;
	}
	/**
	 * @param offer the offer to set
	 */
	public void setOffer(ResourceList offer) {
		this.offer = offer;
	}
}
