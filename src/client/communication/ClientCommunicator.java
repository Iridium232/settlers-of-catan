package client.communication;

import client.control.Reference;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import shared.communication.toServer.games.JoinGameRequest;
import shared.communication.toServer.user.Credentials;
import shared.definitions.CatanColor;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Handles communication between the client and server
 * @author Doug
 *
 */
public class ClientCommunicator {

//******************************************* Static Fields/Methods *************************************************//
	private static final String DEFAULT_SERVER_HOST = "localhost";
	private static final int DEFAULT_SERVER_PORT = 8081;
	private static final String HTTP_GET = "GET";
	private static final String HTTP_POST = "POST";
	private static ClientCommunicator SINGLETON;

	public static ClientCommunicator getSingleton(String server_host, String server_port) {
		if (SINGLETON == null) {
			SINGLETON = new ClientCommunicator(server_host, server_port);
		}
		return SINGLETON;
	}

	public static ClientCommunicator getSINGLETON() throws Exception {
		if (SINGLETON == null) {
			throw new Exception("ClientCommunicator not initialized with host and port");
		}
		return SINGLETON;
	}

//******************************************* Other Fields/Methods **************************************************//
	private String url_prefix;
	private String server_host;
	private int server_port;
	private Serializer serializer;
	private String catan_cookie;
	private String game_ID;
	private Reference ref;

	/**
	 * Constructor that takes the server's host name and port as arguments
	 * @param server_host	Machine the server is running on
	 * @param strServer_port	Port that the server is running on
	 */
	private ClientCommunicator(String server_host, String strServer_port) {
		if (server_host != "") {
			this.server_host = server_host;
		} else {
			this.server_host = DEFAULT_SERVER_HOST;
		}

		if (strServer_port.matches("\\d+")) {
			this.server_port = Integer.parseInt(strServer_port);
		} else {
			this.server_port = DEFAULT_SERVER_PORT;
		}

		this.url_prefix = "http://" + this.server_host + ":" + this.server_port;
		this.serializer = Serializer.getSINGLETON();
		this.catan_cookie = null;
		this.game_ID = null;
		this.ref = Reference.GET_SINGLETON();
		
		if(server_host.contains("http://"))
		{
			url_prefix = url_prefix.substring(7); 
		}
	}

