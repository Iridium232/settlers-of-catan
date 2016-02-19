package client.communication;

import com.google.gson.Gson;
import org.json.JSONObject;
import shared.communication.*;
import shared.communication.fromServer.game.*;
import shared.communication.fromServer.game.Port;
import shared.communication.fromServer.game.TradeOffer;
import shared.definitions.CatanColor;
import shared.definitions.HexType;
import shared.definitions.TurnStatus;
import shared.locations.*;
import shared.model.*;
import shared.model.map.Edge;
import shared.model.map.GameMap;
import shared.model.map.NumberChit;
import shared.model.map.TerrainHex;
import shared.model.map.Vertex;
import shared.model.map.buildings.City;
import shared.model.map.buildings.Settlement;
import shared.model.messages.MessageLine;
import shared.model.messages.MessageList;
import shared.model.player.ResourceMultiSet;
import shared.model.ports.*;

/**
 * Michael Rhodes
 * CS 340
 * Section 2
 * Team 10
 */
public class ModelPopulator {
    public static void populateModel(JSONObject o, Fascade fascade) {
        ModelPopulator mp = new ModelPopulator();
        Gson gson = new Gson();
        ServerModel serverModel = gson.fromJson(o.toString(), ServerModel.class);
        Game newModel = new Game();
        mp.populateGame(serverModel, newModel);
        fascade.changeModel(newModel);
    }

    private ModelPopulator() {
    }

    private void populateGame(ServerModel serverModel, Game newModel) {
        populateDeck(serverModel, newModel);
        populateBank(serverModel, newModel);
        populateChat(serverModel, newModel);
        populateLog(serverModel, newModel);
        populateMap(serverModel, newModel);
        populatePlayers(serverModel, newModel);
        populateTradeOffer(serverModel, newModel);
        populateTurnTracker(serverModel, newModel);
        newModel.setVersion(serverModel.getVersion());
        newModel.setWinner(serverModel.getWinner());
    }

    private void populateDeck(ServerModel serverModel, Game newModel) {
    	shared.communication.fromServer.game.DevCardList deck = serverModel.getDeck();
        shared.model.player.DevCardList development_bank = new shared.model.player.DevCardList();

        development_bank.setMonument(deck.getMonument());
        development_bank.setMonopoly(deck.getMonopoly());
        development_bank.setSoldier(deck.getSoldier());
        development_bank.setRoad_building(deck.getRoadBuilding());
        development_bank.setYear_of_plenty(deck.getYearOfPlenty());

        newModel.setDevelopment_bank(development_bank);
    }

    private void populateBank(ServerModel serverModel, Game newModel) {
        ResourceList bank = serverModel.getBank();
        ResourceMultiSet resource_bank = new ResourceMultiSet(bank.getBrick(),
                bank.getWheat(),
                bank.getOre(),
                bank.getWood(),
                bank.getSheep());

        newModel.setResource_bank(resource_bank);
    }

    private void populateChat(ServerModel serverModel, Game newModel) {
        MessageList serverChat = serverModel.getChat();
        MessageList clientChat = new MessageList();

        for (MessageLine line : serverChat.getMessages()) {
            clientChat.addMessage(line);
        }
        newModel.setChat(clientChat);
    }

    private void populateLog(ServerModel serverModel, Game newModel) {
        MessageList serverLog = serverModel.getLog();
        MessageList clientLog = new MessageList();

        for (MessageLine line : serverLog.getMessages()) {
            clientLog.addMessage(line);
        }
        newModel.setLog(clientLog);
    }

    private void populateMap(ServerModel serverModel, Game newModel) {
        Map serverMap = serverModel.getMap();
        GameMap clientMap = newModel.getMap();

        populateHexes(serverMap, clientMap);
        populatePorts(serverMap, clientMap);
        populateRoads(serverMap, clientMap);
        populateSettlements(serverMap, clientMap);
        populateCities(serverMap, clientMap);
        populateRobber(serverMap, clientMap);
    }

