package client.communication;

import client.base.Controller;
import client.control.IObserver;
import client.control.Reference;
import shared.communication.fromServer.game.Player;
import shared.model.Fascade;
import shared.model.Game;
import shared.model.messages.MessageLine;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

//import shared.model.states.IState;


/**
 * Chat controller implementation
 */
public class ChatController extends Controller implements IChatController, IObserver {
	//private MessageLine[] game;
	private Fascade model;
	private Reference reference;
	private Game game;
	private IServerProxy proxy;
	//private IState model_state;

	public ChatController(IChatView view) {
		super(view);
		reference = Reference.GET_SINGLETON();
		model = reference.getFascade();
//		initFromModel();
		proxy = reference.proxy;

		//Need modelObserver to point to CHAT!!!
		//game = Reference.GET_SINGLETON().getFascade().getLog();
//		fascade.notifyObservers();



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


	private void initFromModel() {
		List<LogEntry> entries = new ArrayList<>();

		MessageLine[] messages = model.getMessages();
		
		//System.out.printf()


		for (MessageLine messageLine : messages) {

			Color color = Color.decode(model.getPlayerColorByIndex(reference.player_index));
			LogEntry tempEntry = new LogEntry(color, messageLine.getMessage());
			entries.add(tempEntry);
			//messageList.getMessages();
			//	entries = messageList.getMessages();

			//if (operation == CommunicationData.CHAT) {

			//if (chat != null) {

			//	}

			//} else {
			//	System.out.printf("New Message");
			//	}
		}

		this.getView().setEntries(entries);
		//getView().setEntries(CommunicationUtil.getLogEntriesFromModel(this.getModel(), CommunicationData.CHAT));



	}

	@Override
	public void ObservableChanged() {
		updateChat();

	}

	private void updateChat() {
		this.initFromModel();
	}
}

