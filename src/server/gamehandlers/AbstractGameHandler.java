package server.gamehandlers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import com.google.gson.Gson;
import com.sun.net.httpserver.*;

import client.communication.IServer;
import server.facade.User;
import shared.communication.toServer.user.Credentials;

public abstract class AbstractGameHandler implements HttpHandler {
	protected IServer server;
	protected int COMMAND_COUNT;
	
	public AbstractGameHandler(IServer s, int command_count){
		server=s;
		this.COMMAND_COUNT = command_count;
	}
	
	protected boolean checkCookie(HttpExchange exchange,IServer s){
		List<String> cookies=exchange.getRequestHeaders().get("Cookie");
		if(cookies.size()!=1){
			return false;
		}
		String encodedCookie=cookies.get(0);
		String decodedCookie;
		try {
			decodedCookie = URLDecoder.decode(encodedCookie, "UTF-8");
			decodedCookie = decodedCookie.substring(11);
			Gson gson=new Gson();
			Credentials user=gson.fromJson(decodedCookie, Credentials.class);
			if(server.login(user.getUsername(), user.getPassword())==null) {
				return false;
			}
			//check credentials server.loginCommand? throw if fail.
			return true;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
	
	protected User getUserFromCookie(HttpExchange exchange,IServer s){
		List<String> cookies=exchange.getRequestHeaders().get("Cookie");
		String encodedCookie=cookies.get(0);
		String decodedCookie;
			try {
				decodedCookie=URLDecoder.decode(encodedCookie, "UTF-8");
//				decodedCookie=decodedCookie.substring(11);
                int firstIndex = decodedCookie.indexOf("{");
                int lastIndex = decodedCookie.indexOf("}");
                decodedCookie = decodedCookie.substring(firstIndex, lastIndex + 1);
                System.out.println(decodedCookie);
				Gson gson=new Gson();
				User user=gson.fromJson(decodedCookie, User.class);
				return user;
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		
	}
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		// TODO Auto-generated method stub
		
	}


}
