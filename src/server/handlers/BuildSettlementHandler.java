package server.handlers;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

import server.facade.ServerFacade;

/**
 * handles a buildSettlement request
 * @author Doug
 *
 */
public class BuildSettlementHandler extends AbstractHandler {
public BuildSettlementHandler(ServerFacade facade) {
		super(facade);
		// TODO Auto-generated constructor stub
	}

/**
 * @post a new Build Settlement command is created and added to the list.
 */
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
	}
}
