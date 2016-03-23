package server.handlers;

import java.io.IOException;

import com.sun.net.httpserver.*;

import client.communication.IServer;

public abstract class AbstractHandler implements HttpHandler {
	IServer server;
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
	}
}
