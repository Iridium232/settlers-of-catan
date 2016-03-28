package server.commands;

import client.communication.IServer;

/**
 * @post the player has a new settlement on the board, paid 1 wood, 1 brick, 1 wheat, and 1 sheep, the settlement count is decremented by 1.
 * @author Doug
 *
 */
public class BuildSettlement extends Command {

	public BuildSettlement(IServer s) {
		super(s);
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}
}
