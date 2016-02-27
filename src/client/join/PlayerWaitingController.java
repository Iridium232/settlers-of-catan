package client.join;

import client.base.*;
import client.communication.IServerProxy;
import client.control.Reference;
import client.data.PlayerInfo;
import shared.definitions.CatanColor;
import shared.model.Fascade;
import shared.model.player.Player;

import java.util.ArrayList;


/**
 * Implementation for the player waiting controller
 */
public class PlayerWaitingController extends Controller implements IPlayerWaitingController {

	/**
	 * Instantiates a new Player waiting controller.
	 *
	 * @param view the view
	 */
	public PlayerWaitingController(IPlayerWaitingView view) {

		super(view);
	}

	@Override
	public IPlayerWaitingView getView() {

		return (IPlayerWaitingView)super.getView();
	}

	/**
	 * make a list of the players from the current game
	 * getView().setPlayers(list)
	 * getView().setAIChoices(AI if so)
	 * showModal
	 */
	@Override
	public void start() {
		Fascade f = Reference.GET_SINGLETON().getFascade();
		ArrayList<PlayerInfo> playerInfos = new ArrayList<>();
		if (f.getModel() != null) {
			for (Player player : f.getModel().getPlayers()) {
				PlayerInfo playerInfo = new PlayerInfo();
				playerInfo.setName(player.getName());
				playerInfo.setColor(getCatanColor(player));
				playerInfo.setId(player.getPlayerID());
				playerInfo.setPlayerIndex(player.getPlayerIndex());
				playerInfos.add(playerInfo);
			}
		}
		String[] AIValues = { "LARGEST_ARMY" };
		try {
			getView().setPlayers(convertToArray(playerInfos));
			getView().setAIChoices(AIValues);
			getView().showModal();
			if (playerInfos.size() == 4) {
				getView().closeModal();
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * Generates an AI to add to player list
	 */
	@Override
	public void addAI() {
		IServerProxy sp = Reference.GET_SINGLETON().getProxy();
		sp.addAIPlayer("LARGEST_ARMY");
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

	private PlayerInfo[] convertToArray(ArrayList<PlayerInfo> playerInfos) {
		PlayerInfo[] newArray = new PlayerInfo[playerInfos.size()];
		int index = 0;
		for (PlayerInfo playerInfo : playerInfos) {
			newArray[index] = playerInfo;
			index++;
		}
		return newArray;
	}

}

