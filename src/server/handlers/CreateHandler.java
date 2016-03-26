package server.handlers;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

import server.facade.ServerFacade;

/**
 * 
 * Handler that generates and sends the command for a Create Game
 * action
 *
 */
public class CreateHandler extends AbstractHandler {

	public CreateHandler(ServerFacade facade) {
		super(facade);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
	}
}
