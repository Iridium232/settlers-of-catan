package server.commands;

import shared.communication.toServer.moves.Monopoly_;
import client.communication.IServer;

/**
 * @post The player has collected all of the selected type of resource from the other players.
 * @author Doug
 *
 */
public class Monopoly extends Command {

	private Monopoly_ params;

	public Monopoly(IServer s, int gameID) {
		super(s,gameID);
	}

	@Override
	public void execute() 
	{
		server.playMonopolyCardCommand(params,gameID);
	}

	public void setParams(Monopoly_ move) 
	{
		params = move;
	}
}
