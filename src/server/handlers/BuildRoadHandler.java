package server.handlers;


import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

import server.facade.ServerFacade;


public class BuildRoadHandler extends AbstractHandler {
	public BuildRoadHandler(ServerFacade facade) {
		super(facade);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @post a build road command is created and added to the command list
	 * @author Doug
	 *
	 */
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
	}
}
