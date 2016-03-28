package server.commands;

import client.communication.IServer;

/**
 * 
 * @author Doug
 * @post A trade is offered to the target player from the sending player
 */
public class OfferTrade extends Command {

	private shared.communication.toServer.moves.OfferTrade params;

	public OfferTrade(IServer s) {
		super(s);
	}

	@Override
	public void execute()
	{
		server.offerTradeCommand(params);
	}

	public void setParams(shared.communication.toServer.moves.OfferTrade move) 
	{
		params = move;
	}

}
