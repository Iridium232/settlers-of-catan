package client.join;

import shared.definitions.CatanColor;
import client.base.*;
import client.data.*;
import client.misc.*;


/**
 * Implementation for the join game controller
 */
public class JoinGameController extends Controller implements IJoinGameController {

	private INewGameView newGameView;
	private ISelectColorView selectColorView;
	private IMessageView messageView;
	private IAction joinAction;

	/**
	 * JoinGameController constructor
	 *
	 * @param view            Join game view
	 * @param newGameView     New game view
	 * @param selectColorView Select color view
	 * @param messageView     Message view (used to display error messages that occur while the user is joining a game)
	 */
	public JoinGameController(IJoinGameView view, INewGameView newGameView,
								ISelectColorView selectColorView, IMessageView messageView) {

		super(view);

		setNewGameView(newGameView);
		setSelectColorView(selectColorView);
		setMessageView(messageView);
	}

	/**
	 * Gets join game view.
	 *
	 * @return the join game view
	 */
	public IJoinGameView getJoinGameView() {
		
		return (IJoinGameView)super.getView();
	}

	/**
	 * Returns the action to be executed when the user joins a game
	 *
	 * @return The action to be executed when the user joins a game
	 */
	public IAction getJoinAction() {
		
		return joinAction;
	}

	/**
	 * Sets the action to be executed when the user joins a game
	 *
	 * @param value The action to be executed when the user joins a game
	 */
	public void setJoinAction(IAction value) {
		
		joinAction = value;
	}

	/**
	 * Gets new game view.
	 *
	 * @return the new game view
	 */
	public INewGameView getNewGameView() {
		
		return newGameView;
	}

	/**
	 * Sets new game view.
	 *
	 * @param newGameView the new game view
	 * @post set a new game view
	 */
	public void setNewGameView(INewGameView newGameView) {
		
		this.newGameView = newGameView;
	}

	/**
	 * Gets select color view.
	 *
	 * @return the select color view
	 */
	public ISelectColorView getSelectColorView() {
		
		return selectColorView;
	}

	/**
	 * Sets select color view.
	 *
	 * @param selectColorView the select color view
	 * @post set a color view
	 */
	public void setSelectColorView(ISelectColorView selectColorView) {
		
		this.selectColorView = selectColorView;
	}

	/**
	 * Gets message view.
	 *
	 * @return the message view
	 */
	public IMessageView getMessageView() {
		
		return messageView;
	}

	/**
	 * Sets message view.
	 *
	 * @param messageView the message view
	 */
	public void setMessageView(IMessageView messageView) {
		
		this.messageView = messageView;
	}

	/**
	 * get the interface for overlay views joinGame
	 */
	@Override
	public void start() {
		
		getJoinGameView().showModal();
	}

	/**
	 * get interface for overlay views newGame
	 */
	@Override
	public void startCreateNewGame() {
		
		getNewGameView().showModal();
	}

	/**
	 * Close Modal
	 */
	@Override
	public void cancelCreateNewGame() {
		
		getNewGameView().closeModal();
	}


	/**
	 * Create a new Game board based on the options in the View(Random or not)
	 * Send create game request to server
	 * then, update Game List
	 * and then closeModal
	 */

	@Override
	public void createNewGame() {
		
		getNewGameView().closeModal();
	}



	/**
	 * Iterate through player in GameInfo and
	 * disable each color that has already been used in ISelectColorView
	 * check if you are already in
	 * if it is true,call JoinGame related with the color that player already picked
	 */
	@Override
	public void startJoinGame(GameInfo game) {

		getSelectColorView().showModal();
	}

	/**
	 * Cancel to join a game
	 */
	@Override
	public void cancelJoinGame() {
	
		getJoinGameView().closeModal();
	}


	/**
	 * call join game on server
	 */
	@Override
	public void joinGame(CatanColor color) {
		
		// If join succeeded
		getSelectColorView().closeModal();
		getJoinGameView().closeModal();
		joinAction.execute();
	}

}

