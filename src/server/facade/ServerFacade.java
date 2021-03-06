package server.facade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import client.communication.ModelPopulator;
import client.data.GameInfo;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import org.json.JSONException;
import org.json.JSONObject;
import server.commands.AcceptTradeCommand;
import server.commands.BuildCity;
import server.commands.Command;
import server.communication.ModelTranslator;
import server.plugin_attachments.IGameDAO;
import server.plugin_attachments.IUserDAO;
import server.plugin_attachments.IPersistenceProvider;
import client.communication.IServer;
import shared.communication.EdgeLocation;
import shared.communication.ResourceList;
import shared.communication.Serializer;
import shared.communication.fromServer.game.CommunicationModel;
import shared.communication.fromServer.game.VertexLocation;
import shared.communication.fromServer.games.Game;
import shared.communication.fromServer.games.NewGame;
import shared.communication.toServer.games.CreateGameRequest;
import shared.communication.toServer.games.JoinGameRequest;
import shared.definitions.CatanColor;
import shared.definitions.ResourceType;
import shared.exceptions.JoinExceptions;
import shared.locations.HexLocation;
import shared.model.Fascade;
import shared.model.player.Player;
import shared.model.player.ResourceMultiSet;

/**
 * 
 * Catan Server Facade that gets called by commands
 *
 */
public class ServerFacade implements IServer
{
    private static int nextGameID = 0;

    public static int getNextGameID() {
        return nextGameID;
    }
	
	private int commanding_player_index;
	private int game_index = 0;
	private int max_command_size;
	private ArrayList<shared.model.Fascade> games;
	private HashMap<Integer,ArrayList<Command>> commands;
	private ArrayList<User> users;
	private IPersistenceProvider factory;
	private IPersistenceProvider persistence_factory;
	private IGameDAO gameDAO;
	private IUserDAO userDAO;
	
    public ServerFacade() {
        this.games = new ArrayList<shared.model.Fascade>();
        this.users = new ArrayList<User>();
        this.commands=new HashMap<Integer,ArrayList<Command>>();
        //createGameCommand(new CreateGameRequest(false, false, false, "Default"));
    }

    /**
	 * Constructor for ServerProxy Implements the interface but is unused
	 */
	@Override
	public void ServerProxy(String host, int port, Fascade f) 
	{
		
	}

	/**
	 * Login
	 * 
	 * logs user into the server
	 * @pre the user has a valid account
	 * @post The user is given user credentials if the login was valid.
	 * 			Signals access denied if the login is invalid
	 */
	@Override
	public String login(String username, String password) 
	{
		for (User user : users)
		{
			if (user.getName().equals(username) && user.checkPassword(password))
			{
				return Integer.toString(user.getPlayerID());
			}
		}
		return null;
	}
	
	/**
	 * Register
	 * 
	 * registers a new user with the server and logs them in
	 * @pre none
	 * @post The new user is added and login credentials are returned
	 */
	@Override
	public String register(String username, String password) 
	{
		for (User user : users)
		{
//			if (user.checkPassword(password))
//			{
//				return "FAILED\n";
//			}
			if (user.getName().equals(username))
			{
				return null;
			}
		}
		
		User new_user = new User();
		new_user.setName(username);
		new_user.setPassword(password);
		new_user.setPlayerID(users.size());
		users.add(new_user);
        addUser(new_user, new_user.getPlayerID());
        return Integer.toString(new_user.getPlayerID());
	}

	/**
	 * Get the list of available games
	 * 
	 * gets the list of games on the server 
	 * 
	 * @pre the user logged in with a valid account
	 * @post The games are returned
	 */
	@Override
	public List<Game> getGameList() 
	{
		List<Game> result = new ArrayList<Game>();
		for (Fascade facade : games)
		{
			Game game_entry = new Game(facade, facade.getModel().getGameinfo().getId());
			result.add(game_entry);
		}
		return result;
	}

	
	/**
	 * Create a game
	 * 
	 * creates a new game
	 * @pre the user logged in with a valid account
	 * @post The game is created and added to the list
	 */
	@Override
	public Game createGame(String name, boolean randomTiles,
			boolean randomNumbers, boolean randomPorts) 
	{
		shared.model.Fascade new_game_facade = new Fascade();
		try
		{
			new_game_facade.buildNewGame(name, randomTiles, randomNumbers, randomPorts);
		}
		catch (Exception e)
		{
			System.err.print("Game Creation Exception " + e.getMessage() + "\n");
			e.printStackTrace();
			return null;
		}
		
		this.games.add(new_game_facade);
		this.commands.put(new_game_facade.getGameID(), new ArrayList<Command>());
		return null;
	}