	/**
	 * Calls login on the server
	 * @param username
	 * @param password
	 * @return JSONObject containing name, password, and ID
	 * @pre username and password are not null
	 * @post receives the http response and sets the user cookie.
	 */
	public int login(String username, String password) throws Exception {
		try {
			URL url = new URL(url_prefix + "/user/login");
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod(HTTP_POST);
			connection.setDoOutput(true);
			connection.connect();

			Credentials credentials = new Credentials(username, password);
			String serialized = serializer.serialize(credentials);
			connection.getOutputStream().write(serialized.getBytes());
			connection.getOutputStream().close();
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				String cookieStr = connection.getHeaderField("Set-cookie");
				catan_cookie = getEncodedValue(cookieStr);
				ref.setName(username);
				ref.setPlayer_id(getPlayerID(cookieStr));
				return connection.getResponseCode();
			} else {
				throw new Exception(String.format("doPost failed: %s (http code %d)",
						"/user/login", connection.getResponseCode()));
			}
		}
		catch (IOException e) {
			throw new Exception(String.format("doPost failed: %s", e.getMessage()), e);
		}
	}

	/**
	 * Calls register on the server
	 * @param username
	 * @param password
	 * @return JSONObject containing name,password, and ID used to set user.cookie
	 * @pre username and password are not null and the username isn't already in use.
	 * @post new user account is created, receive a http response, set the user cookie
	 */
	public int register(String username,String password) throws Exception {
		try {
			URL url = new URL(url_prefix + "/user/register");
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod(HTTP_POST);
			connection.setDoOutput(true);
			connection.connect();

			Credentials credentials = new Credentials(username, password);

			String serialized = serializer.serialize(credentials);
			connection.getOutputStream().write(serialized.getBytes());
			connection.getOutputStream().close();
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				String cookieStr = connection.getHeaderField("Set-cookie");
				catan_cookie = getEncodedValue(cookieStr);
				ref.setName(username);
				return connection.getResponseCode();
			} else {
				throw new Exception(String.format("doPost failed: %s (http code %d)",
						"/user/register", connection.getResponseCode()));
			}
		}
		catch (IOException e) {
			throw new Exception(String.format("doPost failed: %s", e.getMessage()), e);
		}
	}

	/**
	 * Calls games/list on the server
	 * @post got a list of games from the server
	 * @return a JSON array containing a list of objects containing information about the server's games.
	 */
	public List<JSONObject> gamesList() throws Exception {
		try {
			URL url = new URL(url_prefix + "/games/list");
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod(HTTP_GET);
			connection.connect();
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				InputStream result = connection.getInputStream();
				StringWriter writer = new StringWriter();
				try {
					IOUtils.copy(result, writer);
				} catch (IOException e) {
					e.printStackTrace();
				}
				String resultStr = writer.toString();
				List<String> resultList = stringToListOfGames(resultStr);
				List<JSONObject> resultAsJSONs = new ArrayList<>();
				for (String s : resultList) {
					resultAsJSONs.add(new JSONObject(s));
				}
				return resultAsJSONs;
			}
			else {
				throw new Exception(String.format("doGet failed: %s (http code %d)",
						"/games/list", connection.getResponseCode()));
			}
		}
		catch (IOException e) {
			throw new Exception(String.format("doGet failed: %s", e.getMessage()), e);
		}
	}

	/**
	 *
	 * @param name
	 * @param randomTiles
	 * @param randomNumbers
	 * @param randomPorts
	 * @return a JSONObject containing all of the information for the game board including game name, game id and an array for four empty player objects.
	 * @pre name is not null, has values for randomTiles, randomNumbers, and randomPorts.
	 * @post receive a HTTP response for success or failure
	 */
	public JSONObject createGame(String name, boolean randomTiles, boolean randomNumbers, boolean randomPorts){
		return null;
	}

	/**
	 *
	 * @param ID
	 * @param color
	 * @return
	 * @pre the user is logged in, the player is already in the specified game or the game has space for an extra player, the ID is valid, and the color is valid
	 * @post the player is added to the game with the desired color,
	 */
	public JSONObject joinGame(int ID, CatanColor color) throws Exception {
		ref.setGame_id(ID);
		if (catan_cookie == null) {
			throw new Exception("Haven't Logged in yet");
		}
		try {
			URL url = new URL(url_prefix + "/games/join");
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod(HTTP_POST);
			connection.setRequestProperty("Cookie", cookieForJoinBuilder());
			connection.setDoOutput(true);
			connection.connect();
			String serialized = serializer.serialize(makeJoinGameRequest(ID, color));
			connection.getOutputStream().write(serialized.getBytes());
			connection.getOutputStream().close();
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				String cookieStr = connection.getHeaderField("Set-cookie");
				game_ID = getEncodedValue(cookieStr);
			} else {
				throw new Exception(String.format("doPost failed: %s (http code %d)",
						"/games/join", connection.getResponseCode()));
			}
		}
		catch (IOException e) {
			throw new Exception(String.format("doPost failed: %s", e.getMessage()), e);
		}
		return doGet("/game/model");
	}
	
	/**
	 * 
	 * @param path
	 * @return HTTP Get Response
	 * @pre o contains a valid http get request
	 * @post Response from the server
	 */
	public JSONObject doGet(String path) throws Exception {
		if (catan_cookie == null || game_ID == null) {
			throw new Exception("Haven't Logged in and joined a game");
		}
		try {
			URL url = new URL(url_prefix + path);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod(HTTP_GET);
			connection.setRequestProperty("Cookie", cookieBuilder());
			connection.connect();
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				InputStream result = connection.getInputStream();
				return serializer.deserialize(result);
			}
			else {
				throw new Exception(String.format("doGet failed: %s (http code %d)",
						path, connection.getResponseCode()));
			}
		}
		catch (IOException e) {
			throw new Exception(String.format("doGet failed: %s", e.getMessage()), e);
		}
	}
	public JSONObject sendCommand(String path, String s) throws Exception{
		if (catan_cookie == null || game_ID == null) {
			throw new Exception("Haven't Logged in and joined a game");
		}
		try {
			URL url = new URL(url_prefix + path);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod(HTTP_POST);
			connection.setRequestProperty("Cookie", cookieBuilder());
			connection.setDoOutput(true);
			connection.connect();
			connection.getOutputStream().write(s.getBytes());
			connection.getOutputStream().close();
			InputStream result = connection.getInputStream();
			return serializer.deserialize(result);			
		}
		catch (IOException e) {
			return null;
//			throw new Exception(String.format("doPost failed: %s", e.getMessage()), e);
		}
	}
	/**
	 * 
	 * @param o
	 * @return HTTP success response
	 * @pre o is a valid http post request
	 * @post returns an http response
	 */
	public JSONObject doPost(String path, Object o) throws Exception {
		if (!path.equals("/games/create")) {
			if (catan_cookie == null || game_ID == null) {
				throw new Exception("Haven't Logged in and joined a game");
			}
		}
		try {
			URL url = new URL(url_prefix + path);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod(HTTP_POST);
			connection.setRequestProperty("Cookie", cookieBuilder());
			connection.setDoOutput(true);
			connection.connect();
			String serialized = serializer.serialize(o);
			connection.getOutputStream().write(serialized.getBytes());
			connection.getOutputStream().close();
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				InputStream result = connection.getInputStream();
				return serializer.deserialize(result);
			} else {
				throw new Exception(String.format("doPost failed: %s (http code %d)",
						path, connection.getResponseCode()));
			}
		}
		catch (IOException e) {
			throw new Exception(String.format("doPost failed: %s", e.getMessage()), e);
		}
	}

	private String getEncodedValue(String cookie) {
		return cookie.substring(cookie.indexOf('=') + 1, cookie.indexOf(';'));
	}

	private String cookieBuilder() {
		StringBuilder sb = new StringBuilder();
		sb.append("catan.user=");
		sb.append(catan_cookie);
		sb.append("; catan.game=");
		sb.append(game_ID);
		return sb.toString();
	}

	private String cookieForJoinBuilder() {
		StringBuilder sb = new StringBuilder();
		sb.append("catan.user=");
		sb.append(catan_cookie);
		return sb.toString();
	}

	private JoinGameRequest makeJoinGameRequest(int id, CatanColor color) {
		JoinGameRequest request = null;
		switch (color) {
			case RED:
				request = new JoinGameRequest(id, "red");
				break;
			case ORANGE:
				request = new JoinGameRequest(id, "orange");
				break;
			case YELLOW:
				request = new JoinGameRequest(id, "yellow");
				break;
			case BLUE:
				request = new JoinGameRequest(id, "blue");
				break;
			case GREEN:
				request = new JoinGameRequest(id, "green");
				break;
			case PURPLE:
				request = new JoinGameRequest(id, "purple");
				break;
			case PUCE:
				request = new JoinGameRequest(id, "puce");
				break;
			case WHITE:
				request = new JoinGameRequest(id, "white");
				break;
			case BROWN:
				request = new JoinGameRequest(id, "brown");
				break;
		}
		return request;
	}

	private List<String> stringToListOfGames(String param) {
		List<String> splitStrings = Arrays.asList(param.split("\\}\\]\\},"));
		for (int i = 0; i < splitStrings.size() - 1; i++) {
			StringBuilder sb = new StringBuilder();
			sb.append(splitStrings.get(i));
			sb.append("}]}");
			splitStrings.set(i, sb.toString());
		}
		splitStrings.set(0, splitStrings.get(0).substring(1));

		int lastElementIndex = splitStrings.size()-1;
		String lastElement = splitStrings.get(lastElementIndex);
		String lastElementFixed = lastElement.substring(0, lastElement.length() - 1);
		splitStrings.set(lastElementIndex, lastElementFixed);
		return splitStrings;
	}

	private int getPlayerID(String encoding) {
		String[] pieces = encoding.split("playerID%22%3A");
		StringBuilder sb = new StringBuilder();
		int index = 0;
		char token = pieces[1].charAt(index);
		while (token != '%') {
			sb.append(token);
			token = pieces[1].charAt(++index);
		}
		return Integer.parseInt(sb.toString());
	}
}
