package server.movehandlers;

import java.io.IOException;

import client.communication.IServer;

import com.sun.net.httpserver.HttpExchange;

public class ListAIHandler extends AbstractMoveHandler{

	public ListAIHandler(IServer facade) {
		super(facade);
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
	}
	
}
