package server.commands;


import client.communication.IServer;

/**
 * @post the player has traded a resource at a certain ratio for a different resource
 * @author Doug
 *
 */
public class MaritimeTrade extends Command {

	private shared.communication.toServer.moves.MaritimeTrade params;

	public MaritimeTrade(IServer s) {
		super(s);
	}

	@Override
	public void execute() 
	{
		server.maritimeTradeCommand(params);
	}

	public void setParams(shared.communication.toServer.moves.MaritimeTrade move) 
	{
		params = move;
	}
}