    private void populateHexes(Map serverMap, GameMap clientMap) {
        Hex[] serverHexes = serverMap.getHexes();

        for (Hex hex : serverHexes) {
            //Server Model inner objects for deep copying
            HexLocation hexLocation = hex.getLocation();
            int hexNumber = hex.getNumber();
            String hexResource = hex.getResource();

            //Client Model inner objects, deep copy server model objects
            TerrainHex newTerrainHex = new TerrainHex();

            HexLocation newHexLocation = new HexLocation(hexLocation.getX(), hexLocation.getY());
            NumberChit newNumber = new NumberChit();
            newNumber.setLocation(newHexLocation);
            newNumber.setValue(hexNumber);

            //Load values and deep-copied objects
            newTerrainHex.setLocation(newHexLocation);
            newTerrainHex.setNumber(newNumber);
            if (hexResource == null) {
                newTerrainHex.setType(HexType.DESERT);
            } else if (hexResource.equals("wood")){
                newTerrainHex.setType((HexType.WOOD));
            } else if (hexResource.equals("brick")){
                newTerrainHex.setType((HexType.BRICK));
            } else if (hexResource.equals("sheep")){
                newTerrainHex.setType((HexType.SHEEP));
            } else if (hexResource.equals("wheat")){
                newTerrainHex.setType((HexType.WHEAT));
            } else if (hexResource.equals("ore")){
                newTerrainHex.setType((HexType.ORE));
            }
            clientMap.addTerrainHex(newTerrainHex);
        }
    }

    private void populatePorts(Map serverMap, GameMap clientMap) {
        Port[] serverPorts = serverMap.getPorts();

        for (shared.communication.fromServer.game.Port port : serverPorts) {
            HexLocation serverLocation = port.getLocation();
            String serverDirection = port.getDirection();
            int serverRatio = port.getRatio();
            String serverResource = port.getResource();

            shared.model.ports.Port newPort = null;
            Vertex vertex1 = null;
            Vertex vertex2 = null;
            switch (serverDirection) {
                case "NW":
                    vertex1 = new Vertex(new shared.locations.VertexLocation(serverLocation, VertexDirection.NorthWest));
                    vertex2 = new Vertex(new shared.locations.VertexLocation(serverLocation, VertexDirection.West));
                    break;
                case "N":
                    vertex1 = new Vertex(new shared.locations.VertexLocation(serverLocation, VertexDirection.NorthWest));
                    vertex2 = new Vertex(new shared.locations.VertexLocation(serverLocation, VertexDirection.NorthEast));
                    break;
                case "NE":
                    vertex1 = new Vertex(new shared.locations.VertexLocation(serverLocation, VertexDirection.NorthEast));
                    vertex2 = new Vertex(new shared.locations.VertexLocation(serverLocation, VertexDirection.East));
                    break;
                case "SE":
                    vertex1 = new Vertex(new shared.locations.VertexLocation(serverLocation, VertexDirection.East));
                    vertex2 = new Vertex(new shared.locations.VertexLocation(serverLocation, VertexDirection.SouthEast));
                    break;
                case "S":
                    vertex1 = new Vertex(new shared.locations.VertexLocation(serverLocation, VertexDirection.SouthEast));
                    vertex2 = new Vertex(new shared.locations.VertexLocation(serverLocation, VertexDirection.SouthWest));
                    break;
                case "SW":
                    vertex1 = new Vertex(new shared.locations.VertexLocation(serverLocation, VertexDirection.SouthWest));
                    vertex2 = new Vertex(new shared.locations.VertexLocation(serverLocation, VertexDirection.West));
                    break;
            }
            if (serverResource == null) {
                newPort = new MiscPort(vertex1, serverLocation.getX(), serverLocation.getY(), vertex2,
                        serverRatio);
            } else if (serverResource.equals("wood")){
                newPort = new WoodPort(vertex1, serverLocation.getX(), serverLocation.getY(), vertex2,
                        serverRatio);
            } else if (serverResource.equals("brick")){
                newPort = new BrickPort(vertex1, serverLocation.getX(), serverLocation.getY(), vertex2,
                        serverRatio);
            } else if (serverResource.equals("sheep")){
                newPort = new SheepPort(vertex1, serverLocation.getX(), serverLocation.getY(), vertex2,
                        serverRatio);
            } else if (serverResource.equals("wheat")){
                newPort = new WheatPort(vertex1, serverLocation.getX(), serverLocation.getY(), vertex2,
                        serverRatio);
            } else if (serverResource.equals("ore")){
                newPort = new OrePort(vertex1, serverLocation.getX(), serverLocation.getY(), vertex2,
                        serverRatio);
            }
            clientMap.addPort(newPort);
        }
    }

