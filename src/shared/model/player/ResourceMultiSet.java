package shared.model.player;
import shared.definitions.ResourceType;
/**
 * @author rscheuer
 * The ResourceCard class has cards that are available to be played
 */
public class ResourceMultiSet {
    public int brick;
    public int ore;
    public int sheep;
    public int wheat;
    public int wood;

    /**
     * Class constructor. Initializes each stack with 19 of its respective resource.
     */
    public ResourceMultiSet(){
        brick=0;
        wheat=0;
        ore=0;
        wood=0;
        sheep=0;
    }


    /**
     * Class constructor. Initializes each stack with the params sent in of its respective resource.
     * @param brick: the amount of brick cards the ResourceCard has.
     * @param wheat: the amount of wheat cards the ResourceCard has.
     * @param ore: the amount of ores cards the ResourceCard has.
     * @param wood: the amount of woods cards the ResourceCard has.
     * @param sheep: the amount of sheeps cards the ResourceCard has.
     */
    public ResourceMultiSet(int brick, int wheat, int ore, int wood, int sheep) {
        this.brick = brick;
        this.wheat = wheat;
        this.ore = ore;
        this.wood = wood;
        this.sheep = sheep;
    }


    /**
     * Check for amount of card type specified.
     * @pre none
     * @post returns boolean
     */
    public boolean hasSheep(int amt) {
        return sheep >= amt;
    }

    /**
     * Check for amount of card type specified.
     * @pre none
     * @post returns boolean
     */
    public boolean hasOre() {
        return hasOre();
    }

    /**
     * Check for amount of card type specified.
     * @pre none
     * @post returns boolean
     */
    public boolean hasOre(int amt) {
        if (ore >= amt) return true;
        else return false;
    }
    /**
     * Check for amount of card type specified.
     * @pre none
     * @post returns boolean
     */
    public boolean hasWood(int amt) {
        if ((wood >= amt)) return true;
        else return false;
    }
    /**
     * Check for amount of card type specified.
     * @param amt
     * @pre none
     * @post returns boolean
     */
    public boolean hasWheat(int amt) {
        if ((wheat >= amt)) return true;
        else return false;
    }
    /**
     * Check for amount of card type specified.
     * @param amt
     * @pre none
     * @post returns boolean
     */
    public boolean hasBrick(int amt) {
        if (brick >= amt) return true;
        else return false;
    }

    /**
     * Getter for brick
     * @pre none
     * @post returns the amount of brick the ResourceCard has
     */
    public int getBrick() {
        return brick;
    }

    /**
     * Setter for brick
     * @param brick: an integer representing how much brick the ResourceCard needs to have after the method is called
     * @pre none
     * @post sets the ResourceCards amount of bricks to the specified value given in the parameters
     */
    public void setBrick(int brick) {
        this.brick = brick;
    }

    /**
     * Getter for ore
     * @pre none
     * @post returns the amount of ore the ResourceCard has
     */
    public int getOre() {
        return ore;
    }

    /**
     * Setter for Ore
     * @param ore: an integer representing how much ore the ResourceCard needs to have after the method is called
     * @pre none
     * @post sets the ResourceCards amount of ore to the specified value given in the parameters
     */
    public void setOre(int ore) {
        
        
        this.ore = ore;
    }

    /**
     * Getter for sheep
     * @pre none
     * @post returns the amount of sheep the ResourceCard has
     */
    public int getSheep() {
        return sheep;
    }

    /**
     * Setter for sheep
     * @param sheep: an integer representing how much sheep the ResourceCard needs to have after the method is called
     * @pre none
     * @post sets the ResourceCards amount of sheep to the specified value given in the parameters
     */
    public void setSheep(int sheep) {
        this.sheep = sheep;
    }

    /**
     * Getter for wheat
     * @pre none
     * @post returns the amount of wheat the ResourceCard has
     */
    public int getWheat() {
        return wheat;
    }

    /**
     * Setter for wheat
     * @param wheat: an integer representing how much wheat the ResourceCard needs to have after the method is called
     * @pre none
     * @post sets the ResourceCards amount of wheat to the specified value given in the parameters
     */
    public void setWheat(int wheat) {
        
        
        this.wheat = wheat;
    }

