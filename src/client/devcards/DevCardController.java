package client.devcards;

import shared.definitions.ResourceType;
import client.base.*;
import client.control.IObserver;
import client.control.Reference;


/**
 * "Dev card" controller implementation
 */
public class DevCardController extends Controller implements IDevCardController, IObserver {
	private Reference r=Reference.GET_SINGLETON();
	private IBuyDevCardView buyCardView;
	private IAction soldierAction;
	private IAction roadAction;
	
	/**
	 * DevCardController constructor
	 * 
	 * @param view "Play dev card" view
	 * @param buyCardView "Buy dev card" view
	 * @param soldierAction Action to be executed when the user plays a soldier card.  It calls "mapController.playSoldierCard()".
	 * @param roadAction Action to be executed when the user plays a road building card.  It calls "mapController.playRoadBuildingCard()".
	 */
	public DevCardController(IPlayDevCardView view, IBuyDevCardView buyCardView, 
								IAction soldierAction, IAction roadAction) {

		super(view);
		
		this.buyCardView = buyCardView;
		this.soldierAction = soldierAction;
		this.roadAction = roadAction;
	}

	public IPlayDevCardView getPlayCardView() {
		return (IPlayDevCardView)super.getView();
	}

	public IBuyDevCardView getBuyCardView() {
		return buyCardView;
	}
/** 
 * @pre it is the players turn and the player has an ore, a wool and a wheat.
 * @post display a confirmation window
 */
	@Override
	public void startBuyCard() {
		
		getBuyCardView().showModal();
	}
/**
 * @post remove the confirmation window and do nothing
 */
	@Override
	public void cancelBuyCard() {
		
		getBuyCardView().closeModal();
	}
/**
 * remove the resources and give the player a dev card.
 */
	@Override
	public void buyCard() {
		
		getBuyCardView().closeModal();
	}
/**
 * @pre the player has playable cards and it is their turn
 * @post display the dev card view
 */
	@Override
	public void startPlayCard() {
		
		getPlayCardView().showModal();
	}
/**
 * @post reomve the play card view
 */
	@Override
	public void cancelPlayCard() {

		getPlayCardView().closeModal();
	}
/**
 * @pre the player has a monopoly card
 * @post the player chooses which resource they want and receive all of them owned by other players
 */
	@Override
	public void playMonopolyCard(ResourceType resource) {
		r.getProxy().monopoly(resource);
	}
/**
 * @pre the player has a monument card
 * @post the player gets a victory point.
 */
	@Override
	public void playMonumentCard() {
		r.getProxy().monument();
	}
/**
 * @pre the player has a roadbuilding card
 * @post the player chooses two valid locations for roads and they are placed on the map
 */
	@Override
	public void playRoadBuildCard() {
		
		roadAction.execute();
	}
/**
 * @pre the player has a soldier card
 * @post displays a view for the player to choose a new location for the robber, and then displays options to choose which player to rob. And gets a random card from the player
 */
	@Override
	public void playSoldierCard() {
		
		soldierAction.execute();
	}
/**
 * @pre the player has a year of plenty card
 * @post display the view to choose two resourcetypes to receive the player gets their choices as long as the bank has them.
 */
	@Override
	public void playYearOfPlentyCard(ResourceType resource1, ResourceType resource2) {
		r.getProxy().yearOfPlenty(resource1, resource2);
	}

@Override
public void ObservableChanged() {
	// TODO Auto-generated method stub
	
}

}

