package client.turntracker;

import client.control.IObserver;
import client.control.Reference;
import shared.definitions.CatanColor;
import client.base.*;
import shared.definitions.TurnStatus;
import shared.model.Fascade;
import shared.model.Game;
import shared.model.player.Player;

import javax.xml.parsers.FactoryConfigurationError;


/**
 * Implementation for the turn tracker controller
 */
public class TurnTrackerController extends Controller implements ITurnTrackerController, IObserver {

	boolean playerInitialized;

	public TurnTrackerController(ITurnTrackerView view) {
		
		super(view);

		playerInitialized = false;
		
		initFromModel();
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
			Reference.GET_SINGLETON().getFascade().getModel().getTurn_tracker().advanceActivePlayer();
			Reference.GET_SINGLETON().getFascade().getModel().getTurn_tracker().advanceState();
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
			getView().initializePlayer(r.getPlayer_index(), r.getName(), r.getPlayer_color());
			getView().setLocalPlayerColor(r.getPlayer_color());
			playerInitialized = true;
		}
		Player localPlayer = null;
		for (Player player : model.getPlayers()) {
			if (player.getPlayerIndex() == r.getPlayer_index()) {
				localPlayer = player;
			}
		}

		if (localPlayer == null) { return; }

		boolean highlight = false;
		if (model.getTurn_tracker().getActive_player() == localPlayer.getPlayerIndex()) {
			highlight = true;
		}

		boolean largestArmy = false;
		if (model.getTurn_tracker().getLargest_army_player() == localPlayer.getPlayerIndex()) {
			largestArmy = true;
		}

		boolean longestRoad = false;
		if (model.getTurn_tracker().getLongest_road_player() == localPlayer.getPlayerIndex()) {
			longestRoad = true;
		}

		getView().updatePlayer(localPlayer.getPlayerIndex(), localPlayer.getVictoryPoints(),
				highlight, largestArmy, longestRoad);

		String label = "";
		boolean enable = false;
		TurnStatus state = model.getTurnStatus(localPlayer.getPlayerIndex());
		switch (state) {
			case ROLLING:
				label = "Roll the Dice";
				break;
			case ROBBING:
				label = "Rob a Player";
				break;
			case PLAYING:
				label = "Finish Turn";
				enable = true;
				break;
			case DISCARDING:
				label = "Discard Cards";
				break;
			case FIRSTROUND:
				label = "Place Structures";
				break;
			case SECONDROUND:
				label = "Place Structures";
				break;
			case WAITING:
				label = "Waiting for Players";
				break;
			case TRADING:
				label = "Trading";
				break;
		}
		getView().updateGameState(label, enable);
	}
}

