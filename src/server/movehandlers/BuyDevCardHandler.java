package server.movehandlers;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

import server.facade.ServerFacade;



public class BuyDevCardHandler extends AbstractMoveHandler {
public BuyDevCardHandler(ServerFacade facade) {
		super(facade);
		// TODO Auto-generated constructor stub
	}

/**
 * @post handles a BuyDevCard request and creates a buydevcard command. 
 */
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
	}

}
