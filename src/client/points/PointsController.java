package client.points;

import client.base.*;
import client.control.IObserver;
import client.control.Reference;
import shared.model.Game;
import shared.model.player.Player;


/**
 * Implementation for the points controller
 */
public class PointsController extends Controller implements IPointsController, IObserver {

	private IGameFinishedView finishedView;
	
	/**
	 * PointsController constructor
	 * 
	 * @param view Points view
	 * @param finishedView Game finished view, which is displayed when the game is over
	 */
	public PointsController(IPointsView view, IGameFinishedView finishedView) {
		
		super(view);
		
		setFinishedView(finishedView);
		
		initFromModel();

		Reference.GET_SINGLETON().getFascade().addObserver(this);
	}
	
	public IPointsView getPointsView() {
		
		return (IPointsView)super.getView();
	}
	/**
	 * @pre a player has accumulated 10 points
	 * @post display the game finished screen
	 * @return
	 */
	public IGameFinishedView getFinishedView() {
		return finishedView;
	}
	public void setFinishedView(IGameFinishedView finishedView) {
		this.finishedView = finishedView;
	}
/**
 * @post initializes the player info based on the model
 */
	private void initFromModel() 
	{
		getPointsView().setPoints(0);
	}

	@Override
	public void ObservableChanged() {
		Reference r = Reference.GET_SINGLETON();
		Game model = r.getFascade().getModel();
		Player localPlayer = null;
		for (Player player : model.getPlayers()) {
			if (player.getPlayerIndex() == r.getPlayer_index()) {
				localPlayer = player;
			}
		}

		if (localPlayer == null) return;
		for (Player player : model.getPlayers()) {
			if (player.getVictoryPoints() >= 10) {
				boolean isLocalPlayer = false;
				if (player.getPlayerIndex() == localPlayer.getPlayerIndex()) {
					isLocalPlayer = true;
				}
				getFinishedView().setWinner(player.getName(), isLocalPlayer);
				return;
			}
		}

		getPointsView().setPoints(r.getFascade().getVictoryPoints(r.player_index));
	}
}

