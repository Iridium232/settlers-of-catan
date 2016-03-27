package server.movehandlers;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

import server.facade.ServerFacade;
import server.gamehandlers.AbstractGameHandler;

public class AddAIHandler extends AbstractMoveHandler {
	public AddAIHandler(ServerFacade facade) {
		super(facade);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @post Adds an AI to the selected game. 
	 * @author Doug
	 *
	 */
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		// TODO Auto-generated method stub
		
	}

}
