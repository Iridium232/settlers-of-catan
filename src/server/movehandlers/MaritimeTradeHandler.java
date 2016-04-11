package server.movehandlers;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;

import shared.communication.toServer.moves.MaritimeTrade;
import shared.communication.toServer.moves.Monopoly_;
import sun.net.www.protocol.http.HttpURLConnection;

import client.communication.IServer;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

/**
 * 
 * Handler that generates and sends the command for a maritime trade
 * action
 *
 */
public class MaritimeTradeHandler extends AbstractMoveHandler{

	public MaritimeTradeHandler(IServer facade, int cOMMANDS_BEFORE_SAVE) {
		super(facade,cOMMANDS_BEFORE_SAVE);
	}

	/**
	 * Maritime Trade Handler for the server
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
			Gson gson=new Gson();
			StringWriter writer = new StringWriter();
			IOUtils.copy(exchange.getRequestBody(), writer);
			MaritimeTrade move = gson.fromJson(writer.toString(), MaritimeTrade.class);
			server.commands.MaritimeTrade command = new server.commands.MaritimeTrade(server,gameID);
			command.setParams(move);
			command.execute();
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