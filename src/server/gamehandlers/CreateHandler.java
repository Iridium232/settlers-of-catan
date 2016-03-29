package server.gamehandlers;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;

import com.sun.org.apache.bcel.internal.generic.NEW;
import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import server.facade.ServerFacade;
import server.facade.User;
import server.movehandlers.AbstractMoveHandler;
import shared.communication.fromServer.games.NewGame;
import shared.communication.toServer.games.CreateGameRequest;
import shared.communication.toServer.games.JoinGameRequest;
import sun.net.www.protocol.http.HttpURLConnection;

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
//			if(!checkCookie(exchange, server)){
//				throw new Exception();
//			}
			Gson gson=new Gson();
			StringWriter writer = new StringWriter();
			IOUtils.copy(exchange.getRequestBody(), writer);
			CreateGameRequest create=gson.fromJson(writer.toString(), CreateGameRequest.class);
			String response = server.createGameCommand(create);
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            OutputStreamWriter output=new OutputStreamWriter(exchange.getResponseBody());
            output.write(response);
            output.close();
			exchange.close();
		} 
		catch(Exception e)
		{
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_FORBIDDEN, -1);
			exchange.getResponseBody().close();
			exchange.close();
		}
	}
}
