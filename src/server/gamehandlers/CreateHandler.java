package server.gamehandlers;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

import server.facade.ServerFacade;
import server.movehandlers.AbstractMoveHandler;

/**
 * 
 * Handler that generates and sends the command for a Create Game
 * action
 *
 */
public class CreateHandler extends AbstractGameHandler {

	public CreateHandler(ServerFacade facade) {
		super(facade);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
	}
}
