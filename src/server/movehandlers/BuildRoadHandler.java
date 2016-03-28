package server.movehandlers;


import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import server.facade.ServerFacade;
import shared.communication.toServer.moves.BuildRoad;
import shared.communication.toServer.moves.BuyDevCard;
import sun.net.www.protocol.http.HttpURLConnection;


public class BuildRoadHandler extends AbstractMoveHandler {
	public BuildRoadHandler(ServerFacade facade) {
		super(facade);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Build Road Handler
	 * 
	 * 
	 * @pre none
	 * @post a build road command is created and executed
	 *
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
			BuildRoad move = gson.fromJson(writer.toString(), BuildRoad.class);
			server.commands.BuildRoad command = new server.commands.BuildRoad(server);
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
