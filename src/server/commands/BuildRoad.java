package server.commands;

import client.communication.IServer;

/**
 * @post the player has a new road on the board, their road count is decremented by 1 and paid they 1 wood and 1 brick.
 * @author Doug
 *
 */
public class BuildRoad extends Command {

	private shared.communication.toServer.moves.BuildRoad params;

	public BuildRoad(IServer s, int gameID) {
		super(s,gameID);
	}

	@Override
	public void execute() 
	{
		server.buildRoadCommand(params,gameID);
	}

	public void setParams(shared.communication.toServer.moves.BuildRoad move) 
	{
		params = move; 
	}
}
