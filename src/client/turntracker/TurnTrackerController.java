package client.turntracker;

import client.control.IObserver;
import shared.definitions.CatanColor;
import client.base.*;
import shared.model.Fascade;

import javax.xml.parsers.FactoryConfigurationError;


/**
 * Implementation for the turn tracker controller
 */
public class TurnTrackerController extends Controller implements ITurnTrackerController, IObserver {

	public TurnTrackerController(ITurnTrackerView view) {
		
		super(view);
		
		initFromModel();
	}
	
	@Override
	public ITurnTrackerView getView() {
		
		return (ITurnTrackerView)super.getView();
	}
/**
 * @pre it is the players turn
 * @post changes the current turn and displays the roll view for the next player
 */
	@Override
	public void endTurn() {

	}
	
	private void initFromModel() {
	}

	@Override
	public void ObservableChanged(Fascade fascade) {

	}
}

