package server.movehandlers;

import java.io.IOException;

import client.communication.IServer;
import com.sun.net.httpserver.HttpExchange;

/**
 * 
 * Handler that generates and sends the command for a 
 * action
 *
 */
public class MoveRobberHandler extends AbstractMoveHandler{

	public MoveRobberHandler(IServer facade) {
		super(facade);
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
	}
	
}