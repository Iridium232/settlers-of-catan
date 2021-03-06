package server.gamehandlers;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.URLEncoder;

import org.apache.commons.io.IOUtils;

import client.communication.IServer;
import server.communication.ModelTranslator;
import server.facade.User;
import shared.communication.Serializer;
import shared.communication.fromServer.game.CommunicationModel;
import shared.communication.toServer.games.JoinGameRequest;
import shared.definitions.CatanColor;
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

	public JoinHandler(IServer facade, int cOMMANDS_BEFORE_SAVE) {
		super(facade,cOMMANDS_BEFORE_SAVE);
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		exchange.getResponseHeaders().set("Content-type","application/text");
		try{
			User player=this.getUserFromCookie(exchange, server);
			int gameID=0;
			Gson gson=new Gson();
			StringWriter writer = new StringWriter();
			IOUtils.copy(exchange.getRequestBody(), writer);
			JoinGameRequest join=gson.fromJson(writer.toString(), JoinGameRequest.class);
			//call server if successful continue if fail throw exception or terminate.
			gameID =server.joinGameCommand(join,player.getPlayerID());
			if(gameID==-1) {
				throw new Exception();
			}
            CommunicationModel comModel = ModelTranslator.translateModel(server.getGameModelByID(gameID));
            String result = Serializer.getSINGLETON().serialize(comModel);
			StringBuilder sb=new StringBuilder();
			sb.append("Catan.game="+gameID+";Path=/;");
			String cookie=sb.toString();
			System.out.println(cookie);
			exchange.getResponseHeaders().add("Set-cookie",cookie);
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			OutputStreamWriter output = new OutputStreamWriter(exchange.getResponseBody());
			output.write(result);
			output.flush();
			exchange.getResponseBody().close();
			exchange.close();
		}catch(Exception e){
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_FORBIDDEN, -1);
			exchange.getResponseBody().close();
			exchange.close();
		}
	}
	
}
