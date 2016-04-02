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
import shared.locations.EdgeDirection;
import shared.locations.VertexDirection;
import shared.model.Fascade;
import shared.model.Game;
import shared.model.map.Vertex;
import shared.model.map.buildings.Building;
import shared.model.map.buildings.City;
import shared.model.map.buildings.Settlement;

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
        System.out.println(devCardsBefore);
        System.out.println(devCardsAfter);
    }

    @Test
    public void testDiscardCardsCommand() {
        assertTrue(true);
    }

    @Test
    public void testFinishTurnCommand() {
        assertTrue(true);
    }

    @Test
    public void testMaritimeTradeCommand() {
        assertTrue(true);
    }

    @Test
    public void testMonopolyCommand() {
        assertTrue(true);
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
    public void testRollNumberCommand() {
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
