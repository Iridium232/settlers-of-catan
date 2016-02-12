package client.maritime;

import shared.definitions.*;
import client.base.*;


/**
 * Implementation for the maritime trade controller
 */
public class MaritimeTradeController extends Controller implements IMaritimeTradeController {

	private IMaritimeTradeOverlay tradeOverlay;
	
	public MaritimeTradeController(IMaritimeTradeView tradeView, IMaritimeTradeOverlay tradeOverlay) {
		
		super(tradeView);

		setTradeOverlay(tradeOverlay);
	}
	
	public IMaritimeTradeView getTradeView() {
		
		return (IMaritimeTradeView)super.getView();
	}
	
	public IMaritimeTradeOverlay getTradeOverlay() {
		return tradeOverlay;
	}

	public void setTradeOverlay(IMaritimeTradeOverlay tradeOverlay) {
		this.tradeOverlay = tradeOverlay;
	}
/**
 * @pre it is currently the players turn
 * @post brings up the trade overlay
 */
	@Override
	public void startTrade() {
		
		getTradeOverlay().showModal();
	}
/**
 * @pre player has selected at least one resource with the required amounts. 
 * @post the traded resource type is decremented and the received type is incremented
 */
	@Override
	public void makeTrade() {

		getTradeOverlay().closeModal();
	}
/**
 * @pre the trade view is open
 * @post closes the trade view. 
 */
	@Override
	public void cancelTrade() {

		getTradeOverlay().closeModal();
	}
/**
 * @pre the bank has the desired type available
 * @post highlights the selected resource to receive
 */
	@Override
	public void setGetResource(ResourceType resource) {

	}
/** 
 * @pre Has the required amount of the selected resource whether it be 4, 3, or 2
 * @post highlights the selected resource.
 */
	@Override
	public void setGiveResource(ResourceType resource) {

	}
/**
 * @pre getvalue is set
 * @post the get selection is removed 
 */
	@Override
	public void unsetGetValue() {

	}
/**
 * @pre give value is set
 * @post the give selection is removed
 */
	@Override
	public void unsetGiveValue() {

	}

}

