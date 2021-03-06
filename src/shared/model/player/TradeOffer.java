package shared.model.player;

import shared.communication.ResourceList;


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
	private ResourceMultiSet sender_gives = new ResourceMultiSet();
	private ResourceMultiSet reciever_gives = new ResourceMultiSet();
	
	public TradeOffer(int sender, int reciever) 
	{
		this.sender = sender;
		this.reciever = reciever;
	}
	
	/**
	 *  Default constructor
	 */
	public TradeOffer() 
	{
		
	}
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
	 * @return the sender_gives
	 */
	public ResourceMultiSet getSender_gives() {
		return sender_gives;
	}
	/**
	 * @param sender_gives the sender_gives to set
	 */
	public void setSender_gives(ResourceMultiSet sender_gives) {
		this.sender_gives = sender_gives;
	}
	/**
	 * @return the reciever_gives
	 */
	public ResourceMultiSet getReciever_gives() {
		return reciever_gives;
	}
	/**
	 * @param reciever_gives the reciever_gives to set
	 */
	public void setReciever_gives(ResourceMultiSet reciever_gives) {
		this.reciever_gives = reciever_gives;
	}
	
	/**
	 * Translates from the negative and positive representation of a trade offer
	 * @param newMultiSet
	 * @pre a new model is being created
	 * @post the sender_gives and reciever_gives are instantiated
	 */
	public void translateOffer(ResourceMultiSet offer_set) 
	{
		ResourceMultiSet sender_offers = new ResourceMultiSet();
		ResourceMultiSet reciever_offers = new ResourceMultiSet();
		
		int count = 0;
		count = offer_set.getBrick();
		if(count < 0) sender_offers.setBrick(Math.abs(offer_set.getBrick()));
		if(count > 0) reciever_offers.setBrick(Math.abs(offer_set.getBrick()));
		
		count = offer_set.getOre();
		if(count < 0) sender_offers.setOre(Math.abs(offer_set.getOre()));
		if(count > 0) reciever_offers.setOre(Math.abs(offer_set.getOre()));
		
		count = offer_set.getSheep();
		if(count < 0) sender_offers.setSheep(Math.abs(offer_set.getSheep()));
		if(count > 0) reciever_offers.setSheep(Math.abs(offer_set.getSheep()));
		
		count = offer_set.getWheat();
		if(count < 0) sender_offers.setWheat(Math.abs(offer_set.getWheat()));
		if(count > 0) reciever_offers.setWheat(Math.abs(offer_set.getWheat()));
		
		count = offer_set.getWood();
		if(count < 0) sender_offers.setWood(Math.abs(offer_set.getWood()));
		if(count > 0) reciever_offers.setWood(Math.abs(offer_set.getWood()));
		
		this.sender_gives = sender_offers;
		this.reciever_gives = reciever_offers;
	}
}
