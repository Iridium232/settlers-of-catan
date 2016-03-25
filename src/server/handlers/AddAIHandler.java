package server.handlers;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

import server.facade.ServerFacade;

public class AddAIHandler extends AbstractHandler {
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
		
	}

}
