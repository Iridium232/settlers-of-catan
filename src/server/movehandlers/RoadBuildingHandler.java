package server.movehandlers;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;

import server.commands.Road_Building;
import shared.communication.toServer.moves.Road_Building_;
import shared.communication.toServer.moves.RobPlayer;
import sun.net.www.protocol.http.HttpURLConnection;

import client.communication.IServer;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

/**
 * 
 * Handler that generates and sends the command for a Road Building card played
 * action
 *
 */
public class RoadBuildingHandler extends AbstractMoveHandler{

	public RoadBuildingHandler(IServer facade) {
		super(facade);
	}

	/**
	 * Handler for Road Building requests to the server
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
			Road_Building_ move = gson.fromJson(writer.toString(), Road_Building_.class);
			Road_Building command = new server.commands.Road_Building(server);
			command.setParams(move);
			command.execute();
			OutputStreamWriter output = new OutputStreamWriter(
					exchange.getResponseBody());
			output.write(server.getModel(gameID));
			output.flush();
			exchange.getResponseBody().close();
			exchange.close();
		}
		catch(Exception e)
		{
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, -1);
			exchange.getResponseBody().close();
			exchange.close();
		}
	}
}
