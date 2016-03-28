package server.commands;


import client.communication.IServer;

/**
 * @post the turn tracker advances control to the next player. 
 * @author Doug
 *
 */
public class FinishTurn extends Command {

	private shared.communication.toServer.moves.FinishTurn params;

	public FinishTurn(IServer s) {
		super(s);
	}

	@Override
	public void execute() 
	{
		server.FinishTurnCommand(params);
	}

	public void setParams(shared.communication.toServer.moves.FinishTurn move) 
	{
		params = move;
	}
}
