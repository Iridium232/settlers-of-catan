package client.join;

import client.base.Controller;
import client.base.IAction;
import client.control.Reference;
import client.data.GameInfo;
import client.data.PlayerInfo;
import client.main.Catan;
import client.misc.IMessageView;
import shared.communication.fromServer.games.Game;
import shared.definitions.CatanColor;
import shared.exceptions.JoinExceptions;

import java.util.List;


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
	public void setJoinAction(IAction value) 
	{	
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
	public void start() 
	{
		List<Game> gamelist = Reference.GET_SINGLETON().proxy.getGameList();
		GameInfo[] games = new GameInfo[gamelist.size()];
		int counter = 0;
		for(Game game: gamelist)
		{
			GameInfo thisgame = new GameInfo();
			thisgame.setId(game.getId());
			thisgame.setTitle(game.getTitle());
			games[counter] = thisgame;
			counter++;
		}
		
		PlayerInfo ourguy = new PlayerInfo();
		ourguy.setColor(null);
		ourguy.setId(-1);
		ourguy.setName("DEFAULT_NAME");
		ourguy.setPlayerIndex(-1);
		
		getJoinGameView().setGames(games, ourguy);
		getJoinGameView().showModal();
	}

	/**
	 * get interface for overlay views newGame
	 */
	@Override
	public void startCreateNewGame() 
	{	
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
	public void createNewGame() 
	{
		//Create new game
		boolean randomhexes = getNewGameView().getRandomlyPlaceHexes();
		boolean randomnumbers = getNewGameView().getRandomlyPlaceNumbers();
		boolean randomports = getNewGameView().getUseRandomPorts();
		String title = getNewGameView().getTitle();
		
		try 
		{
			Reference.GET_SINGLETON().proxy.createGame(title, randomhexes, randomnumbers, randomports);
		} 
		catch (JoinExceptions e) 
		{
			e.printStackTrace();
		}
		
		//Refresh game list
		List<Game> gamelist = Reference.GET_SINGLETON().proxy.getGameList();
		GameInfo[] games = new GameInfo[gamelist.size()];
		int counter = 0;
		for(Game game: gamelist)
		{
			GameInfo thisgame = new GameInfo();
			thisgame.setId(game.getId());
			thisgame.setTitle(game.getTitle());
			games[counter] = thisgame;
			counter++;
		}
		
		PlayerInfo ourguy = new PlayerInfo();
		ourguy.setColor(null);
		ourguy.setId(-1);
		ourguy.setName("DEFAULT_NAME");
		ourguy.setPlayerIndex(-1);
		
		
		getNewGameView().closeModal();
		getJoinGameView().setGames(games, ourguy);
		
	}



	/**
	 * Iterate through player in GameInfo and
	 * disable each color that has already been used in ISelectColorView
	 * check if you are already in
	 * if it is true,call JoinGame related with the color that player already picked
	 */
	@Override
	public void startJoinGame(GameInfo game) 
	{

		PlayerInfo ourguy = new PlayerInfo();
		ourguy.setColor(null);
		ourguy.setId(-1);
		ourguy.setName("DEFAULT_NAME");
		ourguy.setPlayerIndex(-1);
		//List<PlayerInfo> playerlist = Reference.GET_SINGLETON().proxy.joinGame();
		//GameInfo[] player = new GameInfo[playerlist.size()];

		getSelectColorView().showModal();
		for (PlayerInfo playerInfo : game.getPlayers()) {
			if (!playerInfo.equals(ourguy)) {
				getSelectColorView().setColorEnabled(ourguy.getColor(), false);
			}
		}
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
		Catan catan = null;
		Boolean request = false;

		PlayerInfo ourguy = new PlayerInfo();
		ourguy.setColor(color);
		ourguy.setId(-1);
		ourguy.setName("DEFAULT_NAME");
		ourguy.setPlayerIndex(-1);

		try
		{

			Reference.GET_SINGLETON().proxy.joinGame(ourguy.getName(), ourguy.getId(), ourguy.getColor());
		}
		catch (JoinExceptions e)
		{
			e.printStackTrace();
		}

		// If join succeeded
		getSelectColorView().closeModal();
		getJoinGameView().closeModal();

		//Refresh game list
		List<Game> gamelist = Reference.GET_SINGLETON().proxy.getGameList();
		GameInfo[] games = new GameInfo[gamelist.size()];
		int counter = 0;
		for(Game game: gamelist)
		{
			GameInfo thisgame = new GameInfo();
			thisgame.setId(game.getId());
			thisgame.setTitle(game.getTitle());
			counter++;
			games[counter] = thisgame;
		}


		joinAction.execute();
	}

}

