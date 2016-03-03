package client.communication;

import org.json.JSONException;
import org.json.JSONObject;
import shared.communication.ResourceList;
import shared.communication.fromServer.game.VertexLocation;
import shared.communication.fromServer.games.Game;
import shared.communication.fromServer.games.Player;
import shared.definitions.CatanColor;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.model.Fascade;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
	public String joinGame(int id, CatanColor color) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public String getModel(int id) {
		if (id != 0) {
			JSONObject json = null;
			try {
				json = new JSONObject(getHardCodedModel());
			} catch (JSONException e) {
				e.printStackTrace();
			}
			ModelPopulator.populateModel(json, fascade);
		}
		return null;
	}

	@Override
	public void loadGame(String s) {
		//Todo auto-generated method stub
	}

	@Override
	public void saveGame(UUID game_id, String file_name){
		//Todo auto-generated method stub
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
	public String sendChat(int playerIndex,String message) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String acceptTrade(int playerIndes, boolean accept) {
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
	public String buildRoad(boolean free, shared.communication.EdgeLocation roadLocation) {
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
	public String RoadBuilding(shared.communication.EdgeLocation one, shared.communication.EdgeLocation two) {
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
	public String offerTrade(ResourceList offer, shared.model.player.Player receiver) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String robPlayer(HexLocation location, shared.model.player.Player victim) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String playSoldier(HexLocation place, shared.model.player.Player victim) {
		// TODO Auto-generated method stub
		return null;
	}

	private String getHardCodedModel() {
		return "{\n" +
				"  \"deck\": {\n" +
				"    \"yearOfPlenty\": 2,\n" +
				"    \"monopoly\": 2,\n" +
				"    \"soldier\": 14,\n" +
				"    \"roadBuilding\": 2,\n" +
				"    \"monument\": 5\n" +
				"  },\n" +
				"  \"map\": {\n" +
				"    \"hexes\": [\n" +
				"      {\n" +
				"        \"location\": {\n" +
				"          \"x\": 0,\n" +
				"          \"y\": -2\n" +
				"        }\n" +
				"      },\n" +
				"      {\n" +
				"        \"resource\": \"brick\",\n" +
				"        \"location\": {\n" +
				"          \"x\": 1,\n" +
				"          \"y\": -2\n" +
				"        },\n" +
				"        \"number\": 4\n" +
				"      },\n" +
				"      {\n" +
				"        \"resource\": \"wood\",\n" +
				"        \"location\": {\n" +
				"          \"x\": 2,\n" +
				"          \"y\": -2\n" +
				"        },\n" +
				"        \"number\": 11\n" +
				"      },\n" +
				"      {\n" +
				"        \"resource\": \"brick\",\n" +
				"        \"location\": {\n" +
				"          \"x\": -1,\n" +
				"          \"y\": -1\n" +
				"        },\n" +
				"        \"number\": 8\n" +
				"      },\n" +
				"      {\n" +
				"        \"resource\": \"wood\",\n" +
				"        \"location\": {\n" +
				"          \"x\": 0,\n" +
				"          \"y\": -1\n" +
				"        },\n" +
				"        \"number\": 3\n" +
				"      },\n" +
				"      {\n" +
				"        \"resource\": \"ore\",\n" +
				"        \"location\": {\n" +
				"          \"x\": 1,\n" +
				"          \"y\": -1\n" +
				"        },\n" +
				"        \"number\": 9\n" +
				"      },\n" +
				"      {\n" +
				"        \"resource\": \"sheep\",\n" +
				"        \"location\": {\n" +
				"          \"x\": 2,\n" +
				"          \"y\": -1\n" +
				"        },\n" +
				"        \"number\": 12\n" +
				"      },\n" +
				"      {\n" +
				"        \"resource\": \"ore\",\n" +
				"        \"location\": {\n" +
				"          \"x\": -2,\n" +
				"          \"y\": 0\n" +
				"        },\n" +
				"        \"number\": 5\n" +
				"      },\n" +
				"      {\n" +
				"        \"resource\": \"sheep\",\n" +
				"        \"location\": {\n" +
				"          \"x\": -1,\n" +
				"          \"y\": 0\n" +
				"        },\n" +
				"        \"number\": 10\n" +
				"      },\n" +
				"      {\n" +
				"        \"resource\": \"wheat\",\n" +
				"        \"location\": {\n" +
				"          \"x\": 0,\n" +
				"          \"y\": 0\n" +
				"        },\n" +
				"        \"number\": 11\n" +
				"      },\n" +
				"      {\n" +
				"        \"resource\": \"brick\",\n" +
				"        \"location\": {\n" +
				"          \"x\": 1,\n" +
				"          \"y\": 0\n" +
				"        },\n" +
				"        \"number\": 5\n" +
				"      },\n" +
				"      {\n" +
				"        \"resource\": \"wheat\",\n" +
				"        \"location\": {\n" +
				"          \"x\": 2,\n" +
				"          \"y\": 0\n" +
				"        },\n" +
				"        \"number\": 6\n" +
				"      },\n" +
				"      {\n" +
				"        \"resource\": \"wheat\",\n" +
				"        \"location\": {\n" +
				"          \"x\": -2,\n" +
				"          \"y\": 1\n" +
				"        },\n" +
				"        \"number\": 2\n" +
				"      },\n" +
				"      {\n" +
				"        \"resource\": \"sheep\",\n" +
				"        \"location\": {\n" +
				"          \"x\": -1,\n" +
				"          \"y\": 1\n" +
				"        },\n" +
				"        \"number\": 9\n" +
				"      },\n" +
				"      {\n" +
				"        \"resource\": \"wood\",\n" +
				"        \"location\": {\n" +
				"          \"x\": 0,\n" +
				"          \"y\": 1\n" +
				"        },\n" +
				"        \"number\": 4\n" +
				"      },\n" +
				"      {\n" +
				"        \"resource\": \"sheep\",\n" +
				"        \"location\": {\n" +
				"          \"x\": 1,\n" +
				"          \"y\": 1\n" +
				"        },\n" +
				"        \"number\": 10\n" +
				"      },\n" +
				"      {\n" +
				"        \"resource\": \"wood\",\n" +
				"        \"location\": {\n" +
				"          \"x\": -2,\n" +
				"          \"y\": 2\n" +
				"        },\n" +
				"        \"number\": 6\n" +
				"      },\n" +
				"      {\n" +
				"        \"resource\": \"ore\",\n" +
				"        \"location\": {\n" +
				"          \"x\": -1,\n" +
				"          \"y\": 2\n" +
				"        },\n" +
				"        \"number\": 3\n" +
				"      },\n" +
				"      {\n" +
				"        \"resource\": \"wheat\",\n" +
				"        \"location\": {\n" +
				"          \"x\": 0,\n" +
				"          \"y\": 2\n" +
				"        },\n" +
				"        \"number\": 8\n" +
				"      }\n" +
				"    ],\n" +
				"    \"roads\": [\n" +
				"      {\n" +
				"        \"owner\": 2,\n" +
				"        \"location\": {\n" +
				"          \"direction\": \"SW\",\n" +
				"          \"x\": 1,\n" +
				"          \"y\": -1\n" +
				"        }\n" +
				"      },\n" +
				"      {\n" +
				"        \"owner\": 0,\n" +
				"        \"location\": {\n" +
				"          \"direction\": \"SE\",\n" +
				"          \"x\": -1,\n" +
				"          \"y\": -1\n" +
				"        }\n" +
				"      },\n" +
				"      {\n" +
				"        \"owner\": 2,\n" +
				"        \"location\": {\n" +
				"          \"direction\": \"SW\",\n" +
				"          \"x\": -2,\n" +
				"          \"y\": 1\n" +
				"        }\n" +
				"      },\n" +
				"      {\n" +
				"        \"owner\": 3,\n" +
				"        \"location\": {\n" +
				"          \"direction\": \"S\",\n" +
				"          \"x\": -2,\n" +
				"          \"y\": 0\n" +
				"        }\n" +
				"      },\n" +
				"      {\n" +
				"        \"owner\": 3,\n" +
				"        \"location\": {\n" +
				"          \"direction\": \"SE\",\n" +
				"          \"x\": -1,\n" +
				"          \"y\": 0\n" +
				"        }\n" +
				"      },\n" +
				"      {\n" +
				"        \"owner\": 1,\n" +
				"        \"location\": {\n" +
				"          \"direction\": \"SE\",\n" +
				"          \"x\": 0,\n" +
				"          \"y\": -2\n" +
				"        }\n" +
				"      },\n" +
				"      {\n" +
				"        \"owner\": 1,\n" +
				"        \"location\": {\n" +
				"          \"direction\": \"N\",\n" +
				"          \"x\": -1,\n" +
				"          \"y\": -1\n" +
				"        }\n" +
				"      }\n" +
				"    ],\n" +
				"    \"cities\": [],\n" +
				"    \"settlements\": [\n" +
				"      {\n" +
				"        \"owner\": 3,\n" +
				"        \"location\": {\n" +
				"          \"direction\": \"SE\",\n" +
				"          \"x\": -1,\n" +
				"          \"y\": 0\n" +
				"        }\n" +
				"      },\n" +
				"      {\n" +
				"        \"owner\": 2,\n" +
				"        \"location\": {\n" +
				"          \"direction\": \"SW\",\n" +
				"          \"x\": 1,\n" +
				"          \"y\": -1\n" +
				"        }\n" +
				"      },\n" +
				"      {\n" +
				"        \"owner\": 1,\n" +
				"        \"location\": {\n" +
				"          \"direction\": \"E\",\n" +
				"          \"x\": 0,\n" +
				"          \"y\": -2\n" +
				"        }\n" +
				"      },\n" +
				"      {\n" +
				"        \"owner\": 0,\n" +
				"        \"location\": {\n" +
				"          \"direction\": \"SE\",\n" +
				"          \"x\": -1,\n" +
				"          \"y\": -1\n" +
				"        }\n" +
				"      },\n" +
				"      {\n" +
				"        \"owner\": 2,\n" +
				"        \"location\": {\n" +
				"          \"direction\": \"SW\",\n" +
				"          \"x\": -2,\n" +
				"          \"y\": 1\n" +
				"        }\n" +
				"      },\n" +
				"      {\n" +
				"        \"owner\": 3,\n" +
				"        \"location\": {\n" +
				"          \"direction\": \"SW\",\n" +
				"          \"x\": -2,\n" +
				"          \"y\": 0\n" +
				"        }\n" +
				"      },\n" +
				"      {\n" +
				"        \"owner\": 1,\n" +
				"        \"location\": {\n" +
				"          \"direction\": \"NW\",\n" +
				"          \"x\": -1,\n" +
				"          \"y\": -1\n" +
				"        }\n" +
				"      }\n" +
				"    ],\n" +
				"    \"radius\": 3,\n" +
				"    \"ports\": [\n" +
				"      {\n" +
				"        \"ratio\": 3,\n" +
				"        \"direction\": \"NW\",\n" +
				"        \"location\": {\n" +
				"          \"x\": 2,\n" +
				"          \"y\": 1\n" +
				"        }\n" +
				"      },\n" +
				"      {\n" +
				"        \"ratio\": 2,\n" +
				"        \"resource\": \"ore\",\n" +
				"        \"direction\": \"S\",\n" +
				"        \"location\": {\n" +
				"          \"x\": 1,\n" +
				"          \"y\": -3\n" +
				"        }\n" +
				"      },\n" +
				"      {\n" +
				"        \"ratio\": 2,\n" +
				"        \"resource\": \"brick\",\n" +
				"        \"direction\": \"NE\",\n" +
				"        \"location\": {\n" +
				"          \"x\": -2,\n" +
				"          \"y\": 3\n" +
				"        }\n" +
				"      },\n" +
				"      {\n" +
				"        \"ratio\": 2,\n" +
				"        \"resource\": \"wheat\",\n" +
				"        \"direction\": \"S\",\n" +
				"        \"location\": {\n" +
				"          \"x\": -1,\n" +
				"          \"y\": -2\n" +
				"        }\n" +
				"      },\n" +
				"      {\n" +
				"        \"ratio\": 2,\n" +
				"        \"resource\": \"wood\",\n" +
				"        \"direction\": \"NE\",\n" +
				"        \"location\": {\n" +
				"          \"x\": -3,\n" +
				"          \"y\": 2\n" +
				"        }\n" +
				"      },\n" +
				"      {\n" +
				"        \"ratio\": 3,\n" +
				"        \"direction\": \"SW\",\n" +
				"        \"location\": {\n" +
				"          \"x\": 3,\n" +
				"          \"y\": -3\n" +
				"        }\n" +
				"      },\n" +
				"      {\n" +
				"        \"ratio\": 2,\n" +
				"        \"resource\": \"sheep\",\n" +
				"        \"direction\": \"NW\",\n" +
				"        \"location\": {\n" +
				"          \"x\": 3,\n" +
				"          \"y\": -1\n" +
				"        }\n" +
				"      },\n" +
				"      {\n" +
				"        \"ratio\": 3,\n" +
				"        \"direction\": \"N\",\n" +
				"        \"location\": {\n" +
				"          \"x\": 0,\n" +
				"          \"y\": 3\n" +
				"        }\n" +
				"      },\n" +
				"      {\n" +
				"        \"ratio\": 3,\n" +
				"        \"direction\": \"SE\",\n" +
				"        \"location\": {\n" +
				"          \"x\": -3,\n" +
				"          \"y\": 0\n" +
				"        }\n" +
				"      }\n" +
				"    ],\n" +
				"    \"robber\": {\n" +
				"      \"x\": 0,\n" +
				"      \"y\": -2\n" +
				"    }\n" +
				"  },\n" +
				"  \"players\": [\n" +
				"    {\n" +
				"      \"resources\": {\n" +
				"        \"brick\": 0,\n" +
				"        \"wood\": 0,\n" +
				"        \"sheep\": 0,\n" +
				"        \"wheat\": 0,\n" +
				"        \"ore\": 0\n" +
				"      },\n" +
				"      \"oldDevCards\": {\n" +
				"        \"yearOfPlenty\": 0,\n" +
				"        \"monopoly\": 0,\n" +
				"        \"soldier\": 0,\n" +
				"        \"roadBuilding\": 0,\n" +
				"        \"monument\": 0\n" +
				"      },\n" +
				"      \"newDevCards\": {\n" +
				"        \"yearOfPlenty\": 0,\n" +
				"        \"monopoly\": 0,\n" +
				"        \"soldier\": 0,\n" +
				"        \"roadBuilding\": 0,\n" +
				"        \"monument\": 0\n" +
				"      },\n" +
				"      \"roads\": 14,\n" +
				"      \"cities\": 4,\n" +
				"      \"settlements\": 4,\n" +
				"      \"soldiers\": 0,\n" +
				"      \"victoryPoints\": 1,\n" +
				"      \"monuments\": 0,\n" +
				"      \"playedDevCard\": false,\n" +
				"      \"discarded\": false,\n" +
				"      \"playerID\": 10,\n" +
				"      \"playerIndex\": 0,\n" +
				"      \"name\": \"Pete\",\n" +
				"      \"color\": \"orange\"\n" +
				"    },\n" +
				"    {\n" +
				"      \"resources\": {\n" +
				"        \"brick\": 1,\n" +
				"        \"wood\": 0,\n" +
				"        \"sheep\": 0,\n" +
				"        \"wheat\": 0,\n" +
				"        \"ore\": 0\n" +
				"      },\n" +
				"      \"oldDevCards\": {\n" +
				"        \"yearOfPlenty\": 0,\n" +
				"        \"monopoly\": 0,\n" +
				"        \"soldier\": 0,\n" +
				"        \"roadBuilding\": 0,\n" +
				"        \"monument\": 0\n" +
				"      },\n" +
				"      \"newDevCards\": {\n" +
				"        \"yearOfPlenty\": 0,\n" +
				"        \"monopoly\": 0,\n" +
				"        \"soldier\": 0,\n" +
				"        \"roadBuilding\": 0,\n" +
				"        \"monument\": 0\n" +
				"      },\n" +
				"      \"roads\": 13,\n" +
				"      \"cities\": 4,\n" +
				"      \"settlements\": 3,\n" +
				"      \"soldiers\": 0,\n" +
				"      \"victoryPoints\": 2,\n" +
				"      \"monuments\": 0,\n" +
				"      \"playedDevCard\": false,\n" +
				"      \"discarded\": false,\n" +
				"      \"playerID\": -2,\n" +
				"      \"playerIndex\": 1,\n" +
				"      \"name\": \"Miguel\",\n" +
				"      \"color\": \"blue\"\n" +
				"    },\n" +
				"    {\n" +
				"      \"resources\": {\n" +
				"        \"brick\": 0,\n" +
				"        \"wood\": 1,\n" +
				"        \"sheep\": 0,\n" +
				"        \"wheat\": 1,\n" +
				"        \"ore\": 0\n" +
				"      },\n" +
				"      \"oldDevCards\": {\n" +
				"        \"yearOfPlenty\": 0,\n" +
				"        \"monopoly\": 0,\n" +
				"        \"soldier\": 0,\n" +
				"        \"roadBuilding\": 0,\n" +
				"        \"monument\": 0\n" +
				"      },\n" +
				"      \"newDevCards\": {\n" +
				"        \"yearOfPlenty\": 0,\n" +
				"        \"monopoly\": 0,\n" +
				"        \"soldier\": 0,\n" +
				"        \"roadBuilding\": 0,\n" +
				"        \"monument\": 0\n" +
				"      },\n" +
				"      \"roads\": 13,\n" +
				"      \"cities\": 4,\n" +
				"      \"settlements\": 3,\n" +
				"      \"soldiers\": 0,\n" +
				"      \"victoryPoints\": 2,\n" +
				"      \"monuments\": 0,\n" +
				"      \"playedDevCard\": false,\n" +
				"      \"discarded\": false,\n" +
				"      \"playerID\": -3,\n" +
				"      \"playerIndex\": 2,\n" +
				"      \"name\": \"Steve\",\n" +
				"      \"color\": \"red\"\n" +
				"    },\n" +
				"    {\n" +
				"      \"resources\": {\n" +
				"        \"brick\": 0,\n" +
				"        \"wood\": 0,\n" +
				"        \"sheep\": 0,\n" +
				"        \"wheat\": 1,\n" +
				"        \"ore\": 1\n" +
				"      },\n" +
				"      \"oldDevCards\": {\n" +
				"        \"yearOfPlenty\": 0,\n" +
				"        \"monopoly\": 0,\n" +
				"        \"soldier\": 0,\n" +
				"        \"roadBuilding\": 0,\n" +
				"        \"monument\": 0\n" +
				"      },\n" +
				"      \"newDevCards\": {\n" +
				"        \"yearOfPlenty\": 0,\n" +
				"        \"monopoly\": 0,\n" +
				"        \"soldier\": 0,\n" +
				"        \"roadBuilding\": 0,\n" +
				"        \"monument\": 0\n" +
				"      },\n" +
				"      \"roads\": 13,\n" +
				"      \"cities\": 4,\n" +
				"      \"settlements\": 3,\n" +
				"      \"soldiers\": 0,\n" +
				"      \"victoryPoints\": 2,\n" +
				"      \"monuments\": 0,\n" +
				"      \"playedDevCard\": false,\n" +
				"      \"discarded\": false,\n" +
				"      \"playerID\": -4,\n" +
				"      \"playerIndex\": 3,\n" +
				"      \"name\": \"Scott\",\n" +
				"      \"color\": \"puce\"\n" +
				"    }\n" +
				"  ],\n" +
				"  \"log\": {\n" +
				"    \"lines\": [\n" +
				"      {\n" +
				"        \"source\": \"Pete\",\n" +
				"        \"message\": \"Pete built a road\"\n" +
				"      },\n" +
				"      {\n" +
				"        \"source\": \"Pete\",\n" +
				"        \"message\": \"Pete built a settlement\"\n" +
				"      },\n" +
				"      {\n" +
				"        \"source\": \"Pete\",\n" +
				"        \"message\": \"Pete's turn just ended\"\n" +
				"      },\n" +
				"      {\n" +
				"        \"source\": \"Miguel\",\n" +
				"        \"message\": \"Miguel built a road\"\n" +
				"      },\n" +
				"      {\n" +
				"        \"source\": \"Miguel\",\n" +
				"        \"message\": \"Miguel built a settlement\"\n" +
				"      },\n" +
				"      {\n" +
				"        \"source\": \"Miguel\",\n" +
				"        \"message\": \"Miguel's turn just ended\"\n" +
				"      },\n" +
				"      {\n" +
				"        \"source\": \"Steve\",\n" +
				"        \"message\": \"Steve built a road\"\n" +
				"      },\n" +
				"      {\n" +
				"        \"source\": \"Steve\",\n" +
				"        \"message\": \"Steve built a settlement\"\n" +
				"      },\n" +
				"      {\n" +
				"        \"source\": \"Steve\",\n" +
				"        \"message\": \"Steve's turn just ended\"\n" +
				"      },\n" +
				"      {\n" +
				"        \"source\": \"Scott\",\n" +
				"        \"message\": \"Scott built a road\"\n" +
				"      },\n" +
				"      {\n" +
				"        \"source\": \"Scott\",\n" +
				"        \"message\": \"Scott built a settlement\"\n" +
				"      },\n" +
				"      {\n" +
				"        \"source\": \"Scott\",\n" +
				"        \"message\": \"Scott's turn just ended\"\n" +
				"      },\n" +
				"      {\n" +
				"        \"source\": \"Scott\",\n" +
				"        \"message\": \"Scott built a road\"\n" +
				"      },\n" +
				"      {\n" +
				"        \"source\": \"Scott\",\n" +
				"        \"message\": \"Scott built a settlement\"\n" +
				"      },\n" +
				"      {\n" +
				"        \"source\": \"Scott\",\n" +
				"        \"message\": \"Scott's turn just ended\"\n" +
				"      },\n" +
				"      {\n" +
				"        \"source\": \"Steve\",\n" +
				"        \"message\": \"Steve built a road\"\n" +
				"      },\n" +
				"      {\n" +
				"        \"source\": \"Steve\",\n" +
				"        \"message\": \"Steve built a settlement\"\n" +
				"      },\n" +
				"      {\n" +
				"        \"source\": \"Steve\",\n" +
				"        \"message\": \"Steve's turn just ended\"\n" +
				"      },\n" +
				"      {\n" +
				"        \"source\": \"Miguel\",\n" +
				"        \"message\": \"Miguel built a road\"\n" +
				"      },\n" +
				"      {\n" +
				"        \"source\": \"Miguel\",\n" +
				"        \"message\": \"Miguel built a settlement\"\n" +
				"      },\n" +
				"      {\n" +
				"        \"source\": \"Miguel\",\n" +
				"        \"message\": \"Miguel's turn just ended\"\n" +
				"      }\n" +
				"    ]\n" +
				"  },\n" +
				"  \"chat\": {\n" +
				"    \"lines\": []\n" +
				"  },\n" +
				"  \"bank\": {\n" +
				"    \"brick\": 23,\n" +
				"    \"wood\": 23,\n" +
				"    \"sheep\": 24,\n" +
				"    \"wheat\": 22,\n" +
				"    \"ore\": 23\n" +
				"  },\n" +
				"  \"turnTracker\": {\n" +
				"    \"status\": \"SecondRound\",\n" +
				"    \"currentTurn\": 0,\n" +
				"    \"longestRoad\": -1,\n" +
				"    \"largestArmy\": -1\n" +
				"  },\n" +
				"  \"winner\": -1,\n" +
				"  \"version\": 21\n" +
				"}";
	}

}
