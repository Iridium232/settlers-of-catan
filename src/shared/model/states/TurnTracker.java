package shared.model.states;
import shared.definitions.*;

/**
 * 
 * Keeps track of whose turn it is and where they 
 * are at in their turn.
 *
 */
public class TurnTracker 
{
	private boolean first_round;
	private boolean second_round;
	private int active_player;
	private IState[] player_states;
	private int longest_road_player;
	private int largest_army_player;
	
	/**
	 * Default Constructor of the TurnTracker
	 * 
	 * @pre none
	 * @post the turn-tracker is set for the beginnig state of the game.
	 * 
	 */
	public TurnTracker()
	{
		second_round = false;
		first_round = true;
		active_player = 0;
		largest_army_player = -1;
		longest_road_player = -1;
		player_states = new IState[4];
		player_states[0] = new FirstRoundState();
		player_states[1] = new WaitingState();
		player_states[2] = new WaitingState();
		player_states[3] = new WaitingState();
	}
	
	//*****************************************METHODS*******************************************************************
	
	/**
	 * Makes it the next player's turn.
	 * 
	 * @post the active player is the next in line
	 * @pre the active player has finished their turn
	 */
	public void advanceActivePlayer() throws Exception
	{
		if(first_round)
		{
			if(active_player == 3)
			{
				first_round = false;
				second_round = true;
				return;
			}
		}
		else if (second_round)
		{
			if(active_player == 0)
			{
				second_round = false;
				return;
			}
			active_player--;
			return;
		}
		active_player = ++active_player % 4;
	}
	
	/**
	 * Advances
	 * 
	 * @pre Advance Active Player method called this function
	 * @post the States are advanced
	 */
	private void advanceStates() throws Exception
	{
		int index = 0;
		for(IState state: player_states)
		{
			state.finishPhase(this, index);
			index++;
		}
	}
	
	/**
	 * 
	 * @param player_index
	 * @return
	 */
	public TurnStatus turnStatusOf(int player_index)
	{
		return player_states[player_index].getState();
	}
	//***********************************Getters and Setters***********************************************************
	/**
	 * @return the active_player
	 */
	public int getActive_player() 
	{
		return active_player;
	}
	/**
	 * @param active_player the active_player to set
	 */
	public void setActive_player(int active_player) {
		this.active_player = active_player;
	}
	

	/**
	 * @return the longest_road_player
	 */
	public int getLongest_road_player() {
		return longest_road_player;
	}
	/**
	 * @param longest_road_player the longest_road_player to set
	 */
	public void setLongest_road_player(int longest_road_player) {
		this.longest_road_player = longest_road_player;
	}
	/**
	 * @return the largest_army_player
	 */
	public int getLargest_army_player() {
		return largest_army_player;
	}
	/**
	 * @param largest_army_player the largest_army_player to set
	 */
	public void setLargest_army_player(int largest_army_player) {
		this.largest_army_player = largest_army_player;
	}
	
}
