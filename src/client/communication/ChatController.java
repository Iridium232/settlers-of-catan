package client.communication;

import client.base.Controller;
import client.control.IObserver;
import client.control.Reference;
import shared.model.Fascade;
import shared.model.Game;
import shared.model.messages.MessageLine;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

//import shared.model.states.IState;


/**
 * Chat controller implementation
 */
public class ChatController extends Controller implements IChatController, IObserver {

	//private MessageLine[] game;
	private Fascade model;
	private Reference reference;
	private Game game;
	private IServer proxy;
	private int chatSize;
	//private IState model_state;


	public ChatController(IChatView view) {
		super(view);
		reference = Reference.GET_SINGLETON();
		model = reference.getFascade();
//		initFromModel();
		proxy = reference.proxy;
		chatSize = 0;
		Reference.GET_SINGLETON().getFascade().addObserver(this);
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
	public void sendMessage(String message) 
	{
		String result = Reference.GET_SINGLETON().getProxy().sendChat(reference.getPlayer_index(), message);
		try 
		{
			ModelPopulator.populateModel(new JSONObject(result), Reference.GET_SINGLETON().getFascade());
		} 
		catch (JSONException e) 
		{
			e.printStackTrace();
		}
	}


	private void initFromModel() {
		List<LogEntry> entries = new ArrayList<>();

		MessageLine[] messages = Reference.GET_SINGLETON().getFascade().getMessages();
		
		//System.out.printf()


		for (MessageLine messageLine : messages) {
			entries.add(MessageToLogEntry.getMessageToLog().convert(messageLine));			
		}

		this.getView().setEntries(entries);
		//getView().setEntries(CommunicationUtil.getLogEntriesFromModel(this.getModel(), CommunicationData.CHAT));



	}

	@Override
	public void ObservableChanged() {
		MessageLine[] chat = Reference.GET_SINGLETON().getFascade().getMessages();

		if (chat.length != chatSize) {
			initFromModel();
			chatSize = chat.length;
		}

	}

}

