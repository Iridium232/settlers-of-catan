package server.commands;

import shared.communication.toServer.moves.Road_Building_;
import client.communication.IServer;

/**
 * the target player has lost 1 random card to the active player.
 * @author Doug
 *
 */
public class RobPlayer extends Command {

	private shared.communication.toServer.moves.RobPlayer params;

	public RobPlayer(IServer s, int gameID) {
		super(s,gameID);
	}

	@Override
	public void execute() 
	{
		server.robCommand(params,gameID);
	}

	public void setParams(shared.communication.toServer.moves.RobPlayer move) 
	{
		params = move;
	}

}
