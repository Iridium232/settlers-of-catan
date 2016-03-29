package client.communication;

import client.control.Reference;
import com.google.gson.Gson;
import org.json.JSONObject;
import shared.communication.ResourceList;
import shared.communication.ResourceTranslator;
import shared.communication.fromServer.game.CommunicationModel;
import shared.communication.fromServer.game.VertexLocation;
import shared.communication.fromServer.games.Game;
import shared.communication.toServer.game.AddAIRequest;
import shared.communication.toServer.games.CreateGameRequest;
import shared.communication.toServer.games.JoinGameRequest;
import shared.communication.toServer.games.LoadGameRequest;
import shared.communication.toServer.games.SaveGameRequest;
import shared.communication.toServer.moves.*;
import shared.communication.toServer.user.Credentials;
import shared.definitions.CatanColor;
import shared.definitions.ResourceType;
import shared.locations.HexLocation;
import shared.model.Fascade;
import shared.model.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
/**
 * Implements IServer send requests through the client communicator to the server.
 * @author Doug
 *
 */
public class ServerProxy implements IServer {
	private String host;
	private int port;
	private String path;
	private Fascade fascade;
	public ServerProxy(String host, int port, Fascade f)
	{
		this.host=host;
		this.port=port;
		this.fascade=f;
		ClientCommunicator.getSingleton(host, Integer.toString(port));
	}
	@Override
	public void ServerProxy(String host, int port, Fascade f) 
	{
			this.host = host;
			this.port = port;
			this.fascade=f;
	}

