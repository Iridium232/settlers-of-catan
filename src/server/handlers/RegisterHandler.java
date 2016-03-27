package server.handlers;


import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URLEncoder;

import org.apache.commons.io.IOUtils;

import client.communication.IServer;
import server.facade.User;
import shared.communication.toServer.user.Credentials;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;


public class RegisterHandler extends AbstractHandler {

	public RegisterHandler(IServer facade) {
		super(facade);
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
		server.register(cc.getUsername(), cc.getPassword());
		StringBuilder sb=new StringBuilder();
		sb.append("catan.user");
		sb.append(URLEncoder.encode((gson.toJson(cc)),"UTF-8"));
		sb.append(";Path=/;");
		String cookie=sb.toString();
		exchange.getResponseHeaders().add("Set-cookie",cookie);
		exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
		OutputStreamWriter output = new OutputStreamWriter(exchange.getResponseBody());
		output.write(sb.toString());
		output.flush();
		exchange.getResponseBody().close();
	}

}
