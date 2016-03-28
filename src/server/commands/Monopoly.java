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

	public Monopoly(IServer s) {
		super(s);
	}

	@Override
	public void execute() 
	{
		server.playMonopolyCardCommand(params);
	}

	public void setParams(Monopoly_ move) 
	{
		params = move;
	}
}
