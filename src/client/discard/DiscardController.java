package client.discard;

import shared.definitions.*;
import client.base.*;
import client.misc.*;


/**
 * Discard controller implementation
 */
public class DiscardController extends Controller implements IDiscardController {

	private IWaitView waitView;
	
	/**
	 * DiscardController constructor
	 * 
	 * @param view View displayed to let the user select cards to discard
	 * @param waitView View displayed to notify the user that they are waiting for other players to discard
	 */
	public DiscardController(IDiscardView view, IWaitView waitView) {
		
		super(view);
		
		this.waitView = waitView;
	}

	public IDiscardView getDiscardView() {
		return (IDiscardView)super.getView();
	}
	
	public IWaitView getWaitView() {
		return waitView;
	}
/**
 * @pre has already decreased the amount for this resourcetype
 * @post counter for specified ResourceType is increased by 1
 */
	@Override
	public void increaseAmount(ResourceType resource) {
		
	}
/**
 * @pre count for this ResourceType is at least 1
 * @post count for this ResourceType is decreased by 1
 */
	@Override
	public void decreaseAmount(ResourceType resource) {
		
	}
/**
 * @pre the player no longer has more than half the cards they started with
 * @post the ResourceTypes selected are decremented.
 */
	@Override
	public void discard() {
		
		getDiscardView().closeModal();
	}

}

