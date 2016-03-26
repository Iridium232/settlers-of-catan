package server.commands;

import client.communication.IServer;
import shared.communication.toServer.moves.AcceptTrade;

/**
 * @post if accept is true the resources are exchanged if false nothing happens.
 * @author Doug
 *
 */
public class AcceptTradeCommand extends Command {
	private int playerIndex;
	private boolean accept;
	
	public AcceptTradeCommand(AcceptTrade at,IServer s) {
		super(s);
		playerIndex=at.getPlayerIndex();
		accept=at.isWillAccept();
	}
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		server.acceptTrade(playerIndex,accept);
	}

}
