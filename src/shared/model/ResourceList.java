package shared.model;


/**
 * 
 * Indicates a list of resources
 * .This is often used to show posessions from player in hand
 * or costs.
 * 
 * It tells how many brick,ore,sheep,wool, and wheat
 * are there.
 *
 */
public class ResourceList 
{
	private int brick;
	private int ore;
	private int sheep;
	private int wood;
	private int wheat;

	/**
	 * Class Contructor
	 */

	public ResourceList() {
		brick = 0;
		wheat = 0;
		ore = 0;
		sheep = 0;
		wood = 0;
	}

	/**
	 * Class Constructor
	 * @param brick
	 * @param wheat
	 * @param ore
	 * @param sheep
	 * @param wood
	 */

	public ResourceList(int brick, int wheat, int ore, int sheep, int wood) {
		this.wheat = wheat;
		this.wood = wood;
		this.sheep = sheep;
		this.brick = brick;
	}

	/**
	 * @return the brick
	 */
	public int getBrick() {
		return brick;
	}
	/**
	 * @param brick the brick to set
	 */
	public void setBrick(int brick) {
		this.brick = brick;
	}
	/**
	 * @return the ore
	 */
	public int getOre() {
		return ore;
	}
	/**
	 * @param ore the ore to set
	 */
	public void setOre(int ore) {
		this.ore = ore;
	}
	/**
	 * @return the sheep
	 */
	public int getSheep() {
		return sheep;
	}
	/**
	 * @param sheep the sheep to set
	 */
	public void setSheep(int sheep) {
		this.sheep = sheep;
	}
	/**
	 * @return the wood
	 */
	public int getWood() {
		return wood;
	}
	/**
	 * @param wood the wood to set
	 */
	public void setWood(int wood) {
		this.wood = wood;
	}
	/**
	 * @return the wheat
	 */
	public int getwheat() {
		return wheat;
	}
	/**
	 * @param wheat the wheat to set
	 */
	public void setwheat(int wheat) {
		this.wheat = wheat;
	}
}
