package server.userhandler;


import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URLEncoder;

import org.apache.commons.io.IOUtils;

import client.communication.IServer;
import server.facade.User;
import server.movehandlers.AbstractMoveHandler;
import shared.communication.toServer.user.Credentials;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;


public class RegisterHandler extends AbstractMoveHandler {

	public RegisterHandler(IServer facade, int cOMMANDS_BEFORE_SAVE) {
		super(facade,cOMMANDS_BEFORE_SAVE);
	}

	/**
	 * 
	 * Handler that generates and sends the command for a Register
	 * action
	 *
	 */
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		// TODO Auto-generated method stub
		exchange.getResponseHeaders().set("Content-type","application/text");
		Gson gson = new Gson();
		StringWriter writer = new StringWriter();
		IOUtils.copy(exchange.getRequestBody(), writer);
		Credentials cc=gson.fromJson(writer.toString(),Credentials.class);
		String id=server.register(cc.getUsername(), cc.getPassword());//throw/catch exception if registration is unsuccessful
		if(id==null) {
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, -1);
            OutputStreamWriter output = new OutputStreamWriter(exchange.getResponseBody());
            output.write("Error");
            output.close();
			exchange.getResponseBody().close();
			exchange.close();
		} else {
			User user=new User();
			user.setName(cc.getUsername());
			user.setPassword(cc.getPassword());
			user.setPlayerID(Integer.parseInt(id));
			StringBuilder sb=new StringBuilder();
			sb.append("catan.user=");
			sb.append(URLEncoder.encode((gson.toJson(user)), "UTF-8"));
			sb.append(";Path=/;");
			String cookie=sb.toString();
			exchange.getResponseHeaders().add("Set-cookie",cookie);
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			OutputStreamWriter output = new OutputStreamWriter(exchange.getResponseBody());
			output.write("Success");
			output.close();
			exchange.getResponseBody().close();
		}
	}

}
