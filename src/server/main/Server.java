package server.main;

import com.sun.net.httpserver.HttpServer;

import server.facade.ServerFacade;
import server.gamehandlers.CreateHandler;
import server.gamehandlers.GameListHandler;
import server.gamehandlers.JoinHandler;
import server.handlers.*;
import server.movehandlers.AbstractMoveHandler;
import server.movehandlers.AcceptTradeHandler;
import server.movehandlers.BuildCityHandler;
import server.movehandlers.BuildRoadHandler;
import server.movehandlers.BuildSettlementHandler;
import server.movehandlers.BuyDevCardHandler;
import server.movehandlers.DiscardCardsHandler;
import server.movehandlers.FinishTurnHandler;
import server.movehandlers.GetModelHandler;
import server.movehandlers.ListAIHandler;
import server.movehandlers.MaritimeTradeHandler;
import server.movehandlers.MonopolyHandler;
import server.movehandlers.MonumentHandler;
import server.movehandlers.OfferTradeHandler;
import server.movehandlers.RoadBuildingHandler;
import server.movehandlers.RobPlayerHandler;
import server.movehandlers.RollNumberHandler;
import server.movehandlers.SendChatHandler;
import server.movehandlers.SoldierHandler;
import server.movehandlers.YearOfPlentyHandler;
import server.userhandler.LoginHandler;
import server.userhandler.RegisterHandler;

import java.io.IOException;
import java.net.InetSocketAddress;


/**
 * Main Class for Catan Server
 *
 */
public class Server 
{
	private static  int SERVER_PORT_NUMBER=8081;
	private static final int MAX_WAITING_CONNECTIONS = 20;
	
	/**
	 * Main Function that starts up all the pieces of the server.
	 * @param args
	 */
	private HttpServer server;
	private ServerFacade facade;
	private Server() {
		return;
	}
	
	public static void main(String[] args)
	{
		new Server().run();
		String host = "localhost";
		String port = "8081";
		if(args.length > 0)
		{
			host = args[0];
			System.out.print("Running client on host: http://" + host + ".\n");
		}
		else
		{
			System.out.print("\nNo host specified. Using default 'localhost'.");
		}
		if(args.length > 1)
		{
			port = args[1];
			System.out.print("Port: " + port +".\n");
		}
		else
		{
			System.out.print("\nNo port specified. Using default '8081'.");
		}
		
		System.out.print("\nServer Running Happily. Have fun!\n");
		
	}
	private void run() {
		try {
			server = HttpServer.create(new InetSocketAddress(SERVER_PORT_NUMBER),MAX_WAITING_CONNECTIONS);
		} 
		catch (IOException e) {
			return;
		}
		
		server.setExecutor(null);
		server.createContext("/user/login", loginHandler);
		server.createContext("/user/register", registerHandler);
		server.createContext("/games/list", listHandler);
		server.createContext("/games/create", createHandler);
		server.createContext("/games/join", joinHandler);
		server.createContext("/game/model", modelHandler);
		server.createContext("/game/listAI", getAITypeHandler);
		server.createContext("/moves/sendChat", sendChatHandler);
		server.createContext("/moves/rollNumber", rollNumberHandler);
		server.createContext("/moves/robPlayer", robPlayerHandler);
		server.createContext("/moves/buyDevCard", buyDevCardHandler);
		server.createContext("/moves/Year_of_Plenty", yearOfPlentyHandler);
		server.createContext("/moves/Road_Building", roadBuildingHandler);
		server.createContext("/moves/Soldier", soldierHandler);
		server.createContext("/moves/Monopoly", monopolyHandler);
		server.createContext("/moves/buildRoad", buildRoadHandler);
		server.createContext("/moves/buildSettlement", buildSettlementHandler);
		server.createContext("/moves/buildCity", buildCityHandler);
		server.createContext("/moves/offerTrade", offerTradeHandler);
		server.createContext("/moves/acceptTrade", acceptTradeHandler);
		server.createContext("/moves/maritimeTrade", maritimeTradeHandler);
		server.createContext("/moves/discardCards", discardCardsHandler);
		server.createContext("/moves/finishTurn", finishTurnHandler);
		server.createContext("/moves/Monument", monumentHandler);
		server.createContext("/docs/api/data", new Handlers.JSONAppender(""));
		server.createContext("/docs/api/view", new Handlers.BasicFile(""));
		
		server.start();
	}
	
	private AbstractMoveHandler listHandler = new GameListHandler(facade);
	private AbstractMoveHandler getAITypeHandler = new ListAIHandler(facade);
	private AbstractMoveHandler modelHandler = new GetModelHandler(facade);
	private AbstractMoveHandler createHandler = new CreateHandler(facade);
	private AbstractMoveHandler registerHandler = new RegisterHandler(facade);
	private AbstractMoveHandler acceptTradeHandler = new AcceptTradeHandler(facade);
	private AbstractMoveHandler buildCityHandler = new BuildCityHandler(facade);
	private AbstractMoveHandler buildRoadHandler = new BuildRoadHandler(facade);
	private AbstractMoveHandler buildSettlementHandler = new BuildSettlementHandler(facade);
	private AbstractMoveHandler buyDevCardHandler = new BuyDevCardHandler(facade);
	private AbstractMoveHandler discardCardsHandler = new DiscardCardsHandler(facade);
	private AbstractMoveHandler finishTurnHandler = new FinishTurnHandler(facade);
	private AbstractMoveHandler maritimeTradeHandler = new MaritimeTradeHandler(facade);
	private AbstractMoveHandler monopolyHandler = new MonopolyHandler(facade);
	private AbstractMoveHandler monumentHandler = new MonumentHandler(facade);
	private AbstractMoveHandler offerTradeHandler = new OfferTradeHandler(facade);
	private AbstractMoveHandler roadBuildingHandler = new RoadBuildingHandler(facade);
	private AbstractMoveHandler robPlayerHandler = new RobPlayerHandler(facade);
	private AbstractMoveHandler rollNumberHandler = new RollNumberHandler(facade);
	private AbstractMoveHandler sendChatHandler = new SendChatHandler(facade);
	private AbstractMoveHandler soldierHandler = new SoldierHandler(facade);
	private AbstractMoveHandler yearOfPlentyHandler = new YearOfPlentyHandler(facade);
	private AbstractMoveHandler loginHandler = new LoginHandler(facade);
	private AbstractMoveHandler joinHandler = new JoinHandler(facade);
}
