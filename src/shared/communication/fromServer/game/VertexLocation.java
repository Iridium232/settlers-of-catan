package shared.communication.fromServer.game;

/**
 * Michael Rhodes
 * CS 340
 * Section 2
 * Team 10
 */
public class VertexLocation {
    String direction;
    int x;
    int y;


    
    public VertexLocation(String direction, int x, int y) {
        this.direction = direction;
        this.x = x;
        this.y = y;
    }
    
    public VertexLocation(shared.locations.VertexDirection direction, int x, int y)
    {
    	String direction_string = "";
    	switch (direction)
    	{
		case East:
			direction_string = "E";
			break;
		case NorthEast:
			direction_string = "NE";
			break;
		case NorthWest:
			direction_string = "NW";
			break;
		case SouthEast:
			direction_string = "SE";
			break;
		case SouthWest:
			direction_string = "SW";
			break;
		case West:
			direction_string = "W";
			break;
		default:
			direction_string = "ERROR";
			break;
    	
    	}
    	this.direction = direction_string;
    	this.x = x;
    	this.y = y;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
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
}
