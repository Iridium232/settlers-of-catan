package server.movehandlers;

import java.io.IOException;

import client.communication.IServer;
import com.sun.net.httpserver.HttpExchange;


public class RobPlayerHandler extends AbstractMoveHandler {

	public RobPlayerHandler(IServer facade) {
		super(facade);
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		// TODO Auto-generated method stub

	}

}