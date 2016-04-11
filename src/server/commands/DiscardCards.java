package server.commands;

import client.communication.IServer;

/**
 * @post The selected cards have been removed from the players hand. 
 * @author Doug
 *
 */
public class DiscardCards extends Command {

	private shared.communication.toServer.moves.DiscardCards params;

	public DiscardCards(int gameID) {
		super(gameID);
	}

	@Override
	public void execute(IServer server)
	{
		server.discardCommand(params,gameID);
	}

	public void setParams(shared.communication.toServer.moves.DiscardCards move)
	{
		params = move;
	}
}
