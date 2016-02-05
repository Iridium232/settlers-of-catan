package shared.model;

/**
 * A collection of development cards
 * 
 *
 */
public class DevCardList 
{
	private int monopoly;
	private int monument;
	private int road_building;
	private int soldier;
	private int year_of_plenty;
	/**
	 * Class constructor
	 * @param monopoly
	 * @param monument
	 * @param road_buildingl
	 * @param soldier
	 * @param year_of_plenty
	 */
	
	
	/**
	 * 
	 * @param cards
	 * @pre none
	 * @post result is true iff the current devcardlist has >= the cards in the parameter
	 */
	public boolean includes(DevCardList cards)
	{
		int other_monopoly = cards.getMonopoly();
		int other_monument = cards.getMonument();
		int other_road_building = cards.getRoad_building();
		int other_soldier = cards.getSoldier();
		int other_yearop = cards.getYear_of_plenty();
		boolean result = (monopoly >= other_monopoly) && (monument >= other_monument)
				&& (road_building >= other_road_building) && (soldier >= other_soldier)
				&& (year_of_plenty >= other_yearop);
		return result;
	}
	
	/**
	 * @return the total number of cards
	 */
	public int getTotalCards()
	{
		return monopoly + monument + road_building + soldier + year_of_plenty;
	}

	/**
	 * @return the monopoly
	 */
	public int getMonopoly() {
		return monopoly;
	}
	/**
	 * @param monopoly the monopoly to set
	 */
	public void setMonopoly(int monopoly) {
		this.monopoly = monopoly;
	}
	/**
	 * @return the monument
	 */
	public int getMonument() {
		return monument;
	}
	/**
	 * @param monument the monument to set
	 */
	public void setMonument(int monument) {
		this.monument = monument;
	}
	/**
	 * @return the road_building
	 */
	public int getRoad_building() {
		return road_building;
	}
	/**
	 * @param road_building the road_building to set
	 */
	public void setRoad_building(int road_building) {
		this.road_building = road_building;
	}
	/**
	 * @return the soldier
	 */
	public int getSoldier() {
		return soldier;
	}
	/**
	 * @param soldier the soldier to set
	 */
	public void setSoldier(int soldier) {
		this.soldier = soldier;
	}
	/**
	 * @return the year_of_plenty
	 */
	public int getYear_of_plenty() {
		return year_of_plenty;
	}
	/**
	 * @param year_of_plenty the year_of_plenty to set
	 */
	public void setYear_of_plenty(int year_of_plenty) {
		this.year_of_plenty = year_of_plenty;
	}
}
