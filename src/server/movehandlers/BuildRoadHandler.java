package server.movehandlers;


import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

import server.facade.ServerFacade;


public class BuildRoadHandler extends AbstractMoveHandler {
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
