package client.domestic;

import client.control.IObserver;
import client.control.Reference;
import client.data.PlayerInfo;
import shared.communication.ResourceList;
import shared.definitions.*;
import client.base.*;
import client.misc.*;
import shared.model.Game;
import shared.model.player.Player;
import shared.model.player.ResourceMultiSet;

import java.util.ArrayList;


/**
 * Domestic trade controller implementation
 */
public class DomesticTradeController extends Controller implements IDomesticTradeController, IObserver {

	private IDomesticTradeOverlay tradeOverlay;
	private IWaitView waitOverlay;
	private IAcceptTradeOverlay acceptOverlay;

	private int brick_offer;
	private int wood_offer;
	private int wheat_offer;
	private int sheep_offer;
	private int ore_offer;

	private String brick_offer_type;
	private String wood_offer_type;
	private String wheat_offer_type;
	private String sheep_offer_type;
	private String ore_offer_type;

	private int targetPlayer;
	private boolean playersSet;


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

		brick_offer = 0;
		wood_offer = 0;
		wheat_offer = 0;
		sheep_offer = 0;
		ore_offer = 0;

		brick_offer_type = "none";
		wood_offer_type = "none";
		wheat_offer_type = "none";
		sheep_offer_type = "none";
		ore_offer_type = "none";

		targetPlayer = -1;
		playersSet = false;

		Reference.GET_SINGLETON().getFascade().addObserver(this);
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
		if (!playersSet) {
			getTradeOverlay().setPlayers(getPlayerInfos());
			playersSet = true;
		}
		getTradeOverlay().setStateMessage("Select the resources you want to trade");
		getTradeOverlay().showModal();
	}
/**
 * @pre the resource amount has been incremented
 * @post the resource amount is decremented
 */
	@Override
	public void decreaseResourceAmount(ResourceType resource) {
		adjustResourceOffer(resource, -1);
		int amount = resourceCount(resource);
		getTradeOverlay().setResourceAmount(resource, String.valueOf(amount));
		if (amount == 0) {
			getTradeOverlay().setResourceAmountChangeEnabled(resource, true, false);
		}
		checkOkToTrade();
	}
/**
 * @pre the player has at least one of the selected resourcetype
 * @post the selected type to trade is increased by one
 */
	@Override
	public void increaseResourceAmount(ResourceType resource) {
		adjustResourceOffer(resource, 1);
		int amount = resourceCount(resource);
		getTradeOverlay().setResourceAmount(resource, String.valueOf(amount));

		Reference r = Reference.GET_SINGLETON();
		Game model = r.getFascade().getModel();
		Player localPlayer = getLocalPlayer();

		int owned = getOwnedCount(resource, localPlayer);

		if (amount == owned && getOfferType(resource).equals("send")) {
			getTradeOverlay().setResourceAmountChangeEnabled(resource, false, true);
		} else {
			getTradeOverlay().setResourceAmountChangeEnabled(resource, true, true);
		}
		checkOkToTrade();
	}
/**
 * @pre resources have been chosen to send and receive as well as a player to send the offer to.
 * @post displays the wait overlay and displays the trade offer to the selected player
 */
	@Override
	public void sendTradeOffer() {

		getTradeOverlay().closeModal();
		getWaitOverlay().showModal();
		Reference r = Reference.GET_SINGLETON();
		r.getProxy().offerTrade(createOffer(), getPlayer());
		//Change State Properly  ******************************************************************
	}
/**
 * @post the player selected is highlighted
 */
	@Override
	public void setPlayerToTradeWith(int playerIndex) {
		targetPlayer = playerIndex;
		checkOkToTrade();
	}
/**
 * @post the selected receive resource type is highlighted
 */
	@Override
	public void setResourceToReceive(ResourceType resource) {
		setOfferType(resource, "receive");
		getTradeOverlay().setResourceAmount(resource, "0");
		getTradeOverlay().setResourceAmountChangeEnabled(resource, true, false);
	}
/**
 * @post the selected send resource is highlighted
 */
	@Override
	public void setResourceToSend(ResourceType resource) {
		setOfferType(resource, "send");
		getTradeOverlay().setResourceAmount(resource, "0");
		if (getOwnedCount(resource, getLocalPlayer()) > 0) {
			getTradeOverlay().setResourceAmountChangeEnabled(resource, true, false);
		} else {
			getTradeOverlay().setResourceAmountChangeEnabled(resource, false, false);
		}
	}
/**
 * @pre the resourcetype is highlighted
 * @post removes the highlight on the selected resource
 */
	@Override
	public void unsetResource(ResourceType resource) {
		setOfferType(resource, "none");
	}
/**
 * @post closes the trade overlay
 */
