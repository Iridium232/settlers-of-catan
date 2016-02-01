package client.communication;

import java.util.List;

import shared.communication.toServer.games.CreateGameRequest;
import shared.communication.toServer.games.LoadGameRequest;
import shared.communication.toServer.games.SaveGameRequest;
import shared.communication.toServer.user.Credentials;
import shared.definitions.CatanColor;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
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
	@Override
	public void ServerProxy(String host, int port) {
		// TODO Auto-generated method stub
			this.host = host;
			this.port = port;
	}

	@Override
	public void login(String username, String password) {
		// TODO Auto-generated method stub
		Credentials login=new Credentials(username,password);
		try {
			ClientCommunicator.getSINGLETON().doPost("/user/login", login);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void register(String username, String password) {
		// TODO Auto-generated method stub
		Credentials register=new Credentials(username,password);
		try{
				ClientCommunicator.getSINGLETON().doPost("/user/register",register);
		} catch (Exception e){
				e.printStackTrace();
		}
	}

	@Override
	public List<Game> getGameList() {
		// TODO Auto-generated method stub
		try {
			ClientCommunicator.getSINGLETON().doGet("/games/list");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
		
	}

	@Override
	public void saveGame(int id, String filename) {
		// TODO Auto-generated method stub
		SaveGameRequest save=new SaveGameRequest(id,filename);
		try {
			ClientCommunicator.getSINGLETON().doPost("/games/save", save);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void loadGame(String filename) {
		// TODO Auto-generated method stub
		LoadGameRequest load=new LoadGameRequest(filename);
		try {
			ClientCommunicator.getSINGLETON().doPost(filename, load);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void getModel(int id) {
		// need to figure out how we're getting the current number from the fascade and generally how the fascade interacts with the server proxy.
		try {
			ClientCommunicator.getSINGLETON().doGet("/game/model");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<String> getCommands() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String executeCommands(List<String> commands) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendChat(String message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void acceptTrade(boolean accept) {
		// TODO Auto-generated method stub

	}

	@Override
	public void rollNumber() {
		// TODO Auto-generated method stub

	}

	@Override
	public void buildRoad(boolean free, EdgeLocation roadLocation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void buildSettlement(boolean free, VertexLocation place) {
		// TODO Auto-generated method stub

	}

	@Override
	public void buildCity(VertexLocation place) {
		// TODO Auto-generated method stub

	}

	@Override
	public void maritimeTrade(int ratio, ResourceType input, ResourceType output) {
		// TODO Auto-generated method stub

	}

	@Override
	public void robPlayer(HexLocation location, Player victim) {
		// TODO Auto-generated method stub

	}

	@Override
	public void finishTurn() {
		// TODO Auto-generated method stub

	}

	@Override
	public void buyDevCard() {
		// TODO Auto-generated method stub

	}

	@Override
	public void playSoldier(HexLocation place, Player victim) {
		// TODO Auto-generated method stub

	}

	@Override
	public void yearOfPlenty(ResourceType one, ResourceType two) {
		// TODO Auto-generated method stub

	}

	@Override
	public void RoadBuilding(EdgeLocation one, EdgeLocation two) {
		// TODO Auto-generated method stub

	}

	@Override
	public void monopoly(ResourceType one) {
		// TODO Auto-generated method stub

	}

	@Override
	public void monument() {
		// TODO Auto-generated method stub

	}

	@Override
	public void discardCards(ResourceMultiSet discardedCards) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void offerTrade(ResourceMultiSet offer, Player receiver) {
		// TODO Auto-generated method stub
		
	}

}
