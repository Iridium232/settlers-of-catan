package server.movehandlers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import com.google.gson.Gson;
import com.sun.net.httpserver.*;

import client.communication.IServer;
import server.facade.User;
import server.gamehandlers.AbstractGameHandler;

import shared.communication.toServer.user.Credentials;

public abstract class AbstractMoveHandler extends AbstractGameHandler implements HttpHandler {
	public AbstractMoveHandler(IServer facade)
	{
		super(facade);
		server=facade;
	}
	protected int checkCookie(HttpExchange exchange) throws UnsupportedEncodingException {
		int gameID=0;
		List<String> cookies=exchange.getRequestHeaders().get("Cookie");
		if(cookies.size()!=1){
			
		}
		String encodedCookie=cookies.get(0);
		String decodedCookie=URLDecoder.decode(encodedCookie, "UTF-8");
		decodedCookie = decodedCookie.substring(10);
		System.out.println(decodedCookie);
		int locationOfSemicolon = decodedCookie.indexOf(';');
		String userCookie = decodedCookie.substring(0,locationOfSemicolon);
		String gameCookie = decodedCookie.substring(locationOfSemicolon);
		Gson gson=new Gson();
		User user=gson.fromJson(decodedCookie, User.class);
		if(server.login(user.getName(), user.getPassword())==null) return -1;
		gson.fromJson(gameCookie, Integer.TYPE);
		//compare model number
		System.out.println(gameCookie);
		return gameID;
	}
	
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
	}
}
