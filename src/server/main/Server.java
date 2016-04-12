package server.main;

import com.sun.net.httpserver.HttpServer;

import server.facade.ServerFacade;
import server.gamehandlers.AbstractGameHandler;
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
import server.plugin_attachments.IGameDAO;
import server.plugin_attachments.IPersistenceProvider;
import server.plugin_attachments.IUserDAO;
import server.plugin_attachments.PluginInfo;
import server.plugin_attachments.PluginRegistry;
import server.userhandler.LoginHandler;
import server.userhandler.RegisterHandler;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.JarFile;


/**
 * Main Class for Catan Server
 *
 */
public class Server 
{
	private static  int SERVER_PORT_NUMBER = 8081;
	private static final int MAX_WAITING_CONNECTIONS = 20;
	private static String PERSISTENCE_TYPE = "NoSQL";
	private static int COMMANDS_BEFORE_SAVE = 10;
	private static final String PLUGIN_REGISTRY_RELATIVE_PATH = "src/plugins.config";
	private static final boolean RESET = true;
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
		
		int n = 10;
		String port = "8081";
		String plugin = "NoSQL";

		if(args.length > 0)
		{
			port = args[0];
			System.out.print("Port: " + port +".\n");
		}
		else
		{
			System.out.print("\nNo port specified. Using default '8081'.\n");
		}
		Server.SERVER_PORT_NUMBER = new Integer(port).intValue();
		if(args.length > 1)
		{
			plugin = args[1];
			System.out.print("Persistence Plugin: " + plugin + "\n");
		}
		else
		{
			System.out.print("No Persistence Plugin Name Given. Using Default: NoSQL");
		}
		if(args.length > 2)
		{
			String commandcount = args[2];
			COMMANDS_BEFORE_SAVE = Integer.parseInt(commandcount);
			System.out.print("Running client with command_count of " + COMMANDS_BEFORE_SAVE + ".\n");
		}
		else
		{
			System.out.print("\nNo command count given, using default: 10.");
		}
		
		Server.PERSISTENCE_TYPE = plugin;
		
		new Server().run();
		System.out.print("\nServer Running Happily. Have fun!\n");	
		
	}
	private void run() {
		facade=new ServerFacade(COMMANDS_BEFORE_SAVE);
		try {
			server = HttpServer.create(new InetSocketAddress(SERVER_PORT_NUMBER),MAX_WAITING_CONNECTIONS);
		} 
		catch (IOException e) {
			return;
		}
		this.facade.setN(this.COMMANDS_BEFORE_SAVE);
		try
		{
			PluginRegistry registry = PluginRegistry.getSingleton();
			registry.LoadInPlugins(this.PLUGIN_REGISTRY_RELATIVE_PATH);
			PluginInfo info = registry.getPluginByName(PERSISTENCE_TYPE);
			System.out.print("\nUsing " + info.getPlugin_name() + " persistence Provider.\n");

			//http://stackoverflow.com/questions/60764/how-should-i-load-jars-dynamically-at-runtime
			File file = new File(info.getJar_relative_uri());
			if (file.exists())
			{
				System.err.print("FOUND THE JAR!");
			}
			else
			{
				System.err.print("NO JAR!");
			}
			ClassLoader loader = new URLClassLoader(
				    new URL[] { file.toURI().toURL()}//,
				    //this.getClass().getClassLoader()
				);
			
			System.out.print("\nLoading Plugin class: '"  + info.getMain_class_name() + "'\n");
			Class<? extends IPersistenceProvider> c = 
					(Class<? extends IPersistenceProvider>) Class.forName(
							info.getMain_class_name(),true,loader
							);
			//end StackOverflow references
			
			IPersistenceProvider pp =  c.newInstance();
			if(this.RESET)
			{
				IGameDAO games = pp.generateGameDAO();
				IUserDAO users = pp.generateIUserDAO();
				games.eraseAll();
				users.clearAll();
			}
			facade.addPersistence(pp);
		}
		catch (Exception e)
		{
			System.out.print("\nERROR loading persistence plugins\n");
			e.printStackTrace();
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
	
	private AbstractGameHandler listHandler = new GameListHandler(facade, this.COMMANDS_BEFORE_SAVE);
	private AbstractMoveHandler getAITypeHandler = new ListAIHandler(facade, this.COMMANDS_BEFORE_SAVE);
	private AbstractMoveHandler modelHandler = new GetModelHandler(facade, this.COMMANDS_BEFORE_SAVE);
	private AbstractGameHandler createHandler = new CreateHandler(facade, this.COMMANDS_BEFORE_SAVE);
	private AbstractMoveHandler registerHandler = new RegisterHandler(facade, this.COMMANDS_BEFORE_SAVE);
	private AbstractMoveHandler acceptTradeHandler = new AcceptTradeHandler(facade, this.COMMANDS_BEFORE_SAVE);
	private AbstractMoveHandler buildCityHandler = new BuildCityHandler(facade, this.COMMANDS_BEFORE_SAVE);
	private AbstractMoveHandler buildRoadHandler = new BuildRoadHandler(facade, this.COMMANDS_BEFORE_SAVE);
	private AbstractMoveHandler buildSettlementHandler = new BuildSettlementHandler(facade, this.COMMANDS_BEFORE_SAVE);
	private AbstractMoveHandler buyDevCardHandler = new BuyDevCardHandler(facade, this.COMMANDS_BEFORE_SAVE);
	private AbstractMoveHandler discardCardsHandler = new DiscardCardsHandler(facade, this.COMMANDS_BEFORE_SAVE);
	private AbstractMoveHandler finishTurnHandler = new FinishTurnHandler(facade, this.COMMANDS_BEFORE_SAVE);
	private AbstractMoveHandler maritimeTradeHandler = new MaritimeTradeHandler(facade, this.COMMANDS_BEFORE_SAVE);
	private AbstractMoveHandler monopolyHandler = new MonopolyHandler(facade, this.COMMANDS_BEFORE_SAVE);
	private AbstractMoveHandler monumentHandler = new MonumentHandler(facade, this.COMMANDS_BEFORE_SAVE);
	private AbstractMoveHandler offerTradeHandler = new OfferTradeHandler(facade, this.COMMANDS_BEFORE_SAVE);
	private AbstractMoveHandler roadBuildingHandler = new RoadBuildingHandler(facade, this.COMMANDS_BEFORE_SAVE);
	private AbstractMoveHandler robPlayerHandler = new RobPlayerHandler(facade, this.COMMANDS_BEFORE_SAVE);
	private AbstractMoveHandler rollNumberHandler = new RollNumberHandler(facade, this.COMMANDS_BEFORE_SAVE);
	private AbstractMoveHandler sendChatHandler = new SendChatHandler(facade, this.COMMANDS_BEFORE_SAVE);
	private AbstractMoveHandler soldierHandler = new SoldierHandler(facade, this.COMMANDS_BEFORE_SAVE);
	private AbstractMoveHandler yearOfPlentyHandler = new YearOfPlentyHandler(facade, this.COMMANDS_BEFORE_SAVE);
	private AbstractMoveHandler loginHandler = new LoginHandler(facade, this.COMMANDS_BEFORE_SAVE);
	private AbstractGameHandler joinHandler = new JoinHandler(facade, this.COMMANDS_BEFORE_SAVE);
}




