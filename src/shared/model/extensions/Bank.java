package shared.model.extensions;

import shared.communication.ResourceList;

/**
 * Michael Rhodes
 * CS 340
 * Section 2
 * Team 10
 */
public class Bank extends ResourceList {
    public Bank(int brick, int ore, int sheep, int wheat, int wood) {
        super(brick, ore, sheep, wheat, wood);
    }

    @Override
    public int getBrick() {
        return super.getBrick();
    }

    @Override
    public void setBrick(int brick) {
        super.setBrick(brick);
    }

    @Override
    public int getOre() {
        return super.getOre();
    }

    @Override
    public void setOre(int ore) {
        super.setOre(ore);
    }

    @Override
    public int getSheep() {
        return super.getSheep();
    }

    @Override
    public void setSheep(int sheep) {
        super.setSheep(sheep);
    }

    @Override
    public int getWheat() {
        return super.getWheat();
    }

    @Override
    public void setWheat(int wheat) {
        super.setWheat(wheat);
    }

    @Override
    public int getWood() {
        return super.getWood();
    }

    @Override
    public void setWood(int wood) {
        super.setWood(wood);
    }
}