	/**
	 * Join a game
	 * 
	 * Adds a user to the game
	 * @pre the user is already in the game or there is space for them
	 * @post the player is added to the game and gets information about the game.
	 */
	@Override
	public String joinGame(int id, CatanColor color) throws JoinExceptions 
	{
		game_index = indexOfGameID(id);
		try 
		{
			User joiner = users.get(commanding_player_index);	
			games.get(game_index).addPlayer(joiner.getName(), color, commanding_player_index);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return null;
		}
		return Integer.toString(game_index);
	}

	/**
	 * Get Model
	 * does nothing here
	 */
	@Override
	public String getModel(int id) 
	{
		return Serializer.getSINGLETON().serialize(ModelTranslator.translateModel(getModelCommand(id)));
	}

	/**
	 * Save Game
	 * 
	 * saves the game state
	 * 
	 * @pre the active user is in the game
	 * @post the game is saved for later in a file named by the second parameter.
	 * 
	 */
	@Override
	public void saveGame(UUID game_id, String file_name) throws JoinExceptions 
	{
		// TODO Auto-generated method stub
		
	}

	/**
	 * Load Game
	 * 
	 * loads the saved games from a file. Also loads the users
	 * 
	 * @pre there is a game at that file address.
	 * @post the games are reloaded into the server memory.
	 * 
	 */
	@Override
	public void loadGame(String file_name) throws JoinExceptions 
	{
		try 
		{
			IUserDAO userDAO = factory.generateIUserDAO();
			IGameDAO gameDAO = factory.generateGameDAO();
		} 
		catch (Exception e) 
		{
			System.err.print("ERROR: Failed to load users and games" +
					" from persistent storage");
		}
		
	}

	/**
	 * Add AI Player
	 * 
	 * adds an AI player to the current game.
	 * 
	 * @pre this game has room for more players
	 * @post an AI player is added to the game.
	 * 
	 */
	@Override
	public String addAIPlayer(String AiType) 
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Get AI Type
	 * 
	 * gets the ai type choices
	 * 
	 * @pre none
	 * @post the list of enabled AI types is returned
	 * 
	 */
	@Override
	public String getAITypes() 
	{
		return null;
	}

	/**
	 * Send Chat
	 * 
	 * sends a chat message
	 * 
	 * @pre the user is in a game
	 * @post the game chat list has this message added
	 * 
	 */
	@Override
	public String sendChat(int playerIndex, String message) 
	{
		try 
		{
			games.get(game_index).sendChat(playerIndex, message);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return "FAILED\n";
		}
		return "SUCCESS\n";
	}

	/**
	 * Accept Trade
	 * 
	 * accepts a domestic trade
	 * 
	 * @pre there is a domestic trade available to the player
	 * @post the trade is accepted or denied based on the boolean
	 * 
	 */
	@Override
	public String acceptTrade(int playerIndex, boolean accept) 
	{
		try 
		{
			games.get(game_index).acceptTrade(commanding_player_index, accept);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return "FAILED\n";
		}
		return "SUCCESS\n";
	}

	/**
	 * Discard Cards
	 * 
	 * discards the specified cards
	 * 
	 * @pre this player has to discard after a 7
	 * @post the player has these resources removed.
	 * 
	 */
	@Override
	public String discardCards(ResourceList discardedCards) 
	{
		ResourceMultiSet discards = new ResourceMultiSet(discardedCards);
		try 
		{
			games.get(game_index).discardResources(commanding_player_index,
                    discards);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return "FAILED\n";
		}
		return "SUCCESS\n";
	}

	/**
	 * Roll Number
	 * 
	 * applies a roll of the dice to the model
	 * 
	 * @pre the active player rolled the dice. The number is between 2 and 12
	 * @post everybody gets their resources and discarding and robbing are 
	 * triggered on a 7 roll
	 * 
	 */
	@Override
	public String rollNumber(int number) 
	{
		try 
		{
			games.get(game_index).RollDice(number);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return "FAILED\n";
		}
		return "SUCCESS\n";
	}

