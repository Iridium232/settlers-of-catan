package client.join;

import client.base.Controller;
import client.base.IAction;
import client.communication.ModelPopulator;
import client.communication.ServerPoller;
import client.control.IObserver;
import client.control.Reference;
import client.data.GameInfo;
import client.data.PlayerInfo;
import client.misc.IMessageView;
import org.json.JSONException;
import org.json.JSONObject;
import shared.communication.fromServer.games.Game;
import shared.communication.fromServer.games.Player;
import shared.definitions.CatanColor;
import shared.exceptions.JoinExceptions;

import java.util.List;


/**
 * Implementation for the join game controller
 */
public class JoinGameController extends Controller implements IJoinGameController, IObserver {

	private INewGameView newGameView;
	private ISelectColorView selectColorView;
	private IMessageView messageView;
	private IAction joinAction;
	private GameInfo[] oldGames;

	/**
	 * JoinGameController constructor
	 *
	 * @param view            Join game view
	 * @param newGameView     New game view
	 * @param selectColorView Select color view
	 * @param messageView     Message view (used to display error messages that occur while the user is joining a game)
	 */
	public JoinGameController(IJoinGameView view, INewGameView newGameView,
								ISelectColorView selectColorView, IMessageView messageView) 
	{

		super(view);
		setNewGameView(newGameView);
		setSelectColorView(selectColorView);
		setMessageView(messageView);
		oldGames = null;
		Reference.GET_SINGLETON().joinview = view;
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
		ServerPoller.getServerPoller().setObserver(this);
		List<Game> gamelist = Reference.GET_SINGLETON().proxy.getGameList();
		GameInfo[] games = new GameInfo[gamelist.size()];
		int counter = 0;
		for(Game game: gamelist)
		{
			GameInfo thisgame = new GameInfo();
			thisgame.setId(game.getId());
			thisgame.setTitle(game.getTitle());
			for(Player player : game.getPlayers())
			{
				PlayerInfo player_info = new PlayerInfo(player);
				player_info.setColor(player.getColor());
				if(!(player.color == null))thisgame.addPlayer(player_info);
			}
			games[counter] = thisgame;
			counter++;
		}

		if (Reference.GET_SINGLETON().getPlayer_color() != null) return;
		if (!hasChanged(games, oldGames)) return;
		oldGames = games;
		
		Reference ref = Reference.GET_SINGLETON();
		
		PlayerInfo ourguy = new PlayerInfo();
		
		ourguy.setId(ref.player_id);
		ourguy.setName(ref.name);
		
		getJoinGameView().setGames(games, ourguy);

		if (getJoinGameView().isModalShowing()) getJoinGameView().closeModal();
		if(!getJoinGameView().isModalShowing())
		{
			getJoinGameView().showModal();
		}
	}

	/**
	 * get interface for overlay views newGame
	 */
	@Override
	public void startCreateNewGame() 
	{	
		getNewGameView().setTitle("");
		getNewGameView().setRandomlyPlaceHexes(false);
		getNewGameView().setRandomlyPlaceNumbers(false);
		getNewGameView().setUseRandomPorts(false);
		if(!getNewGameView().isModalShowing())
		{
			getNewGameView().showModal();
		}
	}

	/**
	 * Close Modal
	 */
	@Override
	public void cancelCreateNewGame() 
	{
		if(getNewGameView().isModalShowing())
		{
			getNewGameView().closeModal();
		}
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
			for(Player player : game.getPlayers())
			{
				PlayerInfo player_info = new PlayerInfo(player);
				player_info.setColor(player.getColor());
				if(!(player.color == null))thisgame.addPlayer(player_info);
			}
			games[counter] = thisgame;
			counter++;
		}
		
		Reference ref = Reference.GET_SINGLETON();
		
		PlayerInfo ourguy = new PlayerInfo();
		
		ourguy.setId(ref.player_id);
		ourguy.setName(ref.name);
		if(getNewGameView().isModalShowing())
		{
			getNewGameView().closeModal();
		}
		if (getNewGameView().isModalShowing())
		{
			getNewGameView().closeModal();
		}

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
		if(game == null) return;
		
		getSelectColorView().enableAllColors();
		Reference ref = Reference.GET_SINGLETON();
		ref.game_id = game.getId();
		ref.player_index = getIndex(game.getPlayers());
		for(PlayerInfo player_info: game.getPlayers())
		{
			if(player_info.getColor() == null)continue;
			getSelectColorView().setColorEnabled(player_info.getColor(), false);
			if (player_info.getName().equals(ref.getName())) 
			{
				getSelectColorView().setColorEnabled(player_info.getColor(), true);
			}
		}
		if(getJoinGameView().isModalShowing()) 
		{
			getJoinGameView().closeModal();
		}
		
		if(!getSelectColorView().isModalShowing())
		{
			getSelectColorView().showModal();
		}
	}


	/**
	 * Cancel to join a game
	 */
	@Override
	public void cancelJoinGame() 
	{
		if(getSelectColorView().isModalShowing())
		{
			getSelectColorView().closeModal();
		}
		if(!getJoinGameView().isModalShowing())
		{
			getJoinGameView().showModal();
		}
		
	}


	/**
	 * call join game on server
	 */
	@Override
	public void joinGame(CatanColor color) 
	{
		Reference ref = Reference.GET_SINGLETON();
		JSONObject model;
		try 
		{
			String model_string =ref.proxy.joinGame(ref.game_id, color);
			model = new JSONObject(model_string);
			if(model.equals("FAILED\n"))
			{
				throw new JoinExceptions("Join Refused by server");
			}
			if(getSelectColorView().isModalShowing())
			{
				getSelectColorView().closeModal();
				setSelectColorView(null);
//				if(this.getJoinGameView().isModalShowing())
//				{
//					getJoinGameView().closeModal();
//				}
			}
			ModelPopulator.populateModel(model, ref.getFascade());
			// If join succeeded
			Reference.GET_SINGLETON().player_color = color;

			//joinAction.execute();
		} 
		catch (JSONException | JoinExceptions e) 
		{
			String error = "Sorry, Failed to Join Game. Please try again or contact your system administrator.";
			this.messageView.setTitle("Join Failed");
			this.messageView.setMessage(error);
			System.err.print("ERROR: FAILED TO JOIN GAME");
			e.printStackTrace();

			if(!this.messageView.isModalShowing())
			{
				this.messageView.showModal();
			}
			if(getSelectColorView().isModalShowing())
			{
				getSelectColorView().closeModal();
			}
		} 
	}

	@Override
	public void ObservableChanged() {
		start();
	}

	private int getIndex(List<PlayerInfo> playerInfos)
	{
		if (playerInfos.size() < 4) 
		{
			return playerInfos.size();
		}
		Reference r = Reference.GET_SINGLETON();
		for (int i = 0; i < playerInfos.size(); i++) 
		{
			if (playerInfos.get(i).getName().equals(r.getName())) 
			{
				return i;
			}
		}
		return -1;
	}

	private boolean hasChanged(GameInfo[] newGames, GameInfo[] oldGames) {
		if (oldGames == null) return true;
		if (newGames.length != oldGames.length) return true;
		for (int i = 0; i < newGames.length; i++) {
			List<PlayerInfo> newPlayers = newGames[i].getPlayers();
			List<PlayerInfo> oldPlayers = oldGames[i].getPlayers();
			if (newPlayers.size() != oldPlayers.size()) return true;
		}
		return false;
	}

}

