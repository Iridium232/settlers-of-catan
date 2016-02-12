package client.join;

import client.base.*;


/**
 * Implementation for the player waiting controller
 */
public class PlayerWaitingController extends Controller implements IPlayerWaitingController {

	public PlayerWaitingController(IPlayerWaitingView view) {

		super(view);
	}

	@Override
	public IPlayerWaitingView getView() {

		return (IPlayerWaitingView)super.getView();
	}
/**
 * brings up the waiting view
 * @pre the game is not ready to start or the player is waiting on other players to discard.
 */
	@Override
	public void start() {

		getView().showModal();
	}
/**
 * adds an AI to the game
 * @pre The game is not full
 */
	@Override
	public void addAI() {

		// TEMPORARY
		getView().closeModal();
	}

}

