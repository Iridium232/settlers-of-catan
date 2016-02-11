package shared.model.states;

import shared.definitions.TurnStatus;

/**
 * 
 * A state where the active player is robbing.
 * Implements the methods from IState
 */
public class RobbingState implements IState 
{

	/**
	 * Gets the state
	 * 
	 * @pre none
	 * @post returns the state as defined in the shared definitions
	 */
	@Override
	public TurnStatus getState() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Transitions to the next turn phase which is Trading.
	 * 
	 * @pre this phase of the turn is over
	 * @post the turn-tracker's state is now a state of Trading
	 */
	@Override
	public void finishPhase(TurnTracker turn_tracker_pointer, int player_index) 
	{
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public void forceDiscard(TurnTracker turn_tracker_pointer, int player_index) throws Exception 
	{
		throw new Exception("ERROR: tried to make a" +
				" player discard while in the Robbing State!");
	}

}
