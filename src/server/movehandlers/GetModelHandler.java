package server.movehandlers;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;

import shared.communication.Serializer;
import shared.communication.toServer.moves.MaritimeTrade;
import sun.net.www.protocol.http.HttpURLConnection;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import client.communication.IServer;


public class GetModelHandler extends AbstractMoveHandler {

	public GetModelHandler(IServer facade, int cOMMANDS_BEFORE_SAVE) {
		super(facade,cOMMANDS_BEFORE_SAVE);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Get model Handler for the server
	 */
	@Override
	public void handle(HttpExchange exchange) throws IOException 
	{
		exchange.getResponseHeaders().set("Content-type","application/json");
		try
		{
			int gameID=checkCookie(exchange);
			if(gameID==-1)
			{
				System.err.print("\nInvalid Cookie. Thowing Error");
				throw new Exception("INVALID COOKIE!");
			}
			Gson gson=new Gson();
			StringWriter writer = new StringWriter();
			IOUtils.copy(exchange.getRequestBody(), writer);
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			OutputStreamWriter output = new OutputStreamWriter(
					exchange.getResponseBody());
            String result = server.getModel(gameID);
			output.write(result);
			output.flush();
			exchange.getResponseBody().close();
			exchange.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, -1);
			exchange.getResponseBody().close();
			exchange.close();
		}
	}

}
