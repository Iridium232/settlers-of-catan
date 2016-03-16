package server.handlers;

<<<<<<< HEAD
import java.io.IOException;

import javax.xml.ws.spi.http.HttpExchange;
import javax.xml.ws.spi.http.HttpHandler;

public class AcceptTradeHandler extends HttpHandler {
	/**
	 * Handles an accept trade request from the client and creates an accept trade request command.
	 * @post A new accepttrade command is created and executed with the proper values
	 * @author Doug
	 *
	 */
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		// TODO Auto-generated method stub

=======
/**
 * 
 * Handler that generates and sends the command for an accept Trade
 * action
 *
 */
public class AcceptTradeHandler implements IHandler {

	@Override
	public Object handle() {
		// TODO Auto-generated method stub
		return null;
>>>>>>> 5f8eee25c70b212b91a2cc394ea620b6d7283411
	}

}
