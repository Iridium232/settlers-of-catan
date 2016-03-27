package server.movehandlers;


import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

import server.facade.ServerFacade;


public class DiscardCardsHandler extends AbstractMoveHandler {
public DiscardCardsHandler(ServerFacade facade) {
		super(facade);
		// TODO Auto-generated constructor stub
	}

/**
 * @post creates a new Discard card command and executes it. 
 */
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		// TODO Auto-generated method stub


	}

}
