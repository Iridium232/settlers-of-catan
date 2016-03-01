package client.communication;

import client.base.Controller;
import client.control.IObserver;
import client.control.Reference;
import shared.communication.fromServer.game.Player;



/**
 * Chat controller implementation
 */
public class ChatController extends Controller implements IChatController, IObserver {

	public ChatController(IChatView view) {
		super(view);
		//initFromModel();

	}

	@Override
	public IChatView getView() {
		return (IChatView) super.getView();
	}

	/**
	 * @post a message is posted to the chat view.
	 */
	@Override
	public void sendMessage(String message) {
		int playerIndex = new Player().getPlayerIndex();
		Reference.GET_SINGLETON().getProxy().sendChat(playerIndex, message);
	}

	@Override
	public void ObservableChanged() {
		// TODO Auto-generated method stub

	}
}

