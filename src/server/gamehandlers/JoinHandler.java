package server.gamehandlers;

import java.io.IOException;
import java.io.OutputStreamWriter;
import client.communication.IServer;
import server.facade.User;
import sun.net.www.protocol.http.HttpURLConnection;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

/**
 * 
 * Handler that generates and sends the command for a Join
 * action
 *
 */
public class JoinHandler extends AbstractGameHandler{

	public JoinHandler(IServer facade) {
		super(facade);
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		try{
			if(!checkCookie(exchange, server)){
				throw new Exception();
			}
			User player=this.getUserFromCookie(exchange, server);
			int gameID=0;
			Gson gson=new Gson();
			StringBuilder sb=new StringBuilder();
			sb.append("catan.user");
			sb.append("username"+player.getName()+"password"+player.getPassword()+"playerID"+player.getPlayerID());
			sb.append(";Path=/;");
			sb.append("Catan.game="+gameID+";Path=/;");
			String cookie=sb.toString();
			exchange.getResponseHeaders().add("Set-cookie",cookie);
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			OutputStreamWriter output = new OutputStreamWriter(exchange.getResponseBody());
			output.write(sb.toString());
			output.flush();
			exchange.getResponseBody().close();
		}catch(Exception e){
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			exchange.getResponseBody().close();
		}
	}
	
}
