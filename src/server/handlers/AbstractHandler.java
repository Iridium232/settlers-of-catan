package server.handlers;

import java.io.IOException;

import com.sun.net.httpserver.*;

import client.communication.IServer;
import server.facade.ServerFacade;

public abstract class AbstractHandler implements HttpHandler {
	IServer server;
	public AbstractHandler(IServer facade){
		server=facade;
	}
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
	}
}
