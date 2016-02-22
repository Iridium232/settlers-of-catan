package client.control;

import client.communication.IServerProxy;
import shared.definitions.CatanColor;
import shared.model.Fascade;

public class Reference 
{
	private static Reference SINGLETON;

	private static Reference GET_SINGLETON() {
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
}
