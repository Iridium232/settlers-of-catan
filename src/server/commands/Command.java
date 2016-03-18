package server.commands;

import client.control.IObserver;

/**
 * Interface for the commands that the server receives.
 * @author Doug
 *
 */
public abstract class Command 
{
	public static IObserver server;
	abstract void execute();
}
