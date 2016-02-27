package client.control;

import client.communication.IServerProxy;
import shared.definitions.CatanColor;
import shared.model.Fascade;

public class Reference 
{
	private static Reference SINGLETON;

	public static Reference GET_SINGLETON() {
		if (SINGLETON == null) {
			SINGLETON = new Reference();
		}
		return SINGLETON;
	}

	private Reference() {
	}

	public Fascade fascade;
	public String name;
	public int player_index;
	public CatanColor player_color;
	public IServerProxy proxy;
	public int port;
	public String host;
	public int game_id;
	public int player_id;

	public Fascade getFascade() {
		return fascade;
	}

	public void setFascade(Fascade fascade) {
		this.fascade = fascade;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPlayer_index() {
		return player_index;
	}

	public void setPlayer_index(int player_index) {
		this.player_index = player_index;
	}

	public CatanColor getPlayer_color() {
		return player_color;
	}

	public void setPlayer_color(CatanColor player_color) {
		this.player_color = player_color;
	}

	public IServerProxy getProxy() {
		return proxy;
	}

	public void setProxy(IServerProxy proxy) {
		this.proxy = proxy;
	}

	public void setPort(Integer integer) 
	{
		this.port = integer;
		
	}
	public void setHost(String host)
	{
		this.host = host;
	}

	public String getHost() 
	{
		return host;
	}

	public String getPort() 
	{
		return Integer.toString(port);
	}

	public int getGame_id() {
		return game_id;
	}

	public void setGame_id(int game_id) {
		this.game_id = game_id;
	}

	public int getPlayer_id() {
		return player_id;
	}

	public void setPlayer_id(int player_id) {
		this.player_id = player_id;
	}
}
