package test;

import static org.junit.Assert.*;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import shared.definitions.TurnStatus;
import shared.model.DevCardList;
import shared.model.Fascade;
import shared.model.Game;
import shared.model.Player;
import shared.model.ResourceMultiSet;
import shared.model.TradeOffer;
import shared.model.TurnTracker;

import client.communication.MockServer;
import client.communication.ModelPopulator;

public class FascadeTest {

	MockServer server;
	Fascade facade;
	
	
	@Before
	public void setUp()
	{
		facade = new Fascade();
		this.setupModelForTesting();
		//TurnTracker Sets active player as player 0
		Game model = facade.getModel();
		
		TurnTracker tracker = model.getTurn_tracker();
		tracker.setActive_player(1);
		tracker.setWaiting_for_player(0);
		tracker.setStatus(TurnStatus.PLAYING);
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
		//this.setupModelForTesting();
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
		
		int active_player = 0;
		int inactive_player = 1;
		
		//Should be false because it is not this player's turn
		assertFalse(facade.canUseMonument(inactive_player));
		
		//Should be false because the player does not have this card
		assertFalse(facade.canUseMonument(active_player));
		
		//Give Active Player the monument card
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
		//this.setupModelForTesting();
		int active_player = 0;
		int inactive_player = 1;
		
		//Should be false because it is not this player's turn
		assertFalse(facade.canUseSoldier(inactive_player));
		
		//Should be false because the player does not have this card
		assertFalse(facade.canUseSoldier(active_player));
		
		//Give Active Player the soldier card
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
		//this.setupModelForTesting();
		int active_player = 0;
		int inactive_player = 1;
		
		//Should be false because it is not this player's turn
		assertFalse(facade.canUseYearOfPlenty(inactive_player));
		
		//Should be false because the player does not have this card
		assertFalse(facade.canUseYearOfPlenty(active_player));
		
		//Give Active Player the year of plenty card
		Game model = facade.getModel();
		Player player= model.getPlayers()[0];
		DevCardList cardlist = new DevCardList();
		cardlist.setYear_of_plenty(2);
		player.setOldDevCards(cardlist);
		int a = 4 + 3;
		
		//SHOULD be true because this simulates him already having 
		//the card and choosing to play it on his turn
		assertTrue(facade.canUseYearOfPlenty(active_player));
		
		//Simulate another dev card was already played
		player.setPlayedDevCard(true);
		
		//Should be false because the player already played on his turn.
		assertFalse(facade.canUseYearOfPlenty(active_player));
	}

	
	//13
	@Test
	public void testCanUseRoadBuilding() 
	{
		//this.setupModelForTesting();
		int active_player = 0;
		int inactive_player = 1;
		
		//Should be false because it is not this player's turn
		assertFalse(facade.canUseRoadBuilding(inactive_player));
		
		//Should be false because the player does not have this card
		assertFalse(facade.canUseRoadBuilding(active_player));
		
		//Give Active Player the road_building card
		Game model = facade.getModel();
		Player player= model.getPlayers()[0];
		DevCardList cardlist = new DevCardList();
		cardlist.setRoad_building(2);
		player.setOldDevCards(cardlist);
		
		
		//SHOULD be true because this simulates him already having 
		//the card and choosing to play it on his turn
		assertTrue(facade.canUseRoadBuilding(active_player));
		
		//Simulate another dev card was already played
		player.setPlayedDevCard(true);
		
		//Should be false because the player already played on his turn.
		assertFalse(facade.canUseRoadBuilding(active_player));
	}

	//14
	@Test
	public void testCanFinishTurn() 
	{
		//this.setupModelForTesting();
		int active_player = 0;
		int inactive_player = 1;
		Game model = facade.getModel();
		
		//Should be false because it is not this player's turn
		assertFalse(facade.canUseRoadBuilding(inactive_player));
		
		//Should be true becuase this active player is in the state where
		//they can end their turn
		assertTrue(facade.canUseRoadBuilding(active_player));
		
		//TurnTracker 
		TurnTracker tracker = model.getTurn_tracker();
		tracker.setActive_player(1);
		tracker.setWaiting_for_player(0);
		tracker.setStatus(TurnStatus.DISCARDING);
		
		//Should be false because we are waiting for player 1 to discard
		assertFalse(facade.canUseRoadBuilding(active_player));
	}
	
	
	//15
	@Test
	public void testCanSendMessage() 
	{
		//this.setupModelForTesting();
		int active_player = 0;
		int inactive_player = 1;
		
		for(int i = 0; i < 4; i++)
		{
		//Should return true because any player may send a message whenever
		assertTrue(facade.canSendMessage(i, "I am winning you all!"));
		}
		
		//Invalid players should not be allowed to send messages
		assertFalse(facade.canSendMessage(6, "I want to play too!"));
	}
	
