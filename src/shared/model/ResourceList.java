package shared.model;


/**
 * 
 * Indicates a list of resources
 * .This is often used to show posessions
 * or costs.
 * 
 * It tells how many brick,ore,sheep,wool, and food
 * are there.
 *
 */
public class ResourceList 
{
	private int brick;
	private int ore;
	private int sheep;
	private int wood;
	private int food;
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
	 * @return the food
	 */
	public int getFood() {
		return food;
	}
	/**
	 * @param food the food to set
	 */
	public void setFood(int food) {
		this.food = food;
	}
}
