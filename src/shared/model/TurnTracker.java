package shared.model;
import shared.definitions.*;

/**
 * 
 * Keeps track of whose turn it is and where they 
 * are at in their turn.
 *
 */
public class TurnTracker 
{
	private int active_player;
	private TurnStatus status;
	private int longest_road_player;
	private int largest_army_player;
	/**
	 * @return the active_player
	 */
	public int getActive_player() {
		return active_player;
	}
	/**
	 * @param active_player the active_player to set
	 */
	public void setActive_player(int active_player) {
		this.active_player = active_player;
	}
	/**
	 * @return the status
	 */
	public TurnStatus getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(TurnStatus status) {
		this.status = status;
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
