package server.communication;

import shared.communication.ResourceList;
import shared.communication.fromServer.game.CommunicationModel;
import shared.communication.fromServer.game.Map;
import shared.model.messages.MessageList;

/**
 * Michael Rhodes
 * CS 340
 * Section 2
 * Team 10
 */
public class ModelTranslator {

    public static CommunicationModel translateModel(shared.model.Game serverModel) {
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
    }

    private ModelTranslator() {
    }

    private shared.communication.fromServer.game.DevCardList translateDeck(shared.model.Game serverModel) {
        //To do
        return null;
    }

    private ResourceList translateBank(shared.model.Game serverModel) {
        //To do
        return null;
    }

    private MessageList translateChat(shared.model.Game serverModel) {
        //To do
        return null;
    }

    private MessageList translateLog(shared.model.Game serverModel) {
        //To do
        return null;
    }

    private Map translateMap(shared.model.Game serverModel) {
        //To do
        return null;
    }

    private shared.communication.fromServer.game.Player[] translatePlayers(shared.model.Game serverModel) {
        //To do
        return null;
    }

    private shared.communication.fromServer.game.TradeOffer translateTradeOffer(shared.model.Game serverModel) {
        //To do
        return null;
    }

    private shared.communication.fromServer.game.TurnTracker translateTurnTracker(shared.model.Game serverModel) {
        //To do
        return null;
    }
}