	/**
	 * Build Road
	 * 
	 * builds a road an puts it in the model
	 * 
	 * @pre It is a valid edgelocation
	 * @post a road is placed there
	 * 
	 */
	@Override
	public String buildRoad(boolean free, EdgeLocation roadLocation) 
	{
		try 
		{
			games.get(game_index).buildRoadAt(commanding_player_index,
                    roadLocation, free);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return "FAILED\n";
		}
		return "SUCCESS\n";
	}

	/**
	 * Build Settlement
	 * 
	 * builds a settlement in that place
	 * 
	 * @pre this is a valid settlement location
	 * @post the model has the settlement played there
	 * 
	 */
	@Override
	public String buildSettlement(boolean free, VertexLocation place) 
	{
		try 
		{
			games.get(game_index).buildSettlement(commanding_player_index, place, free);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return "FAILED\n";
		}
		return "SUCCESS\n";
	}

	/**
	 * Build city
	 * 
	 * builds a city in that place
	 * 
	 * @pre this is a valid city location where the player already has a 
	 * 		settlement
	 * @post the model has the city played there
	 * 
	 */
	@Override
	public String buildCity(VertexLocation place) 
	{
		try 
		{
			games.get(game_index).BuildCity(commanding_player_index, place);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return "FAILED\n";
		}
		return "SUCCESS\n";
	}

	/**
	 * Build Settlement
	 * 
	 * builds a settlement in that place
	 * 
	 * @pre this is a valid settlement location
	 * @post the model has the settlement played there
	 * 
	 */
	@Override
	public String offerTrade(ResourceList offer, Player receiver) 
	{
		try 
		{
			games.get(game_index).offerTrade(commanding_player_index,
                    offer, receiver.getPlayerIndex());
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return "FAILED\n";
		}
		return "SUCCESS\n";
	}

	/**
	 * Maritime Trade
	 * 
	 * trades resources at the given exchange rate
	 * 
	 * @pre none
	 * @post the player trades these resources.
	 * 
	 */
	@Override
	public String maritimeTrade(int ratio, ResourceType input,
			ResourceType output) 
	{
		try 
		{
			games.get(game_index).maritimeTrade(commanding_player_index,
                    ratio, input, output);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return "FAILED\n";
		}
		return "SUCCESS\n";
	}

	/**
	 * Rob Player
	 * 
	 * the player sending the command robs the other
	 * 
	 * @pre none
	 * @post the victim is robbed of a random resource.
	 * 
	 */
	@Override
	public String robPlayer(HexLocation location, Player victim) 
	{
		try 
		{
			games.get(game_index).rob(commanding_player_index, victim, location);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return "FAILED\n";
		}
		return "SUCCESS\n";
	}

	/**
	 * Finish Turn
	 * 
	 * ends the current player's turn
	 * 
	 * @pre none
	 * @post the next player is queued up to roll
	 * 
	 */
	@Override
	public String finishTurn() 
	{
		try 
		{
			games.get(game_index).finishTurn(commanding_player_index);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return "FAILED\n";
		}
		return "SUCCESS\n";
	}

	/**
	 * Buy Development Card
	 * 
	 * buys a development card for the player
	 * 
	 * @pre there are still dev-cards left to be drawn
	 * @post the player has the new card in his new_dev_cards hand
	 * 
	 */
	@Override
	public String buyDevCard() 
	{
		try 
		{
			games.get(game_index).buyDevelopmentCard(commanding_player_index);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return "FAILED\n";
		}
		return "SUCCESS\n";
	}

	/**
	 * Play the Soldier dev Card
	 * 
	 * plays the Soldier Card
	 * 
	 * @pre none
	 * @post the soldier card is played and the player's army increases and the
	 * player gets to rob.
	 * 
	 */
	@Override
	public String playSoldier(HexLocation place, Player victim) 
	{
		try 
		{
			games.get(game_index).playSoldier(
                    commanding_player_index, place, victim.getPlayerIndex());
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return "FAILED\n";
		}
		return "SUCCESS\n";
	}
	
	
	/**
	 * Play the Year of Plenty dev Card
	 * 
	 * plays the Year of Plenty
	 * 
	 * @pre none
	 * @post the Year of Plenty card is played and the player gets to get
	 * resources. One each of the 2 types sent.
	 * 
	 */
	@Override
	public String yearOfPlenty(ResourceType one, ResourceType two) 
	{
		try 
		{
			games.get(game_index).playYearOfPlenty(commanding_player_index, one, two);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return "FAILED\n";
		}
		return "SUCCESS\n";
	}

