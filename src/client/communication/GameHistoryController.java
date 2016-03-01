package client.communication;

import java.util.*;
import java.util.List;

import client.base.*;
import shared.definitions.*;


/**
 * Game history controller implementation
 */
public class GameHistoryController extends Controller implements IGameHistoryController {

	public GameHistoryController(IGameHistoryView view) {
		
		super(view);
		
		initFromModel();
	}
	
	@Override
	public IGameHistoryView getView() {
		
		return (IGameHistoryView)super.getView();
	}
	/**
	 * @post The Game history display will display upto the last 8 moves that have been made
	 */
	private void initFromModel() {
		
		//<temp>
		

//		getView().setEntries();
	
		//</temp>
	}
	
}

