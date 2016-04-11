package server.commands;

import client.communication.IServer;

/**
 * 
 * @author Doug
 * @post A trade is offered to the target player from the sending player
 */
public class OfferTrade extends Command {

	private shared.communication.toServer.moves.OfferTrade params;

	public OfferTrade(int gameID) {
		super(gameID);
	}

	@Override
	public void execute(IServer server)
	{
		server.offerTradeCommand(params,gameID);
	}

	public void setParams(shared.communication.toServer.moves.OfferTrade move) 
	{
		params = move;
	}

}
