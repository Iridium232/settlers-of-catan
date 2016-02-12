package client.join;

import client.base.*;


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
	 * make a list of the players from the currernt game
	 * getView().setPlayers(list)
	 * getView().setAIChoices(AI if so)
	 * showModal
	 */
	@Override
	public void start() {
		try {
			getView().setPlayers(null);
			getView().setAIChoices(null);
			getView().showModal();
			getView().closeModal();
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * Generates a AI to add to player list
	 */
	@Override
	public void addAI() {

		// TEMPORARY
		getView().closeModal();
	}

}

