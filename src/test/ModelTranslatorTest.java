package test;

import client.communication.ModelPopulator;
import junit.framework.TestCase;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import server.communication.ModelTranslator;
import shared.communication.ResourceList;
import shared.communication.fromServer.game.CommunicationModel;
import shared.communication.fromServer.game.Hex;
import shared.communication.fromServer.game.Map;
import shared.communication.fromServer.game.VertexLocation;
import shared.definitions.HexType;
import shared.locations.VertexDirection;
import shared.model.Fascade;
import shared.model.Game;
import shared.model.map.GameMap;
import shared.model.map.TerrainHex;
import shared.model.messages.MessageLine;
import shared.model.messages.MessageList;
import shared.model.player.ResourceMultiSet;

/**
 * Michael Rhodes
 * CS 340
 * Section 2
 * Team 10
 */
public class ModelTranslatorTest extends TestCase {
    Fascade fascade;
    String strModel;
    Game model;
    ModelTranslator mt;

    @Before
    public void setUp()
    {
        this.fascade = new Fascade();
        this.model = new Game();
        this.strModel = getStrModel();
        this.mt = ModelTranslator.forTesting();

        JSONObject json = null;
        try {
            json = new JSONObject(strModel);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ModelPopulator.populateModel(json, fascade);
        model = fascade.getModel();
    }

    @Test
    public void testTranslateModel() throws Exception {
        CommunicationModel comModel = ModelTranslator.translateModel(model);
        assertNotNull(comModel);
    }

    @Test
    public void testTranslateDeck() throws Exception {
        shared.model.player.DevCardList modelList = model.getDevelopment_bank();
        shared.communication.fromServer.game.DevCardList result = mt.testTranslateDeck(model);
        assertEquals(modelList.getMonopoly(), result.getMonopoly());
        assertEquals(modelList.getMonument(), result.getMonument());
        assertEquals(modelList.getRoad_building(), result.getRoadBuilding());
        assertEquals(modelList.getSoldier(), result.getSoldier());
        assertEquals(modelList.getYear_of_plenty(), result.getYearOfPlenty());
    }

    @Test
    public void testTranslateBank() throws Exception {
        shared.model.player.ResourceMultiSet modelBank = model.getResource_bank();
        ResourceList result = mt.testTranslateBank(model);
        assertEquals(modelBank.getBrick(), result.getBrick());
        assertEquals(modelBank.getWheat(), result.getWheat());
        assertEquals(modelBank.getWood(), result.getWood());
        assertEquals(modelBank.getSheep(), result.getSheep());
        assertEquals(modelBank.getOre(), result.getOre());
    }

    @Test
    public void testTranslateChat() throws Exception {
        MessageList modelChat = model.getChat();
        MessageList result = mt.testTranslateChat(model);
        assertEquals(modelChat.getMessages().size(), result.getMessages().size());
        for (int i = 0; i < modelChat.getMessages().size(); i++) {
            assertEquals(modelChat.getMessages().get(i), result.getMessages().get(i));
        }
    }

    @Test
    public void testTranslateLog() throws Exception {
        MessageList modelLog = model.getLog();
        MessageList result = mt.testTranslateLog(model);
        assertEquals(modelLog.getMessages().size(), result.getMessages().size());
        for (int i = 0; i < modelLog.getMessages().size(); i++) {
            assertEquals(modelLog.getMessages().get(i), result.getMessages().get(i));
        }
    }

    @Test
    public void testTranslateMap() throws Exception {
        GameMap modelMap = model.getMap();
        Map result = mt.testTranslateMap(model);
        assertTrue(result.getCities().length == 0);
        assertTrue(result.getHexes().length == 19);
        assertEquals(result.getPorts().length, modelMap.getPorts().length);
        assertEquals(result.getRobber(), modelMap.getRobber().getLocation());
        assertEquals(result.getRoads().length, modelMap.getRoads().length);
        assertTrue(result.getSettlements().length == 8);
    }

    @Test
    public void testTranslatePlayers() throws Exception {
        shared.model.player.Player[] modelPlayers = model.getPlayers();
        shared.communication.fromServer.game.Player[] result = mt.testTranslatePlayers(model);
        assertEquals(modelPlayers.length, result.length);
    }

    @Test
    public void testTranslateTradeOffer() throws Exception {
        shared.model.player.TradeOffer modelOffer = model.getTrade_offer();
        shared.communication.fromServer.game.TradeOffer result = mt.testTranslateTradeOffer(model);
        assertNull(result);
    }

    @Test
    public void testTranslateTurnTracker() throws Exception {
        shared.model.states.TurnTracker modelTracker = model.getTurn_tracker();
        shared.communication.fromServer.game.TurnTracker result = mt.testTranslateTurnTracker(model);
        assertEquals(modelTracker.getActive_player(), result.getCurrentTurn());
        assertEquals(modelTracker.getLargest_army_player(), result.getLargestArmy());
        assertEquals(modelTracker.getLongest_road_player(), result.getLongestRoad());
        assertEquals(modelTracker.getState().getState().name(), result.getStatus());
    }

    @Test
    public void testTranslateHexes() throws Exception {
        TerrainHex[][] modelHexes = model.getHexes();
        Hex[] result = mt.testTranslateHexes(model.getMap());
        for (Hex hex : result) {
            assertTrue(hexHasMatch(modelHexes, hex));
        }
    }

    @Test
    public void testTranslatePorts() throws Exception {
        shared.model.ports.Port[] modelPorts = model.getMap().getPorts();
        shared.communication.fromServer.game.Port[] result = mt.testTranslatePorts(model.getMap());
        assertEquals(modelPorts.length, result.length);
        assertEquals(modelPorts[0].getRatio(), result[0].getRatio());
        assertEquals(modelPorts[0].getResource().name().toLowerCase(), result[0].getResource().toLowerCase());
        assertEquals(modelPorts[0].getLocation().getX(), result[0].getLocation().getX());
        assertEquals(modelPorts[0].getLocation().getY(), result[0].getLocation().getY());
    }

    @Test
    public void testTranslateRoads() throws Exception {
        shared.model.map.Road[] modelRoads = model.getMap().getRoads();
        shared.communication.fromServer.game.Road[] result = mt.testTranslateRoads(model.getMap());
        assertEquals(modelRoads.length, result.length);
        assertEquals(modelRoads[0].getOwnerIndex(), result[0].getOwner());
        assertEquals(modelRoads[0].getLocation().getLocation().getHexLoc().getX(), result[0].getLocation().getX());
        assertEquals(modelRoads[0].getLocation().getLocation().getHexLoc().getY(), result[0].getLocation().getY());
        switch (modelRoads[0].getLocation().getDirection().name().toLowerCase()) {
            case "north":
                assertEquals("n", result[0].getLocation().getDirection().toLowerCase());
                break;
            case "northeast":
                assertEquals("ne", result[0].getLocation().getDirection().toLowerCase());
                break;
            case "northwest":
                assertEquals("nw", result[0].getLocation().getDirection().toLowerCase());
                break;
            case "south":
                assertEquals("s", result[0].getLocation().getDirection().toLowerCase());
                break;
            case "southwest":
                assertEquals("sw", result[0].getLocation().getDirection().toLowerCase());
                break;
            case "southeast":
                assertEquals("se", result[0].getLocation().getDirection().toLowerCase());
                break;
        }
    }

    @Test
    public void testTranslateSettlements() throws Exception {
        shared.model.map.buildings.Building[] modelBuildings = model.getMap().getBuildings();
        shared.communication.fromServer.game.VertexObject[] result = mt.testTranslateSettlements(model.getMap());
        assertEquals(modelBuildings.length, result.length);
        assertEquals(modelBuildings[0].getOwner(), result[0].getOwner());
        assertEquals(modelBuildings[0].getLocation().getHexLoc().getX(), result[0].getLocation().getX());
        assertEquals(modelBuildings[0].getLocation().getHexLoc().getY(), result[0].getLocation().getY());
        switch (modelBuildings[0].getLocation().getDir().name().toLowerCase()) {
            case "northwest":
                assertEquals("nw", result[0].getLocation().getDirection().toLowerCase());
                break;
            case "west":
                assertEquals("w", result[0].getLocation().getDirection().toLowerCase());
                break;
            case "southwest":
                assertEquals("sw", result[0].getLocation().getDirection().toLowerCase());
                break;
            case "northeast":
                assertEquals("ne", result[0].getLocation().getDirection().toLowerCase());
                break;
            case "east":
                assertEquals("e", result[0].getLocation().getDirection().toLowerCase());
                break;
            case "southeast":
                assertEquals("se", result[0].getLocation().getDirection().toLowerCase());
                break;
        }

        model.getMap().addBuilding(new shared.model.map.buildings.City());
        modelBuildings = model.getMap().getBuildings();
        result = mt.testTranslateSettlements(model.getMap());
        assertEquals(modelBuildings.length - 1, result.length);
    }

    @Test
    public void testTranslateCities() throws Exception {
        shared.communication.fromServer.game.VertexObject[] result = mt.testTranslateCities(model.getMap());
        assertEquals(0, result.length);

        VertexLocation location = new VertexLocation(VertexDirection.East, 0, 0);
        model.getMap().addBuilding(new shared.model.map.buildings.City("blue", location, 0));
        result = mt.testTranslateCities(model.getMap());
        assertEquals(1, result.length);
    }

    @Test
    public void testTranslateRobber() throws Exception {
        shared.model.map.Robber modelRobber = model.getRobber();
        shared.communication.fromServer.game.Map result = mt.testTranslateMap(model);
        assertEquals(modelRobber.getLocation().getX(), result.getRobber().getX());
        assertEquals(modelRobber.getLocation().getY(), result.getRobber().getY());
    }

    @Test
    public void testTranslateDevCardList() throws Exception {
        shared.model.player.DevCardList modelDev = new shared.model.player.DevCardList();
        modelDev.setMonopoly(1);
        modelDev.setMonument(2);
        modelDev.setRoad_building(3);
        modelDev.setSoldier(4);
        modelDev.setYear_of_plenty(5);
        shared.communication.fromServer.game.DevCardList result = mt.testTranslateDevCardList(modelDev);
        assertEquals(1, result.getMonopoly());
        assertEquals(2, result.getMonument());
        assertEquals(3, result.getRoadBuilding());
        assertEquals(4, result.getSoldier());
        assertEquals(5, result.getYearOfPlenty());
    }

    @Test
    public void testTranslateResourceMultiSet() throws Exception {
        shared.model.player.ResourceMultiSet modelSet = new shared.model.player.ResourceMultiSet(1,2,3,4,5);
        ResourceList result = mt.testTranslateResourceMultiSet(modelSet);
        assertEquals(1, result.getBrick());
        assertEquals(2, result.getWheat());
        assertEquals(3, result.getOre());
        assertEquals(4, result.getWood());
        assertEquals(5, result.getSheep());
    }

    @Test
    public void testTranslateOffer() throws Exception {
        shared.model.player.TradeOffer testOffer = new shared.model.player.TradeOffer(0, 1);
        testOffer.setReciever_gives(new ResourceMultiSet(1,2,0,0,0));
        testOffer.setSender_gives(new ResourceMultiSet(0,0,0,2,1));
        ResourceList result = mt.testTranslateOffer(testOffer);
        assertEquals(result.getBrick(), 1);
        assertEquals(result.getWheat(), 2);
        assertEquals(result.getOre(), 0);
        assertEquals(result.getWood(), -2);
        assertEquals(result.getSheep(), -1);
    }

    private boolean hexHasMatch(TerrainHex[][] modelHexes, Hex result) {
        for (TerrainHex[] hexes : modelHexes) {
            for (TerrainHex hex : hexes) {
                if (hex.getType() == HexType.WATER) continue;
                if (hex.getType() == HexType.DESERT && result.getResource() == null) {
                    return true;
                } else if (hex.getType() == HexType.DESERT || result.getResource() == null) {
                    continue;
                }
                if (hex.getResource().name().toLowerCase().equals(result.getResource().toLowerCase())) {
                    if (hex.getLocation().getX() == result.getLocation().getX()) {
                        if (hex.getLocation().getY() == result.getLocation().getY()) {
                            return true;
                        }
                    }
                }
            }
        }
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