	//16
	@Test
	public void testCanAcceptTrade() 
	{
		//this.setupModelForTesting();
		int active_player = 0;
		int inactive_player = 1;
		
		//Setup trade values
		Game model = facade.getModel();
		ResourceMultiSet offer_grain = new ResourceMultiSet();
		ResourceMultiSet offer_ore = new ResourceMultiSet();
		ResourceMultiSet want_trees = new ResourceMultiSet();
		ResourceMultiSet want_bricks = new ResourceMultiSet();
		offer_grain.setWheat(1);
		offer_ore.setOre(1);
		want_trees.setWood(1);
		want_bricks.setBrick(1);
		TradeOffer grain_for_trees = new TradeOffer();
		grain_for_trees.setOffer(offer_grain);
		grain_for_trees.setReciever(1);
		grain_for_trees.setSender(0);
		
		//Should be false because there is no trade offer out there
		assertFalse(facade.canAcceptTrade(inactive_player, want_trees, offer_grain));
		assertFalse(facade.canAcceptTrade(active_player, want_trees, offer_grain));
		assertFalse(facade.canAcceptTrade(2, want_trees, offer_grain));
		assertFalse(facade.canAcceptTrade(3, want_trees, offer_grain));
		
		//Active Player Offers trade of Grain for Trees
		model.setTrade_offer(grain_for_trees);
		
		//Should be true because the offer is out there to them
		assertTrue(facade.canAcceptTrade(inactive_player, want_trees, offer_grain));
		assertTrue(facade.canAcceptTrade(2, want_trees, offer_grain));
		assertTrue(facade.canAcceptTrade(3, want_trees, offer_grain));

		
		//Should be false because this person sent the trade request
		assertFalse(facade.canAcceptTrade(active_player, want_trees, offer_grain));
	}
	
	//17
	@Test
	public void testCanDiscardCards() 
	{
		//this.setupModelForTesting();
		int active_player = 0;
		int inactive_player = 1;
		
		//Nobody should be able to discard anything when there has
		//not been any seven rolled
		assertFalse(facade.canDiscardCards(active_player, new ResourceMultiSet()));
		assertFalse(facade.canDiscardCards(inactive_player, new ResourceMultiSet()));
		assertFalse(facade.canDiscardCards(2, new ResourceMultiSet()));
		assertFalse(facade.canDiscardCards(3, new ResourceMultiSet()));
		
		//Setup model where a 7 was just rolled so player 1 
		//with 8 resources must discard half of them
		Game model = facade.getModel();
		
		//TurnTracker knows we are waiting for Player 1 to discard
		TurnTracker tracker = model.getTurn_tracker();
		tracker.setActive_player(1);
		tracker.setWaiting_for_player(0);
		tracker.setStatus(TurnStatus.DISCARDING);
		
		//Player 1 has 4 brick and 4 ore
		Player unlucky_player = model.getPlayers()[1];
		ResourceMultiSet unlucky_player_resources = new ResourceMultiSet();
		unlucky_player_resources.setBrick(4);
		unlucky_player_resources.setOre(4);
		unlucky_player.setResources(unlucky_player_resources);
		
		//player 1 could discard this
		ResourceMultiSet valid_discard_list = new ResourceMultiSet();
		valid_discard_list.setBrick(2);
		valid_discard_list.setOre(2);
		
		//player 1 cannot discard these cards that they do not have
		ResourceMultiSet invalid_discard_list = new ResourceMultiSet();
		invalid_discard_list.setBrick(1);
		invalid_discard_list.setSheep(3);
		
		//Should be possible
		assertTrue(facade.canDiscardCards(1, valid_discard_list));
		
		//Should not work because the player cannot afford to discard these.
		//Player 1 has no sheep, you see.
		assertFalse(facade.canDiscardCards(1, invalid_discard_list));
		
		//Should not work becuase the players are not required to discard
		assertFalse(facade.canDiscardCards(0, new ResourceMultiSet()));
		assertFalse(facade.canDiscardCards(2, new ResourceMultiSet()));
		assertFalse(facade.canDiscardCards(3, new ResourceMultiSet()));
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
