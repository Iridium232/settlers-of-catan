package server.movehandlers;

import java.io.IOException;

import client.communication.IServer;
import com.sun.net.httpserver.HttpExchange;

public class SendChatHandler extends AbstractMoveHandler {

	public SendChatHandler(IServer facade) {
		super(facade);
	}

	/**
	 * 
	 * Handler that generates and sends the command for a Send Chat
	 * action
	 *
	 */
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		// TODO Auto-generated method stub
		

	}

}
