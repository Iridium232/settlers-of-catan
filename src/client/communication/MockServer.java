package client.communication;

import java.util.ArrayList;
import java.util.List;

import shared.communication.ResourceList;
import shared.communication.fromServer.games.EmptyPlayer;
import shared.communication.fromServer.games.NewGame;
import shared.definitions.CatanColor;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import shared.model.Fascade;
import shared.communication.fromServer.games.Game;
import shared.communication.fromServer.games.Player;

public class MockServer implements IServerProxy {
	private Fascade fascade;
	@Override
	public void ServerProxy(String host, int port, Fascade f) {
		// TODO Auto-generated method stub
		this.fascade=f;
	}

	@Override
	public String login(String username, String password) {
		// TODO Auto-generated method stub
		return "Success";
	}

	@Override
	public String register(String username, String password) {
		// TODO Auto-generated method stub
		return "Success";
	}

	@Override
	public List<Game> getGameList() {
		// TODO Auto-generated method stub
		List<Game> returnList=new ArrayList<Game>();
		Game temp=new Game(null, 0, null);
		returnList.add(temp);
		return returnList;
	}

	@Override
	public Game createGame(String name, boolean randomTiles, boolean randomNumbers, boolean randomPorts) {
		// TODO Auto-generated method stub
		Player[] players= new Player[4];
		Game temp=new Game(name,1,players);
		return temp;
	}

	@Override
	public String joinGame(String playerinfo, int id, CatanColor color) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getModel(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addAIPlayer(String AiType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAITypes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String sendChat(String message) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String acceptTrade(boolean accept) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String discardCards(ResourceList discardedCards) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String rollNumber(int number) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String buildRoad(boolean free, EdgeLocation roadLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String buildSettlement(boolean free, VertexLocation place) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String buildCity(VertexLocation place) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String maritimeTrade(int ratio, ResourceType input, ResourceType output) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String finishTurn() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String buyDevCard() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String yearOfPlenty(ResourceType one, ResourceType two) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String RoadBuilding(EdgeLocation one, EdgeLocation two) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String monopoly(ResourceType one) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String monument() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String offerTrade(ResourceList offer, shared.model.Player receiver) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String robPlayer(HexLocation location, shared.model.Player victim) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String playSoldier(HexLocation place, shared.model.Player victim) {
		// TODO Auto-generated method stub
		return null;
	}

}
