package server.main;

import com.sun.net.httpserver.HttpServer;

import server.facade.ServerFacade;
import server.handlers.AbstractHandler;
import server.handlers.AcceptTradeHandler;
import server.handlers.BuildCityHandler;
import server.handlers.BuildRoadHandler;
import server.handlers.BuildSettlementHandler;
import server.handlers.BuyDevCardHandler;
import server.handlers.CreateHandler;
import server.handlers.DiscardCardsHandler;
import server.handlers.FinishTurnHandler;
import server.handlers.GameListHandler;
import server.handlers.GetModelHandler;
import server.handlers.JoinHandler;
import server.handlers.ListAIHandler;
import server.handlers.LoginHandler;
import server.handlers.MaritimeTradeHandler;
import server.handlers.MonopolyHandler;
import server.handlers.MonumentHandler;
import server.handlers.OfferTradeHandler;
import server.handlers.RegisterHandler;
import server.handlers.RoadBuildingHandler;
import server.handlers.RobPlayerHandler;
import server.handlers.RollNumberHandler;
import server.handlers.SendChatHandler;
import server.handlers.SoldierHandler;
import server.handlers.YearOfPlentyHandler;
import server.handlers.*;

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
	
	private AbstractHandler listHandler = new GameListHandler(facade);
	private AbstractHandler getAITypeHandler = new ListAIHandler(facade);
	private AbstractHandler modelHandler = new GetModelHandler(facade);
	private AbstractHandler createHandler = new CreateHandler(facade);
	private AbstractHandler registerHandler = new RegisterHandler(facade);
	private AbstractHandler acceptTradeHandler = new AcceptTradeHandler(facade);
	private AbstractHandler buildCityHandler = new BuildCityHandler(facade);
	private AbstractHandler buildRoadHandler = new BuildRoadHandler(facade);
	private AbstractHandler buildSettlementHandler = new BuildSettlementHandler(facade);
	private AbstractHandler buyDevCardHandler = new BuyDevCardHandler(facade);
	private AbstractHandler discardCardsHandler = new DiscardCardsHandler(facade);
	private AbstractHandler finishTurnHandler = new FinishTurnHandler(facade);
	private AbstractHandler maritimeTradeHandler = new MaritimeTradeHandler(facade);
	private AbstractHandler monopolyHandler = new MonopolyHandler(facade);
	private AbstractHandler monumentHandler = new MonumentHandler(facade);
	private AbstractHandler offerTradeHandler = new OfferTradeHandler(facade);
	private AbstractHandler roadBuildingHandler = new RoadBuildingHandler(facade);
	private AbstractHandler robPlayerHandler = new RobPlayerHandler(facade);
	private AbstractHandler rollNumberHandler = new RollNumberHandler(facade);
	private AbstractHandler sendChatHandler = new SendChatHandler(facade);
	private AbstractHandler soldierHandler = new SoldierHandler(facade);
	private AbstractHandler yearOfPlentyHandler = new YearOfPlentyHandler(facade);
	private AbstractHandler loginHandler = new LoginHandler(facade);
	private AbstractHandler joinHandler = new JoinHandler(facade);
}
