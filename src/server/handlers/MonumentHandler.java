package server.handlers;


import java.io.IOException;

import client.communication.IServer;
import com.sun.net.httpserver.HttpExchange;

public class MonumentHandler extends AbstractHandler{

	public MonumentHandler(IServer facade) {
		super(facade);
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
	}
	
}