package client.resources;

import java.util.*;

import client.base.*;
import client.control.*;
import shared.definitions.TurnStatus;
import shared.model.Game;
import shared.model.player.Player;
import shared.model.player.ResourceMultiSet;


/**
 * Implementation for the resource bar controller
 */
public class ResourceBarController extends Controller implements IResourceBarController, IObserver {
	private Reference r=Reference.GET_SINGLETON();

	private Map<ResourceBarElement, IAction> elementActions;
	
	public ResourceBarController(IResourceBarView view) {

		super(view);
		
		elementActions = new HashMap<ResourceBarElement, IAction>();
	}

	@Override
	public IResourceBarView getView() {
		return (IResourceBarView)super.getView();
	}
	
	private void enableActions(){
		Player localP=null;
		for(Player p:r.getFascade().getModel().getPlayers()){
			if(p.getPlayerID()==r.getPlayer_id()){
				localP=p;
			}
		}
		if(localP.getRoads()>0&&localP.canPlaceCity()){
			getView().setElementEnabled(ResourceBarElement.ROAD,true);
		} 
		if(localP.getCities()>0&&localP.canPlaceCity()){
			getView().setElementEnabled(ResourceBarElement.CITY,true);
		} 
		if(localP.getSettlements()>0&&localP.canPlaceSettlement(false)){
			getView().setElementEnabled(ResourceBarElement.SETTLEMENT,true);
		} 
		if(localP.canBuyDevCard()){
			getView().setElementEnabled(ResourceBarElement.BUY_CARD, true);
		} 		
		if(localP.getOldDevCards().size()>0){
			getView().setElementEnabled(ResourceBarElement.PLAY_CARD, true);
		}
	}
	private void disableActions(){
		getView().setElementEnabled(ResourceBarElement.ROAD,false);
		getView().setElementEnabled(ResourceBarElement.CITY,false);
		getView().setElementEnabled(ResourceBarElement.SETTLEMENT,false);
		getView().setElementEnabled(ResourceBarElement.BUY_CARD, false);
		getView().setElementEnabled(ResourceBarElement.PLAY_CARD, false);
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

	@Override
	public void ObservableChanged() {
		// TODO Auto-generated method stub
		Player localP=null;
		Game model=r.getFascade().getModel();
		for(Player p:r.getFascade().getModel().getPlayers()){
			if(r.getPlayer_id()==p.getPlayerID()){
				localP=p;
			}
		}
		
		ResourceMultiSet currentResources=localP.getResources();
		getView().setElementAmount(ResourceBarElement.BRICK,currentResources.getBrick());
		getView().setElementAmount(ResourceBarElement.ORE,currentResources.getOre());
		getView().setElementAmount(ResourceBarElement.SHEEP,currentResources.getSheep());
		getView().setElementAmount(ResourceBarElement.WHEAT,currentResources.getWheat());
		getView().setElementAmount(ResourceBarElement.WOOD,currentResources.getWood());
		getView().setElementAmount(ResourceBarElement.ROAD,localP.getRoads());
		getView().setElementAmount(ResourceBarElement.CITY,localP.getCities());
		getView().setElementAmount(ResourceBarElement.SETTLEMENT,localP.getSettlements());
		getView().setElementAmount(ResourceBarElement.SOLDIERS,localP.getSoldiers());
		if(model.getTurn_tracker().getActive_player() ==r.getPlayer_index()&&model.getTurnStatus(r.getPlayer_index())==TurnStatus.ROLLING){
			 enableActions();
		} else{
			disableActions();
		}
	}

}

