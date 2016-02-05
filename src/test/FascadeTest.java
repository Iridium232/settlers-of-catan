package test;

import static org.junit.Assert.*;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import shared.definitions.TurnStatus;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;
import shared.model.DevCardList;
import shared.model.Edge;
import shared.model.Fascade;
import shared.model.Game;
import shared.model.GameMap;
import shared.model.Player;
import shared.model.ResourceMultiSet;
import shared.model.Road;
import shared.model.Settlement;
import shared.model.TerrainHex;
import shared.model.TradeOffer;
import shared.model.TurnTracker;
import shared.model.Vertex;
import shared.model.ports.Port;
import shared.model.ports.WheatPort;

import client.communication.MockServer;
import client.communication.ModelPopulator;

public class FascadeTest {

	MockServer server;
	Fascade facade;
	
	Edge desertSE;
	Edge desertNE;
	Edge desertN;
	Edge otherHexN;
	Edge desertNW;
	TerrainHex desert;
	TerrainHex byTheSea;
	
	
	@Before
	public void setUp()
	{
		facade = new Fascade();
		this.setupModelForTesting();
		//TurnTracker Sets active player as player 0
		Game model = facade.getModel();
		
		TurnTracker tracker = model.getTurn_tracker();
		tracker.setActive_player(0);
		tracker.setWaiting_for_player(-1);
		tracker.setStatus(TurnStatus.PLAYING);
		
		prepareModelForBuildingTests();
	}
	
	
	
	
	
	@Test
	public void testCanBuildRoad() 
	{
		int active_player = 0;
		int inactive_player = 1;
		Game model = facade.getModel();
		Player builder = model.getPlayers()[active_player];
		Player other_guy = model.getPlayers()[inactive_player];

		
		
		//Should be false because it is not his turn
		assertFalse(facade.canBuildRoad(inactive_player, this.desertN));
		
		//Should be false because he cannot afford it
		assertFalse(facade.canBuildRoad(active_player, this.desertN));
		
		//set up the player's resource list
		ResourceMultiSet resources = new ResourceMultiSet();
		resources.setBrick(2);
		resources.setOre(1);
		resources.setWood(1);
		builder.setResources(resources);
		
		//Should be false because he has no adjoining pieces
		assertFalse(facade.canBuildRoad(active_player, desertNW));
		
		//Should be true because he can afford it, it is his turn, and there are adjoining pieces
		assertTrue(facade.canBuildRoad(active_player, desertN));
		
		//Should be false because there is already a road there
		assertFalse(facade.canBuildRoad(active_player, desertNE));
		
	}

	@Test
	public void testCanBuyDevelopmentCard() 
	{
		int active_player = 0;
		int inactive_player = 1;
		Game model = facade.getModel();
		Player builder = model.getPlayers()[active_player];
		
		//Should be false because it is not his turn
		assertFalse(facade.canBuyDevelopmentCard(inactive_player));
		
		//Should be false because he cannot afford it
		assertFalse(facade.canBuyDevelopmentCard(active_player));
		
		//set up the player's resource list
		ResourceMultiSet resources = new ResourceMultiSet();
		resources.setBrick(2);
		resources.setOre(3);
		resources.setWood(1);
		resources.setWheat(3);
		resources.setSheep(1);
		builder.setResources(resources);
		
		//Should be true because it is his turn and he can afford it
		assertTrue(facade.canBuyDevelopmentCard(active_player));
	}

	@Test
	public void testCanBuildSettlement() 
	{
		int active_player = 0;
		int inactive_player = 1;
		Game model = facade.getModel();
		Player builder = model.getPlayers()[active_player];

		
		
		//Should be false because it is not his turn
		assertFalse(facade.canBuildSettlement(inactive_player, desertSE.getEnd1()));
		
		//Should be false because he cannot afford it
		assertFalse(facade.canBuildSettlement(active_player, desertSE.getEnd1()));
		
		//set up the player's resource list
		ResourceMultiSet resources = new ResourceMultiSet();
		resources.setBrick(2);
		resources.setOre(1);
		resources.setWood(1);
		resources.setWheat(1);
		resources.setSheep(1);
		builder.setResources(resources);
		
		//Should be false because he has no adjoining pieces
		assertFalse(facade.canBuildSettlement(active_player, desertN.getEnd2()));
		
		//Should be true because he can afford it, it is his turn, and there are adjoining pieces
		assertTrue(facade.canBuildSettlement(active_player, desertSE.getEnd1()));
		
		//Should be false because it is too close to other settlements
		assertFalse(facade.canBuildSettlement(active_player, desertSE.getEnd2()));
	}

