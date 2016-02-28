package client.resources;

import java.util.*;

import client.base.*;


/**
 * Implementation for the resource bar controller
 */
public class ResourceBarController extends Controller implements IResourceBarController {

	private Map<ResourceBarElement, IAction> elementActions;
	
	public ResourceBarController(IResourceBarView view) {

		super(view);
		
		elementActions = new HashMap<ResourceBarElement, IAction>();
	}

	@Override
	public IResourceBarView getView() {
		return (IResourceBarView)super.getView();
	}

	/**
	 * Sets the action to be executed when the specified resource bar element is clicked by the user
	 * 
	 * @param element The resource bar element with which the action is associated
	 * @param action The action to be executed
	 */
	public void setElementAction(ResourceBarElement element, IAction action) {

		elementActions.put(element, action);
	}
/**
 * @pre It is the players turn and the player has at least one brick and one wood necessary to build a road
 * @post the place road map view is displayed
 */
	@Override
	public void buildRoad() {
		executeElementAction(ResourceBarElement.ROAD);
	}
/**
 * @pre it is the players turn and the player has at least one wheat, one wool, one brick, and one wood.
 * @post displays the building view
 */
	@Override
	public void buildSettlement() {
		executeElementAction(ResourceBarElement.SETTLEMENT);
	}
/**
 * @pre it is the players turn and the player has at least 3 wheat and 2 ore
 * @post displays the building view
 */
	@Override
	public void buildCity() {
		executeElementAction(ResourceBarElement.CITY);
	}
/**
 * @pre it is the players turn and the player has one ore, one wool, and one wheat.a card 
 * @post a dev card is given to the player
 */
	@Override
	public void buyCard() {
		executeElementAction(ResourceBarElement.BUY_CARD);
	}
/**
 * @pre it is the players turn and he has at least one dev card in his old hand or a victory point in his new hand
 * @post Brings up the play card menu
 */
	@Override
	public void playCard() {
		executeElementAction(ResourceBarElement.PLAY_CARD);
	}
	
	private void executeElementAction(ResourceBarElement element) {
		
		if (elementActions.containsKey(element)) {
			
			IAction action = elementActions.get(element);
			action.execute();
		}
	}

}

