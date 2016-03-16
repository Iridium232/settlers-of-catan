package server.handlers;

<<<<<<< HEAD
import java.io.IOException;

import javax.xml.ws.spi.http.HttpExchange;
import javax.xml.ws.spi.http.HttpHandler;
/**
 * handles a buildSettlement request
 * @author Doug
 *
 */
public class BuildSettlementHandler extends HttpHandler {
/**
 * @post a new Build Settlement command is created and added to the list.
 */
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		// TODO Auto-generated method stub

=======
/**
 * 
 * Handler that generates and sends the command for a Build Settlement
 * action
 *
 */
public class BuildSettlementHandler implements IHandler {

	@Override
	public Object handle() {
		// TODO Auto-generated method stub
		return null;
>>>>>>> 5f8eee25c70b212b91a2cc394ea620b6d7283411
	}

}
