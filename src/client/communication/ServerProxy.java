package client.communication;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import shared.communication.ResourceList;
import shared.communication.toServer.game.AddAIRequest;
import shared.communication.toServer.games.CreateGameRequest;
import shared.communication.toServer.games.LoadGameRequest;
import shared.communication.toServer.games.SaveGameRequest;
import shared.communication.toServer.moves.AcceptTrade;
import shared.communication.toServer.moves.BuildCity;
import shared.communication.toServer.moves.BuildRoad;
import shared.communication.toServer.moves.BuildSettlement;
import shared.communication.toServer.moves.BuyDevCard;
import shared.communication.toServer.moves.DiscardCards;
import shared.communication.toServer.moves.FinishTurn;
import shared.communication.toServer.moves.MaritimeTrade;
import shared.communication.toServer.moves.Monopoly_;
import shared.communication.toServer.moves.Monument_;
import shared.communication.toServer.moves.OfferTrade;
import shared.communication.toServer.moves.Road_Building_;
import shared.communication.toServer.moves.RobPlayer;
import shared.communication.toServer.moves.RollNumber;
import shared.communication.toServer.moves.Soldier_;
import shared.communication.toServer.moves.Year_of_Plenty_;
import shared.communication.toServer.user.Credentials;
import shared.definitions.CatanColor;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import shared.model.Fascade;
import shared.model.Game;
import shared.model.Player;
import shared.model.ResourceMultiSet;
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
	@Override
	public void ServerProxy(String host, int port, Fascade f) {
		// TODO Auto-generated method stub
			this.host = host;
			this.port = port;
			this.fascade=f;
	}

	@Override
	public void login(String username, String password) {
		// TODO Auto-generated method stub
		try {
			ClientCommunicator.getSINGLETON().login(username, password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void register(String username, String password) {
		// TODO Auto-generated method stub
		try{
				ClientCommunicator.getSINGLETON().register(username, password);;
		} catch (Exception e){
				e.printStackTrace();
		}
	}

	@Override
	public List<Game> getGameList() {
		// TODO Auto-generated method stub
		List<Game> returnList=new ArrayList<Game>();
		try {
			JSONObject result=ClientCommunicator.getSINGLETON().gamesList();
			List<JSONObject> gamesList=(List<JSONObject>)result.get("games");
			Gson gee=new Gson();
			for(JSONObject j:gamesList){
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
		try {
			ClientCommunicator.getSINGLETON().doPost("/games/create", create);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void joinGame(String playerinfo, int id, CatanColor color) {
		// TODO Auto-generated method stub
		try {
			ClientCommunicator.getSINGLETON().joinGame(id, color);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void getModel(int id) {
		// need to figure out how we're getting the current number from the fascade and generally how the fascade interacts with the server proxy.
		try {
			JSONObject model=ClientCommunicator.getSINGLETON().doGet("/game/model");
			ModelPopulator.populateModel(model, fascade);//add fascade to IServerProxy
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void sendChat(String message) {
		// TODO Auto-generated method stub
		try {
			ClientCommunicator.getSINGLETON().doPost("/moves/sendChat", message);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void acceptTrade(boolean accept) {
		// TODO Auto-generated method stub
		AcceptTrade at=new AcceptTrade(playerIndex,accept);
		try {
			ClientCommunicator.getSINGLETON().doPost("/moves/acceptTrade", at);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void rollNumber(int number) {
		// TODO Auto-generated method stub
		RollNumber roll=new RollNumber(playerIndex,number);
		try {
			ClientCommunicator.getSINGLETON().doPost("/moves/rollNumber", roll);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void buildRoad(boolean free, EdgeLocation roadLocation) {
		// TODO Auto-generated method stub
		BuildRoad road=new BuildRoad(playerIndex,roadLocation,free);
		try {
			ClientCommunicator.getSINGLETON().doPost("/moves/buildRoad", road);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void buildSettlement(boolean free, VertexLocation place) {
		// TODO Auto-generated method stub
		BuildSettlement settle=new BuildSettlement(playerIndex,place,free);
		try {
			ClientCommunicator.getSINGLETON().doPost("/moves/buildSettlement", settle);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}

	@Override
	public void buildCity(VertexLocation place) {
		// TODO Auto-generated method stub
		BuildCity city=new BuildCity(playerIndex,place);
		try {
			ClientCommunicator.getSINGLETON().doPost("/moves/buildCity", city);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void maritimeTrade(int ratio, ResourceType input, ResourceType output) {
		// TODO Auto-generated method stub
		MaritimeTrade trade=new MaritimeTrade(playerIndex,ratio,input,output);
		try {
			ClientCommunicator.getSINGLETON().doPost("/moves/maritimeTrade", trade);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void robPlayer(HexLocation location, Player victim) {
		// TODO Auto-generated method stub
		RobPlayer rob=new RobPlayer(playerIndex,victim.getPlayerIndex(),location);
		try {
			ClientCommunicator.getSINGLETON().doPost("/moves/robPlayer", rob);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void finishTurn() {
		// TODO Auto-generated method stub
		FinishTurn end=new FinishTurn(playerIndex);
		try {
			ClientCommunicator.getSINGLETON().doPost("/moves/finishTurn", end);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void buyDevCard() {
		// TODO Auto-generated method stub
		BuyDevCard card=new BuyDevCard(playerIndex);
		try {
			ClientCommunicator.getSINGLETON().doPost("/moves/buyDevCard", card);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void playSoldier(HexLocation place, Player victim) {
		// TODO Auto-generated method stub
		Soldier_ soldier=new Soldier_(playerIndex,victim.getPlayerIndex(),place);
		try {
			ClientCommunicator.getSINGLETON().doPost("/moves/Soldier", soldier);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void yearOfPlenty(ResourceType one, ResourceType two) {
		// TODO Auto-generated method stub
		Year_of_Plenty_ year=new Year_of_Plenty_(playerIndex,one,two);
		try {
			ClientCommunicator.getSINGLETON().doPost("/moves/Year_of_Plenty", year);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void RoadBuilding(EdgeLocation one, EdgeLocation two) {
		// TODO Auto-generated method stub
		Road_Building_ rb=new Road_Building_(playerIndex,one,two);
		try {
			ClientCommunicator.getSINGLETON().doPost("/moves/Road_Building", rb);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void monopoly(ResourceType one) {
		// TODO Auto-generated method stub
		Monopoly_ mono=new Monopoly_(playerIndex,one);
		try {
			ClientCommunicator.getSINGLETON().doPost("/moves/Monopoly", mono);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void monument() {
		// TODO Auto-generated method stub
		Monument_ mon=new Monument_(playerIndex);
		try {
			ClientCommunicator.getSINGLETON().doPost("/moves/Monument", mon);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void discardCards(ResourceList discardedCards) {
		// TODO Auto-generated method stub
		DiscardCards dc=new DiscardCards(playerIndex,discardedCards);
		try {
			ClientCommunicator.getSINGLETON().doPost("/moves/discardCards", dc);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void offerTrade(ResourceList offer, Player receiver) {
		// TODO Auto-generated method stub
		OfferTrade ot=new OfferTrade(playerIndex,offer,receiver.getPlayerIndex());
		try {
			ClientCommunicator.getSINGLETON().doPost("/moves/offerTrade", ot);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void addAIPlayer(String AiType) {
		// TODO Auto-generated method stub
		AddAIRequest ai=new AddAIRequest(AiType);
		try {
			ClientCommunicator.getSINGLETON().doPost("/game/addAI", ai);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String getAITypes() {
		// TODO Auto-generated method stub
		String result=null;
		try {
			JSONObject j=ClientCommunicator.getSINGLETON().doGet("/game/listAI");
			result=j.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}
