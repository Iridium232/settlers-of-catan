package shared.model.states;

import shared.definitions.TurnStatus;

public class DiscardState implements IState 
{

	@Override
	public TurnStatus getState() 
	{
		return shared.definitions.TurnStatus.DISCARDING;
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
				" player discard while already in the Discarding State!");
	}

}