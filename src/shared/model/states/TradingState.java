package shared.model.states;

import shared.definitions.TurnStatus;

public class TradingState implements IState 
{
	@Override
	public TurnStatus getState() 
	{
		return shared.definitions.TurnStatus.TRADING;
	}

	@Override
	public void finishPhase(TurnTracker turn_tracker_pointer, int player_index) 
	{
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public void forceDiscard(TurnTracker turn_tracker_pointer, int player_index) throws Exception 
	{
		throw new Exception("ERROR: tried to make a" +
				" player discard while in the Trading State!");
	}	
}
