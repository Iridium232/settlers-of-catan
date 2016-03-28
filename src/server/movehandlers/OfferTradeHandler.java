package server.movehandlers;


import java.io.IOException;

import client.communication.IServer;
import com.sun.net.httpserver.HttpExchange;

/**
 * 
 * Handler that generates and sends the command for an offer trade handler
 * action
 *
 */
public class OfferTradeHandler extends AbstractMoveHandler{

	public OfferTradeHandler(IServer facade) {
		super(facade);
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
	}
	
}