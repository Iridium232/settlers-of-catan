package server.movehandlers;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import server.facade.ServerFacade;
import shared.communication.toServer.moves.FinishTurn;
import shared.communication.toServer.moves.MaritimeTrade;
import sun.net.www.protocol.http.HttpURLConnection;


public class FinishTurnHandler extends AbstractMoveHandler {

	public FinishTurnHandler(ServerFacade facade, int cOMMANDS_BEFORE_SAVE) {
		super(facade,cOMMANDS_BEFORE_SAVE);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Finish Turn Handler for the server
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
			FinishTurn move = gson.fromJson(writer.toString(), FinishTurn.class);
			server.commands.FinishTurn command = new server.commands.FinishTurn(gameID);
			command.setParams(move);
			command.execute(server);
			server.addCommand(gameID, command);
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
