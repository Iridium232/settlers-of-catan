package server.commands;


import client.communication.IServer;

/**
 * @post the player has traded a resource at a certain ratio for a different resource
 * @author Doug
 *
 */
public class MaritimeTrade extends Command {

	private shared.communication.toServer.moves.MaritimeTrade params;

	public MaritimeTrade(int gameID) {
		super(gameID);
	}

	@Override
	public void execute(IServer server)
	{
		server.maritimeTradeCommand(params,gameID);
	}

	public void setParams(shared.communication.toServer.moves.MaritimeTrade move) 
	{
		params = move;
	}
}
