package server.movehandlers;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import server.facade.ServerFacade;
import shared.communication.toServer.moves.BuyDevCard;
import shared.communication.toServer.moves.DiscardCards;
import sun.net.www.protocol.http.HttpURLConnection;



public class BuyDevCardHandler extends AbstractMoveHandler 
{
	public BuyDevCardHandler(ServerFacade facade) {
		super(facade);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Handler for buying a Dev Card
	 * 
	 * @post handles a BuyDevCard request and creates a buydevcard command. 
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
			BuyDevCard move = gson.fromJson(writer.toString(), BuyDevCard.class);
			server.commands.BuyDevCard command = new server.commands.BuyDevCard(server);
			command.setParams(move);
			command.execute();
			OutputStreamWriter output = new OutputStreamWriter(
				exchange.getResponseBody());
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
