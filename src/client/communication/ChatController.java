package client.communication;

import client.base.*;


/**
 * Chat controller implementation
 */
public class ChatController extends Controller implements IChatController {

	public ChatController(IChatView view) {
		
		super(view);
	}

	@Override
	public IChatView getView() {
		return (IChatView)super.getView();
	}
/**
 * @post a message is posted to the chat view.
 */
	@Override
	public void sendMessage(String message) {
		
	}

}

