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
	IServer server;
	public AbstractGameHandler(IServer s){
		server=s;
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
			decodedCookie = decodedCookie.substring(10);
			System.out.println(decodedCookie);
			Gson gson=new Gson();
			Credentials user=gson.fromJson(decodedCookie, Credentials.class);
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
				decodedCookie=URLDecoder.decode(encodedCookie,"UTF-8");
				decodedCookie=decodedCookie.substring(10);
				Gson gson=new Gson();
				Credentials user=gson.fromJson(decodedCookie, Credentials.class);
				User cur=new User();
				cur.setName(user.getUsername());
				cur.setPassword(user.getPassword());
				return cur;
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
