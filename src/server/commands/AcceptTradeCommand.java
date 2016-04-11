package server.commands;

import client.communication.IServer;
import shared.communication.toServer.moves.AcceptTrade;

/**
 * @post if accept is true the resources are exchanged if false nothing happens.
 * @author Doug
 *
 */
public class AcceptTradeCommand extends Command 
{

	private AcceptTrade params;

	public AcceptTradeCommand(int gameID)
	{
		super(gameID);
		
	}
	
	@Override
	public void execute(IServer server)
	{
		server.acceptTradeCommand(params,gameID);
	}

	public void setParams(AcceptTrade move) 
	{
		params = move;
	}

}
