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

	public AcceptTradeCommand(IServer s) 
	{
		super(s);
	}
	
	@Override
	public void execute() 
	{
		server.acceptTradeCommand(params);
	}

	public void setParams(AcceptTrade move) 
	{
		params = move;
	}

}
