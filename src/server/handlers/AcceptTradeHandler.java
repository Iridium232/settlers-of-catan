package server.handlers;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

public class AcceptTradeHandler extends AbstractHandler {
	/**
	 * Handles an accept trade request from the client and creates an accept trade request command.
	 * @post A new accepttrade command is created and executed with the proper values
	 * @author Doug
	 *
	 */
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
	}

}
