package test;

import static org.junit.Assert.*;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;

import client.communication.ClientCommunicator;
import client.communication.ModelPopulator;
import client.communication.ServerProxy;
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
		
	}

	@Test
	public void testLogin() {
		String result=sp.login("bob", "bob");
		System.out.println(result);
		assertTrue (result=="Success");
	}
	
	@Test
	public void testRegister() {
		String result=sp.login("doug", "bob");
		assertTrue(result=="Success");
	}

	@Test
	public void testGetGameList() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreateGame() {
		fail("Not yet implemented");
	}

	@Test
	public void testJoinGame() {
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
