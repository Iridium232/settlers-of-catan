package client.communication;

import com.google.gson.Gson;
import org.json.JSONObject;
import shared.communication.ResourceList;
import shared.communication.fromServer.game.*;
import shared.communication.fromServer.game.DevCardList;
import shared.communication.fromServer.game.EdgeLocation;
import shared.communication.fromServer.game.Port;
import shared.communication.fromServer.game.Road;
import shared.communication.fromServer.game.Player;
import shared.communication.fromServer.game.TradeOffer;
import shared.communication.fromServer.game.TurnTracker;
import shared.definitions.CatanColor;
import shared.definitions.HexType;
import shared.definitions.TurnStatus;
import shared.locations.*;
import shared.model.*;
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
        //fascade.attachNewModel(newModel);
    }

    private ModelPopulator() {
    }

    private void populateGame(ServerModel serverModel, Game newModel) {
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

    private void populateBank(ServerModel serverModel, Game newModel) {
        ResourceList bank = serverModel.getBank();
        ResourceMultiSet resource_bank = newModel.getResource_bank();

        resource_bank.setBrick(bank.getBrick());
        resource_bank.setOre(bank.getOre());
        resource_bank.setSheep(bank.getSheep());
        resource_bank.setWheat(bank.getWheat());
        resource_bank.setWood(bank.getWood());
    }

    private void populateChat(ServerModel serverModel, Game newModel) {
        MessageList serverChat = serverModel.getChat();
        MessageList clientChat = newModel.getChat();

        for (MessageLine line : serverChat.getMessages()) {
            clientChat.addMessage(line);
        }
    }

    private void populateLog(ServerModel serverModel, Game newModel) {
        MessageList serverLog = serverModel.getLog();
        MessageList clientLog = newModel.getLog();

        for (MessageLine line : serverLog.getMessages()) {
            clientLog.addMessage(line);
        }
    }

    private void populateMap(ServerModel serverModel, Game newModel) {
        Map serverMap = serverModel.getMap();
        GameMap clientMap = newModel.getMap();

        populateHexes(serverMap, clientMap);
        populatePorts(serverMap, clientMap);
        populateRoads(serverMap, clientMap);
        populateSettlements(serverMap, clientMap);
        populateCities(serverMap, clientMap);
        clientMap.setRadius(serverMap.getRadius());
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
            } else if (hexResource.equals("Wood")){
                newTerrainHex.setType((HexType.WOOD));
            } else if (hexResource.equals("Brick")){
                newTerrainHex.setType((HexType.BRICK));
            } else if (hexResource.equals("Sheep")){
                newTerrainHex.setType((HexType.SHEEP));
            } else if (hexResource.equals("Wheat")){
                newTerrainHex.setType((HexType.WHEAT));
            } else if (hexResource.equals("Ore")){
                newTerrainHex.setType((HexType.ORE));
            }
            clientMap.addTerrainHex(newTerrainHex);
        }
    }

    private void populatePorts(Map serverMap, GameMap clientMap) {
        Port[] serverPorts = serverMap.getPorts();

        for (Port port : serverPorts) {
            HexLocation serverLocation = port.getLocation();
            String serverDirection = port.getDirection();
            int serverRatio = port.getRatio();
            String serverResource = port.getResource();

            shared.model.ports.Port newPort = null;
            if (serverResource == null) {
                newPort = new MiscPort(serverLocation.getX(), serverLocation.getY(), serverDirection, serverRatio);
            } else if (serverResource.equals("Wood")){
                newPort = new WoodPort(serverLocation.getX(), serverLocation.getY(), serverDirection, serverRatio);
            } else if (serverResource.equals("Brick")){
                newPort = new BrickPort(serverLocation.getX(), serverLocation.getY(), serverDirection, serverRatio);
            } else if (serverResource.equals("Sheep")){
                newPort = new SheepPort(serverLocation.getX(), serverLocation.getY(), serverDirection, serverRatio);
            } else if (serverResource.equals("Wheat")){
                newPort = new WheatPort(serverLocation.getX(), serverLocation.getY(), serverDirection, serverRatio);
            } else if (serverResource.equals("Ore")){
                newPort = new OrePort(serverLocation.getX(), serverLocation.getY(), serverDirection, serverRatio);
            }
            clientMap.addPort(newPort);
        }
    }

    private void populateRoads(Map serverMap, GameMap clientMap) {
        Road[] serverRoads = serverMap.getRoads();

        for (Road road : serverRoads) {
            //Values from server
            int serverOwner = road.getOwner();
            EdgeLocation serverLocation = road.getLocation();
            HexLocation newLocation = new HexLocation(serverLocation.getX(), serverLocation.getY());

            //Load values from server
            shared.model.Road newRoad = new shared.model.Road();

            Edge newRoadEdge = new Edge();
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
            newRoadEdge.setRoad(newRoad);
            newRoad.setLocation(newRoadEdge);
            newRoad.setOwner(serverOwner);
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
            VertexLocation serverVertexLocation = vertexObject.getLocation();
            HexLocation serverHexLocation = serverVertexLocation.getHexLoc();

            VertexLocation newLocation = new VertexLocation(new HexLocation(serverHexLocation.getX(),
                    serverHexLocation.getY()), serverVertexLocation.getDir());

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
            VertexLocation serverVertexLocation = vertexObject.getLocation();
            HexLocation serverHexLocation = serverVertexLocation.getHexLoc();

            VertexLocation newLocation = new VertexLocation(new HexLocation(serverHexLocation.getX(),
                    serverHexLocation.getY()), serverVertexLocation.getDir());

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
        Player[] serverPlayers = serverModel.getPlayers();

        for (Player player : serverPlayers) {
            shared.model.Player newPlayer = new shared.model.Player();
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

            DevCardList serverOldList = player.getOldDevCards();
            shared.model.DevCardList newOldList = new shared.model.DevCardList();
            populateDevCardList(serverOldList, newOldList);

            DevCardList serverNewList = player.getNewDevCards();
            shared.model.DevCardList newNewList = new shared.model.DevCardList();
            populateDevCardList(serverNewList, newNewList);

            newPlayer.setResources(newMultiSet);
            newPlayer.setOldDevCards(newOldList);
            newPlayer.setNewDevCards(newNewList);
            newModel.setPlayer(newPlayer, player.getPlayerIndex());
        }
        setPieceColors(newModel);
    }

    private void setPieceColors(Game newModel) {
        for (shared.model.Road road : newModel.getMap().getRoads()) {
            for (shared.model.Player player : newModel.getPlayers()) {
                if (road.getOwner() == player.getPlayerIndex()) {
                    if (player.getColor().equals("red")) { road.setColor(CatanColor.RED); }
                    if (player.getColor().equals("orange")) { road.setColor(CatanColor.ORANGE); }
                    if (player.getColor().equals("yellow")) { road.setColor(CatanColor.YELLOW); }
                    if (player.getColor().equals("blue")) { road.setColor(CatanColor.BLUE); }
                    if (player.getColor().equals("green")) { road.setColor(CatanColor.GREEN); }
                    if (player.getColor().equals("purple")) { road.setColor(CatanColor.PURPLE); }
                    if (player.getColor().equals("puce")) { road.setColor(CatanColor.PUCE); }
                    if (player.getColor().equals("white")) { road.setColor(CatanColor.WHITE); }
                    if (player.getColor().equals("brown")) { road.setColor(CatanColor.BROWN); }
                }
            }
        }
    }

    private void populateResourceMultiSet(ResourceList serverList, ResourceMultiSet newSet) {
        newSet.setBrick(serverList.getBrick());
        newSet.setOre(serverList.getOre());
        newSet.setSheep(serverList.getSheep());
        newSet.setWheat(serverList.getWheat());
        newSet.setWood(serverList.getWood());
    }

    private void populateDevCardList(DevCardList serverList, shared.model.DevCardList newList) {
        newList.setMonopoly(serverList.getMonopoly());
        newList.setMonument(serverList.getMonument());
        newList.setRoad_building(serverList.getRoadBuilding());
        newList.setSoldier(serverList.getSoldier());
        newList.setYear_of_plenty(serverList.getYearOfPlenty());
    }

    private void populateTradeOffer(ServerModel serverModel, Game newModel) {
        TradeOffer serverOffer = serverModel.getTradeOffer();
        shared.model.TradeOffer newOffer = new shared.model.TradeOffer();

        newOffer.setSender(serverOffer.getSender());
        newOffer.setReciever(serverOffer.getReceiver());

        ResourceMultiSet newMultiSet = new ResourceMultiSet();
        populateResourceMultiSet(serverOffer.getOffer(), newMultiSet);
        newOffer.setOffer(newMultiSet);

        newModel.setTrade_offer(newOffer);
    }

    private void populateTurnTracker(ServerModel serverModel, Game newModel) {
        TurnTracker serverTracker = serverModel.getTurnTracker();
        shared.model.TurnTracker newTracker = new shared.model.TurnTracker();

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
