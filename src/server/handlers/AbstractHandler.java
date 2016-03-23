package server.handlers;

import java.io.IOException;

import javax.xml.ws.spi.http.HttpExchange;
import javax.xml.ws.spi.http.HttpHandler;

import client.communication.IServer;

public abstract class AbstractHandler extends HttpHandler {
	IServer server;
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		// TODO Auto-generated method stub

	}

}
