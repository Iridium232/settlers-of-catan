package server.handlers;

<<<<<<< HEAD
import java.io.IOException;

import javax.xml.ws.spi.http.HttpExchange;
import javax.xml.ws.spi.http.HttpHandler;

public class BuyDevCardHandler extends HttpHandler {
/**
 * @post handles a BuyDevCard request and creates a buydevcard command. 
 */
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		// TODO Auto-generated method stub

=======
/**
 * 
 * Handler that generates and sends the command for a buy dev card
 * action
 *
 */
public class BuyDevCardHandler implements IHandler {

	@Override
	public Object handle() {
		// TODO Auto-generated method stub
		return null;
>>>>>>> 5f8eee25c70b212b91a2cc394ea620b6d7283411
	}

}
