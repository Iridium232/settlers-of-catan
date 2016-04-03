package test;

import client.communication.ModelPopulator;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import server.commands.AcceptTradeCommand;
import server.facade.ServerFacade;
import shared.communication.ResourceList;
import shared.communication.toServer.moves.AcceptTrade;
import shared.communication.toServer.moves.Monopoly_;
import shared.locations.EdgeDirection;
import shared.locations.VertexDirection;
import shared.model.Fascade;
import shared.model.Game;
import shared.model.map.Vertex;
import shared.model.map.buildings.Building;
import shared.model.map.buildings.City;
import shared.model.map.buildings.Settlement;
import shared.model.player.ResourceMultiSet;

import static org.junit.Assert.*;

/**
 * Michael Rhodes
 * CS 340
 * Section 2
 * Team 10
 */
public class CommandsTest {
    Fascade gameFascade;
    ServerFacade serverFacade;
    String strModel;
    Game model;

    @Before
    public void setUp() {
        this.gameFascade = new Fascade();
        this.serverFacade = new ServerFacade();
        this.model = new Game();
        this.strModel = getStrModel();

        JSONObject json = null;
        try {
            json = new JSONObject(strModel);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ModelPopulator.populateModel(json, gameFascade);
        model = gameFascade.getModel();
        serverFacade.forTestingSet(gameFascade);
    }

    @Test
    public void testOfferTradeCommand() {
        shared.communication.toServer.moves.OfferTrade tradeOffer =
                new shared.communication.toServer.moves.OfferTrade(0,new ResourceList(1,-1,0,0,0), 1);
        server.commands.OfferTrade command = new server.commands.OfferTrade(serverFacade);
        command.setParams(tradeOffer);
        command.execute();
        assertNotNull(model.getTrade_offer());
    }

    @Test
    public void testAcceptTradeCommand() {
        int senderOldBrick = model.getPlayers()[1].getResources().getBrick();
        int senderOldOre = model.getPlayers()[1].getResources().getOre();
        int receiverOldBrick = model.getPlayers()[0].getResources().getBrick();
        int receiverOldOre = model.getPlayers()[0].getResources().getOre();
        gameFascade.offerTrade(0, new ResourceList(1,-1,0,0,0), 0);
        gameFascade.getModel().getTrade_offer().setReciever(1);
        gameFascade.getModel().getTrade_offer().setSender(0);


        serverFacade.forTestingSet(gameFascade);
        AcceptTrade at = new AcceptTrade(1, true);
        AcceptTradeCommand command = new AcceptTradeCommand(serverFacade);
        command.setParams(at);
        command.execute();
        model = serverFacade.forTestingGet().getModel();

        int senderNewBrick = model.getPlayers()[1].getResources().getBrick();
        int senderNewOre = model.getPlayers()[1].getResources().getOre();
        int receiverNewBrick = model.getPlayers()[0].getResources().getBrick();
        int receiverNewOre = model.getPlayers()[0].getResources().getOre();

        assertEquals(senderOldBrick + 1, senderNewBrick);
        assertEquals(senderOldOre - 1, senderNewOre);
        assertEquals(receiverOldBrick -1, receiverNewBrick);
        assertEquals(receiverOldOre + 1, receiverNewOre);
    }

    @Test
    public void testBuildCityCommand() {
        shared.model.player.Player player = serverFacade.forTestingGet().getModel().getPlayers()[0];
        int brickBefore = player.getResources().getBrick();
        int woodBefore = player.getResources().getWood();
        int wheatBefore = player.getResources().getWheat();
        int sheepBefore = player.getResources().getSheep();
        int oreBefore = player.getResources().getOre();

        shared.communication.fromServer.game.VertexLocation location =
                new shared.communication.fromServer.game.VertexLocation(VertexDirection.SouthWest, -1, 1);
        shared.communication.toServer.moves.BuildCity arg =
                new shared.communication.toServer.moves.BuildCity(0, location);
        server.commands.BuildCity command = new server.commands.BuildCity(serverFacade);
        command.setParams(arg);
        command.execute();

        shared.locations.VertexLocation location2 = new shared.locations.VertexLocation(location);
        Building building = serverFacade.forTestingGet().getModel().getMap().getBuildingOnVertex(new Vertex(location2));
        assertEquals(City.class, building.getClass());

        int brickAfter = player.getResources().getBrick();
        int woodAfter = player.getResources().getWood();
        int wheatAfter = player.getResources().getWheat();
        int sheepAfter = player.getResources().getSheep();
        int oreAfter = player.getResources().getOre();
        assertEquals(brickBefore, brickAfter);
        assertEquals(woodBefore, woodAfter);
        assertEquals(wheatBefore - 2, wheatAfter);
        assertEquals(sheepBefore, sheepAfter);
        assertEquals(oreBefore - 3, oreAfter);
    }

    @Test
    public void testBuildRoadCommand() {
        shared.model.player.Player player = serverFacade.forTestingGet().getModel().getPlayers()[0];
        int brickBefore = player.getResources().getBrick();
        int woodBefore = player.getResources().getWood();
        int wheatBefore = player.getResources().getWheat();
        int sheepBefore = player.getResources().getSheep();
        int oreBefore = player.getResources().getOre();

        shared.communication.EdgeLocation location = new shared.communication.EdgeLocation(-1, 1, EdgeDirection.North);
        shared.communication.toServer.moves.BuildRoad arg =
                new shared.communication.toServer.moves.BuildRoad(0, location, true);
        server.commands.BuildRoad command = new server.commands.BuildRoad(serverFacade);
        command.setParams(arg);
        command.execute();

        boolean roadExists = roadExists(arg, serverFacade.forTestingGet().getModel().getRoads());
        assertTrue(roadExists);

        int brickAfter = player.getResources().getBrick();
        int woodAfter = player.getResources().getWood();
        int wheatAfter = player.getResources().getWheat();
        int sheepAfter = player.getResources().getSheep();
        int oreAfter = player.getResources().getOre();
        assertEquals(brickBefore, brickAfter);
        assertEquals(woodBefore, woodAfter);
        assertEquals(wheatBefore, wheatAfter);
        assertEquals(sheepBefore, sheepAfter);
        assertEquals(oreBefore, oreAfter);

        location = new shared.communication.EdgeLocation(-1, 1, EdgeDirection.South);
        arg = new shared.communication.toServer.moves.BuildRoad(0, location, false);
        command = new server.commands.BuildRoad(serverFacade);
        command.setParams(arg);
        command.execute();

        roadExists = roadExists(arg, serverFacade.forTestingGet().getModel().getRoads());
        assertTrue(roadExists);

        brickAfter = player.getResources().getBrick();
        woodAfter = player.getResources().getWood();
        wheatAfter = player.getResources().getWheat();
        sheepAfter = player.getResources().getSheep();
        oreAfter = player.getResources().getOre();
        assertEquals(brickBefore - 1, brickAfter);
        assertEquals(woodBefore - 1, woodAfter);
        assertEquals(wheatBefore, wheatAfter);
        assertEquals(sheepBefore, sheepAfter);
        assertEquals(oreBefore, oreAfter);
    }

    @Test
    public void testBuildSettlementCommand() {
        shared.model.player.Player player = serverFacade.forTestingGet().getModel().getPlayers()[0];
        int brickBefore = player.getResources().getBrick();
        int woodBefore = player.getResources().getWood();
        int wheatBefore = player.getResources().getWheat();
        int sheepBefore = player.getResources().getSheep();
        int oreBefore = player.getResources().getOre();

        shared.communication.fromServer.game.VertexLocation location =
                new shared.communication.fromServer.game.VertexLocation(VertexDirection.SouthWest, -1, 1);
        shared.communication.toServer.moves.BuildSettlement arg =
                new shared.communication.toServer.moves.BuildSettlement(0, location, true);
        server.commands.BuildSettlement command = new server.commands.BuildSettlement(serverFacade);
        command.setParams(arg);
        command.execute();

        shared.locations.VertexLocation location2 = new shared.locations.VertexLocation(location);
        Building building = serverFacade.forTestingGet().getModel().getMap().getBuildingOnVertex(new Vertex(location2));
        assertEquals(Settlement.class, building.getClass());

        int brickAfter = player.getResources().getBrick();
        int woodAfter = player.getResources().getWood();
        int wheatAfter = player.getResources().getWheat();
        int sheepAfter = player.getResources().getSheep();
        int oreAfter = player.getResources().getOre();
        assertEquals(brickBefore, brickAfter);
        assertEquals(woodBefore, woodAfter);
        assertEquals(wheatBefore, wheatAfter);
        assertEquals(sheepBefore, sheepAfter);
        assertEquals(oreBefore, oreAfter);

        location = new shared.communication.fromServer.game.VertexLocation(VertexDirection.SouthEast, -1, 1);
        arg = new shared.communication.toServer.moves.BuildSettlement(0, location, false);
        command = new server.commands.BuildSettlement(serverFacade);
        command.setParams(arg);
        command.execute();

        location2 = new shared.locations.VertexLocation(location);
        building = serverFacade.forTestingGet().getModel().getMap().getBuildingOnVertex(new Vertex(location2));
        assertEquals(Settlement.class, building.getClass());

        brickAfter = player.getResources().getBrick();
        woodAfter = player.getResources().getWood();
        wheatAfter = player.getResources().getWheat();
        sheepAfter = player.getResources().getSheep();
        oreAfter = player.getResources().getOre();
        assertEquals(brickBefore - 1, brickAfter);
        assertEquals(woodBefore - 1, woodAfter);
        assertEquals(wheatBefore - 1, wheatAfter);
        assertEquals(sheepBefore - 1, sheepAfter);
        assertEquals(oreBefore, oreAfter);
    }

    @Test
    public void testBuyDevCardCommand() {
        shared.model.player.Player player = serverFacade.forTestingGet().getModel().getPlayers()[0];
        int devCardsBefore = player.getNewDevCards().getTotalCards();
        shared.communication.toServer.moves.BuyDevCard arg = new shared.communication.toServer.moves.BuyDevCard(0);
        server.commands.BuyDevCard command = new server.commands.BuyDevCard(serverFacade);
        command.setParams(arg);
        command.execute();

        int devCardsAfter = player.getNewDevCards().getTotalCards();
        assertEquals(devCardsBefore + 1, devCardsAfter);
    }

    @Test
    public void testDiscardCardsCommand() {
        shared.model.player.Player player0 = serverFacade.forTestingGet().getModel().getPlayers()[0];
        shared.model.player.Player player1 = serverFacade.forTestingGet().getModel().getPlayers()[1];
        shared.model.player.Player player2 = serverFacade.forTestingGet().getModel().getPlayers()[2];
        player0.setResources(new ResourceMultiSet(10, 10, 10, 10, 10));
        player2.setResources(new ResourceMultiSet(1, 1, 1, 1, 5));

        int player0ResourcesBefore = getTotalResources(player0);
        int player1ResourcesBefore = getTotalResources(player1);
        int player2ResourcesBefore = getTotalResources(player2);


        ResourceList discarding = new ResourceList(5,5,5,5,5);
        shared.communication.toServer.moves.DiscardCards arg =
                new shared.communication.toServer.moves.DiscardCards(0, discarding);
        server.commands.DiscardCards command = new server.commands.DiscardCards(serverFacade);
        command.setParams(arg);
        command.execute();

        int player0ResourcesAfter = getTotalResources(player0);
        assertEquals(player0ResourcesBefore / 2, player0ResourcesAfter);
        assertTrue(player0.isDiscarded());
        assertFalse(player1.isDiscarded());


        assertFalse(player1.isDiscarded());
        discarding = new ResourceList(0,0,0,0,0);
        arg = new shared.communication.toServer.moves.DiscardCards(1, discarding);
        command = new server.commands.DiscardCards(serverFacade);
        command.setParams(arg);
        command.execute();

        int player1ResourcesAfter = getTotalResources(player1);
        assertEquals(player1ResourcesBefore, player1ResourcesAfter);
        assertTrue(player1.isDiscarded());


        discarding = new ResourceList(0,0,0,0,4);
        arg = new shared.communication.toServer.moves.DiscardCards(2, discarding);
        command = new server.commands.DiscardCards(serverFacade);
        command.setParams(arg);
        command.execute();

        int player2ResourcesAfter = getTotalResources(player2);
        assertEquals(player2ResourcesBefore - 4, player2ResourcesAfter);
        assertFalse(player0.isDiscarded());
        assertFalse(player1.isDiscarded());
        assertFalse(player2.isDiscarded());
    }

    @Test
    public void testRollNumberCommand() {
        shared.model.player.Player player0 = serverFacade.forTestingGet().getModel().getPlayers()[0];
        shared.model.player.Player player1 = serverFacade.forTestingGet().getModel().getPlayers()[1];
        shared.model.player.Player player2 = serverFacade.forTestingGet().getModel().getPlayers()[2];
        shared.model.player.Player player3 = serverFacade.forTestingGet().getModel().getPlayers()[3];

        int player0BrickBefore = player0.getResources().getBrick();
        int player0WheatBefore = player0.getResources().getWheat();
        int player0WoodBefore = player0.getResources().getWood();
        int player0SheepBefore = player0.getResources().getSheep();
        int player0OreBefore = player0.getResources().getOre();

        int player1BrickBefore = player1.getResources().getBrick();
        int player1WheatBefore = player1.getResources().getWheat();
        int player1WoodBefore = player1.getResources().getWood();
        int player1SheepBefore = player1.getResources().getSheep();
        int player1OreBefore = player1.getResources().getOre();

        int player2BrickBefore = player2.getResources().getBrick();
        int player2WheatBefore = player2.getResources().getWheat();
        int player2WoodBefore = player2.getResources().getWood();
        int player2SheepBefore = player2.getResources().getSheep();
        int player2OreBefore = player2.getResources().getOre();

        int player3BrickBefore = player3.getResources().getBrick();
        int player3WheatBefore = player3.getResources().getWheat();
        int player3WoodBefore = player3.getResources().getWood();
        int player3SheepBefore = player3.getResources().getSheep();
        int player3OreBefore = player3.getResources().getOre();

        server.commands.RollNumber rollCommand = new server.commands.RollNumber(serverFacade);
        rollCommand.setParams(new shared.communication.toServer.moves.RollNumber(0, 6));
        rollCommand.execute();

        int player0BrickAfter = player0.getResources().getBrick();
        int player0WheatAfter = player0.getResources().getWheat();
        int player0WoodAfter = player0.getResources().getWood();
        int player0SheepAfter = player0.getResources().getSheep();
        int player0OreAfter = player0.getResources().getOre();

        int player1BrickAfter = player1.getResources().getBrick();
        int player1WheatAfter = player1.getResources().getWheat();
        int player1WoodAfter = player1.getResources().getWood();
        int player1SheepAfter = player1.getResources().getSheep();
        int player1OreAfter = player1.getResources().getOre();

        int player2BrickAfter = player2.getResources().getBrick();
        int player2WheatAfter = player2.getResources().getWheat();
        int player2WoodAfter = player2.getResources().getWood();
        int player2SheepAfter = player2.getResources().getSheep();
        int player2OreAfter = player2.getResources().getOre();

        int player3BrickAfter = player3.getResources().getBrick();
        int player3WheatAfter = player3.getResources().getWheat();
        int player3WoodAfter = player3.getResources().getWood();
        int player3SheepAfter = player3.getResources().getSheep();
        int player3OreAfter = player3.getResources().getOre();

        assertEquals(player0BrickBefore, player0BrickAfter);
        assertEquals(player0WheatBefore + 1, player0WheatAfter);
        assertEquals(player0WoodBefore, player0WoodAfter);
        assertEquals(player0SheepBefore, player0SheepAfter);
        assertEquals(player0OreBefore, player0OreAfter);

        assertEquals(player1BrickBefore, player1BrickAfter);
        assertEquals(player1WheatBefore, player1WheatAfter);
        assertEquals(player1WoodBefore + 1, player1WoodAfter);
        assertEquals(player1SheepBefore, player1SheepAfter);
        assertEquals(player1OreBefore, player1OreAfter);

        assertEquals(player2BrickBefore, player2BrickAfter);
        assertEquals(player2WheatBefore, player2WheatAfter);
        assertEquals(player2WoodBefore, player2WoodAfter);
        assertEquals(player2SheepBefore, player2SheepAfter);
        assertEquals(player2OreBefore, player2OreAfter);

        assertEquals(player3BrickBefore, player3BrickAfter);
        assertEquals(player3WheatBefore, player3WheatAfter);
        assertEquals(player3WoodBefore + 1, player3WoodAfter);
        assertEquals(player3SheepBefore, player3SheepAfter);
        assertEquals(player3OreBefore, player3OreAfter);
    }

    @Test
    public void testFinishTurnCommand() {
        server.commands.RollNumber rollCommand = new server.commands.RollNumber(serverFacade);
        rollCommand.setParams(new shared.communication.toServer.moves.RollNumber(0, 6));
        rollCommand.execute();

        shared.communication.toServer.moves.FinishTurn arg = new shared.communication.toServer.moves.FinishTurn(0);
        server.commands.FinishTurn command = new server.commands.FinishTurn(serverFacade);
        command.setParams(arg);
        command.execute();

        assertEquals(1, serverFacade.forTestingGet().getModel().getTurn_tracker().getActive_player());
    }

    @Test
    public void testMaritimeTradeCommand() {
        shared.model.player.Player player = serverFacade.forTestingGet().getModel().getPlayers()[0];
        int brickBefore = player.getResources().getBrick();
        int wheatBefore = player.getResources().getWheat();
        int woodBefore = player.getResources().getWood();
        int sheepBefore = player.getResources().getSheep();
        int oreBefore = player.getResources().getOre();

        shared.communication.toServer.moves.MaritimeTrade arg =
                new shared.communication.toServer.moves.MaritimeTrade(0, 4, "brick", "wheat");
        server.commands.MaritimeTrade command = new server.commands.MaritimeTrade(serverFacade);
        command.setParams(arg);
        command.execute();

        int brickAfter = player.getResources().getBrick();
        int wheatAfter = player.getResources().getWheat();
        int woodAfter = player.getResources().getWood();
        int sheepAfter = player.getResources().getSheep();
        int oreAfter = player.getResources().getOre();

        assertEquals(brickBefore - 4, brickAfter);
        assertEquals(wheatBefore + 1, wheatAfter);
        assertEquals(woodBefore, woodAfter);
        assertEquals(sheepBefore, sheepAfter);
        assertEquals(oreBefore, oreAfter);


        brickBefore = player.getResources().getBrick();
        wheatBefore = player.getResources().getWheat();
        woodBefore = player.getResources().getWood();
        sheepBefore = player.getResources().getSheep();
        oreBefore = player.getResources().getOre();

        arg = new shared.communication.toServer.moves.MaritimeTrade(0, 3, "wood", "sheep");
        command = new server.commands.MaritimeTrade(serverFacade);
        command.setParams(arg);
        command.execute();

        brickAfter = player.getResources().getBrick();
        wheatAfter = player.getResources().getWheat();
        woodAfter = player.getResources().getWood();
        sheepAfter = player.getResources().getSheep();
        oreAfter = player.getResources().getOre();

        assertEquals(brickBefore, brickAfter);
        assertEquals(wheatBefore, wheatAfter);
        assertEquals(woodBefore - 3, woodAfter);
        assertEquals(sheepBefore + 1, sheepAfter);
        assertEquals(oreBefore, oreAfter);


        brickBefore = player.getResources().getBrick();
        wheatBefore = player.getResources().getWheat();
        woodBefore = player.getResources().getWood();
        sheepBefore = player.getResources().getSheep();
        oreBefore = player.getResources().getOre();

        arg = new shared.communication.toServer.moves.MaritimeTrade(0, 2, "ore", "brick");
        command = new server.commands.MaritimeTrade(serverFacade);
        command.setParams(arg);
        command.execute();

        brickAfter = player.getResources().getBrick();
        wheatAfter = player.getResources().getWheat();
        woodAfter = player.getResources().getWood();
        sheepAfter = player.getResources().getSheep();
        oreAfter = player.getResources().getOre();

        assertEquals(brickBefore + 1, brickAfter);
        assertEquals(wheatBefore, wheatAfter);
        assertEquals(woodBefore, woodAfter);
        assertEquals(sheepBefore, sheepAfter);
        assertEquals(oreBefore - 2, oreAfter);
    }

    @Test
    public void testMonopolyCommand() {
        shared.model.player.Player player = serverFacade.forTestingGet().getModel().getPlayers()[0];
        player.getNewDevCards().setMonopoly(1);
        serverFacade.forTestingGet().getModel().getPlayers()[1].getResources().setBrick(3);
        serverFacade.forTestingGet().getModel().getPlayers()[2].getResources().setBrick(3);
        serverFacade.forTestingGet().getModel().getPlayers()[3].getResources().setBrick(3);

        int brickBefore = player.getResources().getBrick();
        int oldMonopolyBefore = player.getOldDevCards().getMonopoly();
        int newMonopolyBefore = player.getNewDevCards().getMonopoly();


        Monopoly_ arg = new Monopoly_(0, "brick");
        server.commands.Monopoly command = new server.commands.Monopoly(serverFacade);
        command.setParams(arg);
        command.execute();

        int brickAfter = player.getResources().getBrick();
        int oldMonopolyAfter = player.getOldDevCards().getMonopoly();
        int newMonopolyAfter = player.getNewDevCards().getMonopoly();
        assertTrue(player.isPlayedDevCard());
        assertEquals(oldMonopolyBefore + 1, oldMonopolyAfter);
        assertEquals(newMonopolyBefore - 1, newMonopolyAfter);

        assertEquals(brickBefore + 9, brickAfter);
        shared.model.player.Player player1 = serverFacade.forTestingGet().getModel().getPlayers()[1];
        shared.model.player.Player player2 = serverFacade.forTestingGet().getModel().getPlayers()[2];
        shared.model.player.Player player3 = serverFacade.forTestingGet().getModel().getPlayers()[3];
        assertEquals(0, player1.getResources().getBrick());
        assertEquals(0, player2.getResources().getBrick());
        assertEquals(0, player3.getResources().getBrick());
    }

    @Test
    public void testMonumentCommand() {
        assertTrue(true);
    }

    @Test
    public void testRoad_BuildingCommand() {
        assertTrue(true);
    }

    @Test
    public void testRobPlayerCommand() {
        assertTrue(true);
    }

    @Test
    public void testSendChatCommand() {
        assertTrue(true);
    }

    @Test
    public void testSoldierCommand() {
        assertTrue(true);
    }

    @Test
    public void testYear_Of_PlentyCommand() {
        assertTrue(true);
    }

    private boolean roadExists(shared.communication.toServer.moves.BuildRoad arg, shared.model.map.Road[] roads) {
        for (shared.model.map.Road road : roads) {
            if (road.getLocation().getLocation().getHexLoc().getX() == arg.getRoadLocation().getX()) {
                if (road.getLocation().getLocation().getHexLoc().getY() == arg.getRoadLocation().getY()) {
                    String str1 = road.getLocation().getLocation().getDir().name();
                    String str2 = arg.getRoadLocation().getDirection();
                    if (matchDirection(str1, str2)) {
                        if (road.getOwnerIndex() == arg.getPlayerIndex()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean matchDirection(String str1, String str2) {
        if (str1.toLowerCase().equals(str2.toLowerCase())) return true;
        if (str1.toLowerCase().equals("northwest") && str2.toLowerCase().equals("nw")) return true;
        if (str1.toLowerCase().equals("north") && str2.toLowerCase().equals("n")) return true;
        if (str1.toLowerCase().equals("northeast") && str2.toLowerCase().equals("ne")) return true;
        if (str1.toLowerCase().equals("southwest") && str2.toLowerCase().equals("sw")) return true;
        if (str1.toLowerCase().equals("south") && str2.toLowerCase().equals("s")) return true;
        if (str1.toLowerCase().equals("southeast") && str2.toLowerCase().equals("se")) return true;
        return false;
    }

    private int getTotalResources(shared.model.player.Player player) {
        int total = 0;
        ResourceMultiSet resources = player.getResources();
        total += resources.getBrick();
        total += resources.getWheat();
        total += resources.getWood();
        total += resources.getSheep();
        total += resources.getOre();
        return total;
    }

    private String getStrModel() {
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
                "        \"owner\": 1,\n" +
                "        \"location\": {\n" +
                "          \"direction\": \"S\",\n" +
                "          \"x\": -1,\n" +
                "          \"y\": -1\n" +
                "        }\n" +
                "      },\n" +
                "      {\n" +
                "        \"owner\": 3,\n" +
                "        \"location\": {\n" +
                "          \"direction\": \"SW\",\n" +
                "          \"x\": -1,\n" +
                "          \"y\": 1\n" +
                "        }\n" +
                "      },\n" +
                "      {\n" +
                "        \"owner\": 3,\n" +
                "        \"location\": {\n" +
                "          \"direction\": \"SW\",\n" +
                "          \"x\": 2,\n" +
                "          \"y\": -2\n" +
                "        }\n" +
                "      },\n" +
                "      {\n" +
                "        \"owner\": 2,\n" +
                "        \"location\": {\n" +
                "          \"direction\": \"S\",\n" +
                "          \"x\": 1,\n" +
                "          \"y\": -1\n" +
                "        }\n" +
                "      },\n" +
                "      {\n" +
                "        \"owner\": 0,\n" +
                "        \"location\": {\n" +
                "          \"direction\": \"S\",\n" +
                "          \"x\": 0,\n" +
                "          \"y\": 1\n" +
                "        }\n" +
                "      },\n" +
                "      {\n" +
                "        \"owner\": 2,\n" +
                "        \"location\": {\n" +
                "          \"direction\": \"S\",\n" +
                "          \"x\": 0,\n" +
                "          \"y\": 0\n" +
                "        }\n" +
                "      },\n" +
                "      {\n" +
                "        \"owner\": 1,\n" +
                "        \"location\": {\n" +
                "          \"direction\": \"SW\",\n" +
                "          \"x\": -2,\n" +
                "          \"y\": 1\n" +
                "        }\n" +
                "      },\n" +
                "      {\n" +
                "        \"owner\": 0,\n" +
                "        \"location\": {\n" +
                "          \"direction\": \"SW\",\n" +
                "          \"x\": 2,\n" +
                "          \"y\": 0\n" +
                "        }\n" +
                "      }\n" +
                "    ],\n" +
                "    \"cities\": [],\n" +
                "    \"settlements\": [\n" +
                "      {\n" +
                "        \"owner\": 3,\n" +
                "        \"location\": {\n" +
                "          \"direction\": \"SW\",\n" +
                "          \"x\": -1,\n" +
                "          \"y\": 1\n" +
                "        }\n" +
                "      },\n" +
                "      {\n" +
                "        \"owner\": 3,\n" +
                "        \"location\": {\n" +
                "          \"direction\": \"SE\",\n" +
                "          \"x\": 1,\n" +
                "          \"y\": -2\n" +
                "        }\n" +
                "      },\n" +
                "      {\n" +
                "        \"owner\": 2,\n" +
                "        \"location\": {\n" +
                "          \"direction\": \"SW\",\n" +
                "          \"x\": 0,\n" +
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
                "          \"direction\": \"SW\",\n" +
                "          \"x\": -2,\n" +
                "          \"y\": 1\n" +
                "        }\n" +
                "      },\n" +
                "      {\n" +
                "        \"owner\": 0,\n" +
                "        \"location\": {\n" +
                "          \"direction\": \"SE\",\n" +
                "          \"x\": 0,\n" +
                "          \"y\": 1\n" +
                "        }\n" +
                "      },\n" +
                "      {\n" +
                "        \"owner\": 1,\n" +
                "        \"location\": {\n" +
                "          \"direction\": \"SW\",\n" +
                "          \"x\": -1,\n" +
                "          \"y\": -1\n" +
                "        }\n" +
                "      },\n" +
                "      {\n" +
                "        \"owner\": 0,\n" +
                "        \"location\": {\n" +
                "          \"direction\": \"SW\",\n" +
                "          \"x\": 2,\n" +
                "          \"y\": 0\n" +
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
                "        \"wood\": 1,\n" +
                "        \"sheep\": 1,\n" +
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
                "      \"playerID\": 0,\n" +
                "      \"playerIndex\": 0,\n" +
                "      \"name\": \"Sam\",\n" +
                "      \"color\": \"orange\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"resources\": {\n" +
                "        \"brick\": 1,\n" +
                "        \"wood\": 0,\n" +
                "        \"sheep\": 1,\n" +
                "        \"wheat\": 0,\n" +
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
                "      \"playerID\": 1,\n" +
                "      \"playerIndex\": 1,\n" +
                "      \"name\": \"Brooke\",\n" +
                "      \"color\": \"blue\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"resources\": {\n" +
                "        \"brick\": 0,\n" +
                "        \"wood\": 1,\n" +
                "        \"sheep\": 1,\n" +
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
                "      \"playerID\": 10,\n" +
                "      \"playerIndex\": 2,\n" +
                "      \"name\": \"Pete\",\n" +
                "      \"color\": \"red\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"resources\": {\n" +
                "        \"brick\": 0,\n" +
                "        \"wood\": 1,\n" +
                "        \"sheep\": 1,\n" +
                "        \"wheat\": 0,\n" +
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
                "      \"playerID\": 11,\n" +
                "      \"playerIndex\": 3,\n" +
                "      \"name\": \"Mark\",\n" +
                "      \"color\": \"green\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"log\": {\n" +
                "    \"lines\": [\n" +
                "      {\n" +
                "        \"source\": \"Sam\",\n" +
                "        \"message\": \"Sam built a road\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"source\": \"Sam\",\n" +
                "        \"message\": \"Sam built a settlement\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"source\": \"Sam\",\n" +
                "        \"message\": \"Sam's turn just ended\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"source\": \"Brooke\",\n" +
                "        \"message\": \"Brooke built a road\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"source\": \"Brooke\",\n" +
                "        \"message\": \"Brooke built a settlement\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"source\": \"Brooke\",\n" +
                "        \"message\": \"Brooke's turn just ended\"\n" +
                "      },\n" +
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
                "        \"source\": \"Mark\",\n" +
                "        \"message\": \"Mark built a road\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"source\": \"Mark\",\n" +
                "        \"message\": \"Mark built a settlement\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"source\": \"Mark\",\n" +
                "        \"message\": \"Mark's turn just ended\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"source\": \"Mark\",\n" +
                "        \"message\": \"Mark built a road\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"source\": \"Mark\",\n" +
                "        \"message\": \"Mark built a settlement\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"source\": \"Mark\",\n" +
                "        \"message\": \"Mark's turn just ended\"\n" +
                "      },\n" +
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
                "        \"source\": \"Brooke\",\n" +
                "        \"message\": \"Brooke built a road\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"source\": \"Brooke\",\n" +
                "        \"message\": \"Brooke built a settlement\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"source\": \"Brooke\",\n" +
                "        \"message\": \"Brooke's turn just ended\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"source\": \"Sam\",\n" +
                "        \"message\": \"Sam built a road\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"source\": \"Sam\",\n" +
                "        \"message\": \"Sam built a settlement\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"source\": \"Sam\",\n" +
                "        \"message\": \"Sam's turn just ended\"\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  \"chat\": {\n" +
                "    \"lines\": []\n" +
                "  },\n" +
                "  \"bank\": {\n" +
                "    \"brick\": 23,\n" +
                "    \"wood\": 21,\n" +
                "    \"sheep\": 20,\n" +
                "    \"wheat\": 22,\n" +
                "    \"ore\": 22\n" +
                "  },\n" +
                "  \"turnTracker\": {\n" +
                "    \"status\": \"Rolling\",\n" +
                "    \"currentTurn\": 0,\n" +
                "    \"longestRoad\": -1,\n" +
                "    \"largestArmy\": -1\n" +
                "  },\n" +
                "  \"winner\": -1,\n" +
                "  \"version\": 0\n" +
                "}";
    }

}
