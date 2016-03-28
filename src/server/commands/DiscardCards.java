package server.commands;

import client.communication.IServer;

/**
 * @post The selected cards have been removed from the players hand. 
 * @author Doug
 *
 */
public class DiscardCards extends Command {

	private shared.communication.toServer.moves.DiscardCards params;

	public DiscardCards(IServer s) {
		super(s);
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