	@Test
	public void testCanBuildCity() 
	{
		int active_player = 0;
		int inactive_player = 1;
		Game model = facade.getModel();
		Player builder = model.getPlayers()[active_player];

		
		//Should be false because it is not his turn
		assertFalse(facade.canBuildCity(inactive_player, desertSE.getEnd1()));
		
		//Should be false because he cannot afford it
		assertFalse(facade.canBuildCity(active_player, desertN.getEnd1()));
		
		//set up the player's resource list
		ResourceMultiSet resources = new ResourceMultiSet();
		resources.setBrick(2);
		resources.setOre(3);
		resources.setWood(1);
		resources.setWheat(3);
		resources.setSheep(1);
		builder.setResources(resources);
		
		//Should be true because he can afford it, it is his turn, and he has a settlement there
		assertTrue(facade.canBuildCity(active_player, desertN.getEnd1()));
		
		//Should be false because there is no settlement there
		assertFalse(facade.canBuildCity(active_player, desertSE.getEnd2()));
	}

	@Test
	public void testCanRollDice() 
	{
		int active_player = 0;
		int inactive_player = 1;
		Game model = facade.getModel();
		
		//Should be false because it is not this player's turn
		assertFalse(facade.canRollDice(inactive_player));

		//TurnTracker 
		TurnTracker tracker = model.getTurn_tracker();
		tracker.setActive_player(0);
		tracker.setStatus(TurnStatus.ROLLING);
		
		
		//Should be true because this active player is in the state where
		//they can roll the dice
		assertTrue(facade.canRollDice(active_player));
		
		//Should be false because we are waiting for player 0 to roll
		assertFalse(facade.canRollDice(inactive_player));
		
		
	}

	@Test
	public void testCanPlaceRobber() 
	{
		int active_player = 0;
		int inactive_player = 1;
		Game model = facade.getModel();
		
		//Should be false because it is not this player's turn
		assertFalse(facade.canPlaceRobber(desert.getLocation(), inactive_player));

		//TurnTracker 
		TurnTracker tracker = model.getTurn_tracker();
		tracker.setActive_player(0);
		tracker.setStatus(TurnStatus.MOVEROBBER);
		
		
		//Should be true because this active player is in the state where
		//they can move the robber and rob
		assertTrue(facade.canPlaceRobber(desert.getLocation(), active_player));
		
		//Should be false because we are waiting for player 0 to rob
		assertFalse(facade.canPlaceRobber(desert.getLocation(), inactive_player));
	}

	@Test
	public void testCanOfferTrade() 
	{
		int active_player = 0;
		int inactive_player = 1;
		Game model = facade.getModel();
		ResourceMultiSet grain = new ResourceMultiSet();
		grain.setWheat(1);
		ResourceMultiSet brick = new ResourceMultiSet();
		grain.setBrick(1);
		Player active = model.getPlayers()[active_player];
		Player inactive = model.getPlayers()[inactive_player];
		active.setResources(grain);
		inactive.setResources(brick);
		
		//Should be false because it is not this player's turn
		assertFalse(facade.canOfferTrade(inactive_player, brick, grain ));
		
		//TurnTracker 
		TurnTracker tracker = model.getTurn_tracker();
		tracker.setActive_player(0);
		tracker.setWaiting_for_player(-1);
		tracker.setStatus(TurnStatus.TRADING);
		
		//Should be true becuase this active player is in the state where
		//they can offer a trade
		assertTrue(facade.canOfferTrade(active_player, grain, brick));
		

		
		//Should be false because we are waiting for player 1 to discard
		assertFalse(facade.canOfferTrade(inactive_player, grain, brick ));
	}

