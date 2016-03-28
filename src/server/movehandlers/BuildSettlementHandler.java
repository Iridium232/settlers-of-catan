package server.movehandlers;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import server.facade.ServerFacade;
import shared.communication.toServer.moves.BuildSettlement;
import shared.communication.toServer.moves.BuyDevCard;
import sun.net.www.protocol.http.HttpURLConnection;

/**
 * handles a buildSettlement request
 * @author Doug
 *
 */
public class BuildSettlementHandler extends AbstractMoveHandler {
	public BuildSettlementHandler(ServerFacade facade) {
		super(facade);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Settlement Build Handler
	 * 
	 * @pre none
	 * @post a new Build Settlement command is created and added to the list.
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
			BuildSettlement move = gson.fromJson(writer.toString(), BuildSettlement.class);
			server.commands.BuildSettlement command = new server.commands.BuildSettlement(server);
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
