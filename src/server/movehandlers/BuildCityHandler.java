package server.movehandlers;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

import server.facade.ServerFacade;

public class BuildCityHandler extends AbstractMoveHandler {
	public BuildCityHandler(ServerFacade facade) {
		super(facade);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @post a build city command is created and executed on the proper coordinates with the correct owner
	 * @author Doug
	 *
	 */
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
	}

}
