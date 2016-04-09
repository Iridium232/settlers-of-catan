package server.commands;

import client.communication.IServer;
import client.control.IObserver;

/**
 * Interface for the commands that the server receives.
 * @author Doug
 *
 */
public abstract class Command 
{
	IServer server;
	public static IObserver observer;
	private int gameID;
	public Command(IServer s,int gameID) {
		server=s;
		this.gameID=gameID;
	}
	abstract public void execute();
}
