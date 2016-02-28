package client.maritime;

import client.control.IObserver;
import client.control.Reference;
import shared.definitions.*;
import client.base.*;
import shared.model.Fascade;
import shared.model.Game;
import shared.model.player.Player;
import shared.model.player.ResourceMultiSet;

import java.util.ArrayList;


/**
 * Implementation for the maritime trade controller
 */
public class MaritimeTradeController extends Controller implements IMaritimeTradeController, IObserver {

	private IMaritimeTradeOverlay tradeOverlay;
	private ResourceType give;
	private ResourceType get;
	private int ratio;
	
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
		getTradeOverlay().setStateMessage("Trade!");
		getTradeOverlay().showGiveOptions(getTradeable());
		getTradeOverlay().hideGetOptions();
		getTradeOverlay().showModal();
	}
/**
 * @pre player has selected at least one resource with the required amounts. 
 * @post the traded resource type is decremented and the received type is incremented
 */
	@Override
	public void makeTrade() {
		Reference.GET_SINGLETON().getProxy().maritimeTrade(ratio, get, give);
		getTradeOverlay().closeModal();
	}
/**
 * @pre the trade view is open
 * @post closes the trade view. 
 */
	@Override
	public void cancelTrade() {
		getTradeOverlay().hideGetOptions();
		getTradeOverlay().hideGiveOptions();
		getTradeOverlay().setTradeEnabled(false);
		getTradeOverlay().closeModal();
	}
/**
 * @pre the bank has the desired type available
 * @post highlights the selected resource to receive
 */
	@Override
	public void setGetResource(ResourceType resource) {
		getTradeOverlay().hideGetOptions();
		getTradeOverlay().selectGetOption(resource, 1);
		this.get = resource;
		getTradeOverlay().setTradeEnabled(true);
	}
/** 
 * @pre Has the required amount of the selected resource whether it be 4, 3, or 2
 * @post highlights the selected resource.
 */
	@Override
	public void setGiveResource(ResourceType resource) {
		int amount = getGiveAmount(resource);
		getTradeOverlay().hideGiveOptions();
		getTradeOverlay().selectGiveOption(resource, amount);
		this.give = resource;
		this.ratio = amount;
		getTradeOverlay().showGetOptions(getAvailable());
	}
/**
 * @pre getvalue is set
 * @post the get selection is removed 
 */
	@Override
	public void unsetGetValue() {
		getTradeOverlay().setTradeEnabled(false);
		getTradeOverlay().hideGetOptions();
		getTradeOverlay().showGetOptions(getAvailable());
	}
