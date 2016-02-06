package test;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import client.communication.ClientCommunicator;
import client.communication.ModelPopulator;
import client.communication.ServerProxy;
import shared.communication.fromServer.games.Game;
import shared.communication.toServer.game.Commands;
import shared.definitions.CatanColor;
import shared.definitions.ResourceType;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.model.Fascade;
import shared.model.Player;

public class ServerProxyTest {

	private static ServerProxy sp;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Fascade f=new Fascade();
		JSONObject json;
		sp=new ServerProxy("localhost",8081,f);
		ClientCommunicator.getSingleton("localhost", "8081");
//		try 
//		{
//			 json = new JSONObject(SetupModel.model1);
//		} 
//		catch (JSONException e) 
//		{
//			e.printStackTrace();
//			return;
//		}
//		ModelPopulator.populateModel(json,f);
//		sp.register("bob", "bob");
	}
	@Ignore
	@Test
	public void testLogin() {
		String result=sp.login("bob", "bob");
		assertEquals (result,"200");
	}
	@Ignore
	@Test
	public void testRegister() {
		String result=sp.register("Greg", "greg");
		assertEquals("200",result);
	}
	@Ignore
	@Test
	public void testGetGameList() {
		List<Game> result=sp.getGameList();
		assert(!result.isEmpty());
	}
	@Ignore
	@Test
	public void testCreateandJoinGame() {
		Game test=sp.createGame("testGame", false, false, false);
		assert(test.getPlayers().length==0);
		assert(test.getTitle()=="testGame");
		assert(test.getId()!=0);
		String testy=sp.joinGame("Bob", 3, CatanColor.RED);
		
	}
	@Ignore
	@Test
	public void testJoinGame() {
		String test=sp.joinGame("Bob", 3, CatanColor.RED);
		assert(!test.isEmpty());
	}

	@Test
	public void testGetModel() {
		fail("Not yet implemented");
	}

	@Test
	public void testSendChat() {
		fail("Not yet implemented");
	}

	@Test
	public void testAcceptTrade() {
		fail("Not yet implemented");
	}

	@Test
	public void testRollNumber() {
		fail("Not yet implemented");
	}

	@Test
	public void testBuildRoad() {
		fail("Not yet implemented");
	}

	@Test
	public void testBuildSettlement() {
		fail("Not yet implemented");
	}

	@Test
	public void testBuildCity() {
		fail("Not yet implemented");
	}

	@Test
	public void testMaritimeTrade() {
		fail("Not yet implemented");
	}

	@Test
	public void testRobPlayer() {
		fail("Not yet implemented");
	}

	@Test
	public void testFinishTurn() {
		fail("Not yet implemented");
	}

	@Test
	public void testBuyDevCard() {
		fail("Not yet implemented");
	}

	@Test
	public void testPlaySoldier() {
		FileReader fr;
		StringBuilder sb=new StringBuilder();
		String line;
		try {
			fr = new FileReader("commands.txt");
			BufferedReader br=new BufferedReader(fr);
			while((line=br.readLine())!=null){
				sb.append(line);
			}
			sp.login("Pete", "pete");
			sp.joinGame("Pete", 0, CatanColor.RED);
			ClientCommunicator.getSINGLETON().doPost("/game/reset", null);
			ClientCommunicator.getSINGLETON().sendCommand("/game/commands", sb.toString());
			Player v=new Player();
			v.setPlayerIndex(2);
			String mon=sp.playSoldier(new HexLocation(-1,1), v);
			assert(!mon.isEmpty());
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void testYearOfPlenty() {
		FileReader fr;
		StringBuilder sb=new StringBuilder();
		String line;
		try {
			fr = new FileReader("commands.txt");
			BufferedReader br=new BufferedReader(fr);
			while((line=br.readLine())!=null){
				sb.append(line);
			}
			sp.login("Mark", "mark");
			sp.joinGame("Pete", 0, CatanColor.RED);
			ClientCommunicator.getSINGLETON().doPost("/game/reset", null);
			ClientCommunicator.getSINGLETON().sendCommand("/game/commands", sb.toString());
			String mon=sp.yearOfPlenty(ResourceType.WHEAT, ResourceType.ORE);
			assert(!mon.isEmpty());
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testRoadBuilding() {
		FileReader fr;
		StringBuilder sb=new StringBuilder();
		String line;
		try {
			fr = new FileReader("commands.txt");
			BufferedReader br=new BufferedReader(fr);
			while((line=br.readLine())!=null){
				sb.append(line);
			}
			sp.login("Pete", "pete");
			sp.joinGame("Pete", 0, CatanColor.RED);
			ClientCommunicator.getSINGLETON().doPost("/game/reset", null);
			ClientCommunicator.getSINGLETON().sendCommand("/game/commands", sb.toString());
			EdgeLocation one=new EdgeLocation(new HexLocation(0,0),EdgeDirection.SouthEast);
			EdgeLocation two=new EdgeLocation(new HexLocation(2,1),EdgeDirection.South);
			String mon=sp.RoadBuilding(one, two);
			assert(!mon.isEmpty());
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testMonopoly() {
		FileReader fr;
		StringBuilder sb=new StringBuilder();
		String line;
		try {
			fr = new FileReader("commands.txt");
			BufferedReader br=new BufferedReader(fr);
			while((line=br.readLine())!=null){
				sb.append(line);
			}
			sp.login("Mark", "mark");
			sp.joinGame("Pete", 0, CatanColor.RED);
			ClientCommunicator.getSINGLETON().doPost("/game/reset", null);
			ClientCommunicator.getSINGLETON().sendCommand("/game/commands", sb.toString());
			String mon=sp.monopoly(ResourceType.BRICK);
			assert(!mon.isEmpty());
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testMonument() {
		FileReader fr;
		StringBuilder sb=new StringBuilder();
		String line;
		try {
			fr = new FileReader("commands.txt");
			BufferedReader br=new BufferedReader(fr);
			while((line=br.readLine())!=null){
				sb.append(line);
			}
			sp.login("Pete", "pete");
			sp.joinGame("Pete", 0, CatanColor.RED);
			ClientCommunicator.getSINGLETON().doPost("/game/reset", null);
			ClientCommunicator.getSINGLETON().sendCommand("/game/commands", sb.toString());
			String mon=sp.monument();
			assert(!mon.isEmpty());
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testDiscardCards() {
		fail("Not yet implemented");
	}

	@Test
	public void testOfferTrade() {
		fail("Not yet implemented");
	}

}
