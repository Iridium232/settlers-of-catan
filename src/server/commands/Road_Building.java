package server.commands;

import shared.communication.toServer.moves.Road_Building_;
import client.communication.IServer;

/**
 * the player has two new roads placed. their road count is decremented by 2.
 * @author Doug
 *
 */
public class Road_Building extends Command {

	private Road_Building_ params;

	public Road_Building(int gameID) {
		super(gameID);
	}

	@Override
	public void execute(IServer server)
	{
		server.playRoadBuildingCardCommand(params,gameID);
	}

	public void setParams(Road_Building_ move) 
	{
		params = move;
	}

}
