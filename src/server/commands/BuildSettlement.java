package server.commands;

import client.communication.IServer;

/**
 * @post the player has a new settlement on the board, paid 1 wood, 1 brick, 1 wheat, and 1 sheep, the settlement count is decremented by 1.
 * @author Doug
 *
 */
public class BuildSettlement extends Command {

	private shared.communication.toServer.moves.BuildSettlement params;

	public BuildSettlement(IServer s, int gameID) {
		super(s,gameID);
	}

	@Override
	public void execute()
	{
		server.buildSettlementCommand(params,gameID);
	}

	public void setParams(shared.communication.toServer.moves.BuildSettlement move) 
	{
		params = move;
	}
}
