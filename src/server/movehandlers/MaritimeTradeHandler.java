package server.movehandlers;

import java.io.IOException;

import client.communication.IServer;
import com.sun.net.httpserver.HttpExchange;

/**
 * 
 * Handler that generates and sends the command for a maritime trade
 * action
 *
 */
public class MaritimeTradeHandler extends AbstractMoveHandler{

	public MaritimeTradeHandler(IServer facade) {
		super(facade);
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
	}
	
}