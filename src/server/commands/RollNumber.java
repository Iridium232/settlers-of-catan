package server.commands;


import client.communication.IServer;

/**
 * @post resources have been distributed to the players on tiles with the rolled number.
 * @author Doug
 *
 */
public class RollNumber extends Command {

	private shared.communication.toServer.moves.RollNumber params;

	public RollNumber(IServer s, int gameID) {
		super(s,gameID);
	}

	@Override
	public void execute() 
	{
		server.rollNumberCommand(params,gameID);
	}

	public void setParams(shared.communication.toServer.moves.RollNumber move) 
	{
		params = move;
	}
}
