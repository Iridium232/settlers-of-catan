package test;

import static org.junit.Assert.*;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import shared.model.DevCardList;
import shared.model.Fascade;
import shared.model.Game;
import shared.model.Player;

import client.communication.MockServer;
import client.communication.ModelPopulator;

public class FascadeTest {

	MockServer server;
	Fascade facade;
	
	
	@Before
	public void setUp()
	{
		facade = new Fascade();
	}
	
	@Test
	public void testCanBuildRoad() 
	{
		fail("Not yet implemented");
	}

	@Test
	public void testCanBuyDevelopmentCard() 
	{
		fail("Not yet implemented");
	}

	@Test
	public void testCanBuildSettlement() 
	{
		fail("Not yet implemented");
	}

	@Test
	public void testCanBuildCity() {
		fail("Not yet implemented");
	}

	@Test
	public void testCanRollDice() {
		fail("Not yet implemented");
	}

	@Test
	public void testCanPlaceRobber() {
		fail("Not yet implemented");
	}

	@Test
	public void testCanOfferTrade() {
		fail("Not yet implemented");
	}

	@Test
	public void testCanMaritimeTrade() {
		fail("Not yet implemented");
	}

//***********************************************************	
	//9
	@Test
	public void testCanUseMonopoly()
	{
		this.setupModelForTesting();
		int active_player = 0;
		int inactive_player = 1;
		
		//Should be false because it is not this player's turn
		assertFalse(facade.canUseMonopoly(inactive_player));
		
		//Should be false because the player does not have this card
		assertFalse(facade.canUseMonopoly(active_player));
		
		//Give Active Player the monopoly card
		Game model = facade.getModel();
		Player player= model.getPlayers()[0];
		DevCardList cardlist = new DevCardList();
		cardlist.setMonopoly(2);
		player.setOldDevCards(cardlist);
		
		
		//SHOULD be true because this simulates him already having 
		//the card and choosing to play it on his turn
		assertTrue(facade.canUseMonopoly(active_player));
		
		//Simulate another dev card was already played
		player.setPlayedDevCard(true);
		
		//Should be false because the player already played on his turn.
		assertFalse(facade.canUseMonument(active_player));
	}
	
	
	//10
	@Test
	public void testCanUseMonument() 
	{
		this.setupModelForTesting();
		int active_player = 0;
		int inactive_player = 1;
		
		//Should be false because it is not this player's turn
		assertFalse(facade.canUseMonument(inactive_player));
		
		//Should be false because the player does not have this card
		assertFalse(facade.canUseMonument(active_player));
		
		//Give Active Player the monopoly card
		Game model = facade.getModel();
		Player player= model.getPlayers()[0];
		DevCardList cardlist = new DevCardList();
		cardlist.setMonument(2);
		player.setOldDevCards(cardlist);
		
		
		//SHOULD be true because this simulates him already having 
		//the card and choosing to play it on his turn
		assertTrue(facade.canUseMonument(active_player));
		
		//Simulate another dev card was already played
		player.setPlayedDevCard(true);
		
		//Should be false because the player already played on his turn.
		assertFalse(facade.canUseMonument(active_player));
		
	}

	//11
	@Test
	public void testCanUseSoldier() 
	{
		this.setupModelForTesting();
		int active_player = 0;
		int inactive_player = 1;
		
		//Should be false because it is not this player's turn
		assertFalse(facade.canUseSoldier(inactive_player));
		
		//Should be false because the player does not have this card
		assertFalse(facade.canUseSoldier(active_player));
		
		//Give Active Player the monopoly card
		Game model = facade.getModel();
		Player player= model.getPlayers()[0];
		DevCardList cardlist = new DevCardList();
		cardlist.setSoldier(2);
		player.setOldDevCards(cardlist);
		
		
		//SHOULD be true because this simulates him already having 
		//the card and choosing to play it on his turn
		assertTrue(facade.canUseSoldier(active_player));
		
		//Simulate another dev card was already played
		player.setPlayedDevCard(true);
		
		//Should be false because the player already played on his turn.
		assertFalse(facade.canUseSoldier(active_player));
	}

	//12
	@Test
	public void testCanUseYearOfPlenty() 
	{
		fail("Not yet implemented");
	}

	
	//13
	@Test
	public void testCanUseRoadBuilding() 
	{
		fail("Not yet implemented");
	}

	//14
	@Test
	public void testCanFinishTurn() 
	{
		fail("Not yet implemented");
	}
	
	
	//15
	@Test
	public void testCanSendMessage() 
	{
		fail("Not yet implemented");
	}
	
	//16
	@Test
	public void testCanAcceptTrade() 
	{
		fail("Not yet implemented");
	}
	
	//17
	@Test
	public void testCanDiscardCards() 
	{
		fail("Not yet implemented");
	}
	
	/**
	 * Sets up the model for testing functions 9-17
	 * 
	 * @pre none
	 * @post the model is ready for testing
	 */
	private void setupModelForTesting()
	{
		JSONObject json;
		try 
		{
			 json = new JSONObject(SetupModel.model1);
		} 
		catch (JSONException e) 
		{
			e.printStackTrace();
			return;
		}
		ModelPopulator.populateModel(json,facade);
	}
	
}