	/**
	 * Play the Road Building dev Card
	 * 
	 * plays the Road Building
	 * 
	 * @pre none
	 * @post the Road Building card is played and the player gets to place
	 * 2 roads.
	 * 
	 */
	@Override
	public String RoadBuilding(EdgeLocation one, EdgeLocation two) 
	{
		try 
		{
			games.get(game_index).playRoadBuilding(
                    commanding_player_index, one, two);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return "FAILED\n";
		}
		return "SUCCESS\n";
	}

	/**
	 * Play the Monopoly dev Card
	 * 
	 * plays the Monopoly
	 * 
	 * @pre none
	 * @post the Monopoly card is played and the player gets to steal all of 
	 * one type of resource.
	 * 
	 */
	@Override
	public String monopoly(ResourceType one) 
	{
		try 
		{
			games.get(game_index).playMonopoly(commanding_player_index, one);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return "FAILED\n";
		}
		return "SUCCESS\n";
	}

	/**
	 * Play the Monument dev Card
	 * 
	 * plays the Monument
	 * 
	 * @pre none
	 * @post the Monument card is played and the player gets a victory point
	 * 
	 */
	@Override
	public String monument() 
	{
		try 
		{
			games.get(game_index).playMonument(commanding_player_index);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return "FAILED\n";
		}
		return "SUCCESS\n";
	}
	
	/**
	 * Access for a command class to the server operations
	 * Register command
	 * 
	 * @pre none
	 * @post the command is executed and a communication class is filled 
	 * and returned. Null means an error
	 */
	public String registerCommand(shared.communication.toServer.user.Credentials params)
	{
		
		return this.register(params.getUsername(), params.getPassword());
	}
	
	/**
	 * Access for a command class to the server operations
	 * Login Command
	 * 
	 * @pre none
	 * @post the command is executed and a communication class is filled 
	 * and returned. Null means an error
	 */
	public String loginCommand(shared.communication.toServer.user.Credentials params)
	{
		return this.login(params.getUsername(), params.getPassword());
	}
	
	/**
	 * Access for a command class to the server operations
	 * getGameList command
	 * 
	 * @pre none
	 * @post the command is executed and a communication class is filled 
	 * and returned. Null means an error
	 */
	public shared.communication.fromServer.games.Game[] getGameListCommand()
	{
		List<Game> games = this.getGameList();
		return games.toArray(new Game[games.size()]);
	}
	