	@Test
	public void testCanMaritimeTrade() 
	{
		int active_player = 0;
		int inactive_player = 1;
		Game model = facade.getModel();
		
		//Should be false because this player has no port connection
		assertFalse(facade.canMaritimeTrade(0));
		
		//Should be false because this player has a port connection
		assertTrue(facade.canMaritimeTrade(1));
		
	}
	
	//9
	@Test
	public void testCanUseMonopoly()
	{
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
		Player player = model.getPlayers()[0];
		DevCardList cardlist = new DevCardList();
		cardlist.setYear_of_plenty(2);
		player.setOldDevCards(cardlist);
		
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
		int active_player = 0;
		int inactive_player = 1;
		Game model = facade.getModel();
		
		//Should be false because it is not this player's turn
		assertFalse(facade.canFinishTurn(inactive_player));
		
		//Should be true becuase this active player is in the state where
		//they can end their turn
		assertTrue(facade.canFinishTurn(active_player));
		
		//TurnTracker 
		TurnTracker tracker = model.getTurn_tracker();
		tracker.setActive_player(1);
		tracker.setWaiting_for_player(0);
		tracker.setStatus(TurnStatus.DISCARDING);
		
		//Should be false because we are waiting for player 1 to discard
		assertFalse(facade.canFinishTurn(active_player));
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
		grain_for_trees.setSender_gives(offer_grain);
		grain_for_trees.setReciever_gives(want_trees);
		grain_for_trees.setReciever(1);
		grain_for_trees.setSender(0);
		
		//Should be false because there is no trade offer out there
		assertFalse(facade.canAcceptTrade(inactive_player));
		assertFalse(facade.canAcceptTrade(active_player));
		assertFalse(facade.canAcceptTrade(2));
		assertFalse(facade.canAcceptTrade(3));
		
		//Active Player Offers trade of Grain for Trees
		model.setTrade_offer(grain_for_trees);
		
		//Should be true because the offer is out there to them
		assertTrue(facade.canAcceptTrade(inactive_player));
		
		//Should be false because the offer is not to them
		assertFalse(facade.canAcceptTrade(2));
		assertFalse(facade.canAcceptTrade(3));

		
		//Should be false because this person sent the trade request
		assertFalse(facade.canAcceptTrade(active_player));
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
	
	/** \           /           \  ||      /      ~    \ 
	 *   \_________S(0)          \_||     /         ~   \_________ 
	 *   /         \             / ||     \     ~  ~    /         \
	 *  /           \R(0)       /  ||    ~ \  ~    ~   /  ~ WHEAT  \
	 * /     (0,0)   \_________/   ||   ~   \_________/S(1)*PORT ~  \__
	 * \    Desert   /    R(1) \   ||      ~/         \* * * *      /~
	 *  \           /R(0)       \  ||    ~ /           \* * * ~    /
	 *   \_________/             \_||    _/             \*________/ ~
	 *   /         \    (1,0)    / ||     \    (0,2)    /         \
	 *  /           \           /  ||      \           /           \   ~
	 * /             \_________/   ||       \_________/             \__
	 * 
	 * S(0) Settlement of Player 0
	 * R(0) Road of Player 0
	 * R(1) Road of Player 1
	 * 
	 * prepares the model for build tests. Does not use the model populator so
	 * that these unit tests can be independent
	 * @pre none
	 * @post the model is ready as depicted above
	 *
	 */
	private void prepareModelForBuildingTests()
	{
		//Get the model and map and the hexes of interest
		Game model = facade.getModel();
		GameMap map = model.getMap();
		int x = 0;
		int y = 0;
		TerrainHex desertHex = map.getHexAt(x, y);
		TerrainHex otherHex = map.getHexAt(x+1, y);
		TerrainHex hexByTheSea = map.getHexAt(x, y+2);
		desert = desertHex;
		byTheSea = hexByTheSea;
		
		//Initialize edges
		desertNE = new Edge();
		desertNE.setDirection(EdgeDirection.NorthEast);
		desertNE.setEnd1(new Vertex(new VertexLocation(desertHex.getLocation(), VertexDirection.NorthEast)));
		desertNE.setEnd2(new Vertex(new VertexLocation(desertHex.getLocation(), VertexDirection.East)));
		desertNE.setLocation(new EdgeLocation(desertHex.getLocation(), EdgeDirection.NorthEast));
		
		desertSE = new Edge();
		desertSE.setDirection(EdgeDirection.SouthEast);
		desertSE.setEnd1(new Vertex(new VertexLocation(desertHex.getLocation(), VertexDirection.SouthEast)));
		desertSE.setEnd2(new Vertex(new VertexLocation(desertHex.getLocation(), VertexDirection.East)));
		desertSE.setLocation(new EdgeLocation(desertHex.getLocation(), EdgeDirection.SouthEast));
		
		desertN = new Edge();
		desertN.setDirection(EdgeDirection.North);
		desertN.setEnd1(new Vertex(new VertexLocation(desertHex.getLocation(), VertexDirection.NorthEast)));
		desertN.setEnd2(new Vertex(new VertexLocation(desertHex.getLocation(), VertexDirection.NorthWest)));
		desertN.setLocation(new EdgeLocation(desertHex.getLocation(), EdgeDirection.North));
		
		otherHexN = new Edge();
		desertSE.setDirection(EdgeDirection.North);
		desertSE.setEnd1(new Vertex(new VertexLocation(otherHex.getLocation(), VertexDirection.NorthEast)));
		desertSE.setEnd2(new Vertex(new VertexLocation(otherHex.getLocation(), VertexDirection.NorthWest)));
		desertSE.setLocation(new EdgeLocation(otherHex.getLocation(), EdgeDirection.North));
		
		desertNW = new Edge();
		desertNW.setDirection(EdgeDirection.NorthWest);
		desertNW.setEnd1(new Vertex(new VertexLocation(desertHex.getLocation(), VertexDirection.NorthWest)));
		desertNW.setEnd2(new Vertex(new VertexLocation(desertHex.getLocation(), VertexDirection.West)));
		desertNW.setLocation(new EdgeLocation(desertHex.getLocation(), EdgeDirection.NorthWest));
		
		otherHexN = new Edge();
		otherHexN.setDirection(EdgeDirection.NorthEast);
		otherHexN.setEnd1();
		otherHexN.setEnd2();
		otherHexN.
		
		//Add roads
		Road road1 = new Road();
		road1.setOwnerIndex(0);
		road1.setLocation(desertNE);
		
		Road road2 = new Road();
		road2.setOwnerIndex(0);
		road2.setLocation(desertSE);
		
		Road road3 = new Road();
		road3.setOwnerIndex(1);
		road3.setLocation(otherHexN);
		
		try
		{
			map.addRoad(road1);
			map.addRoad(road2);
			map.addRoad(road3);
		}
		catch(Exception e)
		{
			
		}
		
		//Add Ports and Settlements
		Vertex desertNEvertex = new Vertex(new VertexLocation(desertHex.getLocation(), VertexDirection.NorthEast));
		Vertex wheatPortVertex = new Vertex(new VertexLocation(hexByTheSea.getLocation(), VertexDirection.NorthEast));
		
		Port wheatPort = new WheatPort();
		wheatPort.setLocation(new HexLocation(1,3));
		wheatPort.setVertex1(wheatPortVertex);
		wheatPort.setVertex2(new Vertex(new VertexLocation(hexByTheSea.getLocation(), VertexDirection.East)));
		
		map.addPort(wheatPort);
		
		Settlement desertSettlement = new Settlement();
		desertSettlement.setLocation(desertNEvertex.getLocation());
		desertSettlement.setOwner(0);
		
		
		Settlement seaSettlement = new Settlement();
		seaSettlement.setLocation(wheatPortVertex.getLocation());
		seaSettlement.setOwner(1);
		
		try
		{
			map.addBuilding(desertSettlement);
			map.addBuilding(seaSettlement);
		}
		catch(Exception e)
		{
			
		}
	}
	
}
