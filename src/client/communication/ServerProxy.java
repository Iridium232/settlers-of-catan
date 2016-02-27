package client.communication;

import com.google.gson.Gson;
import org.json.JSONObject;
import shared.communication.ResourceList;
import shared.communication.ResourceTranslator;
import shared.communication.fromServer.game.VertexLocation;
import shared.communication.fromServer.games.Game;
import shared.communication.toServer.game.AddAIRequest;
import shared.communication.toServer.games.CreateGameRequest;
import shared.communication.toServer.moves.*;
import shared.definitions.CatanColor;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.model.Fascade;
import shared.model.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
/**
 * Implements IServerProxy send requests through the client communicator to the server.
 * @author Doug
 *
 */
public class ServerProxy implements IServerProxy {
	private String host;
	private int port;
	private String path;
	private int playerIndex;
	private Fascade fascade;
	public ServerProxy(String host, int port, Fascade f){
		this.host=host;
		this.port=port;
		this.fascade=f;
		ClientCommunicator.getSingleton(host, Integer.toString(port));
	}
	@Override
	public void ServerProxy(String host, int port, Fascade f) {
		// TODO Auto-generated method stub
			this.host = host;
			this.port = port;
			this.fascade=f;
	}

	@Override
	public String login(String username, String password) {
		// TODO Auto-generated method stub
		try {
			return Integer.toString(ClientCommunicator.getSINGLETON().login(username, password));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "FAILED\n";
	}

	@Override
	public String register(String username, String password) {
		// TODO Auto-generated method stub
		try{
				return Integer.toString(ClientCommunicator.getSINGLETON().register(username, password));
		} catch (Exception e){
				e.printStackTrace();
		}
		return "FAILED\n";
	}

	@Override
	public List<Game> getGameList() {
		// TODO Auto-generated method stub
		List<Game> returnList=new ArrayList<Game>();
		try {
			List<JSONObject> result=ClientCommunicator.getSINGLETON().gamesList();
			Gson gee=new Gson();
			for(JSONObject j:result){
				String gg=j.toString();
				returnList.add(gee.fromJson(gg, Game.class));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	public Game createGame(String name, boolean randomTiles, boolean randomNumbers, boolean randomPorts) {
		// TODO Auto-generated method stub
		CreateGameRequest create= new CreateGameRequest(randomTiles,randomNumbers,randomPorts,name);
		JSONObject result=null;
		try {
			result=ClientCommunicator.getSINGLETON().doPost("/games/create", create);
			String game=result.toString();
			Gson gee=new Gson();
			return gee.fromJson(game, Game.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String joinGame(int id, CatanColor color) {
		// TODO Auto-generated method stub
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
	public void loadGame(String file_name) {
		//Todo auto-generated method stub
	}

	@Override
	public void saveGame(UUID game_id, String file_name){
		//Todo auto-generated method stub
	}

	@Override
	public String getModel(int id) {
		String result=null;
		try {
			JSONObject model=ClientCommunicator.getSINGLETON().doGet("/game/model");
			result=model.toString();
			ModelPopulator.populateModel(model, fascade);//add fascade to IServerProxy
		} catch (Exception e) {
			result="FAILED\n";
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public String sendChat(int playerIndex, String content) {
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
	public String acceptTrade(boolean accept) {
		// TODO Auto-generated method stub
		String result="FAILED\n";
		AcceptTrade at=new AcceptTrade(playerIndex,accept);
		try {
			result=ClientCommunicator.getSINGLETON().doPost("/moves/acceptTrade", at).toString();
		} catch (Exception e) {
			result="FAILED\n";
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String rollNumber(int number) {
		// TODO Auto-generated method stub
		String result=null;
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
		// TODO Auto-generated method stub
		String result=null;
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
	public String buildSettlement(boolean free, VertexLocation place) {
		// TODO Auto-generated method stub
		String result=null;
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
	public String buildCity(VertexLocation place) {
	// TODO Auto-generated method stub
		String result=null;
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
	public String maritimeTrade(int ratio, ResourceType input, ResourceType output) {
		
		String result=null;
		MaritimeTrade trade=new MaritimeTrade(playerIndex,ratio,
				ResourceTranslator.translate(input),ResourceTranslator.translate(output));
		try {
			result=ClientCommunicator.getSINGLETON().doPost("/moves/maritimeTrade", trade).toString();
		} catch (Exception e) {
			result="FAILED\n";
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String robPlayer(HexLocation location, Player victim) {
		// TODO Auto-generated method stub
		String result=null;
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
	public String finishTurn() {
		// TODO Auto-generated method stub
		String result=null;
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
	public String playSoldier(HexLocation place, Player victim) {
		// TODO Auto-generated method stub
		String result=null;
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
	public String yearOfPlenty(ResourceType one, ResourceType two) {
		// TODO Auto-generated method stub
		String result=null;
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
		// TODO Auto-generated method stub
		String result=null;
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
	public String monopoly(ResourceType one) {
		// TODO Auto-generated method stub
		String result=null;
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
	public String monument() {
		// TODO Auto-generated method stub
		String result=null;
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
	public String discardCards(ResourceList discardedCards) {
		// TODO Auto-generated method stub
		String result=null;
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
	public String offerTrade(ResourceList offer, Player receiver) {
		// TODO Auto-generated method stub
		String result=null;
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
	public String addAIPlayer(String AiType) {
		// TODO Auto-generated method stub
		String result=null;
		AddAIRequest ai=new AddAIRequest(AiType);
		try {
			result=ClientCommunicator.getSINGLETON().doPost("/game/addAI", ai).toString();
		} catch (Exception e) {
			result="FAILED\n";
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String getAITypes() {
		// TODO Auto-generated method stub
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

}
