package shared.communication;

import shared.locations.EdgeDirection;

/**
 * Michael Rhodes
 * CS 340
 * Section 2
 * Team 10
 */
public class EdgeLocation {
    int x;
    int y;
    String direction;

    public EdgeLocation(int x, int y, String direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public EdgeLocation(int x, int y, EdgeDirection dir) 
    {
		this.x = x;
		this.y = y;
		this.direction = dir.toString();
	}

	public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
