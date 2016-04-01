package shared.model.player;

import java.util.ArrayList;
import java.util.Random;

import shared.definitions.DevCardType;

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
	public DevCardList(){};
	
	/**
	 * Constuctor
	 * @param i
	 * @param j
	 * @param k
	 * @param l
	 * @param m
	 */
	public DevCardList(int i, int j, int k, int l, int m) 
	{
		soldier = i;
		monument = j;
		road_building = k;
		monopoly = l;
		year_of_plenty = m;
	}

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

	/**
	 * play a dev card
	 * 
	 * @pre none
	 * @post that dev card count is reduced by one. Throws an error when there are none
	 * @param devcard
	 * @throws Exception 
	 */
	public void play(DevCardType devcard) throws Exception 
	{
		switch (devcard)
		{
		case MONOPOLY:
			if(this.monopoly > 0)
			{
				this.monopoly--;
				return;
			}
			break;
		case MONUMENT:
			if(this.monument > 0)
			{
				this.monument--;
				return;
			}
			break;
		case SOLDIER:
			if(this.soldier > 0)
			{
				this.soldier--;
				return;
			}
			break;
		case ROAD_BUILD:
			if(this.road_building > 0)
			{
				this.road_building--;
				return;
			}
			break;
		case YEAR_OF_PLENTY:
			if(this.year_of_plenty > 0)
			{
				this.year_of_plenty--;
				return;
			}
			break;
		default:
			throw new Exception("ERROR: invalid dev card type");
		}
		throw new Exception("ERROR: This player cannot play that card.");
	}

	/**
	 * adds this many of this card.
	 * 
	 * @pre none
	 * @post this many of that card is added to the multiset.
	 * @param type
	 * @param i
	 */
	public void add(DevCardType type, int i) 
	{
		switch(type)
		{
		case MONOPOLY:
			monopoly += i;
			break;
		case MONUMENT:
			monument += i;
			break;
		case ROAD_BUILD:
			road_building += i;
			break;
		case SOLDIER:
			soldier += i;
			break;
		case YEAR_OF_PLENTY:
			year_of_plenty += i;
			break;
		default:
			break;
		
		}
	}

	/**
	 * Draws a random card from this deck
	 * 
	 * @pre there are contents of this.
	 * @post gives back a random card
	 * @return
	 */
	public DevCardType getRandomCard() 
	{
		ArrayList<DevCardType> options = new ArrayList<DevCardType>();
		for (int i = 0; i < monument; i++)
		{
			options.add(DevCardType.MONUMENT);
		}
		for (int i = 0; i < road_building; i++)
		{
			options.add(DevCardType.ROAD_BUILD);
		}
		for (int i = 0; i < year_of_plenty; i++)
		{
			options.add(DevCardType.YEAR_OF_PLENTY);
		}
		for (int i = 0; i < soldier; i++)
		{
			options.add(DevCardType.SOLDIER);
		}
		for (int i = 0; i < monopoly; i++)
		{
			options.add(DevCardType.MONOPOLY);
		}
		Random r = new Random(System.currentTimeMillis());
		return options.get(r.nextInt(options.size()));
	}

	/**
	 * the union of the two sets
	 * 
	 * @pre none
	 * @post this many of that card is added to the multiset.
	 * @param type
	 * @param i
	 */
	public void add(DevCardList other) 
	{
		this.monopoly += other.getMonopoly();
		this.monument += other.getMonument();
		this.soldier += other.soldier;
		this.year_of_plenty += other.getYear_of_plenty();
		this.road_building += other.getRoad_building();
	}
}
