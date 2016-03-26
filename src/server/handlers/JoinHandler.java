package server.handlers;

import java.io.IOException;

import client.communication.IServer;
import com.sun.net.httpserver.HttpExchange;

/**
 * 
 * Handler that generates and sends the command for a Join
 * action
 *
 */
public class JoinHandler extends AbstractHandler{

	public JoinHandler(IServer facade) {
		super(facade);
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
	}
	
}
