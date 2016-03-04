package client.communication;

import java.util.*;

import client.base.*;
import client.control.IObserver;
import client.control.Reference;
import shared.definitions.*;
import shared.model.messages.MessageLine;


/**
 * Game history controller implementation
 */
public class GameHistoryController extends Controller implements IGameHistoryController, IObserver {

	private int historySize;

	public GameHistoryController(IGameHistoryView view) {
		
		super(view);
		historySize = 0;
		Reference.GET_SINGLETON().getFascade().addObserver(this);
	}
	
	@Override
	public IGameHistoryView getView() {
		
		return (IGameHistoryView)super.getView();
	}
	/**
	 * @post The Game history display will display upto the last 8 moves that have been made
	 */
	private void initFromModel() {
		List<LogEntry> entries = new ArrayList<>();

		MessageLine[] messages = Reference.GET_SINGLETON().getFascade().getLog();
		
		for (MessageLine messageLine : messages) {
			entries.add(MessageToLogEntry.getMessageToLog().convert(messageLine));			
		}

		this.getView().setEntries(entries);

	}

	@Override
	public void ObservableChanged() {
		// TODO Auto-generated method stub
		MessageLine[] history = Reference.GET_SINGLETON().getFascade().getLog();
		if (history.length != historySize) {
			initFromModel();
			historySize = history.length;
		}
	}
	
}