	/**
	 * Access for a command class to the server operations
	 * Create Game command
	 * 
	 * @pre none
	 * @post the command is executed and a communication class is filled 
	 * and returned. Null means an error
	 */
	public String createGameCommand(
				shared.communication.toServer.games.CreateGameRequest params)
	{
		try 
		{
			this.createGame(params.getName(), params.isRandomTiles(), params.isRandomNumbers(), params.isRandomPorts());
            shared.communication.fromServer.games.EmptyPlayer[] emptyPlayers =
                    new shared.communication.fromServer.games.EmptyPlayer[0];
            NewGame newGame = new NewGame(params.getName(), ServerFacade.nextGameID++,
                    emptyPlayers);
            return Serializer.getSINGLETON().serialize(newGame);
		} 
		catch (Exception e) 
		{
			System.err.print("\nERROR: FAILED TO CREATE GAME.\n" + e.getMessage() + "\n");
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Access for a command class to the server operations
	 * Join Game command
	 * 
	 * @pre none
	 * @post the command is executed and a communication class is filled 
	 * and returned. Null means an error
	 */
	@Override
	public int joinGameCommand(JoinGameRequest params, int playerID)
	{
		try 
		{
			game_index=params.getId();
			Fascade game=games.get(params.getId());
			for(shared.model.player.Player p:game.getModel().getPlayers()){
				if(p.getName()!=null)
				{
					if(p.getPlayerID() == playerID)
					{
						String oldColor = p.getColor();
						p.setColor(params.getColor());
						game.getModel().getMap().recolor(CatanColor.valueOf(oldColor.toUpperCase()),
								CatanColor.valueOf(params.getColor().toUpperCase()) );
						return params.getId();
					} 
				}
			}
			User currentUser=users.get(playerID);
			game.addPlayer(currentUser.getName(),CatanColor.valueOf(params.getColor().toUpperCase()),playerID);
			return params.getId();
		} 
		catch (Exception e)
		{
			System.err.print("WARNING: BAD JOIN REQUEST\n");
			//e.printStackTrace();
			return params.getId();
		}
	}
	
	/**
	 * Access for a command class to the server operations
	 * Get Model command
	 * 
	 * @pre none
	 * @post the command is executed and a communication class is filled 
	 * and returned. Null means an error
	 */
	public shared.model.Game getModelCommand(int id)
	{
		return games.get(indexOfGameID(id)).getModel();
		
	}
	
	/**
	 * Access for a command class to the server operations
	 * Save Game command
	 * 
	 * @pre none
	 * @post the command is executed and a communication class is filled 
	 * and returned. Null means an error
	 */
	public String saveGameCommand(
				shared.communication.toServer.games.SaveGameRequest params)
	{
		return "FAILED\n";
	}
	
	/**
	 * Access for a command class to the server operations
	 * Load Game command
	 * 
	 * @pre none
	 * @post the command is executed and a communication class is filled 
	 * and returned. Null means an error
	 */
	public CommunicationModel loadGameCommand(
			shared.communication.toServer.games.LoadGameRequest params)
	{
		return null;
	}
	
	/**
	 * Access for a command class to the server operations
	 * Add AI Player command
	 * 
	 * @pre none
	 * @post the command is executed and a communication class is filled 
	 * and returned. Null means an error
	 */
	public CommunicationModel addAIPlayerCommand(
			shared.communication.toServer.game.AddAIRequest params)
	{
		//add this: params.getAIType();
		return null;
	}
	
	/**
	 * Access for a command class to the server operations
	 * getAITypes command
	 * 
	 * @pre none
	 * @post the command is executed and a communication class is filled 
	 * and returned. Null means an error
	 */
	public String[] getAITypesCommand()
	{
		String[] result = new String[1];
		result[0] = "Sorry, we dont support that";
		return result;
	}
	
	/**
	 * Access for a command class to the server operations
	 * Send Chat command
	 * 
	 * @pre none
	 * @post the command is executed and a communication class is filled 
	 * and returned. Null means an error
	 */
	public CommunicationModel sendChatCommand(
			shared.communication.toServer.moves.SendChat params,int gameID)
	{
		game_index = gameID;
		commanding_player_index = params.getPlayerIndex();
		this.sendChat(params.getPlayerIndex(), params.getContent());
		return null;
	}
	
	/**
	 * Access for a command class to the server operations
	 * Accept Trade command
	 * 
	 * @pre none
	 * @post the command is executed and a communication class is filled 
	 * and returned. Null means an error
	 */
	public CommunicationModel acceptTradeCommand(
			shared.communication.toServer.moves.AcceptTrade params,int gameID)
	{
		game_index = gameID;
		commanding_player_index = params.getPlayerIndex();
		this.acceptTrade(params.getPlayerIndex(), params.isWillAccept());
		return null;
	}

	/**
	 * Access for a command class to the server operations
	 * Discard command
	 * 
	 * @pre none
	 * @post the command is executed and a communication class is filled 
	 * and returned. Null means an error
	 */
	public CommunicationModel discardCommand(
			shared.communication.toServer.moves.DiscardCards params,int gameID)
	{
		game_index = gameID;
		commanding_player_index = params.getPlayerIndex();
		this.discardCards(params.getDiscardedCards());
		return null;
	}
	
	/**
	 * Access for a command class to the server operations
	 * rollNumber command
	 * 
	 * @pre none
	 * @post the command is executed and a communication class is filled 
	 * and returned. Null means an error
	 */
	public CommunicationModel rollNumberCommand(
				shared.communication.toServer.moves.RollNumber params,int gameID)
	{
		game_index = gameID;
		commanding_player_index = params.getPlayerIndex();
		this.rollNumber(params.getNumber());
		return null;
	}
	
	/**
	 * Access for a command class to the server operations
	 * Build Road command
	 * 
	 * @pre none
	 * @post the command is executed and a communication class is filled 
	 * and returned. Null means an error
	 */
	public CommunicationModel buildRoadCommand(
			shared.communication.toServer.moves.BuildRoad params,int gameID)
	{
		game_index = gameID;
		commanding_player_index = params.getPlayerIndex();
		this.buildRoad(params.isFree(), params.getRoadLocation());
		return null;
	}
	
	/**
	 * Access for a command class to the server operations
	 * Build City command
	 * 
	 * @pre none
	 * @post the command is executed and a communication class is filled 
	 * and returned. Null means an error
	 */
	public CommunicationModel buildCityCommand(
			shared.communication.toServer.moves.BuildCity params,int gameID)
	{
		game_index = gameID;
		commanding_player_index = params.getPlayerIndex();
		this.buildCity(params.getVertexLocation());
		return null;
	}
	
	/**
	 * Access for a command class to the server operations
	 * build Settlement command
	 * 
	 * @pre none
	 * @post the command is executed and a communication class is filled 
	 * and returned. Null means an error
	 */
	public CommunicationModel buildSettlementCommand(
			shared.communication.toServer.moves.BuildSettlement params,int gameID)
	{
		game_index = gameID;
		commanding_player_index = params.getPlayerIndex();
		this.buildSettlement(params.isFree(), params.getVertexLocation());
		return null;
	}
	
	/**
	 * Access for a command class to the server operations
	 * offer Trade command
	 * 
	 * @pre none
	 * @post the command is executed and a communication class is filled 
	 * and returned. Null means an error
	 */
	public CommunicationModel offerTradeCommand(
			shared.communication.toServer.moves.OfferTrade params,int gameID)
	{
		game_index = gameID;
		commanding_player_index = params.getPlayerIndex();
		this.offerTrade(params.getOffer(), new Player(params.getReceiver()));
		return null;
	}
	
	/**
	 * Access for a command class to the server operations
	 * MaritimeTrade command
	 * 
	 * @pre none
	 * @post the command is executed and a communication class is filled 
	 * and returned. Null means an error
	 */
	public CommunicationModel maritimeTradeCommand(
			shared.communication.toServer.moves.MaritimeTrade params,int gameID)
	{
		game_index = gameID;
		commanding_player_index = params.getPlayerIndex();
		this.maritimeTrade(params.getRatio(),
                ResourceType.valueOf(params.getInputResource().toUpperCase()),
                ResourceType.valueOf(params.getOutputResource().toUpperCase()));
		return null;
	}
	
	/**
	 * Access for a command class to the server operations
	 * Rob command
	 * 
	 * @pre none
	 * @post the command is executed and a communication class is filled 
	 * and returned. Null means an error
	 */
	public CommunicationModel robCommand(
			shared.communication.toServer.moves.RobPlayer params,int gameID)
	{
		game_index = gameID;
		commanding_player_index = params.getPlayerIndex();
		this.robPlayer(params.getLocation(), new Player(params.getVictimIndex()));
		return null;//TODO serialize and return
	}
	
	/**
	 * Access for a command class to the server operations
	 * Finish Turn command
	 * 
	 * @pre none
	 * @post the command is executed and a communication class is filled 
	 * and returned. Null means an error
	 */
	public CommunicationModel FinishTurnCommand(
			shared.communication.toServer.moves.FinishTurn params,int gameID)
	{
		game_index = gameID;
		commanding_player_index = params.getPlayerIndex();
		this.finishTurn();
		return null;//TODO serialize and return
	}
	
	/**
	 * Access for a command class to the server operations
	 * buyDevCard command
	 * 
	 * @pre none
	 * @post the command is executed and a communication class is filled 
	 * and returned. Null means an error
	 */
	public CommunicationModel buyDevCardCommand(
			shared.communication.toServer.moves.BuyDevCard params,int gameID)
	{
		game_index = gameID;
		commanding_player_index = params.getPlayerIndex();
		this.buyDevCard();
		return null;//TODO serialize and return
	}
	
	/**
	 * Access for a command class to the server operations
	 * Play Soldier command
	 * 
	 * @pre none
	 * @post the command is executed and a communication class is filled 
	 * and returned. Null means an error
	 */
	public CommunicationModel playSoldierCardCommand(
			shared.communication.toServer.moves.Soldier_ params,int gameID)
	{
		game_index = gameID;
		commanding_player_index = params.getPlayerIndex();
		this.playSoldier(params.getLocation(), new Player(params.getVictimIndex()));
		return null;
	}
	
	/**
	 * Access for a command class to the server operations
	 * Play Monument command
	 * 
	 * @pre none
	 * @post the command is executed and a communication class is filled 
	 * and returned. Null means an error
	 */
	public CommunicationModel playMonumentCardCommand(
			shared.communication.toServer.moves.Monument_ params,int gameID)
	{
		game_index = gameID;
		commanding_player_index = params.getPlayerIndex();
		this.monument();
		return null;//TODO serialize and return
	}
	
	/**
	 * Access for a command class to the server operations
	 * Play Monopoly command
	 * 
	 * @pre none
	 * @post the command is executed and a communication class is filled 
	 * and returned. Null means an error
	 */
	public CommunicationModel playMonopolyCardCommand(
			shared.communication.toServer.moves.Monopoly_ params,int gameID)
	{
		game_index = gameID;
		commanding_player_index = params.getPlayerIndex();
		monopoly(ResourceType.valueOf(params.getResource().toUpperCase()));
		return null;
	}
	
	/**
	 * Access for a command class to the server operations
	 * Play Year Of Plenty command
	 * 
	 * @pre none
	 * @post the command is executed and a communication class is filled 
	 * and returned. Null means an error
	 */
	public CommunicationModel playYearOfPlentyCardCommand(
			shared.communication.toServer.moves.Year_of_Plenty_ params,int gameID)
	{
		game_index = gameID;
		commanding_player_index = params.getPlayerIndex();
		try
		{
			yearOfPlenty(ResourceType.valueOf(params.getResource1().toUpperCase()),
				ResourceType.valueOf(params.getResource2().toUpperCase()));
		}
		catch(Exception e)
		{
			System.err.print("Resource Conversion ERROR");
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Access for a command class to the server operations
	 * Play Soldier command
	 * 
	 * @pre none
	 * @post the command is executed and a communication class is filled 
	 * and returned. Null means an error
	 */
	public CommunicationModel playRoadBuildingCardCommand(
			shared.communication.toServer.moves.Road_Building_ params,int gameID)
	{
		game_index = gameID;
		commanding_player_index = params.getPlayerIndex();
		this.RoadBuilding(params.getSpot1(), params.getSpot2());
		return null;
	}
	
	/**
	 * get the facade by ID
	 * 
	 * @pre this is a real gameID
	 * @post returns the Game model objext
	 * This gets game facades for the serializer to use
	 * @param game_id_index
	 * @return
	 */
	public shared.model.Game getGameModelByID(int game_id)
	{
        if(indexOfGameID(game_id) == -1) return null;
        return games.get(indexOfGameID(game_id)).getModel();
	}
	
	/**
	 * GetVersion
	 * @param game_id
	 * @return
	 */
	public int getVersionOf(int game_id)
	{
		return games.get(game_id).getVersion();
	}

	/**
	 * Used to insert pre-build games in unit tests
	 * @param game_facade
	 */
    public void forTestingSet(shared.model.Fascade game_facade) 
    {
        games.add(0, game_facade);
    }

    /**
     * used to get pre-built games in unit tests
     * @return
     */
    public shared.model.Fascade forTestingGet() 
    {
        return games.get(0);
    }

    /**
     * getter
     */
	@Override
	public shared.model.Fascade getFacadeByID(int gameID) 
	{
		return games.get(gameID);
	}
	
	/**
	 * Gets the game's index in the list
	 * @pre none 
	 * @post gets the proper index of that game id 
	 * 
	 * @param id
	 * 
	 */
    private int indexOfGameID(int id) {
        for (int i = 0; i < games.size(); i++) {
            if (games.get(i).getModel().getGameinfo().getId() == id) {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * gets the user index by user_id
     * 
     * @pre none
     * @post gets the proper index of the user in the list
     * 
     */
    private int indexofUserID(int id)
    {
    	int i = 0;
    	for( User user : this.users)
    	{
    		if(user.getPlayerID() == id)
 			{
    			return i;
    		}
    		i++;
    	}
    	return -1;
    }

    /**
     * Add a persistence provider to the server
     * Loads all of the users and the games from storage. 
     * If any game has commands then it runs them to update the model then clears the list.
     * @pre none
     * @post the persistence provider will be available
     * @param pp
     */
	public void addPersistence(IPersistenceProvider pp) 
	{
        Gson gson = new Gson();
		this.persistence_factory = pp;
		try 
		{
			userDAO=pp.generateIUserDAO();
			gameDAO=pp.generateGameDAO();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		if(userDAO.getUsers() != null)
		{
			for(Map.Entry<Integer,String> user: userDAO.getUsers().entrySet())
			{
				users.add(gson.fromJson(user.getValue(), User.class));
			}
		}
		if(gameDAO.getGames() != null)
		{
			for(Map.Entry<Integer,String> g:gameDAO.getGames().entrySet())
			{

                JSONObject comObAsJSON = Serializer.getSINGLETON().deserialize(g.getValue());
                Fascade newFascade = new Fascade();
                ModelPopulator.populateModel(comObAsJSON, newFascade);
                GameInfo info = new GameInfo();
                info.setId(g.getKey());
                newFascade.getModel().setGameinfo(info);
				games.add(g.getKey(), newFascade);
			}
		}
		if(gameDAO.getCommands() != null)
		{
			for(Entry<Integer, List<String>> c:gameDAO.getCommands().entrySet())
			{
				List<String> coms = c.getValue();
				if(coms.size() > 0) 
				{
					for(String s:coms)
					{
						Command w=getCommandInstance(s);
						w.execute(this);
					}
				}
                int gameID = c.getKey().intValue();
                CommunicationModel cm = ModelTranslator.translateModel(getFacadeByID(gameID).getModel());
                gameDAO.saveModelAndEmptyCommands(cm, gameID);
			}
		}
	}
	
	/**
	 * gets the persistence provider factory
	 * @return
	 */
	public IPersistenceProvider getPersister()
	{
		return persistence_factory;
	}

    @Override
    public void addCommand(int gameID, Command c)
    {
        IGameDAO gameDAO = null;
        try 
        {
            gameDAO = persistence_factory.generateGameDAO();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        ArrayList<Command> some = commands.get(gameID);
        some.add(c);
        gameDAO.saveCommand(c, gameID);
        if(some.size()==max_command_size)
        {
            CommunicationModel cm = ModelTranslator.translateModel(getFacadeByID(gameID).getModel());
            gameDAO.saveModelAndEmptyCommands(cm, gameID);
            some.clear();
        }
    }

    @Override
    public void addUser(User user, int user_id) {
        IUserDAO userDAO = null;
        if (persistence_factory == null) {
            return;
        }
        try 
        {
            userDAO = persistence_factory.generateIUserDAO();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        userDAO.addUser(user, user.getPlayerID());
    }

	public void setN(int commands_BEFORE_SAVE)
	{
		max_command_size = commands_BEFORE_SAVE;
	}

    private Command getCommandInstance(String s) {
        Gson gson = new Gson();
        JSONObject j = Serializer.getSINGLETON().deserialize(s);
        JSONObject j2 = null;
        String j3 = null;
        try {
            j2 = (JSONObject)j.get("params");
            j3 = (String)j2.get("type");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        switch (j3) {
            case "acceptTrade":
                return gson.fromJson(s, AcceptTradeCommand.class);
            case "buildCity":
                return gson.fromJson(s, server.commands.BuildCity.class);
            case "buildRoad":
                return gson.fromJson(s, server.commands.BuildRoad.class);
            case "buildSettlement":
                return gson.fromJson(s, server.commands.BuildSettlement.class);
            case "buyDevCard":
                return gson.fromJson(s, server.commands.BuyDevCard.class);
            case "discardCards":
                return gson.fromJson(s, server.commands.DiscardCards.class);
            case "finishTurn":
                return gson.fromJson(s, server.commands.FinishTurn.class);
            case "maritimeTrade":
                return gson.fromJson(s, server.commands.MaritimeTrade.class);
            case "Monopoly":
                return gson.fromJson(s, server.commands.Monopoly.class);
            case "Monument":
                return gson.fromJson(s, server.commands.Monument.class);
            case "offerTrade":
                return gson.fromJson(s, server.commands.OfferTrade.class);
            case "Road_Building":
                return gson.fromJson(s, server.commands.Road_Building.class);
            case "robPlayer":
                return gson.fromJson(s, server.commands.RobPlayer.class);
            case "rollNumber":
                return gson.fromJson(s, server.commands.RollNumber.class);
            case "sendChat":
                return gson.fromJson(s, server.commands.SendChat.class);
            case "Soldier":
                return gson.fromJson(s, server.commands.Soldier.class);
            case "Year_of_Plenty":
                return gson.fromJson(s, server.commands.Year_of_Plenty.class);
            default:
                return null;
        }
    }
    
    public void populateBanks(Fascade facade)
    {
    	facade.populateBanks();
    }
}
