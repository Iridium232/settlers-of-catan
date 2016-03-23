package server.communication;

import shared.communication.ResourceList;
import shared.communication.fromServer.game.CommunicationModel;
import shared.communication.fromServer.game.Hex;
import shared.communication.fromServer.game.Map;
import shared.definitions.HexType;
import shared.definitions.ResourceType;
import shared.locations.EdgeDirection;
import shared.locations.HexLocation;
import shared.model.map.Edge;
import shared.model.map.GameMap;
import shared.model.map.Robber;
import shared.model.map.TerrainHex;
import shared.model.map.buildings.Building;
import shared.model.map.buildings.City;
import shared.model.map.buildings.Settlement;
import shared.model.messages.MessageLine;
import shared.model.messages.MessageList;
import shared.model.player.ResourceMultiSet;

import java.util.ArrayList;

/**
 * Michael Rhodes
 * CS 340
 * Section 2
 * Team 10
 */
public class ModelTranslator {

    public static CommunicationModel translateModel(shared.model.Game serverModel) {
        try {
            if (serverModel == null) throw new NullPointerException("our model is null");
            ModelTranslator mt = new ModelTranslator();
            shared.communication.fromServer.game.DevCardList deck = mt.translateDeck(serverModel);
            ResourceList bank = mt.translateBank(serverModel);
            MessageList chat = mt.translateChat(serverModel);
            MessageList log = mt.translateLog(serverModel);
            Map map = mt.translateMap(serverModel);
            shared.communication.fromServer.game.Player[] players = mt.translatePlayers(serverModel);
            shared.communication.fromServer.game.TradeOffer tradeOffer = mt.translateTradeOffer(serverModel);
            shared.communication.fromServer.game.TurnTracker turnTracker = mt.translateTurnTracker(serverModel);
            int version = serverModel.getVersion();
            int winner = serverModel.getWinner();

            return new CommunicationModel(deck, bank, chat, log, map, players, tradeOffer, turnTracker, version, winner);
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    private ModelTranslator() {
    }

    //*************************************** For Translating Model *************************************************
    private shared.communication.fromServer.game.DevCardList translateDeck(shared.model.Game serverModel)
            throws NullPointerException {
        shared.model.player.DevCardList serverDeck = serverModel.getDevelopment_bank();
        if (serverDeck == null) throw new NullPointerException("the deck in our model is null");

        int monopoly = serverDeck.getMonopoly();
        int monument = serverDeck.getMonument();
        int roadBuilding = serverDeck.getRoad_building();
        int soldier = serverDeck.getSoldier();
        int yearOfPlenty = serverDeck.getYear_of_plenty();

        shared.communication.fromServer.game.DevCardList comDeck = new shared.communication.fromServer.game
                .DevCardList(monopoly, monument, roadBuilding, soldier, yearOfPlenty);
        return comDeck;
    }

    private ResourceList translateBank(shared.model.Game serverModel)
            throws NullPointerException {
        ResourceMultiSet serverBank = serverModel.getResource_bank();
        if (serverBank == null) throw new NullPointerException("the bank in our model is null");

        int brick = serverBank.getBrick();
        int wheat = serverBank.getWheat();
        int wood = serverBank.getWood();
        int sheep = serverBank.getSheep();
        int ore = serverBank.getOre();

        ResourceList comBank = new ResourceList(brick, ore, sheep, wheat, wood);
        return comBank;
    }

    private MessageList translateChat(shared.model.Game serverModel)
            throws NullPointerException {
        MessageList serverChat = serverModel.getChat();
        if (serverChat == null) throw new NullPointerException("the chat in our model is null");

        MessageList comChat = new MessageList();
        for (MessageLine message : serverChat.getMessages()) {
            comChat.addMessage(message);
        }

        return comChat;
    }

    private MessageList translateLog(shared.model.Game serverModel)
            throws NullPointerException {
        MessageList serverLog = serverModel.getLog();
        if (serverLog == null) throw new NullPointerException("the log in our model is null");

        MessageList comLog = new MessageList();
        for (MessageLine message : serverLog.getMessages()) {
            comLog.addMessage(message);
        }

        return comLog;
    }

    private Map translateMap(shared.model.Game serverModel)
            throws NullPointerException, Exception {
        GameMap serverMap = serverModel.getMap();
        if (serverMap == null) throw new NullPointerException("the map in our model is null");

        Hex[] hexes = translateHexes(serverMap);
        shared.communication.fromServer.game.Port[] ports = translatePorts(serverMap);
        shared.communication.fromServer.game.Road[] roads = translateRoads(serverMap);
        shared.communication.fromServer.game.VertexObject[] settlements = translateSettlements(serverMap);
        shared.communication.fromServer.game.VertexObject[] cities = translateCities(serverMap);
        int radius = serverMap.getRadius();
        HexLocation robber = translateRobber(serverMap);

        Map comMap = new Map(hexes, ports, roads, settlements, cities, radius, robber);
        return comMap;
    }

    private shared.communication.fromServer.game.Player[] translatePlayers(shared.model.Game serverModel)
            throws NullPointerException {
        shared.model.player.Player[] serverPlayers = serverModel.getPlayers();

        ArrayList<shared.communication.fromServer.game.Player> comPlayers = new ArrayList<>();
        for (shared.model.player.Player player: serverPlayers) {
            int cities = player.getCities();
            String color = player.getColor();
            boolean discarded = player.isDiscarded();
            int monuments = player.getMonuments();
            String name = player.getName();
            shared.communication.fromServer.game.DevCardList newDevCards =
                    translateDevCardList(player.getNewDevCards());
            shared.communication.fromServer.game.DevCardList oldDevCards =
                    translateDevCardList(player.getOldDevCards());
            int playerIndex = player.getPlayerIndex();
            boolean playedDevCard = player.isPlayedDevCard();
            int playerID = player.getPlayerID();
            ResourceList resources = translateResourceMultiSet(player.getResources());
            int roads = player.getRoads();
            int settlements = player.getSettlements();
            int soldiers = player.getSoldiers();
            int victoryPoints = player.getVictoryPoints();
            shared.communication.fromServer.game.Player comPlayer = new shared.communication.fromServer.game.Player(
                    cities, color, discarded, monuments, name, newDevCards, oldDevCards, playerIndex, playedDevCard,
                    playerID, resources, roads, settlements, soldiers, victoryPoints);
            comPlayers.add(comPlayer);
        }

        return (shared.communication.fromServer.game.Player[])comPlayers.toArray();
    }

    private shared.communication.fromServer.game.TradeOffer translateTradeOffer(shared.model.Game serverModel)
            throws NullPointerException {
        shared.model.player.TradeOffer serverTradeOffer = serverModel.getTrade_offer();
        if (serverTradeOffer == null) return null;

        int receiver = serverTradeOffer.getReciever();
        int sender = serverTradeOffer.getSender();
        ResourceList offer = translateOffer(serverTradeOffer);
        return new shared.communication.fromServer.game.TradeOffer(sender, receiver, offer);
    }

    private shared.communication.fromServer.game.TurnTracker translateTurnTracker(shared.model.Game serverModel)
            throws NullPointerException {
        shared.model.states.TurnTracker serverTracker = serverModel.getTurn_tracker();
        if (serverTracker == null) throw new NullPointerException("serverTracker in model is null");

        int currentTurn = serverTracker.getActive_player();
        String status = translateState(serverTracker.getState());
        int longestRoad = serverTracker.getLongest_road_player();
        int largestArmy = serverTracker.getLargest_army_player();
        return new shared.communication.fromServer.game.TurnTracker(currentTurn, status, longestRoad, largestArmy);
    }

    //*************************************** For Translating Map ****************************************************
    private Hex[] translateHexes(GameMap serverMap)
            throws NullPointerException, Exception {
        TerrainHex[][] serverHexes = serverMap.getHexes();
        ArrayList<Hex> comHexes = new ArrayList<>();

        for (TerrainHex[] outerHexes : serverHexes) {
            for (TerrainHex hex : outerHexes) {
                HexLocation location = hex.getLocation();
                String resource = hexTypeToString(hex.getType());
                if (hex.getNumber() == null) throw new NullPointerException("NumberChit in a hex in our map is null");
                int number = hex.getNumber().getValue();
                Hex comHex = new Hex(location, resource, number);
                comHexes.add(comHex);
            }
        }
        return (Hex[])comHexes.toArray();
    }

    private shared.communication.fromServer.game.Port[] translatePorts(GameMap serverMap)
            throws NullPointerException, Exception {
        shared.model.ports.Port[] serverPorts = serverMap.getPorts();

        ArrayList<shared.communication.fromServer.game.Port> comPorts = new ArrayList<>();

        for (shared.model.ports.Port port : serverPorts) {
            String resource = resourceTypeToString(port.getResource());
            HexLocation location = port.getLocation();
            String direction = edgeDirectionToString(port.getEdgeDirection());
            int ratio = port.getRatio();
            shared.communication.fromServer.game.Port comPort =
                    new shared.communication.fromServer.game.Port(resource, location, direction, ratio);
            comPorts.add(comPort);
        }
        return (shared.communication.fromServer.game.Port[])comPorts.toArray();
    }

    private shared.communication.fromServer.game.Road[] translateRoads(GameMap serverMap)
            throws NullPointerException {
        shared.model.map.Road[] serverRoads = serverMap.getRoads();

        ArrayList<shared.communication.fromServer.game.Road> comRoads = new ArrayList<>();

        for (shared.model.map.Road road : serverRoads) {
            int owner = road.getOwnerIndex();
            shared.communication.EdgeLocation location = edgeToEdgeLocation(road.getLocation());
            shared.communication.fromServer.game.Road comRoad =
                    new shared.communication.fromServer.game.Road(owner, location);
            comRoads.add(comRoad);
        }
        return (shared.communication.fromServer.game.Road[])comRoads.toArray();
    }

    private shared.communication.fromServer.game.VertexObject[] translateSettlements(GameMap serverMap)
            throws NullPointerException {
        Building[] serverBuildings = serverMap.getBuildings();

        ArrayList<shared.communication.fromServer.game.VertexObject> comVertexObjects = new ArrayList<>();

        for (Building building: serverBuildings) {
            if (building.getClass() == Settlement.class) {
                int owner = building.getOwner();
                shared.communication.fromServer.game.VertexLocation location =
                        translateVertexLocation(building.getLocation());
                shared.communication.fromServer.game.VertexObject comVertexObject =
                        new shared.communication.fromServer.game.VertexObject(owner, location);
                comVertexObjects.add(comVertexObject);
            }
        }
        return (shared.communication.fromServer.game.VertexObject[])comVertexObjects.toArray();
    }

    private shared.communication.fromServer.game.VertexObject[] translateCities(GameMap serverMap)
            throws NullPointerException {
        Building[] serverBuildings = serverMap.getBuildings();

        ArrayList<shared.communication.fromServer.game.VertexObject> comVertexObjects = new ArrayList<>();

        for (Building building: serverBuildings) {
            if (building.getClass() == City.class) {
                int owner = building.getOwner();
                shared.communication.fromServer.game.VertexLocation location =
                        translateVertexLocation(building.getLocation());
                shared.communication.fromServer.game.VertexObject comVertexObject =
                        new shared.communication.fromServer.game.VertexObject(owner, location);
                comVertexObjects.add(comVertexObject);
            }
        }
        return (shared.communication.fromServer.game.VertexObject[])comVertexObjects.toArray();
    }

    private HexLocation translateRobber(GameMap serverMap)
            throws NullPointerException {
        Robber robber = serverMap.getRobber();
        if (robber == null) throw new NullPointerException("robber in our in our map is null");

        return robber.getLocation();
    }

    private String hexTypeToString(HexType type) throws Exception {
        String strType;
        switch (type) {
            case BRICK:
                strType = "brick";
                break;
            case WHEAT:
                strType = "wheat";
                break;
            case WOOD:
                strType = "wood";
                break;
            case SHEEP:
                strType = "sheep";
                break;
            case ORE:
                strType = "ore";
                break;
            case WATER:
                strType = "water";
                break;
            case DESERT:
                strType = "desert";
                break;
            default:
                throw new Exception("Unknown type");
        }
        return strType;
    }

    private String resourceTypeToString(ResourceType type) throws Exception {
        String strType;
        switch (type) {
            case BRICK:
                strType = "brick";
                break;
            case WHEAT:
                strType = "wheat";
                break;
            case WOOD:
                strType = "wood";
                break;
            case SHEEP:
                strType = "sheep";
                break;
            case ORE:
                strType = "ore";
                break;
            default:
                throw new Exception("Unknown type");
        }
        return strType;
    }

    private String edgeDirectionToString(EdgeDirection edgeDirection) throws Exception {
        String strType;
        switch (edgeDirection) {
            case NorthEast:
                strType = "NE";
                break;
            case North:
                strType = "N";
                break;
            case NorthWest:
                strType = "NW";
                break;
            case SouthEast:
                strType = "SE";
                break;
            case South:
                strType = "S";
                break;
            case SouthWest:
                strType = "SW";
                break;
            default:
                throw new Exception("Unknown direction");
        }
        return strType;
    }

    private shared.communication.EdgeLocation edgeToEdgeLocation(Edge edge) {
        shared.locations.EdgeLocation location = edge.getLocation();
        int x = location.getHexLoc().getX();
        int y = location.getHexLoc().getY();
        shared.locations.EdgeDirection edgeDirection = location.getDir();
        return new shared.communication.EdgeLocation(x, y, edgeDirection);
    }

    private shared.communication.fromServer.game.VertexLocation translateVertexLocation(
            shared.locations.VertexLocation location) {
        shared.locations.VertexDirection direction = location.getDir();
        int x = location.getHexLoc().getX();
        int y = location.getHexLoc().getY();
        return new shared.communication.fromServer.game.VertexLocation(direction, x, y);
    }

    //*************************************** For Translating Players *************************************************
    private shared.communication.fromServer.game.DevCardList translateDevCardList(
            shared.model.player.DevCardList list) {
        int monopoly = list.getMonopoly();
        int monument = list.getMonument();
        int roadBuilding = list.getRoad_building();
        int soldier = list.getSoldier();
        int yearOfPlenty = list.getYear_of_plenty();
        return new shared.communication.fromServer.game.DevCardList(
                monopoly, monument, roadBuilding, soldier, yearOfPlenty);
    }

    private ResourceList translateResourceMultiSet(ResourceMultiSet multiSet) {
        int brick = multiSet.getBrick();
        int wheat = multiSet.getWheat();
        int wood = multiSet.getWood();
        int sheep = multiSet.getSheep();
        int ore = multiSet.getOre();
        return new ResourceList(brick, ore, sheep, wheat, wood);
    }

    //************************************ For Translating TradeOffer **********************************************
    private ResourceList translateOffer(shared.model.player.TradeOffer serverOffer) {
        ResourceMultiSet receiver_gives = serverOffer.getReciever_gives();
        ResourceMultiSet sender_gives = serverOffer.getSender_gives();

        int brick = 0;
        int wheat = 0;
        int wood = 0;
        int sheep = 0;
        int ore = 0;

        if (receiver_gives.getBrick() > 0) {
            brick = receiver_gives.getBrick();
        } else if (sender_gives.getBrick() > 0) {
            brick = sender_gives.getBrick() * -1;
        }

        if (receiver_gives.getWheat() > 0) {
            wheat = receiver_gives.getWheat();
        } else if (sender_gives.getWheat() > 0) {
            wheat = sender_gives.getWheat() * -1;
        }

        if (receiver_gives.getWood() > 0) {
            wood = receiver_gives.getWood();
        } else if (sender_gives.getWood() > 0) {
            wood = sender_gives.getWood() * -1;
        }

        if (receiver_gives.getSheep() > 0) {
            sheep = receiver_gives.getSheep();
        } else if (sender_gives.getSheep() > 0) {
            sheep = sender_gives.getSheep() * -1;
        }

        if (receiver_gives.getOre() > 0) {
            ore = receiver_gives.getOre();
        } else if (sender_gives.getOre() > 0) {
            ore = sender_gives.getOre() * -1;
        }

        return new ResourceList(brick, ore, sheep, wheat, wood);
    }

    //************************************ For Translating TurnTracker **********************************************
    private String translateState(shared.model.states.IState state) {
        return state.getState().name();
    }
}
