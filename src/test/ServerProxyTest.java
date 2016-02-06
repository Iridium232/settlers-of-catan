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
import shared.definitions.CatanColor;
import shared.model.Fascade;

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
	//	sp.register("bob", "bob");
	}
	
	@Test
	public void testLogin() {
		String result=sp.login("Pete", "pete");
		assertEquals (result,"200");
	}
	@Ignore
	@Test
	public void testRegister() {
		String result=sp.register("greg", "bob");
		System.out.println(result);
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
		
		fail("Not yet implemented");
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
		fail("Not yet implemented");
	}

	@Test
	public void testYearOfPlenty() {
		fail("Not yet implemented");
	}

	@Test
	public void testRoadBuilding() {
		fail("Not yet implemented");
	}

	@Test
	public void testMonopoly() {
		fail("Not yet implemented");
	}
	@Ignore
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
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			sp.login("Pete", "pete");
			sp.joinGame("pete", 0, CatanColor.RED);
			ClientCommunicator.getSINGLETON().doPost("/game/commands", sb.toString());
			System.out.print(sp.monument().toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fail("Not yet implemented");
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
