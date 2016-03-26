package server.handlers;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

import server.facade.ServerFacade;

/**
 * Handles a create game request from the client
 * @author Doug
 *
 */
public class CreateGameHandler extends AbstractHandler {
public CreateGameHandler(ServerFacade facade) {
		super(facade);
		// TODO Auto-generated constructor stub
	}

/**
 * @post Creates a new game on the server
 */
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		// TODO Auto-generated method stub
		
	}

}