/**
 * @pre give value is set
 * @post the give selection is removed
 */
	@Override
	public void unsetGiveValue() {
		getTradeOverlay().setTradeEnabled(false);
		getTradeOverlay().hideGetOptions();
		getTradeOverlay().hideGiveOptions();
		getTradeOverlay().showGiveOptions(getTradeable());
	}

	@Override
	public void ObservableChanged() {
		getTradeView().enableMaritimeTrade(setButtonStatus());
	}

	private ResourceType[] getAvailable() {
		ArrayList<ResourceType> available = new ArrayList<>();
		Game model = Reference.GET_SINGLETON().getFascade().getModel();
		ResourceMultiSet bank = model.getResource_bank();
		if (bank.getBrick() > 0) {
			available.add(ResourceType.BRICK);
		}
		if (bank.getWood() > 0) {
			available.add(ResourceType.WOOD);
		}
		if (bank.getWheat() > 0) {
			available.add(ResourceType.WHEAT);
		}
		if (bank.getSheep() > 0) {
			available.add(ResourceType.SHEEP);
		}
		if (bank.getOre() > 0) {
			available.add(ResourceType.ORE);
		}
		return convertToArray(available);
	}

	private ResourceType[] getTradeable() {
		ArrayList<ResourceType> tradeables = new ArrayList<>();
		Player localPlayer = getLocalPlayer();
		Fascade f = Reference.GET_SINGLETON().getFascade();

		if (f.canTradeAtBrickHarbor(localPlayer.getPlayerIndex(), new ResourceMultiSet(2,0,0,0,0)) ||
				f.canTradeAtMiscHarbor(localPlayer.getPlayerIndex(), new ResourceMultiSet(3,0,0,0,0)) ||
				localPlayer.getResources().getBrick() >= 4) {
			tradeables.add(ResourceType.BRICK);
		}
		if (f.canTradeAtWoodHarbor(localPlayer.getPlayerIndex(), new ResourceMultiSet(0, 0, 0, 2, 0)) ||
				f.canTradeAtMiscHarbor(localPlayer.getPlayerIndex(), new ResourceMultiSet(0,0,0,3,0)) ||
				localPlayer.getResources().getWood() >= 4) {
			tradeables.add(ResourceType.WOOD);
		}
		if (f.canTradeAtWheatHarbor(localPlayer.getPlayerIndex(), new ResourceMultiSet(0, 2, 0, 0, 0)) ||
				f.canTradeAtMiscHarbor(localPlayer.getPlayerIndex(), new ResourceMultiSet(0,3,0,0,0)) ||
				localPlayer.getResources().getWheat() >= 4) {
			tradeables.add(ResourceType.WHEAT);
		}
		if (f.canTradeAtWoolHarbor(localPlayer.getPlayerIndex(), new ResourceMultiSet(0, 0, 0, 0, 2)) ||
				f.canTradeAtMiscHarbor(localPlayer.getPlayerIndex(), new ResourceMultiSet(0,0,0,0,3)) ||
				localPlayer.getResources().getSheep() >= 4) {
			tradeables.add(ResourceType.SHEEP);
		}
		if (f.canTradeAtOreHarbor(localPlayer.getPlayerIndex(), new ResourceMultiSet(0, 0, 2, 0, 0)) ||
				f.canTradeAtMiscHarbor(localPlayer.getPlayerIndex(), new ResourceMultiSet(0,0,3,0,0)) ||
				localPlayer.getResources().getOre() >= 4) {
			tradeables.add(ResourceType.ORE);
		}
		return convertToArray(tradeables);
	}

	private int getOwnedCount(ResourceType resource, Player localPlayer) {
		int owned = 0;
		switch (resource) {
			case BRICK:
				owned = localPlayer.getResources().getBrick();
				break;
			case WOOD:
				owned = localPlayer.getResources().getWood();
				break;
			case WHEAT:
				owned = localPlayer.getResources().getWheat();
				break;
			case SHEEP:
				owned = localPlayer.getResources().getSheep();
				break;
			case ORE:
				owned = localPlayer.getResources().getOre();
				break;
		}
		return owned;
	}

	private Player getLocalPlayer() {
		Reference r = Reference.GET_SINGLETON();
		for (Player player : r.getFascade().getModel().getPlayers()) {
			if (player.getPlayerIndex() == r.getPlayer_index()) {
				return player;
			}
		}
		return null;
	}

	private int getGiveAmount(ResourceType resource) {
		Fascade f = Reference.GET_SINGLETON().getFascade();
		int localPlayerIndex = Reference.GET_SINGLETON().getPlayer_index();
		int amount = 0;
		switch (resource) {
			case BRICK:
				if (f.canTradeAtBrickHarbor(localPlayerIndex, new ResourceMultiSet(2,0,0,0,0))) {
					amount = 2;
				} else if (f.canTradeAtMiscHarbor(localPlayerIndex, new ResourceMultiSet(3,0,0,0,0))) {
					amount = 3;
				} else {
					amount = 4;
				}
				break;
			case WOOD:
				if (f.canTradeAtWoodHarbor(localPlayerIndex, new ResourceMultiSet(0,0,0,2,0))) {
					amount = 2;
				} else if (f.canTradeAtMiscHarbor(localPlayerIndex, new ResourceMultiSet(0,0,0,3,0))) {
					amount = 3;
				} else {
					amount = 4;
				}
				break;
			case WHEAT:
				if (f.canTradeAtWheatHarbor(localPlayerIndex, new ResourceMultiSet(0,2,0,0,0))) {
					amount = 2;
				} else if (f.canTradeAtMiscHarbor(localPlayerIndex, new ResourceMultiSet(0,3,0,0,0))) {
					amount = 3;
				} else {
					amount = 4;
				}
				break;
			case SHEEP:
				if (f.canTradeAtWoolHarbor(localPlayerIndex, new ResourceMultiSet(0,0,0,0,2))) {
					amount = 2;
				} else if (f.canTradeAtMiscHarbor(localPlayerIndex, new ResourceMultiSet(0,0,0,0,3))) {
					amount = 3;
				} else {
					amount = 4;
				}
				break;
			case ORE:
				if (f.canTradeAtOreHarbor(localPlayerIndex, new ResourceMultiSet(0,0,2,0,0))) {
					amount = 2;
				} else if (f.canTradeAtMiscHarbor(localPlayerIndex, new ResourceMultiSet(0,0,3,0,0))) {
					amount = 3;
				} else {
					amount = 4;
				}
				break;
		}
		return amount;
	}

	private boolean setButtonStatus() {
		Reference r = Reference.GET_SINGLETON();
		Game model = r.getFascade().getModel();
		Player localPlayer = null;
		for (Player player : model.getPlayers()) {
			if (player.getPlayerIndex() == r.getPlayer_index()) {
				localPlayer = player;
			}
		}
		if (localPlayer == null) return false;

		if (getTradeable().length > 0) {
			return true;
		}
		return false;
	}

	private ResourceType[] convertToArray(ArrayList<ResourceType> resourceTypes) {
		ResourceType[] newArray = new ResourceType[resourceTypes.size()];
		int index = 0;
		for (ResourceType playerInfo : resourceTypes) {
			newArray[index] = playerInfo;
			index++;
		}
		return newArray;
	}
}

