package shared.model.states;



/**
 * Interface to the state classes in accordance with the state pattern
 * 
 * @author Joshua Reynolds
 *
 */
public interface IState 
{
	/**
	 * Gets the state
	 * 
	 * @pre none
	 * @post returns the state as defined in the shared definitions
	 */
	public shared.definitions.TurnStatus getState();
	
	/**
	 * Advances to the next state in the turn 
	 * 
	 * @pre the player is finished with all required actions in this state
	 * @post the state is advanced to the next state
	 * 
	 */
	public void finishPhase(TurnTracker turn_tracker_pointer, int player_index);
	
	/**
	 * Forces this player into the Discarding State
	 * @throws Exception 
	 * 
	 * @pre the player is in the Waiting or Rolling State
	 * @post the player is in the Discarding state
	 */
	public void forceDiscard(TurnTracker turn_tracker_pointer, int player_index) throws Exception;
}
