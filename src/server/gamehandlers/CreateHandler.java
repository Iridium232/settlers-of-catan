package server.gamehandlers;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import server.facade.ServerFacade;
import server.facade.User;
import server.movehandlers.AbstractMoveHandler;
import shared.communication.toServer.games.JoinGameRequest;

/**
 * 
 * Handler that generates and sends the command for a Create Game
 * action
 *
 */
public class CreateHandler extends AbstractGameHandler {

	public CreateHandler(ServerFacade facade) {
		super(facade);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		exchange.getResponseHeaders().set("Content-type","application/text");
		try{
			if(!checkCookie(exchange, server)){
				throw new Exception();
			}
			User player=this.getUserFromCookie(exchange, server);
			int gameID=0;
			Gson gson=new Gson();
			StringWriter writer = new StringWriter();
			IOUtils.copy(exchange.getRequestBody(), writer);
			JoinGameRequest join=gson.fromJson(writer.toString(), JoinGameRequest.class);

		} catch
	}
}