    /**
     * Getter for wood
     * @pre none
     * @post returns the amount of wood the ResourceCard has
     */
    public int getWood() {
        return wood;
    }

    /**
     * Setter for wood
     * @param wood: an integer representing how much wood the ResourceCard needs to have after the method is called
     * @pre none
     * @post sets the ResourceCards amount of wood to the specified value given in the parameters
     */
    public void setWood(int wood) {
        
        this.wood = wood;
    }

    /**
     * This method checks whether the ResourceCard has enough resources as specified in the parameter
     * @param resource: resource is a class that has values for all five possible resources
     * @pre none
     * @post This method returns a boolean: true if the ResourceCard has the amount of resources specified, false if the ResourceCard does not have the amount of resources specified.
     */
    public boolean hasResource(ResourceType resource) {
        boolean hasResource = false;

        if (resource == ResourceType.BRICK) {
            if (getBrick() > 0)
                hasResource = true;

        } else if (resource == ResourceType.ORE) {
            if (getOre() > 0)
                hasResource = true;

        } else if (resource == ResourceType.SHEEP) {
            if (getSheep() > 0)
                hasResource = true;

        } else if (resource == ResourceType.WHEAT) {
            if (getWheat() > 0)
                hasResource = true;

        } else if (resource == ResourceType.WOOD) {
            if (getWood() > 0)
                hasResource = true;

        }

        return hasResource;
    }


	public boolean canAfford(ResourceMultiSet trade_in_cards) 
	{
		int other_wood = trade_in_cards.getWood();
		int other_ore = trade_in_cards.getOre();
		int other_brick = trade_in_cards.getBrick();
		int other_sheep = trade_in_cards.getSheep();
		int other_wheat = trade_in_cards.getWheat();
		boolean good_on_wood = (this.wood >= other_wood);
		boolean good_on_ore = (this.ore >= other_ore);
		boolean good_on_sheep = this.sheep >= other_sheep;
		boolean good_on_bricks = this.brick >= other_brick;
		boolean good_on_food = this.wheat >= other_wheat;
		return  good_on_wood && good_on_ore && good_on_sheep
				&& good_on_bricks && good_on_food;
		
		
	}


	public int size() {
		// TODO Auto-generated method stub
		return brick+ore+sheep+wheat+wood;
	}


	public int getAmount(ResourceType resource) 
	{
		switch (resource)
		{
		case BRICK:
			return brick;
		case ORE:
			return ore;
		case SHEEP:
			return sheep;
		case WHEAT:
			return wheat;
		case WOOD:
			return wood;
		default: return 0;
		}
	}

	/**
	 * Gets the total resources
	 * 
	 * @pre none
	 * @post the total cardinality of the multiset is returned
	 * @return
	 */
	public int total() 
	{
		if(brick+ore+sheep+wheat+wood < 0)return 0;
		return brick+ore+sheep+wheat+wood;
	}

	/**
	 * add a resource
	 * 
	 * gives a player a resource
	 * 
	 * @param resource
	 * @throws Exception
	 */
	public void add(ResourceType resource, int quantity) throws Exception 
	{
		switch (resource)
		{
		case BRICK:
			this.brick += quantity;
			break;
		case ORE:
			this.ore += quantity;
			break;
		case SHEEP:
			this.sheep += quantity;
			break;
		case WHEAT:
			this.wheat += quantity;
			break;
		case WOOD:
			this.wood += quantity;
			break;
		default: 
			throw new Exception("ERROR: Unsupported resource type");
		}
	}


	public void pay(ResourceType resource, int quantity) throws Exception 
	{
		switch (resource)
		{
		case BRICK:
			this.brick -= quantity;
			break;
		case ORE:
			this.ore -= quantity;
			break;
		case SHEEP:
			this.sheep -= quantity;
			break;
		case WHEAT:
			this.wheat -= quantity;
			break;
		case WOOD:
			this.wood -= quantity;
			break;
		default: 
			throw new Exception("ERROR: Unsupported resource type");
		}
	}



}
