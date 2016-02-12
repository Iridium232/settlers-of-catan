package client.domestic;

import shared.definitions.*;
import client.base.*;
import client.misc.*;


/**
 * Domestic trade controller implementation
 */
public class DomesticTradeController extends Controller implements IDomesticTradeController {

	private IDomesticTradeOverlay tradeOverlay;
	private IWaitView waitOverlay;
	private IAcceptTradeOverlay acceptOverlay;

	/**
	 * DomesticTradeController constructor
	 * 
	 * @param tradeView Domestic trade view (i.e., view that contains the "Domestic Trade" button)
	 * @param tradeOverlay Domestic trade overlay (i.e., view that lets the user propose a domestic trade)
	 * @param waitOverlay Wait overlay used to notify the user they are waiting for another player to accept a trade
	 * @param acceptOverlay Accept trade overlay which lets the user accept or reject a proposed trade
	 */
	public DomesticTradeController(IDomesticTradeView tradeView, IDomesticTradeOverlay tradeOverlay,
									IWaitView waitOverlay, IAcceptTradeOverlay acceptOverlay) {

		super(tradeView);
		
		setTradeOverlay(tradeOverlay);
		setWaitOverlay(waitOverlay);
		setAcceptOverlay(acceptOverlay);
	}
	
	public IDomesticTradeView getTradeView() {
		
		return (IDomesticTradeView)super.getView();
	}

	public IDomesticTradeOverlay getTradeOverlay() {
		return tradeOverlay;
	}

	public void setTradeOverlay(IDomesticTradeOverlay tradeOverlay) {
		this.tradeOverlay = tradeOverlay;
	}

	public IWaitView getWaitOverlay() {
		return waitOverlay;
	}

	public void setWaitOverlay(IWaitView waitView) {
		this.waitOverlay = waitView;
	}

	public IAcceptTradeOverlay getAcceptOverlay() {
		return acceptOverlay;
	}

	public void setAcceptOverlay(IAcceptTradeOverlay acceptOverlay) {
		this.acceptOverlay = acceptOverlay;
	}
/**
 * @pre it is the players turn 
 * @post displays the domestictrade view
 */
	@Override
	public void startTrade() {

		getTradeOverlay().showModal();
	}
/**
 * @pre the resource amount has been incremented
 * @post the resource amount is decremented
 */
	@Override
	public void decreaseResourceAmount(ResourceType resource) {

	}
/**
 * @pre the player has at least one of the selected resourcetype
 * @post the selected type to trade is increased by one
 */
	@Override
	public void increaseResourceAmount(ResourceType resource) {

	}
/**
 * @pre resources have been chosen to send and receive as well as a player to send the offer to.
 * @post displays the wait overlay and displays the trade offer to the selected player
 */
	@Override
	public void sendTradeOffer() {

		getTradeOverlay().closeModal();
//		getWaitOverlay().showModal();
	}
/**
 * @post the player selected is highlighted
 */
	@Override
	public void setPlayerToTradeWith(int playerIndex) {

	}
/**
 * @post the selected receive resource type is highlighted
 */
	@Override
	public void setResourceToReceive(ResourceType resource) {

	}
/**
 * @post the selected send resource is highlighted
 */
	@Override
	public void setResourceToSend(ResourceType resource) {

	}
/**
 * @pre the resourcetype is highlighted
 * @post removes the highlight on the selected resource
 */
	@Override
	public void unsetResource(ResourceType resource) {

	}
/**
 * @post closes the trade overlay
 */
	@Override
	public void cancelTrade() {

		getTradeOverlay().closeModal();
	}
/**
 * @pre the player has the requested resource
 * @post resources are exchanged and the views are closed
 */
	@Override
	public void acceptTrade(boolean willAccept) {

		getAcceptOverlay().closeModal();
	}

}

