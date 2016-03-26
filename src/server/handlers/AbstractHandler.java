package server.handlers;

import java.io.IOException;
import java.util.List;

import com.sun.net.httpserver.*;

import client.communication.IServer;
import server.facade.ServerFacade;

public abstract class AbstractHandler implements HttpHandler {
	IServer server;
	public AbstractHandler(IServer facade){
		server=facade;
	}
	protected int checkCookie(HttpExchange exchange) {
		int gameID=0;
		List<String> cookies=exchange.getRequestHeaders().get("Cookie");
		String encodedCookie=cookies.get(0);
		System.out.println(encodedCookie);
		return gameID;
	}
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
	}
}
