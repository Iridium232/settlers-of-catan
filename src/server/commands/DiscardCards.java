package server.commands;

import client.communication.IServer;

/**
 * @post The selected cards have been removed from the players hand. 
 * @author Doug
 *
 */
public class DiscardCards extends Command {

	private shared.communication.toServer.moves.DiscardCards params;

	public DiscardCards(IServer s, int gameID) {
		super(s,gameID);
	}

	@Override
	public void execute() 
	{
		server.discardCommand(params);
	}

	public void setParams(shared.communication.toServer.moves.DiscardCards move)
	{
		params = move;
	}
}
