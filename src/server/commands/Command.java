package server.commands;

import client.communication.IServer;
/**
 * Interface for the commands that the server receives.
 * @author Doug
 *
 */
public abstract class Command {
	IServer server;
	abstract void execute();
}
