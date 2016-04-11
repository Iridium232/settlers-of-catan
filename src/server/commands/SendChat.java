package server.commands;


import client.communication.IServer;

/**
 * @post the message has been added to the chat log.
 * @author Doug
 *
 */
public class SendChat extends Command {

	private shared.communication.toServer.moves.SendChat params;

	public SendChat(int gameID) {
		super(gameID);
	}

	@Override
	public void execute(IServer server)
	{
		server.sendChatCommand(params,gameID);
	}

	public void setParams(shared.communication.toServer.moves.SendChat move) 
	{
		params = move;
	}
}
