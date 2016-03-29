package server.gamehandlers;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.util.List;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import server.facade.ServerFacade;
import server.movehandlers.AbstractMoveHandler;
import shared.communication.fromServer.games.Game;

public class GameListHandler extends AbstractGameHandler {

	public GameListHandler(ServerFacade facade) {
		super(facade);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		// TODO Auto-generated method stub
		if(checkCookie(exchange, server)) {
			exchange.getResponseHeaders().set("Content-type","application/text");
			Game[] games =  server.getGameList().toArray(new 
					shared.communication.fromServer.games.Game[server.getGameList().size()]);
			Gson gson=new Gson();	
			OutputStreamWriter output=new OutputStreamWriter(exchange.getResponseBody());
			output.write(gson.toJson(games));
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			exchange.getResponseBody().close();
			exchange.close();
		}
	}

}
