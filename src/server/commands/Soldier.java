package server.commands;

import shared.communication.toServer.moves.Soldier_;
import client.communication.IServer;

/**
 * @post the robber is moved, a player is robbed, the current players soldier count is incremented by 1.
 * @author Doug
 *
 */
public class Soldier extends Command {

	private Soldier_ params;

	public Soldier(IServer s) {
		super(s);
	}

	@Override
	public void execute() 
	{
		this.server.playSoldierCardCommand(params);
	}

	public void setParams(Soldier_ move) 
	{
		this.params = move;
	}
}
