package server.gamehandlers;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

import server.facade.ServerFacade;
import server.movehandlers.AbstractMoveHandler;

public class GameListHandler extends AbstractGameHandler {

	public GameListHandler(ServerFacade facade) {
		super(facade);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		// TODO Auto-generated method stub
		
	}

}