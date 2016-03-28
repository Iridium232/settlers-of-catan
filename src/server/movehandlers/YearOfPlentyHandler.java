package server.movehandlers;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.URLEncoder;

import org.apache.commons.io.IOUtils;

import server.commands.Year_of_Plenty;
import server.facade.User;
import shared.communication.toServer.games.JoinGameRequest;
import shared.communication.toServer.moves.Year_of_Plenty_;
import sun.net.www.protocol.http.HttpURLConnection;

import client.communication.IServer;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

/**
 *
 * Handler that generates and sends the command for a Play Year of Plenty
 * action
 *
 */
public class YearOfPlentyHandler extends AbstractMoveHandler{

	public YearOfPlentyHandler(IServer facade) {
		super(facade);
	}

	/**
	 * Handles Year of Plenty Requests to the server
	 */
	@Override
	public void handle(HttpExchange exchange) throws IOException 
	{
		exchange.getResponseHeaders().set("Content-type","application/json");
		try
		{
			if(checkCookie(exchange) == -1)
			{
				System.err.print("\nInvalid Cookie. Thowing Error");
				throw new Exception("INVALID COOKIE!");
			}
			int gameID = 0;
			//gameID = WHICH????
			Gson gson=new Gson();
			StringWriter writer = new StringWriter();
			IOUtils.copy(exchange.getRequestBody(), writer);
			shared.communication.toServer.moves.Year_of_Plenty_ move =
					gson.fromJson(writer.toString(), Year_of_Plenty_.class);
			Year_of_Plenty command = new server.commands.Year_of_Plenty(server);
			command.setParams(move);
			command.execute();
			OutputStreamWriter output = new OutputStreamWriter(exchange.getResponseBody());
			output.write(server.getModel(gameID));
			output.flush();
			exchange.getResponseBody().close();
		}
		catch(Exception e)
		{
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, -1);
			exchange.getResponseBody().close();
		}
	}

}