    private void populateRoads(Map serverMap, GameMap clientMap) {
    	shared.communication.fromServer.game.Road[] serverRoads = serverMap.getRoads();

        for (shared.communication.fromServer.game.Road road : serverRoads) {
            //Values from server
            int serverOwner = road.getOwner();
            shared.communication.EdgeLocation serverLocation = road.getLocation();
            HexLocation newLocation = new HexLocation(serverLocation.getX(), serverLocation.getY());

            //Load values from server
            shared.model.map.Road newRoad = new shared.model.map.Road();

            Edge newRoadEdge = new Edge();

            Vertex vertex1 = null;
            Vertex vertex2 = null;
            switch (serverLocation.getDirection()) {
                case "NW":
                    vertex1 = new Vertex(new shared.locations.VertexLocation(newLocation, VertexDirection.NorthWest));
                    vertex2 = new Vertex(new shared.locations.VertexLocation(newLocation, VertexDirection.West));
                    break;
                case "N":
                    vertex1 = new Vertex(new shared.locations.VertexLocation(newLocation, VertexDirection.NorthWest));
                    vertex2 = new Vertex(new shared.locations.VertexLocation(newLocation, VertexDirection.NorthEast));
                    break;
                case "NE":
                    vertex1 = new Vertex(new shared.locations.VertexLocation(newLocation, VertexDirection.NorthEast));
                    vertex2 = new Vertex(new shared.locations.VertexLocation(newLocation, VertexDirection.East));
                    break;
                case "SE":
                    vertex1 = new Vertex(new shared.locations.VertexLocation(newLocation, VertexDirection.East));
                    vertex2 = new Vertex(new shared.locations.VertexLocation(newLocation, VertexDirection.SouthEast));
                    break;
                case "S":
                    vertex1 = new Vertex(new shared.locations.VertexLocation(newLocation, VertexDirection.SouthEast));
                    vertex2 = new Vertex(new shared.locations.VertexLocation(newLocation, VertexDirection.SouthWest));
                    break;
                case "SW":
                    vertex1 = new Vertex(new shared.locations.VertexLocation(newLocation, VertexDirection.SouthWest));
                    vertex2 = new Vertex(new shared.locations.VertexLocation(newLocation, VertexDirection.West));
                    break;
            }
            newRoadEdge.setEnd1(vertex1);
            newRoadEdge.setEnd2(vertex2);

            shared.locations.EdgeLocation newEdgeLocation = null;
            if (serverLocation.getDirection().equals("NW")) {
                newEdgeLocation = new shared.locations.EdgeLocation(newLocation, EdgeDirection.NorthWest);
                newRoadEdge.setDirection(EdgeDirection.NorthWest);
            } else if (serverLocation.getDirection().equals("N")) {
                newEdgeLocation = new shared.locations.EdgeLocation(newLocation, EdgeDirection.North);
                newRoadEdge.setDirection(EdgeDirection.North);
            } else if (serverLocation.getDirection().equals("NE")) {
                newEdgeLocation = new shared.locations.EdgeLocation(newLocation, EdgeDirection.NorthEast);
                newRoadEdge.setDirection(EdgeDirection.NorthEast);
            } else if (serverLocation.getDirection().equals("SW")) {
                newEdgeLocation = new shared.locations.EdgeLocation(newLocation, EdgeDirection.SouthWest);
                newRoadEdge.setDirection(EdgeDirection.SouthWest);
            } else if (serverLocation.getDirection().equals("S")) {
                newEdgeLocation = new shared.locations.EdgeLocation(newLocation, EdgeDirection.South);
                newRoadEdge.setDirection(EdgeDirection.South);
            } else if (serverLocation.getDirection().equals("SE")) {
                newEdgeLocation = new shared.locations.EdgeLocation(newLocation, EdgeDirection.SouthEast);
                newRoadEdge.setDirection(EdgeDirection.SouthEast);
            }
            newRoadEdge.setLocation(newEdgeLocation);
            newRoad.setLocation(newRoadEdge);
            newRoad.setOwnerIndex(serverOwner);
            try {
                clientMap.addRoad(newRoad);
            } catch (Exception e) {
            }
        }
    }

