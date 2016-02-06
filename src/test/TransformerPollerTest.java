package test;

import client.communication.MockServer;
import client.communication.ModelPopulator;
import client.communication.ServerPoller;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import shared.definitions.CatanColor;
import shared.definitions.HexType;
import shared.definitions.ResourceType;
import shared.definitions.TurnStatus;
import shared.locations.EdgeDirection;
import shared.locations.VertexDirection;
import shared.model.*;
import shared.model.ports.*;

/**
 * Michael Rhodes
 * CS 340
 * Section 2
 * Team 10
 */
public class TransformerPollerTest {
    MockServer mockServer;
    Fascade fascade;
    String strModel;
    Game model;

    @Before
    public void setUp()
    {
        this.fascade = new Fascade();
        this.model = new Game();
        this.strModel = getStrModel();
        this.fascade.changeModel(this.model);

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
    public void testNotNull() {
        assertNotNull(model);
        assertNotNull(model.getDevelopment_bank());
        assertNotNull(model.getMap());
        assertNotNull(model.getPlayers());
        assertNotNull(model.getLog());
        assertNotNull(model.getResource_bank());
        assertNotNull(model.getTurn_tracker());
    }

    @Test
    public void testDevCardBank() {
        assertEquals(5, model.getDevelopment_bank().getMonument());
        assertEquals(2, model.getDevelopment_bank().getMonopoly());
        assertEquals(2, model.getDevelopment_bank().getRoad_building());
        assertEquals(14, model.getDevelopment_bank().getSoldier());
        assertEquals(2, model.getDevelopment_bank().getYear_of_plenty());
    }

    @Test
    public void testHexes() {
        //Desert Piece
        assertEquals(HexType.DESERT, model.getMap().getHexAt(0, -2).getType());
        assertEquals(0, model.getMap().getHexAt(0, -2).getNumber().getValue());

        //Other pieces
        assertEquals(HexType.WHEAT, model.getMap().getHexAt(2, 0).getType());
        assertEquals(3, model.getMap().getHexAt(-1, 2).getNumber().getValue());
        assertEquals(HexType.SHEEP, model.getMap().getHexAt(-1, 0).getType());
        assertEquals(9, model.getMap().getHexAt(-1, 1).getNumber().getValue());

    }

    @Test
    public void testRoads() {
        assertEquals(CatanColor.BLUE, model.getMap().getRoads()[0].getColor());
        assertEquals(EdgeDirection.SouthWest, model.getMap().getRoads()[1].getLocation().getDirection());
        assertEquals(CatanColor.GREEN, model.getMap().getRoads()[2].getColor());
        assertEquals(EdgeDirection.South, model.getMap().getRoads()[3].getLocation().getDirection());
    }

    @Test
    public void testCities() {
        boolean hasCity = false;
        for (Building bldg : model.getMap().getBuildings()) {
            if (bldg.getClass() == City.class) {
                hasCity = true;
            }
        }
        assertFalse(hasCity);
    }

    @Test
    public void testSettlements() {
        int count = 0;
        for (Building bldg : model.getMap().getBuildings()) {
            if (bldg.getClass() == Settlement.class) {
                count++;
            }
        }
        assertEquals(8, count);
        assertEquals(CatanColor.GREEN, model.getMap().getBuildings()[0].getColor());
        assertEquals(VertexDirection.SouthEast, model.getMap().getBuildings()[1].getLocation().getDir());
        assertEquals(0, model.getMap().getBuildings()[2].getLocation().getHexLoc().getX());
        assertEquals(0, model.getMap().getBuildings()[2].getLocation().getHexLoc().getY());
    }

    @Test
    public void testPorts() {
        int count = 0;
        for (Port port : model.getMap().getPorts()) {
            if (port.getClass() == MiscPort.class) count++;
            if (port.getClass() == BrickPort.class) count++;
            if (port.getClass() == WoodPort.class) count++;
            if (port.getClass() == SheepPort.class) count++;
            if (port.getClass() == OrePort.class) count++;
            if (port.getClass() == WheatPort.class) count++;
        }
        assertEquals(9, count);
        assertEquals(VertexDirection.NorthWest, model.getMap().getPorts()[0].getVertex1().getLocation().getDir());
        assertEquals(VertexDirection.West, model.getMap().getPorts()[0].getVertex2().getLocation().getDir());
        assertEquals(ResourceType.ORE, model.getMap().getPorts()[1].getResource());
        assertEquals(2, model.getMap().getPorts()[2].getRatio());
        assertEquals(-1, model.getMap().getPorts()[3].getLocation().getX());
        assertEquals(-2, model.getMap().getPorts()[3].getLocation().getY());
        assertEquals(ResourceType.MISC, model.getMap().getPorts()[8].getResource());
    }

    @Test
    public void testRobber() {
        assertEquals(0, model.getMap().getRobber().getLocation().getX());
        assertEquals(-2, model.getMap().getRobber().getLocation().getY());
    }

    @Test
    public void testPlayers() {
        int count = 0;
        for (Player player : model.getPlayers()) {
            if (player != null) count++;
        }
        assertEquals(4, count);
        assertEquals(0, model.getPlayers()[0].getResources().getBrick());
        assertEquals(1, model.getPlayers()[0].getResources().getSheep());
        assertEquals(0, model.getPlayers()[0].getOldDevCards().getSoldier());
        assertEquals(0, model.getPlayers()[0].getNewDevCards().getYear_of_plenty());
        assertEquals(13, model.getPlayers()[1].getRoads());
        assertEquals(3, model.getPlayers()[1].getSettlements());
        assertEquals(1, model.getPlayers()[1].getPlayerID());
        assertEquals(11, model.getPlayers()[3].getPlayerID());
        assertFalse(model.getPlayers()[2].isDiscarded());
        assertEquals("Pete", model.getPlayers()[2].getName());
        assertEquals("red", model.getPlayers()[2].getColor());
    }

    @Test
    public void testLog() {
        assertEquals("Sam", model.getLog().getMessages().get(0).getSource());
        assertNotNull(model.getLog().getMessages().get(0).getMessage());
    }

    @Test
    public void testResourceBank() {
        assertEquals(23, model.getResource_bank().getBrick());
        assertEquals(21, model.getResource_bank().getWood());
        assertEquals(20, model.getResource_bank().getSheep());
        assertEquals(22, model.getResource_bank().getWheat());
        assertEquals(22, model.getResource_bank().getOre());
    }

    @Test
    public void testTurnTracker() {
        assertEquals(TurnStatus.ROLLING, model.getTurn_tracker().getStatus());
        assertEquals(0, model.getTurn_tracker().getActive_player());
    }

    @Test
    public void testOthers() {
        assertEquals(-1, model.getWinner());
        assertEquals(0, model.getVersion());
        assertEquals(0, model.getChat().getMessages().size());
    }

    @Test
    public void testPollerMatchingVersion() throws InterruptedException {
        mockServer = new MockServer();
        mockServer.ServerProxy("localHost", 8081, fascade);
        ServerPoller.getServerPoller();
        ServerPoller.setFascade(fascade);
        ServerPoller.setServer(mockServer);
        fascade.changeModel(new Game());

        //Simulate sending a matching version number. Should not change the model.
        fascade.getModel().setVersion(0);
        ServerPoller.Start();
        Thread.sleep(3000); //Pause to let poller do its thing
        ServerPoller.Stop();
        assertNull(fascade.getModel().getPlayers()[1].getName());
        assertEquals(0, fascade.getModel().getVersion());
    }

    @Test
    public void testPollerDifferingVersion() throws InterruptedException {
        mockServer = new MockServer();
        mockServer.ServerProxy("localHost", 8081, fascade);
        ServerPoller.getServerPoller();
        ServerPoller.setFascade(fascade);
        ServerPoller.setServer(mockServer);
        fascade.changeModel(new Game());

        //Simulate sending a differing version number. Should return an updated model.
        fascade.getModel().setVersion(1);
        ServerPoller.Start();
        Thread.sleep(3000); //Pause to let poller do its thing
        ServerPoller.Stop();
        assertNotNull(fascade.getModel().getPlayers()[1].getName());
        assertEquals("Miguel", fascade.getModel().getPlayers()[1].getName());
        assertEquals(21, fascade.getModel().getVersion());
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
