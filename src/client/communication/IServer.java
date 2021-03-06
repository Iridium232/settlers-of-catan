package client.communication;

import java.util.List;

import server.facade.User;
import shared.communication.fromServer.game.*;
import shared.communication.toServer.moves.*;
import shared.communication.toServer.games.*;
import shared.communication.toServer.game.*;
import java.util.UUID;

import shared.communication.ResourceList;
import shared.communication.toServer.moves.Command;
import shared.communication.toServer.user.Credentials;
import shared.definitions.CatanColor;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.communication.fromServer.game.VertexLocation;
import shared.model.Fascade;
import shared.communication.fromServer.games.Game;
import shared.model.player.Player;
import shared.exceptions.JoinExceptions;
import shared.model.exceptions.ModelException;

/**
 * A proxy server that can be implemented to either speak with the server or just return values for testing purposes.
 * @author Doug
 *
 */
public interface IServer {
	/**
	 * @pre the host is running the server on the specified port
	 * @post initializes a ServerProxy to send commands to the Client Communicator
	 * @param host
	 * @param port
	 * @return 
	 */
	public void ServerProxy(String host,int port,Fascade f);
	
	/**
	 * @pre username and password are not null
	 * @post valid username and password combinations will start a session
	 * @param username
	 * @param password
	 */
	public String login(String username, String password);
	
	/**
	 * @pre username and password are not null
	 * @post a new user will be created on the server and the client will log that user in
	 * @param username
	 * @param password
	 */
	public String register(String username, String password);
	
	/**
	 * @pre 1 game need to be activated
	 * @post a list of all the current games on the server is returned
	 */
	public List<Game> getGameList();

	
	/**
	 * @pre none of the parameters are null
	 * @post a new game with specified name and conditions is created on the server.
	 * @param name
	 * @param randomTiles
	 * @param randomNumbers
	 * @param randomPorts
	 * @return
	 */
	public Game createGame(String name, boolean randomTiles, boolean randomNumbers, boolean randomPorts)
		throws JoinExceptions;
	/**
	 * @pre user is logged in, a game with the id exists, the color is a valid color
	 * @post the user is added to the game and the board is updated to show that
	 * @param playerinfo username and password
	 * @param id the game ID
	 * @param color one of the acceptable colors
	 */
	public String joinGame(int id, CatanColor color)
		throws JoinExceptions;
	/**
	 * @pre id is for an existing game
	 * @post the model is returned in a JSON object;
	 * @param model
	 */
	public String getModel(int id);

	/**
	 * @pre game file_name must not be empty string
	 * @post a game is saved with a name file_name
	 */
	public void saveGame(UUID game_id, String file_name)
		throws JoinExceptions;
	/**
	 *@pre a game must exist with correct file_name parameter
	 *@post game is loaded
	*/
	public void loadGame(String file_name)
		throws JoinExceptions;





	public String addAIPlayer(String AiType);
	//Move commands
	public String getAITypes();
	/**
	 * @post the message is posted to the chat
	 * @param message
	 */
	public String sendChat(int playerIndex, String message);
	
	/**
	 * @pre A trade has been offered, if you accept is true the user has the required resources
	 * @post the trade offer is removed. If accepted resources are exchanged if not nothing happens.
	 * @param accept
	 */
	public String acceptTrade(int playerIndex, boolean accept);
	
	/**
	 * @pre status of the client model is discarding, user has over 7 cards, user has chosen cards to discard
	 * @post user no longer has the discarded resources
	 * @param discardedCards
	 */
	public String discardCards(ResourceList discardedCards);
	
	/**
	 * @pre it is the users turn, the client model's status is rolling
	 * @post client model status changes to discarding, robbing or playing
	 */
	public String rollNumber(int number);
	
	/**
	 * @pre the location is open, it is connected to a road owned by the player, 
	 * @pre it is not on water, the player has the required resources, during setup must be placed by a settlement with no adjacent roads
	 * @post the resources used are no longer in the players hand, the road is placed on the game board, and the game has checked for longest road
	 * @param free is the placement part of setup
	 * @param location where the road will be placed
	 */
	public String buildRoad(boolean free, shared.communication.EdgeLocation roadLocation);
	
	/**
	 * @pre the location is open, it is not on water, it is connected to a road unless during setup, player has the required resources, it is not adjacent to another settlement.
	 * @post player no longer has the used resources, the settlment is placed on the map.
	 * @param free
	 * @param place
	 */
	public String buildSettlement(boolean free, VertexLocation place);
	
	/**
	 * @pre the player has a city at the location and they have the required resources.
	 * @post the resources are gone, the city is on the map and the player got a settlement back.
	 * @param place
	 */
	public String buildCity(VertexLocation place);
	
