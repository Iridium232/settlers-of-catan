package server.handlers;

import java.io.IOException;
import java.io.StringWriter;
import java.net.HttpURLConnection;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import server.facade.ServerFacade;
import shared.communication.toServer.moves.AcceptTrade;


public class AcceptTradeHandler extends AbstractHandler {
	public AcceptTradeHandler(ServerFacade facade) {
		super(facade);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Handles an accept trade request from the client and creates an accept trade request command.
	 * @post A new accepttrade command is created and executed with the proper values
	 * @author Doug
	 *
	 */
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		Gson gson = new Gson();
		StringWriter writer = new StringWriter();
		IOUtils.copy(exchange.getRequestBody(), writer);
		AcceptTrade accept=gson.fromJson(writer.toString(),AcceptTrade.class);		
		checkCookie(exchange);
		exchange.getResponseBody();
	}

}
