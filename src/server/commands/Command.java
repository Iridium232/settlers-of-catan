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
	public Command(IServer s) {
		server=s;
	}
	abstract void execute();
}