	/**
	 * @pre the player has the resources they are offering
	 * @post the trade is offered to the other player
	 * @param offer
	 * @param receiver
	 */
	public String offerTrade(ResourceList offer, Player receiver);
	
	/**
	 * @pre the player has the resource they are giving, they have the correct port if ratio is <4
	 * @post the trade has been executed and the resources have been exchanged. 
	 * @param ratio
	 * @param input
	 * @param output
	 */
	public String maritimeTrade(int ratio, ResourceType input, ResourceType output);
	
	/**
	 * @pre the robber is moving from his starting location, the player being robbed has resource cards
	 * @post the robber is in the new location, the player being robbed gave the player robbing one resource card.
	 * @param location
	 * @param victim
	 */
	public String robPlayer(HexLocation location, Player victim);
	
	/**
	 * post card in the new dev card hand move to the old dev card hand.
	 */
	public String finishTurn();
	
	/**
	 * @pre the player has the required resources
	 * @post the player has a new dev card monuments go in the old hand others go in the new hand
	 */
	public String buyDevCard();
	/**
	 * The following are dev card commands they share the following preconditions
	 * @pre it is the players turn, the client model status is playing, they have the card in their old hand, and they have not played a non monument dev card this turn
	 */
	//dev card commands
	/**
	 * @pre the robber is moving, the player being robbed has cards.
	 * @post the robber moved, the victim gave up a card, check for largest army, set dev card played to true for this turn.
	 * @param place
	 * @param victim
	 */
	public String playSoldier(HexLocation place, Player victim);
	
	/**
	 * @pre the specified resources are in the bank
	 * @post the player gained both specified resources
	 * @param one
	 * @param two
	 */
	public String yearOfPlenty(ResourceType one,ResourceType two);
	
	/**
	 * @pre road location one is connected to a current road, road location two is connected to a current road or location one, neither is water, the player has two unused roads
	 * @post the player has two fewer roads, the new roads are placed on the map, longest road check.
	 * @param one
	 * @param two
	 */
	public String RoadBuilding(shared.communication.EdgeLocation one, shared.communication.EdgeLocation two);
	
	/**
	 * @post all other players have given you all of their resource of type one
	 * @param one
	 */
	public String monopoly(ResourceType one);
	
	/**
	 * @pre you have enough monument cards to reach 10 victory points
	 * @post you gained a victory point
	 */
	public String monument();
	
	//THE FOLLOWING PARTS OF THE INTEFACE ARE ONLY USED BY THE SERVER. SEE SERVERFASCADE.java if you want
	//javadocs for these methods
	public String registerCommand(Credentials credentials);
	public String loginCommand(Credentials credentials);
	public Game[] getGameListCommand();
	public String createGameCommand(CreateGameRequest params);
	public int joinGameCommand(JoinGameRequest params,int playerID);
	public shared.model.Game getModelCommand(int params);
	public String saveGameCommand(SaveGameRequest params);
	public CommunicationModel loadGameCommand(LoadGameRequest params);
	public CommunicationModel addAIPlayerCommand(AddAIRequest params);
	public String[] getAITypesCommand();
	public CommunicationModel sendChatCommand(SendChat params,int gameID);
	public CommunicationModel acceptTradeCommand(AcceptTrade params,int gameID);
	public CommunicationModel discardCommand(DiscardCards params,int gameID);
	public CommunicationModel rollNumberCommand(RollNumber params,int gameID);
	public CommunicationModel buildRoadCommand(BuildRoad params,int gameID);
	public CommunicationModel buildCityCommand(BuildCity params,int gameID);
	public CommunicationModel buildSettlementCommand(BuildSettlement params,int gameID);
	public CommunicationModel offerTradeCommand(OfferTrade params,int gameID);
	public CommunicationModel maritimeTradeCommand(MaritimeTrade params,int gameID);
	public CommunicationModel robCommand(RobPlayer params,int gameID);
	public CommunicationModel FinishTurnCommand(FinishTurn params,int gameID);
	public CommunicationModel buyDevCardCommand(BuyDevCard params,int gameID);
	public CommunicationModel playSoldierCardCommand(Soldier_ params,int gameID);
	public CommunicationModel playMonumentCardCommand(Monument_ params,int gameID);
	public CommunicationModel playMonopolyCardCommand(Monopoly_ params,int gameID);
	public CommunicationModel playYearOfPlentyCardCommand(Year_of_Plenty_ params,int gameID);
	public CommunicationModel playRoadBuildingCardCommand(Road_Building_ params,int gameID);
	public shared.model.Game getGameModelByID(int params);
	public int getVersionOf(int game_id);
	public void addCommand(int gameID,server.commands.Command c);
    public void addUser(User user, int user_id);

	public Fascade getFacadeByID(int gameID);
}
