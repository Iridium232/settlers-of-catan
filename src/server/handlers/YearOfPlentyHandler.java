package server.handlers;

import java.io.IOException;

import client.communication.IServer;
import com.sun.net.httpserver.HttpExchange;

/**
 *
 * Handler that generates and sends the command for a Play Year of Plenty
 * action
 *
 */
public class YearOfPlentyHandler extends AbstractHandler{

	public YearOfPlentyHandler(IServer facade) {
		super(facade);
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {

	}

}