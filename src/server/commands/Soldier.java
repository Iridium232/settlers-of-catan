package server.commands;

import client.communication.IServer;

/**
 * @post the robber is moved, a player is robbed, the current players soldier count is incremented by 1.
 * @author Doug
 *
 */
public class Soldier extends Command {

	public Soldier(IServer s) {
		super(s);
	}

	@Override
	void execute() {
		// TODO Auto-generated method stub
		
	}
}
