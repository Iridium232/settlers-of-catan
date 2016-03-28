package server.commands;

import client.communication.IServer;

/**
 * @post the turn tracker advances control to the next player. 
 * @author Doug
 *
 */
public class FinishTurn extends Command {

	public FinishTurn(IServer s) {
		super(s);
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}
}
