package shared.model.states;

import shared.definitions.TurnStatus;

public class WaitingState implements IState 
{
	@Override
	public TurnStatus getState() 
	{
		return shared.definitions.TurnStatus.WAITING;
	}

	@Override
	public void finishPhase(TurnTracker turn_tracker_pointer, int player_index) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void forceDiscard(TurnTracker turn_tracker_pointer, int player_index) throws Exception 
	{
		
	}
	
	

}
