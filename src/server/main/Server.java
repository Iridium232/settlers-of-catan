package server.main;

import com.sun.net.httpserver.HttpServer;

import server.handlers.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.Level;

import com.sun.net.httpserver.*;
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
	
	private AbstractHandler listHandler = new GameListHandler();
	private AbstractHandler getAITypeHandler = new ListAIHandler();
	private AbstractHandler modelHandler = new GetModelHandler();
	private AbstractHandler createHandler = new CreateHandler();
	private AbstractHandler registerHandler = new RegisterHandler();
	private AbstractHandler acceptTradeHandler = new AcceptTradeHandler();
	private AbstractHandler buildCityHandler = new BuildCityHandler();
	private AbstractHandler buildRoadHandler = new BuildRoadHandler();
	private AbstractHandler buildSettlementHandler = new BuildSettlementHandler();
	private AbstractHandler buyDevCardHandler = new BuyDevCardHandler();
	private AbstractHandler discardCardsHandler = new DiscardCardsHandler();
	private AbstractHandler finishTurnHandler = new FinishTurnHandler();
	private AbstractHandler maritimeTradeHandler = new MaritimeTradeHandler();
	private AbstractHandler monopolyHandler = new MonopolyHandler();
	private AbstractHandler monumentHandler = new MonumentHandler();
	private AbstractHandler offerTradeHandler = new OfferTradeHandler();
	private AbstractHandler roadBuildingHandler = new RoadBuildingHandler();
	private AbstractHandler robPlayerHandler = new RobPlayerHandler();
	private AbstractHandler rollNumberHandler = new RollNumberHandler();
	private AbstractHandler sendChatHandler = new SendChatHandler();
	private AbstractHandler soldierHandler = new SoldierHandler();
	private AbstractHandler yearOfPlentyHandler = new YearOfPlentyHandler();
	private AbstractHandler loginHandler = new LoginHandler();
	private AbstractHandler joinHandler = new JoinHandler();
}
