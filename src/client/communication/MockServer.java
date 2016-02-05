package client.communication;

import java.util.List;

import shared.communication.ResourceList;
import shared.communication.toServer.game.AddAIRequest;
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
 * Implements the IServerProxy. returns set values used for testing.
 * @author Doug
 *
 */
public class MockServer implements IServerProxy {
	private String host;
	private int port;
	private String path;
	private int playerIndex;
	private Fascade fascade;
	@Override
	public void ServerProxy(String host, int port,Fascade f) {
		// TODO Auto-generated method stub
		this.fascade=f;
	}

	@Override
	public void login(String username, String password) {
		// TODO Auto-generated method stub

	}

	@Override
	public void register(String username, String password) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Game> getGameList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Game createGame(String name, boolean randomTiles, boolean randomNumbers, boolean randomPorts) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void joinGame(String playerinfo, int id, CatanColor color) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getModel(int id) {
		// TODO Auto-generated method stub

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
	public String getAITypes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addAIPlayer(String AiType) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void discardCards(ResourceList discardedCards) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rollNumber(int number) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void offerTrade(ResourceList offer, Player receiver) {
		// TODO Auto-generated method stub
		
	}

}
