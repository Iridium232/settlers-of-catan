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
	public static IObserver observer;
	protected int gameID;
	public Command(int gameID) {
		this.gameID=gameID;
	}
	abstract public void execute(IServer s);
}