    private void populateSettlements(Map serverMap, GameMap clientMap) {
        VertexObject[] serverSettlements = serverMap.getSettlements();

        for (VertexObject vertexObject : serverSettlements) {
            int serverOwner = vertexObject.getOwner();
            shared.communication.fromServer.game.VertexLocation serverLocation = vertexObject.getLocation();

            VertexDirection direction = null;
            switch (serverLocation.getDirection()) {
                case "NW":
                    direction = VertexDirection.NorthWest;
                    break;
                case "NE":
                    direction = VertexDirection.NorthEast;
                    break;
                case "E":
                    direction = VertexDirection.East;
                    break;
                case "SE":
                    direction = VertexDirection.SouthEast;
                    break;
                case "SW":
                    direction = VertexDirection.SouthWest;
                    break;
                case "W":
                    direction = VertexDirection.West;
                    break;
            }

            shared.locations.VertexLocation newLocation = new shared.locations.VertexLocation(
                    new HexLocation(serverLocation.getX(), serverLocation.getY()), direction);

            Settlement newSettlement = new Settlement();
            newSettlement.setLocation(newLocation);
            newSettlement.setOwner(serverOwner);
            try {
                clientMap.addBuilding(newSettlement);
            } catch (Exception e) {
            }
        }
    }

    private void populateCities(Map serverMap, GameMap clientMap) {
        VertexObject[] serverCities = serverMap.getCities();

        for (VertexObject vertexObject : serverCities) {
            int serverOwner = vertexObject.getOwner();
            shared.communication.fromServer.game.VertexLocation serverLocation = vertexObject.getLocation();

            VertexDirection direction = null;
            switch (serverLocation.getDirection()) {
                case "NW":
                    direction = VertexDirection.NorthWest;
                    break;
                case "NE":
                    direction = VertexDirection.NorthEast;
                    break;
                case "E":
                    direction = VertexDirection.East;
                    break;
                case "SE":
                    direction = VertexDirection.SouthEast;
                    break;
                case "SW":
                    direction = VertexDirection.SouthWest;
                    break;
                case "W":
                    direction = VertexDirection.West;
                    break;
            }

            shared.locations.VertexLocation newLocation = new shared.locations.VertexLocation(
                    new HexLocation(serverLocation.getX(), serverLocation.getY()), direction);

            City newCity = new City();
            newCity.setLocation(newLocation);
            newCity.setOwner(serverOwner);
            try {
                clientMap.addBuilding(newCity);
            } catch (Exception e) {
            }
        }
    }

    private void populateRobber(Map serverMap, GameMap clientMap) {
        HexLocation serverLocation = serverMap.getRobber();
        clientMap.getRobber().setLocation(new HexLocation(serverLocation.getX(), serverLocation.getY()));
    }

    private void populatePlayers(ServerModel serverModel, Game newModel) {
    	shared.communication.fromServer.game.Player[] serverPlayers = serverModel.getPlayers();

        for (shared.communication.fromServer.game.Player player : serverPlayers) {
        	if(player == null)continue;
            shared.model.player.Player newPlayer = new shared.model.player.Player();
            newPlayer.setCities(player.getCities());
            newPlayer.setColor(player.getColor());
            newPlayer.setDiscarded(player.isDiscarded());
            newPlayer.setMonuments(player.getMonuments());
            newPlayer.setName(player.getName());
            newPlayer.setPlayerID(player.getPlayerID());
            newPlayer.setPlayerIndex(player.getPlayerIndex());
            newPlayer.setPlayedDevCard(player.isPlayedDevCard());
            newPlayer.setRoads(player.getRoads());
            newPlayer.setSettlements(player.getSettlements());
            newPlayer.setSoldiers(player.getSoldiers());
            newPlayer.setVictoryPoints(player.getVictoryPoints());

            ResourceList serverList = player.getResources();
            ResourceMultiSet newMultiSet = new ResourceMultiSet();
            populateResourceMultiSet(serverList, newMultiSet);

            shared.communication.fromServer.game.DevCardList serverOldList = player.getOldDevCards();
            shared.model.player.DevCardList newOldList = new shared.model.player.DevCardList();
            populateDevCardList(serverOldList, newOldList);

            shared.communication.fromServer.game.DevCardList serverNewList = player.getNewDevCards();
            shared.model.player.DevCardList newNewList = new shared.model.player.DevCardList();
            populateDevCardList(serverNewList, newNewList);

            newPlayer.setResources(newMultiSet);
            newPlayer.setOldDevCards(newOldList);
            newPlayer.setNewDevCards(newNewList);
            newModel.setPlayer(newPlayer, player.getPlayerIndex());
        }
        setPieceColors(newModel);
    }

