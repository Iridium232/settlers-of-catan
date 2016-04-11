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

	public Monument(int gameID) {
		super(gameID);
	}

	@Override
	public void execute(IServer server)
	{
		server.playMonumentCardCommand(params,gameID);
	}

	public void setParams(Monument_ move) 
	{
		params = move;
	}

}
