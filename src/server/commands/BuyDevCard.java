package server.commands;

import client.communication.IServer;

/**
 * @post the player gains a development card and loses 1 ore, 1 wheat, 1 sheep.
 * @author Doug
 *
 */
public class BuyDevCard extends Command {

	private shared.communication.toServer.moves.BuyDevCard params;

	public BuyDevCard(IServer s, int gameID) {
		super(s,gameID);
	}

	@Override
	public void execute()
	{
		server.buyDevCardCommand(params,gameID);
	}

	public void setParams(shared.communication.toServer.moves.BuyDevCard move) 
	{
		params = move;
	}
}
