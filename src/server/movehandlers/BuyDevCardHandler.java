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
	public BuyDevCardHandler(ServerFacade facade, int cOMMANDS_BEFORE_SAVE) {
		super(facade,cOMMANDS_BEFORE_SAVE);
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
			int gameID=checkCookie(exchange);
			if(gameID== -1)
			{
				System.err.print("\nInvalid Cookie. Thowing Error");
				throw new Exception("INVALID COOKIE!");
			}
			//gameID = WHICH????
			Gson gson=new Gson();
			StringWriter writer = new StringWriter();
			IOUtils.copy(exchange.getRequestBody(), writer);
			BuyDevCard move = gson.fromJson(writer.toString(), BuyDevCard.class);
			server.commands.BuyDevCard command = new server.commands.BuyDevCard(gameID);
			command.setParams(move);
			command.execute(server);
			this.addCommand(command, gameID);
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
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
