package shared.model.states;

import shared.definitions.TurnStatus;

/**
 * 
 * A state where the active player is rolling the dice.
 * Implements the methods from IState
 */
public class RollingState implements IState 
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
		return TurnStatus.ROLLING;
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

	/**
	 *  the game waits for all players to discard
	 * @post An error is thrown
	 */
	@Override
	public void forceDiscard(TurnTracker turn_tracker_pointer, int player_index) throws Exception 
	{
		turn_tracker_pointer.setState(new DiscardState());
	}

}
