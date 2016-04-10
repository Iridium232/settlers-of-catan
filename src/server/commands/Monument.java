package server.commands;

import shared.communication.toServer.moves.Monument_;
import client.communication.IServer;

/**
 * @post The player gains one victory point.
 * @author Doug
 *
 */
public class Monument extends Command {

	private Monument_ params;

	public Monument(IServer s, int gameID) {
		super(s,gameID);
	}

	@Override
	public void execute() 
	{
		server.playMonumentCardCommand(params,gameID);
	}

	public void setParams(Monument_ move) 
	{
		params = move;
	}

}
