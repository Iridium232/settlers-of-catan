package client.communication;

import org.json.JSONObject;
import shared.communication.toServer.user.Credentials;
import shared.definitions.CatanColor;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Handles communication between the client and server
 * @author Doug
 *
 */
public class ClientCommunicator {

//******************************************* Static Fields/Methods *************************************************//
	private static final String DEFAULT_SERVER_HOST = "localhost";
	private static final int DEFAULT_SERVER_PORT = 5555;
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

	/**
	 * Constructer that takes the server's host name and port as arguments
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
	}

	/**
	 * Calls login on the server
	 * @param username
	 * @param password
	 * @return JSONObject containing name, password, and ID
	 * @pre username and password are not null
	 * @post receives the http response and sets the user cookie.
	 */
	public void login(String username, String password) throws Exception {
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
	public void register(String username,String password) throws Exception {
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
	public JSONObject gamesList() throws Exception {
		try {
			URL url = new URL(url_prefix + "/games/list");
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod(HTTP_GET);
			connection.connect();
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				InputStream result = connection.getInputStream();
				return serializer.deserialize(result);
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
	public JSONObject joinGame(int ID, CatanColor color){
		return null;
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
	
	/**
	 * 
	 * @param o
	 * @return HTTP success response
	 * @pre o is a valid http post request
	 * @post returns an http response
	 */
	public JSONObject doPost(String path, Object o) throws Exception {
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
}