	@Override
	public String login(String username, String password) 
	{
		try {
			return Integer.toString(ClientCommunicator.getSINGLETON().login(username, password));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "FAILED\n";
	}

	@Override
	public String register(String username, String password) {
		try{
				return Integer.toString(ClientCommunicator.getSINGLETON().register(username, password));
		} catch (Exception e){
				e.printStackTrace();
		}
		return "FAILED\n";
	}

	@Override
	public List<Game> getGameList() 
	{
		List<Game> returnList=new ArrayList<Game>();
		try {
			List<JSONObject> result=ClientCommunicator.getSINGLETON().gamesList();
			Gson gee=new Gson();
			for(JSONObject j:result){
				String gg=j.toString();
				returnList.add(gee.fromJson(gg, Game.class));
			}
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	public Game createGame(String name, boolean randomTiles, boolean randomNumbers, boolean randomPorts) 
	{
		CreateGameRequest create= new CreateGameRequest(randomTiles,randomNumbers,randomPorts,name);
		JSONObject result=null;
		try 
		{
			result=ClientCommunicator.getSINGLETON().doPost("/games/create", create);
			String game=result.toString();
			Gson gee=new Gson();
			return gee.fromJson(game, Game.class);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String joinGame(int id, CatanColor color) 
	{
		String result="FAILED\n";
		try {
			result=ClientCommunicator.getSINGLETON().joinGame(id, color).toString();
		} catch (Exception e) {
			result="FAILED\n";
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public void loadGame(String file_name) 
	{
		
	}

	@Override
	public void saveGame(UUID game_id, String file_name)
	{
		
	}

	@Override
	public String getModel(int id) 
	{
		String result=null;
		try {
			JSONObject model=ClientCommunicator.getSINGLETON().doGet("/game/model");

			if (!model.has("True")) {
				result=model.toString();
				ModelPopulator.populateModel(model, fascade);//add fascade to IServer
			}
		} catch (Exception e) {

			result="FAILED\n";
			e.printStackTrace();
		}
		
		return result;
	}
	
	@Override
	public String sendChat(int playerIndex, String content) 
	{
		SendChat chat = new SendChat(playerIndex, content);
		String result=null;
		try {
			result=ClientCommunicator.getSINGLETON().doPost("/moves/sendChat", chat).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
}

	@Override
	public String acceptTrade(int playerIndex, boolean accept)
	{
		String result="FAILED\n";
		AcceptTrade at=new AcceptTrade(playerIndex,accept);
		try {
			result=ClientCommunicator.getSINGLETON().doPost("/moves/acceptTrade", at).toString();
			ModelPopulator.populateModel(new JSONObject(result), fascade);
		} catch (Exception e) {
			result="FAILED\n";
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String rollNumber(int number) 
	{
		String result=null;
		int playerIndex = Reference.GET_SINGLETON().getPlayer_index();
		RollNumber roll=new RollNumber(playerIndex,number);
		try {
			result=ClientCommunicator.getSINGLETON().doPost("/moves/rollNumber", roll).toString();
		} catch (Exception e) {
			result="FAILED\n";
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String buildRoad(boolean free, shared.communication.EdgeLocation roadLocation) {
		String result=null;
		int playerIndex = Reference.GET_SINGLETON().getPlayer_index();
		BuildRoad road=new BuildRoad(playerIndex,roadLocation,free);
		try {
			result=ClientCommunicator.getSINGLETON().doPost("/moves/buildRoad", road).toString();
		} catch (Exception e) {
			result="FAILED\n";
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String buildSettlement(boolean free, VertexLocation place) 
	{
		String result=null;
		int playerIndex = Reference.GET_SINGLETON().getPlayer_index();
		BuildSettlement settle=new BuildSettlement(playerIndex,place,free);
		try {
			result=ClientCommunicator.getSINGLETON().doPost("/moves/buildSettlement", settle).toString();
		} catch (Exception e) {
			result="FAILED\n";
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String buildCity(VertexLocation place) 
	{
		String result=null;
		int playerIndex = Reference.GET_SINGLETON().getPlayer_index();
		BuildCity city=new BuildCity(playerIndex, place);
		try {
			result=ClientCommunicator.getSINGLETON().doPost("/moves/buildCity", city).toString();
		} catch (Exception e) {
			result="FAILED\n";
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String maritimeTrade(int ratio, ResourceType input, ResourceType output) 
	{
		
		String result=null;
		int playerIndex = Reference.GET_SINGLETON().getPlayer_index();
		MaritimeTrade trade=new MaritimeTrade(playerIndex,ratio,
				ResourceTranslator.translate(input),ResourceTranslator.translate(output));
		try {
			result=ClientCommunicator.getSINGLETON().doPost("/moves/maritimeTrade", trade).toString();
			ModelPopulator.populateModel(new JSONObject(result), fascade);
		} catch (Exception e) {
			result="FAILED\n";
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String robPlayer(HexLocation location, Player victim) 
	{
		String result=null;
		int playerIndex = Reference.GET_SINGLETON().getPlayer_index();
		RobPlayer rob=new RobPlayer(playerIndex,victim.getPlayerIndex(),location);
		try {
			result=ClientCommunicator.getSINGLETON().doPost("/moves/robPlayer", rob).toString();
		} catch (Exception e) {
			result="FAILED\n";
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String finishTurn() 
	{
		String result=null;
		int playerIndex = Reference.GET_SINGLETON().getPlayer_index();
		FinishTurn end=new FinishTurn(playerIndex);
		try {
			result=ClientCommunicator.getSINGLETON().doPost("/moves/finishTurn", end).toString();
		} catch (Exception e) {
			result="FAILED\n";
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String buyDevCard() 
	{

		String result=null;
		int playerIndex = Reference.GET_SINGLETON().getPlayer_index();
		BuyDevCard card=new BuyDevCard(playerIndex);
		try {
			result=ClientCommunicator.getSINGLETON().doPost("/moves/buyDevCard", card).toString();
		} catch (Exception e) {
			result="FAILED\n";
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String playSoldier(HexLocation place, Player victim) 
	{
		String result=null;
		int playerIndex = Reference.GET_SINGLETON().getPlayer_index();
		Soldier_ soldier=new Soldier_(playerIndex,victim.getPlayerIndex(),place);
		try {
			result=ClientCommunicator.getSINGLETON().doPost("/moves/Soldier", soldier).toString();
		} 
		catch (Exception e) {
			result="FAILED\n";
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String yearOfPlenty(ResourceType one, ResourceType two) 
	{
		String result=null;
		int playerIndex = Reference.GET_SINGLETON().getPlayer_index();
		Year_of_Plenty_ year=new Year_of_Plenty_(playerIndex,
				ResourceTranslator.translate(one),ResourceTranslator.translate(two));
		try {
			result=ClientCommunicator.getSINGLETON().doPost("/moves/Year_of_Plenty", year).toString();
		} catch (Exception e) {
			result="FAILED\n";
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String RoadBuilding(shared.communication.EdgeLocation one, shared.communication.EdgeLocation two) {
		String result=null;
		int playerIndex = Reference.GET_SINGLETON().getPlayer_index();
		Road_Building_ rb=new Road_Building_(playerIndex,one,two);
		try {
			result=ClientCommunicator.getSINGLETON().doPost("/moves/Road_Building", rb).toString();
		} catch (Exception e) {
			result="FAILED\n";
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String monopoly(ResourceType one) 
	{
		String result=null;
		int playerIndex = Reference.GET_SINGLETON().getPlayer_index();
		Monopoly_ mono=new Monopoly_(playerIndex,ResourceTranslator.translate(one));
		try {
			result=ClientCommunicator.getSINGLETON().doPost("/moves/Monopoly", mono).toString();
		} catch (Exception e) {
			result="FAILED\n";
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String monument() 
	{
		String result=null;
		int playerIndex = Reference.GET_SINGLETON().getPlayer_index();
		Monument_ mon=new Monument_(playerIndex);
		try {
			result=ClientCommunicator.getSINGLETON().doPost("/moves/Monument", mon).toString();
		} catch (Exception e) {
			result="FAILED\n";
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String discardCards(ResourceList discardedCards) 
	{
		String result=null;
		int playerIndex = Reference.GET_SINGLETON().getPlayer_index();
		DiscardCards dc=new DiscardCards(playerIndex,discardedCards);
		try {
			result=ClientCommunicator.getSINGLETON().doPost("/moves/discardCards", dc).toString();
		} catch (Exception e) {
			result="FAILED\n";
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String offerTrade(ResourceList offer, Player receiver) 
	{
		String result=null;
		int playerIndex = Reference.GET_SINGLETON().getPlayer_index();
		OfferTrade ot=new OfferTrade(playerIndex,offer,receiver.getPlayerIndex());
		try {
			result=ClientCommunicator.getSINGLETON().doPost("/moves/offerTrade", ot).toString();
		} catch (Exception e) {
			result="FAILED\n";
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String addAIPlayer(String AiType) 
	{
		String result=null;
		AddAIRequest ai=new AddAIRequest(AiType);
		try {
			result=ClientCommunicator.getSINGLETON().doPost("/game/addAI", ai).toString();
			this.getModel(Reference.GET_SINGLETON().getFascade().getLatestModelNum());
		} catch (Exception e) {
			result="FAILED\n";
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String getAITypes() 
	{
		String result=null;
		try {
			JSONObject j=ClientCommunicator.getSINGLETON().doGet("/game/listAI");
			result=j.toString();
		} catch (Exception e) {
			result="FAILED\n";
			e.printStackTrace();
		}
		return result;
	}
	@Override
	public String registerCommand(Credentials credentials) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String loginCommand(Credentials credentials) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Game[] getGameListCommand() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String createGameCommand(CreateGameRequest params) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public shared.model.Game getModelCommand(int params) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String saveGameCommand(SaveGameRequest params) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public CommunicationModel loadGameCommand(LoadGameRequest params) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public CommunicationModel addAIPlayerCommand(AddAIRequest params) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String[] getAITypesCommand() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public CommunicationModel sendChatCommand(SendChat params) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public CommunicationModel acceptTradeCommand(AcceptTrade params) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public CommunicationModel discardCommand(DiscardCards params) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public CommunicationModel rollNumberCommand(RollNumber params) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public CommunicationModel buildRoadCommand(BuildRoad params) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public CommunicationModel buildCityCommand(BuildCity params) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public CommunicationModel buildSettlementCommand(BuildSettlement params) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public CommunicationModel offerTradeCommand(OfferTrade params) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public CommunicationModel maritimeTradeCommand(MaritimeTrade params) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public CommunicationModel robCommand(RobPlayer params) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public CommunicationModel FinishTurnCommand(FinishTurn params) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public CommunicationModel buyDevCardCommand(BuyDevCard params) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public CommunicationModel playSoldierCardCommand(Soldier_ params) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public CommunicationModel playMonumentCardCommand(Monument_ params) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public CommunicationModel playMonopolyCardCommand(Monopoly_ params) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public CommunicationModel playYearOfPlentyCardCommand(Year_of_Plenty_ params) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public CommunicationModel playRoadBuildingCardCommand(Road_Building_ params) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public shared.model.Game getGameModelByID(int params) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int getVersionOf(int game_id) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public Fascade getFacadeByID(int gameID) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int joinGameCommand(JoinGameRequest params, int playerID) {
		// TODO Auto-generated method stub
		return 0;
	}

}
