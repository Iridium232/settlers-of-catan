package server.handlers;

<<<<<<< HEAD
import java.io.IOException;

import javax.xml.ws.spi.http.HttpExchange;
import javax.xml.ws.spi.http.HttpHandler;

public class DiscardCardsHandler extends HttpHandler {
/**
 * @post creates a new Discard card command and executes it. 
 */
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		// TODO Auto-generated method stub

=======
/**
 * 
 * Handler that generates and sends the command for a Discard Cards
 * action
 *
 */
public class DiscardCardsHandler implements IHandler {

	@Override
	public Object handle() {
		// TODO Auto-generated method stub
		return null;
>>>>>>> 5f8eee25c70b212b91a2cc394ea620b6d7283411
	}

}
