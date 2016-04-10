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

	public AcceptTradeCommand(IServer s,int gameID) 
	{
		super(s,gameID);
		
	}
	
	@Override
	public void execute() 
	{
		server.acceptTradeCommand(params,gameID);
	}

	public void setParams(AcceptTrade move) 
	{
		params = move;
	}

}
