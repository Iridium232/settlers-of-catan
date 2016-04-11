package server.movehandlers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import com.google.gson.Gson;
import com.sun.net.httpserver.*;

import client.communication.IServer;
import server.commands.Command;
import server.facade.User;
import server.gamehandlers.AbstractGameHandler;


public abstract class AbstractMoveHandler extends AbstractGameHandler implements HttpHandler {
	public AbstractMoveHandler(IServer facade, int cOMMANDS_BEFORE_SAVE)
	{
		super(facade, cOMMANDS_BEFORE_SAVE);
		server=facade;
	}
	protected int checkCookie(HttpExchange exchange) throws UnsupportedEncodingException {
		int gameID=0;
		List<String> cookies=exchange.getRequestHeaders().get("Cookie");
		if(cookies.size()!=1){
			return -1;
		}
		String encodedCookie=cookies.get(0);
		String decodedCookie=URLDecoder.decode(encodedCookie, "UTF-8");
		decodedCookie = decodedCookie.substring(11);
		int locationOfSemicolon = decodedCookie.indexOf(';');
		String userCookie = decodedCookie.substring(0,locationOfSemicolon);
		String gameCookie = decodedCookie.substring(locationOfSemicolon+1);
		Gson gson=new Gson();
		User user=gson.fromJson(userCookie, User.class);
		if(server.login(user.getName(), user.getPassword())==null) return -1;
		gameCookie=gameCookie.substring(gameCookie.indexOf('=')+1);
		if(server.getGameModelByID(gameID)==null) return -1;
		//compare model number
		gameID=Integer.parseInt(gameCookie);
		return gameID;
	}
	
	public void addCommand(Command command, int game_id)
	{
		server.addCommand(game_id, command);
	}
	
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
	}
}
