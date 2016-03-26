package server.handlers;


import java.io.IOException;

import client.communication.IServer;
import com.sun.net.httpserver.HttpExchange;


public class RegisterHandler extends AbstractHandler {

	public RegisterHandler(IServer facade) {
		super(facade);
	}

	/**
	 * 
	 * Handler that generates and sends the command for a Register
	 * action
	 *
	 */
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		// TODO Auto-generated method stub
		


	}

}
