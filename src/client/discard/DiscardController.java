package client.discard;


import shared.communication.ResourceList;
import shared.definitions.*;
import shared.model.player.Player;
import shared.model.player.ResourceMultiSet;

import java.util.HashMap;
import java.util.Map;

import client.base.*;
import client.control.IObserver;
import client.control.Reference;
import client.misc.*;


/**
 * Discard controller implementation
 */
public class DiscardController extends Controller implements IDiscardController, IObserver {
	private Reference r=Reference.GET_SINGLETON();
	private Player localP=null;
	private IWaitView waitView;
	private Map<ResourceType,Integer> discard=new HashMap<ResourceType,Integer>();
	private int numToDiscard=0;
	private int selected=0;
	/**
	 * DiscardController constructor
	 * 
	 * @param view View displayed to let the user select cards to discard
	 * @param waitView View displayed to notify the user that they are waiting for other players to discard
	 */
	public DiscardController(IDiscardView view, IWaitView waitView) {
		
		super(view);
		
		this.waitView = waitView;
		Reference.GET_SINGLETON().getFascade().addObserver(this);
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
		int amount=discard.get(resource);
		amount++;
		getDiscardView().setResourceDiscardAmount(resource, amount);
		discard.put(resource, amount);
		selected++;
		getDiscardView().setStateMessage(selected+"/"+numToDiscard);
		if(selected==numToDiscard){
			getDiscardView().setDiscardButtonEnabled(true);
		}
		setEnabled(resource,localP.getResources(),amount);
	}
/**
 * @pre count for this ResourceType is at least 1
 * @post count for this ResourceType is decreased by 1
 */
	@Override
	public void decreaseAmount(ResourceType resource) {
		int amount=discard.get(resource);
		amount--;
		getDiscardView().setResourceDiscardAmount(resource, amount);
		selected--;
		discard.replace(resource, amount);
		getDiscardView().setStateMessage(selected+"/"+numToDiscard);
		if(selected!=numToDiscard){
			getDiscardView().setDiscardButtonEnabled(false);
		}
		setEnabled(resource,localP.getResources(),amount);
	}
/**
 * @pre the player no longer has more than half the cards they started with
 * @post the ResourceTypes selected are decremented.
 */
	@Override
	public void discard() {
		r.getProxy().discardCards(new ResourceList(discard.get(ResourceType.BRICK),discard.get(ResourceType.ORE),
				discard.get(ResourceType.SHEEP),discard.get(ResourceType.WHEAT),discard.get(ResourceType.WOOD)));
		getDiscardView().closeModal();
		if(!getWaitView().isModalShowing()){
			getWaitView().showModal();
		}
	}

	@Override

	public void ObservableChanged() 
	{
		if(r.getFascade().getModel().getTurnStatus(r.getFascade().getActivePlayer())
				== TurnStatus.DISCARDING ){

			for(Player p:r.getFascade().getModel().getPlayers()){
				if(r.getPlayer_id()==p.getPlayerID()){
					localP=p;
				}
			}
			ResourceMultiSet hand=localP.getResources();
			if(hand.size()>7){
				if(getDiscardView().isModalShowing()==false) {
					getDiscardView().showModal();
					setResourceAmounts(hand);
					initializeMap();
					numToDiscard=hand.size()/2;
				}
			} else{
				getWaitView().showModal();
			}
		} else{
			if(getDiscardView().isModalShowing()){
				getDiscardView().closeModal();
			}
			if(getWaitView().isModalShowing()){
				getWaitView().closeModal();
			}
		}
	}
	
	private void setResourceAmounts(ResourceMultiSet hand){
		getDiscardView().setResourceMaxAmount(ResourceType.BRICK, hand.getBrick());
		if(hand.getBrick()>0){
			getDiscardView().setResourceAmountChangeEnabled(ResourceType.BRICK, true, false);
		}
		getDiscardView().setResourceMaxAmount(ResourceType.ORE, hand.getOre());
		if(hand.getOre()>0){
			getDiscardView().setResourceAmountChangeEnabled(ResourceType.ORE, true, false);
		}
		getDiscardView().setResourceMaxAmount(ResourceType.SHEEP, hand.getSheep());
		if(hand.getSheep()>0){
			getDiscardView().setResourceAmountChangeEnabled(ResourceType.SHEEP, true, false);
		}
		getDiscardView().setResourceMaxAmount(ResourceType.WHEAT, hand.getWheat());
		if(hand.getWheat()>0){
			getDiscardView().setResourceAmountChangeEnabled(ResourceType.WHEAT, true, false);
		}
		getDiscardView().setResourceMaxAmount(ResourceType.WOOD, hand.getWood());
		if(hand.getWood()>0){
			getDiscardView().setResourceAmountChangeEnabled(ResourceType.WOOD, true, false);
		}
		getDiscardView().setStateMessage("0/"+hand.size()/2);
		getDiscardView().setDiscardButtonEnabled(false);
	}
	
	private void initializeMap(){
		discard.put(ResourceType.BRICK, 0);
		discard.put(ResourceType.ORE, 0);
		discard.put(ResourceType.SHEEP, 0);
		discard.put(ResourceType.WHEAT, 0);
		discard.put(ResourceType.WOOD, 0);
	}
	
	private void setEnabled(ResourceType resource, ResourceMultiSet hand,int amount){
		if(hand.getAmount(resource)==0){
			getDiscardView().setResourceAmountChangeEnabled(resource, false, false);
		} else if(amount>=hand.getAmount(resource)){
			getDiscardView().setResourceAmountChangeEnabled(resource, false, true);
		} else if(amount<=0){
			getDiscardView().setResourceAmountChangeEnabled(resource, true, false);
		} else if(amount>0&& amount<hand.getAmount(resource)){
			getDiscardView().setResourceAmountChangeEnabled(resource, true, true);
		}
	}
}

