package server.handlers;

import java.io.IOException;

import javax.xml.ws.spi.http.HttpExchange;
import javax.xml.ws.spi.http.HttpHandler;

public class AcceptTradeHandler extends HttpHandler {
	/**
	 * Handles an accept trade request from the client and creates an accept trade request command.
	 * @post A new accepttrade command is created and executed with the proper values
	 * @author Doug
	 *
	 */
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		// TODO Auto-generated method stub

	}

}
