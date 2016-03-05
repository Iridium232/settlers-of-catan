package client.turntracker;

import client.communication.ModelPopulator;
import client.control.IObserver;
import client.control.Reference;
import shared.definitions.CatanColor;
import client.base.*;
import shared.definitions.TurnStatus;
import shared.model.Fascade;
import shared.model.Game;
import shared.model.player.Player;

import javax.xml.parsers.FactoryConfigurationError;

import org.json.JSONObject;


/**
 * Implementation for the turn tracker controller
 */
public class TurnTrackerController extends Controller implements ITurnTrackerController, IObserver {

	boolean playerInitialized;

	public TurnTrackerController(ITurnTrackerView view) {
		
		super(view);

		playerInitialized = false;
		
		initFromModel();
		Reference.GET_SINGLETON().getFascade().addObserver(this);
	}
	
	@Override
	public ITurnTrackerView getView() {
		
		return (ITurnTrackerView)super.getView();
	}
/**
 * @pre it is the players turn
 * @post changes the current turn and displays the roll view for the next player
 */
	@Override
	public void endTurn() {
		try {
			Fascade f = Reference.GET_SINGLETON().getFascade();
			if (f.canFinishTurn(Reference.GET_SINGLETON().getPlayer_index())) 
			{
				String finish = Reference.GET_SINGLETON().getProxy().finishTurn();
				ModelPopulator.populateModel(new JSONObject(finish), Reference.GET_SINGLETON().fascade);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void initFromModel() {
	}

	@Override
	public void ObservableChanged() {
		Reference r = Reference.GET_SINGLETON();
		Game model = r.getFascade().getModel();
		if (!playerInitialized) {
			boolean have4Players = true;
			for (Player player : model.getPlayers()) {
				if (player.getName() == null) have4Players = false;
			}
			if (have4Players) {
				for (Player player : model.getPlayers())
                {
                    if (player.getColor() == null || player == null)continue;
                    getView().initializePlayer(player.getPlayerIndex(), player.getName(), getCatanColor(player));
                }
				getView().setLocalPlayerColor(r.getPlayer_color());
				playerInitialized = true;
			}
		}
		Player localPlayer = null;
		if (playerInitialized) {
			for (Player player : model.getPlayers()) {
                if (player == null || player.getColor() == null)continue;
                if (player.getPlayerIndex() == r.getPlayer_index()) {
                    localPlayer = player;
                }
                boolean highlight = false;
                if (model.getTurn_tracker().getActive_player() == player.getPlayerIndex()) {
                    highlight = true;
                }

                boolean largestArmy = false;
                if (model.getTurn_tracker().getLargest_army_player() == player.getPlayerIndex()) {
                    largestArmy = true;
                }

                boolean longestRoad = false;
                if (model.getTurn_tracker().getLongest_road_player() == player.getPlayerIndex()) {
                    longestRoad = true;
                }

                getView().updatePlayer(player.getPlayerIndex(), player.getVictoryPoints(),
                        highlight, largestArmy, longestRoad);
            }
		}

		if (localPlayer == null) { return; }

		String label = "";
		boolean enable = false;
		TurnStatus state = model.getTurnStatus(localPlayer.getPlayerIndex());
		switch (state) {
			case ROLLING:
//				label = "State: ROLLING";
				label = "Roll the Dice";
				break;
			case ROBBING:
//				label = "State: ROBBING";
				label = "Rob a Player";
				break;
			case PLAYING:
//				label = "State: PLAYING";
				label = "Finish Turn";
				enable = true;
				break;
			case DISCARDING:
//				label = "State: DISCARDING";
				label = "Discard Cards";
				break;
			case FIRSTROUND:
//				label = "State: FIRSTROUND";
				label = "Place Structures";
				break;
			case SECONDROUND:
//				label = "State: SECONDROUND";
				label = "Place Structures";
				break;
			case WAITING:
//				label = "State: WAITING";
				label = "Waiting for Players";
				break;
			case TRADING:
//				label = "State: TRADING";
				label = "Trading";
				break;
		}
		getView().updateGameState(label, enable);
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
}

