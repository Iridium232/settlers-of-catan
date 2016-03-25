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
import shared.definitions.HexType;
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
    }

    @Test
    public void testTranslatePorts() throws Exception {

    }

    @Test
    public void testTranslateRoads() throws Exception {

    }

    @Test
    public void testTranslateSettlements() throws Exception {

    }

    @Test
    public void testTranslateCities() throws Exception {

    }

    @Test
    public void testTranslateRobber() throws Exception {

    }

    @Test
    public void testTranslateDevCardList() throws Exception {

    }

    @Test
    public void testTranslateResourceMultiSet() throws Exception {

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

    private boolean hexHasMatch(TerrainHex[][] modelHexes, Hex[] result) {
        boolean hasMatch = false;
        for (TerrainHex[] hexes : modelHexes) {
            for (TerrainHex hex : hexes) {
                if (hex.getType() == HexType.WATER) continue;
                for (Hex resultHex : result) {
                    if (hex.getResource().name().equals(resultHex.getResource())) {
                        if (hex.getLocation().getX() == resultHex.getLocation().getX()) {
                            if (hex.getLocation().getY() == resultHex.getLocation().getY()) {
                                hasMatch = true;
                            }
                        }
                    }
                }
            }
        }
        return hasMatch;
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