@Override
	public void cancelTrade() {
		getTradeOverlay().reset();
		getTradeOverlay().closeModal();
	}
/**
 * @pre the player has the requested resource
 * @post resources are exchanged and the views are closed
 */
	@Override
	public void acceptTrade(boolean willAccept) {
		Reference.GET_SINGLETON().getProxy().acceptTrade(willAccept);
		getAcceptOverlay().closeModal();
	}

	@Override
	public void ObservableChanged() {
		Game model = Reference.GET_SINGLETON().getFascade().getModel();
		if (model.getTrade_offer() != null) {
			if (model.getTrade_offer().getReciever() == Reference.GET_SINGLETON().getPlayer_index()) {
				getAcceptOverlay().showModal();
			}
		}
		getTradeView().enableDomesticTrade(setButtonStatus());
	}

	private int resourceCount(ResourceType resource) {
		int amount = 0;
		switch (resource) {
			case BRICK:
				amount = brick_offer;
				break;
			case WOOD:
				amount = wood_offer;
				break;
			case WHEAT:
				amount = wheat_offer;
				break;
			case SHEEP:
				amount = sheep_offer;
				break;
			case ORE:
				amount = ore_offer;
				break;
		}
		return amount;
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

	private String getOfferType(ResourceType resource) {
		switch (resource) {
			case BRICK:
				return brick_offer_type;
			case WOOD:
				return wood_offer_type;
			case WHEAT:
				return wheat_offer_type;
			case SHEEP:
				return sheep_offer_type;
			case ORE:
				return ore_offer_type;
			default:
				return "";
		}
	}

	private void setOfferType(ResourceType resource, String type) {
		switch (resource) {
			case BRICK:
				if (!brick_offer_type.equals(type)) {
					brick_offer = 0;
				}
				brick_offer_type = type;
				break;
			case WOOD:
				if (!wood_offer_type.equals(type)) {
					wood_offer = 0;
				}
				wood_offer_type = type;
				break;
			case WHEAT:
				if (!wheat_offer_type.equals(type)) {
					wheat_offer = 0;
				}
				wheat_offer_type = type;
				break;
			case SHEEP:
				if (!sheep_offer_type.equals(type)) {
					sheep_offer = 0;
				}
				sheep_offer_type = type;
				break;
			case ORE:
				if (!ore_offer_type.equals(type)) {
					ore_offer = 0;
				}
				ore_offer_type = type;
				break;
		}
		checkOkToTrade();
	}

	private ResourceList createOffer() {
		ResourceMultiSet offer = new ResourceMultiSet();
		if (getOfferType(ResourceType.BRICK).equals("send")) {
			offer.setBrick(brick_offer);
		} else if (getOfferType(ResourceType.BRICK).equals("receive")){
			offer.setBrick(-brick_offer);
		} else {
			offer.setBrick(0);
		}

		if (getOfferType(ResourceType.WOOD).equals("send")) {
			offer.setWood(wood_offer);
		} else if (getOfferType(ResourceType.WOOD).equals("receive")){
			offer.setWood(-wood_offer);
		} else {
			offer.setWood(0);
		}

		if (getOfferType(ResourceType.WHEAT).equals("send")) {
			offer.setWheat(wheat_offer);
		} else if (getOfferType(ResourceType.WHEAT).equals("receive")){
			offer.setWheat(-wheat_offer);
		} else {
			offer.setWheat(0);
		}

		if (getOfferType(ResourceType.SHEEP).equals("send")) {
			offer.setSheep(sheep_offer);
		} else if (getOfferType(ResourceType.SHEEP).equals("receive")){
			offer.setSheep(-sheep_offer);
		} else {
			offer.setSheep(0);
		}

		if (getOfferType(ResourceType.ORE).equals("send")) {
			offer.setOre(ore_offer);
		} else if (getOfferType(ResourceType.ORE).equals("receive")){
			offer.setOre(-ore_offer);
		} else {
			offer.setOre(0);
		}

		ResourceList correctedOffer = new ResourceList(offer.getBrick(), offer.getOre(),
				offer.getSheep(), offer.getWheat(),offer.getWood());

		return correctedOffer;
	}

	private Player getPlayer() {
		Game model = Reference.GET_SINGLETON().getFascade().getModel();
		for (Player player : model.getPlayers()) {
			if (player.getPlayerIndex() == targetPlayer) {
				return player;
			}
		}
		return null;
	}

	private boolean nonZerosInOffer() {
		boolean sendNonZero = false;
		boolean receiveNonZero = false;
		if (brick_offer > 0) {
			if (brick_offer_type.equals("send")) {
				sendNonZero = true;
			} else if (brick_offer_type.equals("receive")) {
				receiveNonZero = true;
			}
		}
		if (wood_offer > 0) {
			if (wood_offer_type.equals("send")) {
				sendNonZero = true;
			} else if (wood_offer_type.equals("receive")) {
				receiveNonZero = true;
			}
		}
		if (wheat_offer > 0) {
			if (wheat_offer_type.equals("send")) {
				sendNonZero = true;
			} else if (wheat_offer_type.equals("receive")) {
				receiveNonZero = true;
			}
		}
		if (sheep_offer > 0) {
			if (sheep_offer_type.equals("send")) {
				sendNonZero = true;
			} else if (sheep_offer_type.equals("receive")) {
				receiveNonZero = true;
			}
		}
		if (ore_offer > 0) {
			if (ore_offer_type.equals("send")) {
				sendNonZero = true;
			} else if (ore_offer_type.equals("receive")) {
				receiveNonZero = true;
			}
		}
		return sendNonZero && receiveNonZero;
	}

	private void checkOkToTrade() {
		if (targetPlayer != -1 && nonZerosInOffer()) {
			getTradeOverlay().setTradeEnabled(true);
			getTradeOverlay().setStateMessage("Trade!");
		} else if (nonZerosInOffer() && targetPlayer == -1) {
			getTradeOverlay().setTradeEnabled(false);
			getTradeOverlay().setStateMessage("Choose with whom you want to trade");
		} else {
			getTradeOverlay().setTradeEnabled(false);
			getTradeOverlay().setStateMessage("Select the resources you want to trade");
		}
	}

	private CatanColor getCatanColor(shared.model.player.Player player) {
		if (player.getColor().equals("red")) return CatanColor.RED;
		if (player.getColor().equals("orange")) return CatanColor.ORANGE;
		if (player.getColor().equals("yellow")) return CatanColor.YELLOW;
		if (player.getColor().equals("blue")) return CatanColor.BLUE;
		if (player.getColor().equals("green")) return CatanColor.GREEN;
		if (player.getColor().equals("purple")) return CatanColor.PURPLE;
		if (player.getColor().equals("puce")) return CatanColor.PUCE;
		if (player.getColor().equals("white")) return CatanColor.WHITE;
		return CatanColor.BROWN;
	}

	private PlayerInfo[] getPlayerInfos() {
		ArrayList<PlayerInfo> playerInfos = new ArrayList<>();
		Game model = Reference.GET_SINGLETON().getFascade().getModel();
		for (Player player : model.getPlayers()) {
			if (player.getPlayerIndex() == Reference.GET_SINGLETON().getPlayer_index()) {
				continue;
			}
			PlayerInfo playerInfo = new PlayerInfo();

			playerInfo.setName(player.getName());
			playerInfo.setColor(getCatanColor(player));
			playerInfo.setId(player.getPlayerID());
			playerInfo.setPlayerIndex(player.getPlayerIndex());

			playerInfos.add(playerInfo);
		}
		return convertToArray(playerInfos);
	}

	private boolean setButtonStatus() {
		Reference r = Reference.GET_SINGLETON();
		Game model = r.getFascade().getModel();
		Player localPlayer = getLocalPlayer();

		if (getOwnedCount(ResourceType.BRICK, localPlayer) > 0 ||
				getOwnedCount(ResourceType.WOOD, localPlayer) > 0 ||
				getOwnedCount(ResourceType.WHEAT, localPlayer) > 0 ||
				getOwnedCount(ResourceType.SHEEP, localPlayer) > 0 ||
				getOwnedCount(ResourceType.ORE, localPlayer) > 0) {
			return true;
		}
		return false;
	}

	private PlayerInfo[] convertToArray(ArrayList<PlayerInfo> playerInfos) {
		PlayerInfo[] newArray = new PlayerInfo[playerInfos.size()];
		int index = 0;
		for (PlayerInfo playerInfo : playerInfos) {
			newArray[index] = playerInfo;
			index++;
		}
		return newArray;
	}

	private Player getLocalPlayer() {
		Reference r = Reference.GET_SINGLETON();
		Game model = r.getFascade().getModel();
		Player localPlayer = null;
		for (Player player : model.getPlayers()) {
			if (player.getPlayerIndex() == r.getPlayer_index()) {
				localPlayer = player;
			}
		}

		return localPlayer;
	}

	private void adjustResourceOffer(ResourceType resource, int amount) {
		switch (resource) {
			case BRICK:
				brick_offer += amount;
				break;
			case WOOD:
				wood_offer += amount;
				break;
			case WHEAT:
				wheat_offer += amount;
				break;
			case SHEEP:
				sheep_offer += amount;
				break;
			case ORE:
				ore_offer += amount;
				break;
		}
	}
}

