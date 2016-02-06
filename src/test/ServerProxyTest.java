package test;

import static org.junit.Assert.*;

import java.util.List;

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
		try 
		{
			 json = new JSONObject(SetupModel.model1);
		} 
		catch (JSONException e) 
		{
			e.printStackTrace();
			return;
		}
		ModelPopulator.populateModel(json,f);
		//sp.register("Pete", "pete");
	}
	
	@Test
	public void testLogin() {
		String result=sp.login("Pete", "pete");
		assertEquals (result,"OK");
	}
	
	@Test
	public void testRegister() {
		String result=sp.register("Brrrrr", "bddd");
		System.out.println(result);
		assertEquals("200",result);
	}

	@Test
	public void testGetGameList() {
		List<Game> result=sp.getGameList();
		assert(!result.isEmpty());
	}

	@Test
	public void testCreateGame() {
		Game test=sp.createGame("testGame", false, false, false);
		assert(test.getPlayers().length==0);
		assert(test.getTitle()=="testGame");
		assert(test.getId()!=0);
	}

	@Test
	public void testJoinGame() {
		String test=sp.joinGame("Bob", 3, CatanColor.RED);
		
		fail("Not yet implemented");
	}

	@Test
	public void testSaveGame() {
		fail("Not yet implemented");
	}

	@Test
	public void testLoadGame() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetModel() {
		fail("Not yet implemented");
	}

	@Test
	public void testReset() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCommands() {
		fail("Not yet implemented");
	}

	@Test
	public void testExecuteCommands() {
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

	@Test
	public void testMonument() {
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