    private void setPieceColors(Game newModel) {
        setRoadColors(newModel);
        setBuildingColors(newModel);
    }

    private void setRoadColors(Game newModel) {
        if(newModel.getMap().getRoads() == null)return;
        for (shared.model.map.Road road : newModel.getMap().getRoads()) {
            for (shared.model.player.Player player : newModel.getPlayers()) {
                if (road.getOwnerIndex() == player.getPlayerIndex()) {
                    road.setColor(getCatanColor(player));
                }
            }
        }
    }

    private void setBuildingColors(Game newModel) {
        if(newModel.getMap().getBuildings() == null)return;
        for (shared.model.map.buildings.Building bldg : newModel.getMap().getBuildings()) {
            for (shared.model.player.Player player : newModel.getPlayers()) {
                if (bldg.getOwner() == player.getPlayerIndex()) {
                    bldg.setColor(getCatanColor(player));
                }
            }
        }
    }

    private CatanColor getCatanColor(shared.model.player.Player player) {
        if (player.getColor().equals("red")) return CatanColor.RED;
        if (player.getColor().equals("orange")) return CatanColor.ORANGE;
        if (player.getColor().equals("yellow")) return CatanColor.YELLOW;
        if (player.getColor().equals("blue")) return CatanColor.BLUE;
        if (player.getColor().equals("green")) return CatanColor.GREEN;
        if (player.getColor().equals("purple")) return CatanColor.PURPLE;
        if (player.getColor().equals("puce")) return CatanColor.PUCE;
        if (player.getColor().equals("white")) return CatanColor.WHITE;
        return CatanColor.BROWN;
    }

    private void populateResourceMultiSet(ResourceList serverList, ResourceMultiSet newSet) {
        newSet.setBrick(serverList.getBrick());
        newSet.setOre(serverList.getOre());
        newSet.setSheep(serverList.getSheep());
        newSet.setWheat(serverList.getWheat());
        newSet.setWood(serverList.getWood());
    }

    private void populateDevCardList(shared.communication.fromServer.game.DevCardList serverList, shared.model.player.DevCardList newList) {
        newList.setMonopoly(serverList.getMonopoly());
        newList.setMonument(serverList.getMonument());
        newList.setRoad_building(serverList.getRoadBuilding());
        newList.setSoldier(serverList.getSoldier());
        newList.setYear_of_plenty(serverList.getYearOfPlenty());
    }

    private void populateTradeOffer(ServerModel serverModel, Game newModel) {
        TradeOffer serverOffer = serverModel.getTradeOffer();
        shared.model.player.TradeOffer newOffer = new shared.model.player.TradeOffer();
        if(serverOffer == null)return;
        newOffer.setSender(serverOffer.getSender());
        newOffer.setReciever(serverOffer.getReceiver());

        ResourceMultiSet newMultiSet = new ResourceMultiSet();
        populateResourceMultiSet(serverOffer.getOffer(), newMultiSet);
        newOffer.translateOffer(newMultiSet);

        newModel.setTrade_offer(newOffer);
    }

    private void populateTurnTracker(ServerModel serverModel, Game newModel) {
    	shared.communication.fromServer.game.TurnTracker serverTracker = serverModel.getTurnTracker();
        shared.model.states.TurnTracker newTracker = new shared.model.states.TurnTracker();

        newTracker.setActive_player(serverTracker.getCurrentTurn());
        newTracker.setLargest_army_player(serverTracker.getLargestArmy());
        newTracker.setLongest_road_player(serverTracker.getLongestRoad());

        if (serverTracker.getStatus().equals("FirstRound")) { newTracker.setStatus(TurnStatus.FIRSTROUND); }
        if (serverTracker.getStatus().equals("SecondRound")) { newTracker.setStatus(TurnStatus.SECONDROUND); }
        if (serverTracker.getStatus().equals("Rolling")) { newTracker.setStatus(TurnStatus.ROLLING); }
        if (serverTracker.getStatus().equals("Robbing")) { newTracker.setStatus(TurnStatus.ROBBING); }
        if (serverTracker.getStatus().equals("Discarding")) { newTracker.setStatus(TurnStatus.DISCARDING); }
        if (serverTracker.getStatus().equals("Playing")) { newTracker.setStatus(TurnStatus.PLAYING); }

        newModel.setTurn_tracker(newTracker);
    }
}
