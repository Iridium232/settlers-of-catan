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
	private IState state;
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
		state = new FirstRoundState();
	}
	
	//*****************************************METHODS*******************************************************************
	
	/**
	 * Makes it the next player's turn.
	 * 
	 * @post the active player is the next in line
	 * @pre the active player has finished their turn
	 */
	public void advanceActivePlayer(int current_player_index) throws Exception
	{
		if (current_player_index != active_player)
		{
			//throw new Exception("ERROR! This player is trying to end his turn...\nbut it is not his turn!");
			return;
		}
		if(first_round)
		{
			this.setState(new FirstRoundState());
			if(active_player == 3)
			{
				first_round = false;
				this.setState(new SecondRoundState());
				second_round = true;
				return;
			}
		}
		else if (second_round)
		{
			this.setState(new SecondRoundState());
			if(active_player == 0)
			{
				second_round = false;
				this.setState(new RollingState());
				return;
			}
			active_player--;
			return;
		}
		active_player = ++active_player % 4;
		if(!first_round && ! second_round)
		{
			this.setState(new RollingState());
		}
	}
	
	/**
	 * Advances
	 * 
	 * @pre Advance Active Player method called this function
	 * @post the States are advanced
	 */
	public void advanceState() throws Exception
	{
		int index = 0;
		state.finishPhase(this, index);
		
	}
	
	/**
	 * gets the turn state of the specified player by index
	 * @param player_index
	 * @pre none
	 * @post result is the turn state of the player
	 */
	public TurnStatus turnStatusOf(int player_index)
	{
		if(player_index != active_player)
		{
			return TurnStatus.WAITING;
		}
		TurnStatus result = state.getState();
		return result;
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
	
	/**
	 * Get the state
	 */
	public IState getState()
	{
		return state;
	}
	
	/**
	 * Supports translation from JSON to Inheritance for the Populator
	 * @param status
	 * @pre none
	 * @post the turns are set up as expected
	 */
	public void setStatus(TurnStatus status)
	{
		IState active_state = new PlayingState(); 
		switch(status)
		{
			
			case DISCARDING:
				active_state = new DiscardState();
				break;
			case PLAYING:
				active_state = new PlayingState();
				break;
			case TRADING:
				active_state = new TradingState();
				break;
			case FIRSTROUND:
				active_state = new FirstRoundState();
				break;
			case SECONDROUND:
				active_state = new SecondRoundState();
				break;
			case ROBBING:
				active_state = new RobbingState();
				break;
			case WAITING:
				active_state = new WaitingState();
				break;
			case ROLLING:
				active_state = new RollingState();
				break;
		}
		state = active_state;
	}

	public void setState(IState playingState) 
	{
		this.state = playingState;
	}

	public void setFirstRound(boolean b) {
		this.first_round = b;
	}
	public void setSecondRound(boolean b){
		this.second_round = b;
	}
}
