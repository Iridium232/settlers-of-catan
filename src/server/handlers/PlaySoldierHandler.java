package server.handlers;

import java.io.IOException;

import client.communication.IServer;
import com.sun.net.httpserver.HttpExchange;

/**
 *
 * Handler that generates and sends the command for a
 * action
 *
 */
public class PlaySoldierHandler extends AbstractHandler{

	public PlaySoldierHandler(IServer facade) {
		super(facade);
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {

	}

}