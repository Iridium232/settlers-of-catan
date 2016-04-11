package server.movehandlers;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;

import shared.communication.toServer.moves.Monopoly_;
import sun.net.www.protocol.http.HttpURLConnection;

import client.communication.IServer;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

public class ListAIHandler extends AbstractMoveHandler{

	public ListAIHandler(IServer facade, int cOMMANDS_BEFORE_SAVE) {
		super(facade,cOMMANDS_BEFORE_SAVE);
	}

	/**
	 * ListAIHandler
	 */
	@Override
	public void handle(HttpExchange exchange) throws IOException 
	{
		exchange.getResponseHeaders().set("Content-type","application/text");
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
			OutputStreamWriter output = new OutputStreamWriter(
					exchange.getResponseBody());
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			output.write("[No AI Available]");
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
